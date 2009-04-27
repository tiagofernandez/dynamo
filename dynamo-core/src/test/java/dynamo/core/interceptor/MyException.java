package dynamo.core.interceptor;

/**
 * MyException.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyException
			extends Exception {

	private static final long serialVersionUID = -5611310248997778609L;

	public MyException() {
		super();
	}
	
	public MyException(String msg) {
		super(msg);
	}
	
	public MyException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public MyException(Throwable ex) {
		super(ex);
	}

}