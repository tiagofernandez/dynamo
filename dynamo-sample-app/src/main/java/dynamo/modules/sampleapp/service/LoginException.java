package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when the username and/or password are/is invalid.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class LoginException
		extends ServiceException {

	private static final long serialVersionUID = 3431516158505614809L;

	/**
	 * Constructor.
	 */
	public LoginException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public LoginException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public LoginException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public LoginException(Throwable ex) {
		super(ex);
	}

}