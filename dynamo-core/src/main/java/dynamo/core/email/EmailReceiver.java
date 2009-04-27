package dynamo.core.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Provides an easy way to receive e-mails.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailReceiver {

	private static final int COOL_DOWN_INTERVAL_MIN = 2;
	private static final int MAX_CONNECTION_ATTEMPTS = 5;
	private static final int WATCHING_INTERVAL_SEC = 10;

	private static Log logger = LogFactory.getLog(EmailReceiver.class);

	private List<EmailReceiverListener> listeners = new ArrayList<EmailReceiverListener>();

	private EmailConnection connection;

	private Thread watcher;
	private boolean isWatching;

	/**
	 * Constructor useful when using POP3 protocol.
	 * 
	 * @param host something like pop3.xyz.com
	 * @param username the username (can't be blank)
	 * @param password the password
	 */
	public EmailReceiver(String host, String username, String password) {
		this(null, host, username, password);
	}

	/**
	 * Constructor useful when using another protocol.
	 * 
	 * @param protocol like IMAP or POP3
	 * @param host something like pop3.xyz.com
	 * @param username the username (can't be blank)
	 * @param password the password
	 */
	public EmailReceiver(String protocol, String host, String username, String password) {
		Assert.hasLength(host, "Host must not be blank");
		Assert.hasLength(username, "Username must not be blank");
		if (StringUtils.isBlank(protocol)) {
			connection = new EmailConnection(host, username, password);
		}
		else {
			connection = new EmailConnection(protocol, host, username, password);
		}
	}

	/**
	 * Adds an EmailReceiverListener.
	 * 
	 * @param listener to add
	 */
	public void addListener(EmailReceiverListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Removes an EmailReceiverListener.
	 * 
	 * @param listener to remove
	 */
	public void removeListener(EmailReceiverListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Sets the EmailReceiverListeners.
	 * 
	 * @param listeners to set
	 */
	public void setListeners(List<EmailReceiverListener> listeners) {
		if (listeners != null) {
			this.listeners = listeners;
		}
	}
	
	/**
	 * Gets the EmailReceiverListeners.
	 * 
	 * @return the listeners
	 */
	public List<EmailReceiverListener> getListeners() {
		return listeners;
	}
	
	/**
	 * Connects to server.
	 * 
	 * @return true if successfully connected
	 */
	public boolean connect() {
		boolean connected = false;
		
		// Try to open a connection
		connected = connection.open();
		int attempts = 1;
		
		// If we're not connected, we might give another shot
		while (!connected && attempts <= MAX_CONNECTION_ATTEMPTS) {
			logger.warn("Could not connect to " + connection.getHost() + ", trying later...");
			
			// Wait a while before retrying
			try {
				Thread.sleep(COOL_DOWN_INTERVAL_MIN * 60000);
			}
			catch (InterruptedException ex) {
				logger.error("Error while attempting connecting", ex);
			}
			
			// Retry one more time
			connected = connection.open();
			attempts++;
		}
		
		// Log the connection event (if it's the case)
		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Connection ").append(connected ? "successful" : "failed");
			sb.append("!\n").append(connection.toString());
			logger.info(sb.toString());
		}		
		return connected;
	}

	/**
	 * Disconnects from server.
	 * 
	 * @return true if successfully disconnected
	 */
	public boolean disconnect() {
		
		// Make sure we stop watching
		if (isWatching) {
			stopWatching();
		}
		
		// Close the connection
		return connection.close();
	}

	/**
	 * Starts watching for incoming messages.
	 */
	public void startWatching() {
		
		// Check whether we're connected
		if (connection != null && connection.isConnected()) {
			
			// Make sure we have a watcher ready-to-use
			if (watcher == null || !watcher.isAlive()) {
				watcher = new Thread(new EmailWatcher());
			}
			
			// Start the watcher thread
			watcher.start();
			isWatching = true;
		}
	}

	/**
	 * Stops watching for incoming messages.
	 */
	public void stopWatching() {
		isWatching = false;
		watcher = null;
	}

	/**
	 * Watchs the e-mail inbox, regarding a pre-defined time interval.
	 */
	private class EmailWatcher
				implements Runnable {

		public void run() {
			while (isWatching) {
				checkEmail();
				try {
					// Wait before checking again
					Thread.sleep(WATCHING_INTERVAL_SEC * 1000);
				}
				catch (InterruptedException ex) {
					logger.error("Error while watching mailbox", ex);
				}
			}
		}

	}

	/* Checks for incoming e-mail messages. */
	private void checkEmail() {
		try {
			// Don't do anything if we're not even connected
			if (connection.isConnected()) {
				
				// Get the inbox
				Folder inbox = connection.reopenInbox();
				
				// Check whether we have messages to read
				if (inbox != null && inbox.getMessageCount() > 0) {
					
					// Read all messages
					for (Message message : inbox.getMessages()) {
						
						// Converts Message to EmailInfo and notify the listeners
						EmailInfo email = toEmailInfo(message);
						notifyListeners(email);
						
						// Delete the message, so we don't read it again
						message.setFlag(Flags.Flag.DELETED, true);
					}
				}
			}
		}
		catch (IOException ex) {
			logger.error("I/O exception while checking mailbox", ex);
		}
		catch (MessagingException ex) {
			logger.error("Error while checking mailbox", ex);
		}
		finally {			
			// Close the connection anyways
			connection.closeInbox();
		}
	}

	/* Notifies the subscribers. */
	private void notifyListeners(EmailInfo email) {
		if (logger.isDebugEnabled()) {
			logger.debug("New message arrived!");
			logger.debug(email.toString());
		}
		for (EmailReceiverListener receiver : listeners) {
			receiver.emailReceived(email);
		}
	}

	/* Converts Message to EmailInfo object. */
	private EmailInfo toEmailInfo(Message message) throws MessagingException, IOException {
		EmailInfo emailInfo = new EmailInfo();
		
		// Import sent date, from and subject
		emailInfo.setCreationDate(message.getSentDate());
		emailInfo.setFrom(EmailUtils.getAddresses(message.getFrom()));
		emailInfo.setSubject(message.getSubject());
		
		// Read body and attachments
		readBody(message, emailInfo);
		readAttachments(message, emailInfo);
		
		// Import 'to' recipients
		Address[] toArray = message.getRecipients(Message.RecipientType.TO);
		if (toArray != null) {
			for (Address to : toArray) {
				emailInfo.addTo(to.toString());
			}
		}
		
		// Import 'cc' recipients
		Address[] ccArray = message.getRecipients(Message.RecipientType.CC);
		if (ccArray != null) {
			for (Address cc : ccArray) {
				emailInfo.addCc(cc.toString());
			}
		}
		
		// Import 'bcc' recipients
		Address[] bccArray = message.getRecipients(Message.RecipientType.BCC);
		if (bccArray != null) {
			for (Address bcc : bccArray) {
				emailInfo.addCc(bcc.toString());
			}
		}
		
		return emailInfo;
	}

	/* Reads message's body. */
	private void readBody(Message message, EmailInfo emailInfo) throws MessagingException, IOException {
		
		// Default values
		String body = "";
		boolean isHtml = false;
		
		// Get the content
		Object content = message.getContent();
		
		// Hanlde Multipart content
		if (content instanceof Multipart) {

			// Get the number of parts
			Multipart multipart = (Multipart) content;			
			int numberOfParts = multipart.getCount();
			
			// Do nothing if there is no part to read
			if (numberOfParts > 0) {
				
				// Read the body parts until we get a not blank one
				int partNumber = 0;
				boolean keepSearching = true;
				while (keepSearching && partNumber < numberOfParts) {
					
					// Read the current body part
					BodyPart bodyPart = multipart.getBodyPart(partNumber);
					body = readBodyPart(bodyPart);
					
					// Check if we're done
					if (StringUtils.isNotBlank(body)) {
						String contentType = bodyPart.getContentType();
						isHtml = StringUtils.containsIgnoreCase(contentType, "html");
						keepSearching = false;
					}
					partNumber++;
				}
			}
		}
		
		// Handle String content
		else if (content instanceof String) {
			body = (String) content;
		}
		
		// Finally import body data to EmailInfo
		emailInfo.setBody(body, isHtml);
	}

	/* Reads a regular body part. */
	private String readBodyPart(BodyPart bodyPart) throws MessagingException, IOException {
		String body = "";
		
		// If disposition is null, the body part is not an attachment
		if (bodyPart.getDisposition() == null) {
			
			// Get the mime body part content
			MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
			Object mbpContent = mimeBodyPart.getContent();
			
			// Ignore non-multipart content
			if (mbpContent instanceof Multipart) {
				Multipart multipart = (Multipart) mbpContent;
				
				// BUG 4827: http://java.sun.com/products/javamail/FAQ.html#unsupen
				if (multipart.getCount() > 0) {
					body = readEncodedBodyPart(multipart.getBodyPart(0));
				}
			}
		}
		return body;
	}

	/* Reads an encoded body part. */
	private String readEncodedBodyPart(BodyPart bodyPart) throws MessagingException, IOException {
		String body = "";
		
		// Ignore non-text mime types
		if (bodyPart.isMimeType("text/*")) {
			
			try {
				// Get body part string value
				Object bodyPartContent = bodyPart.getContent();
				if (bodyPartContent instanceof String) {
					body = (String) bodyPartContent;
				}
			}			
			// Force getting the body content in case of encoding errors
			catch (UnsupportedEncodingException ex) {				
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				bodyPart.writeTo(byteArray);
				body = byteArray.toString();
			}
		}
		
		return body;
	}
	
	/* Reads the attachments. */
	private void readAttachments(Message message, EmailInfo emailInfo) throws MessagingException, IOException {
		Object content = message.getContent();
		
		// Ignore non-multipart content
		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			
			// Iterate through multiparts
			for (int i = 0; i < multipart.getCount(); i++) {
				
				// Get the current body part and its disposition
				Part part = multipart.getBodyPart(i);
				String disposition = part.getDisposition();
				
				// Check whether it's a regular or inline attachment
				if (Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition)) {
					
					// Read part and add it as attachment
					byte[] body = toByteArray(part.getInputStream());
					if (body != null) {
						AttachmentInfo attachmentInfo = new AttachmentInfo();
						attachmentInfo.setName(part.getFileName());
						attachmentInfo.setBody(body);
						emailInfo.addAttachment(attachmentInfo);
					}
				}
			}
		}		
	}

	/* Reads a input stream. */
	private byte[] toByteArray(InputStream stream) {
		byte[] byteArray = null;
		try {
			byteArray = new byte[stream.available()];
			stream.read(byteArray);
		}
		catch (IOException ex) {
			logger.error("Error while reading input stream", ex);
		}
		return byteArray;
	}
	
}