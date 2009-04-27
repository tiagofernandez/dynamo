package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when the User object has invalid data.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class InvalidUserException
		extends ServiceException {

	private static final long serialVersionUID = 7535292638644280119L;

	/**
	 * Constructor.
	 */
	public InvalidUserException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public InvalidUserException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public InvalidUserException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public InvalidUserException(Throwable ex) {
		super(ex);
	}

}