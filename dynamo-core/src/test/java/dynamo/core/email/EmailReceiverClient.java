package dynamo.core.email;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test client for EmailReceiver.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailReceiverClient
			implements EmailReceiverListener {

	private static Log logger = LogFactory.getLog(EmailReceiverClient.class);
	
	private EmailReceiverClient() {
		super();
	}
	
	/**
	 * Startup method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EmailReceiverClient emailReceiverClient = new EmailReceiverClient();
		EmailReceiver emailReceiver = new EmailReceiver("pop.yahoo.com", "login", "password");
		emailReceiver.addListener(emailReceiverClient);
		emailReceiver.connect();
		emailReceiver.startWatching();
	}

	/**
	 * @see dynamo.core.email.EmailReceiverListener#emailReceived(dynamo.core.email.EmailInfo)
	 */
	public void emailReceived(EmailInfo message) {
		logger.info("Message:\n" + message.toString());
		logger.info("Body:\n" + message.getBody());
		List<AttachmentInfo> attachments = message.getAttachments();
		if (attachments != null && attachments.size() > 0) {
			StringBuilder buffer = new StringBuilder("Attachments:\n");
			for (AttachmentInfo attachment : attachments) {
				buffer.append(attachment.getName() + ", size = " + attachment.getBody().length + " bytes\n");
			}
			logger.info(buffer.toString());
		}
	}

}