package dynamo.modules.sampleapp.service;

import dynamo.core.service.ServiceException;

/**
 * Exception thrown when the Group object has invalid data.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class InvalidGroupException
		extends ServiceException {

	private static final long serialVersionUID = -6365544837720587968L;

	/**
	 * Constructor.
	 */
	public InvalidGroupException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public InvalidGroupException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public InvalidGroupException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public InvalidGroupException(Throwable ex) {
		super(ex);
	}

}