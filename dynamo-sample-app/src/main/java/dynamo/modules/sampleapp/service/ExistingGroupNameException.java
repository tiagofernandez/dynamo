package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when attempting to create a Group with an existing name.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class ExistingGroupNameException
		extends ServiceException {

	private static final long serialVersionUID = 2037243339485912681L;

	/**
	 * Constructor.
	 */
	public ExistingGroupNameException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public ExistingGroupNameException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public ExistingGroupNameException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public ExistingGroupNameException(Throwable ex) {
		super(ex);
	}

}