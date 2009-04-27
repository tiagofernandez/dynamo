package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when attempting to create a User with an existing username.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class ExistingUsernameException
		extends ServiceException {

	private static final long serialVersionUID = -8903017781537868205L;

	/**
	 * Constructor.
	 */
	public ExistingUsernameException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public ExistingUsernameException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public ExistingUsernameException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public ExistingUsernameException(Throwable ex) {
		super(ex);
	}

}