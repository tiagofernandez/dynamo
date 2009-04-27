package dynamo.core.email;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Defines high-level operations for handling an e-mail connection.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
final class EmailConnection {

	private static Log logger = LogFactory.getLog(EmailConnection.class);

	private static final int FOLDER_OPENING_MODE = Folder.READ_WRITE;

	private String protocol;
	private String host;
	private String username;
	private String password;

	private Folder defaultFolder;
	private Folder inboxFolder;
	private Session session;
	private Store store;

	/**
	 * Constructor useful when using POP3 protocol.
	 * 
	 * @param host something like pop.xyz.com
	 * @param username for authenticating
	 * @param password for authenticating
	 */
	public EmailConnection(String host, String username, String password) {
		this("pop3", host, username, password);
	}

	/**
	 * Constructor useful when using another protocol.
	 * 
	 * @param protocol something like IMAP or POP3
	 * @param host something like pop.xyz.com
	 * @param username for authenticating
	 * @param password for authenticating
	 */
	public EmailConnection(String protocol, String host, String username, String password) {
		Assert.hasLength(protocol, "Protocol must not be blank");
		Assert.hasLength(host, "Host must not be blank");
		Assert.hasLength(username, "Username must not be blank");
		this.protocol = protocol;
		this.host = host;
		this.username = username;
		this.password = password;
	}

	/**
	 * Opens the connection.
	 * 
	 * @return true if the connection was successfully opened
	 */
	public boolean open() {
		boolean success = false;
		try {
			openSession();
			openStore();
			openDefaultFolder();
			openInboxFolder();
			success = true;
		}
		catch (MessagingException ex) {
			logger.error("Error while opening mailbox connection", ex);
		}
		return success;
	}

	/**
	 * Closes the connection.
	 * 
	 * @return true if the connection was successfully closed
	 */
	public boolean close() {
		boolean success = false;
		try {
			closeInboxFolder();
			closeDefaultFolder();
			closeStore();
			closeSession();
			success = true;
		}
		catch (MessagingException ex) {
			logger.error("Error while closing mailbox connection", ex);
		}
		return success;
	}

	/**
	 * Reopens the inbox.
	 * 
	 * @return the reopened inbox in R/W mode
	 * @throws MessagingException thrown if already opened, or is not initialized yet
	 */
   public Folder reopenInbox() throws MessagingException {
		if (inboxFolder != null && inboxFolder.exists() && !inboxFolder.isOpen()) {
			inboxFolder.open(FOLDER_OPENING_MODE);
		}
		return inboxFolder;
	}

   /**
    * Closes the inbox.
    */
	public void closeInbox() {
		try {
			if (inboxFolder != null && inboxFolder.isOpen()) {
				inboxFolder.close(true);
			}
		}
		catch (MessagingException ex) {
			logger.error("Error while closing mailbox", ex);
		}
	}

	/**
	 * Checks if the connection is done.
	 * 
	 * @return true if connected
	 */
	public boolean isConnected() {
		return store != null && store.isConnected();
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocol: ").append(protocol).append("\n");
		sb.append("Host: ").append(host).append("\n");
		sb.append("Username: ").append(username).append("\n");
		return sb.toString();
	}

	/* Opens the Session. */
	private void openSession() {
		if (session == null) {
			Properties properties = System.getProperties();
			session = Session.getInstance(properties, null);
		}
	}

	/* Opens the Store. */
	private void openStore() throws MessagingException {
		if (session == null) {
			openSession();
		}
		store = session.getStore(protocol);
		store.connect(host, username, password);
	}

	/* Opens the default folder. */
	private void openDefaultFolder() throws MessagingException {
		defaultFolder = store.getDefaultFolder();
		if (defaultFolder == null) {
			throw new MessagingException("Default folder cannot be found");
		}
	}
	
	/* Opens the inbox folder. */
	private void openInboxFolder() throws MessagingException {
		inboxFolder = defaultFolder.getFolder("INBOX");
		if (inboxFolder == null) {
			throw new MessagingException("Inbox folder cannot be found");
		}
	}	

	/* Closes the Session. */
	private void closeSession() {
		session = null;
	}

	/* Closes the Store. */
	private void closeStore() throws MessagingException {
		if (store != null) {
			store.close();
			store = null;
		}
	}

	/* Closes the default folder. */
	private void closeDefaultFolder() throws MessagingException {
		if (defaultFolder != null) {
			if (defaultFolder.isOpen()) {
				defaultFolder.close(true);
			}
			defaultFolder = null;
		}
	}

	/* Closes the inbox folder. */
	private void closeInboxFolder() throws MessagingException {
		if (inboxFolder != null) {
			if (inboxFolder.isOpen()) {
				inboxFolder.close(true);
			}
			inboxFolder = null;
		}
	}

}