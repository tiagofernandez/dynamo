package dynamo.plugins.maven.output;

/**
 * Generic exception thrown by CodeGenerator.
 * 
 * @author Tiago Fernandez
 * @since 0.3
 */
public class CodeGeneratorException
			extends Exception {

	private static final long serialVersionUID = -719500134980970231L;

	/**
	 * Constructor.
	 */
	public CodeGeneratorException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 */
	public CodeGeneratorException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message describing the exception
	 * @param ex the wrapped exception
	 */
	public CodeGeneratorException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * Constructor.
	 * 
	 * @param ex the wrapped exception
	 */
	public CodeGeneratorException(Throwable ex) {
		super(ex);
	}

}