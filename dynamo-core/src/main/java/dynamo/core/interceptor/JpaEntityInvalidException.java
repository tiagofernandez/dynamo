package dynamo.core.interceptor;

/**
 * Exception thrown when an entity is not correctly filled (e.g. not-null constraints are not respected).
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaEntityInvalidException
		extends Exception {

	private static final long serialVersionUID = -7368551002524893216L;

	/**
	 * Constructor.
	 */
	public JpaEntityInvalidException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public JpaEntityInvalidException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public JpaEntityInvalidException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public JpaEntityInvalidException(Throwable ex) {
		super(ex);
	}

}