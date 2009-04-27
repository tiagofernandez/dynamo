package dynamo.core.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dynamo.core.io.ResourceLoader;

/**
 * Test client for EmailSender.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailSenderClient {

	private static Log logger = LogFactory.getLog(EmailSenderClient.class);
	
	private EmailSenderClient() {
		super();
	}
	
	/**
	 * Startup method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			EmailSender emailSender = new EmailSender("smtp.yahoo.com", "login", "password");
			emailSender.sendEmail(buildEmailInfo());
		}
		catch (EmailNotSentException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	private static EmailInfo buildEmailInfo() {
		AttachmentInfo attachment = new AttachmentInfo();
		attachment.setName("France");
		attachment.setPath(ResourceLoader.getResourceAsUrl("dynamo/core/email/France.gif"));
		EmailInfo emailInfo = new EmailInfo();
		emailInfo.setBody(ResourceLoader.getResourceAsString("dynamo/core/email/Address.htm"), true);
		emailInfo.addTo("tiago182@gmail.cmo");
		emailInfo.setFrom("login@yahoo.com");
		emailInfo.setSubject("EmailSender Test");
		emailInfo.addAttachment(attachment);
		return emailInfo;
	}

}