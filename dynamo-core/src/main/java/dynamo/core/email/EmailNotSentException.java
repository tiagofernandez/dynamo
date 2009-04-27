package dynamo.core.email;

/**
 * Exception thrown when an e-mail couldn't be sent.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class EmailNotSentException
			extends Exception {

	private static final long serialVersionUID = -824144071327282350L;

	/**
	 * Constructor.
	 */
	public EmailNotSentException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public EmailNotSentException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public EmailNotSentException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public EmailNotSentException(Throwable ex) {
		super(ex);
	}

}