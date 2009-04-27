package dynamo.core.template;

/**
 * Exception thrown when a template can't be applied.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class TemplateException
			extends Exception {

	private static final long serialVersionUID = -5631837379036208923L;

	/**
	 * Constructor.
	 */
	public TemplateException() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public TemplateException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public TemplateException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public TemplateException(Throwable ex) {
		super(ex);
	}

}