package dynamo.core.email;

/**
 * Listens to EmailReceiver.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public interface EmailReceiverListener {

	/**
	 * Callback method for e-mail receiving events.
	 * 
	 * @param emailInfo the recently received e-mail
	 */
	void emailReceived(EmailInfo emailInfo);
	
}