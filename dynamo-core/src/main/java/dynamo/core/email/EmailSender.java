package dynamo.core.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.util.Assert;

/**
 * Provides an easy way to send e-mails.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailSender {

	private static Log logger = LogFactory.getLog(EmailSender.class);

	private String smtp;
	private String username;
	private String password;
	private boolean requiresAuth;
	
	/**
	 * Constructor used when the SMTP server doesn't require authentication.
	 * 
	 * @param smtp something like smtp.xyz.com
	 */
	public EmailSender(String smtp) {
		Assert.hasLength("SMTP must not be blank");
		this.smtp = smtp;
	}

	/**
	 * Constructor used when the SMTP server requires authentication.
	 * 
	 * @param smtp something like smtp.xyz.com
	 * @param username the username (can't be blank)
	 * @param password the password
	 */
	public EmailSender(String smtp, String username, String password) {
		this(smtp);
		Assert.hasLength("Username must not be blank");
		this.requiresAuth = true;
		this.username = username;
		this.password = password;
	}

	/**
	 * Sends an e-mail.
	 * 
	 * @param emailInfo the e-mail to send
	 * @throws EmailNotSentException when the e-mail could not be delivered
	 */
	public void sendEmail(EmailInfo emailInfo) throws EmailNotSentException {
		try {
			Assert.notNull(emailInfo, "EmailInfo must not be null");
			logger.info("Sending e-mail...");
			
			// Convert EmailInfo to MultiPartEmail
			MultiPartEmail multiPartEmail = toMultiPartEmail(emailInfo);
			
			// Connection settings
			multiPartEmail.setHostName(smtp);
			if (requiresAuth) {
				multiPartEmail.setAuthentication(username, password);
			}
			
			// Send the message
			multiPartEmail.send();
			logger.info("E-mail successfully delivered!");
			logger.info(emailInfo.toString());
		}
		
		// Check the e-mail was successfully sent
		catch (EmailException ex) {
			logger.error("E-mail not delivered");
			throw new EmailNotSentException("Error while sending e-mail", ex);
		}
	}

	/* Converts EmailEmailInfo to MultiPartEmail. */
	private MultiPartEmail toMultiPartEmail(EmailInfo emailInfo) throws EmailException {
		
		// By default we use an HtmlEmail type
		HtmlEmail email = new HtmlEmail();
		
		// Import sender address and subject
		email.setFrom(emailInfo.getFrom());
		email.setSubject(emailInfo.getSubject());
		
		// Set the HTML or text content
		if (emailInfo.isHtml()) {
			email.setHtmlMsg(emailInfo.getBody());
		}
		else {
			email.setTextMsg(emailInfo.getBody());
		}
		
		// Import recipients
		for (String to : emailInfo.getTo()) {
			email.addTo(to);
		}
		for (String cc : emailInfo.getCc()) {
			email.addCc(cc);
		}
		for (String bcc : emailInfo.getBcc()) {
			email.addBcc(bcc);
		}
		
		// Import attachments
		for (AttachmentInfo attachment : emailInfo.getAttachments()) {
			EmailAttachment emailAttachment = new EmailAttachment();
			emailAttachment.setName(attachment.getName());
			emailAttachment.setURL(attachment.getPath());
			emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
			email.attach(emailAttachment);
		}
		
		return email;
	}
	
}