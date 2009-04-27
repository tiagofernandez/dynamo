package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when attempting to create a User with an existing e-mail.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class ExistingEmailException
		extends ServiceException {

	private static final long serialVersionUID = -3073839622422035078L;

	/**
	 * Constructor.
	 */
	public ExistingEmailException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public ExistingEmailException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public ExistingEmailException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public ExistingEmailException(Throwable ex) {
		super(ex);
	}

}