package dynamo.core.interceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;

/**
 * MyMethodInvocation.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyMethodInvocation
			implements MethodInvocation {

	private Method method;
	private Object[] arguments;

	public MyMethodInvocation() {
		super();
	}

	public MyMethodInvocation(Method method, Object[] arguments) {
		this.method = method;
		if (arguments != null) {
			this.arguments = ArrayUtils.subarray(arguments, 0, arguments.length);
		}
	}

	public Object[] getArguments() {
		Object[] argsCopy = null;
		if (arguments != null) {
			argsCopy = ArrayUtils.subarray(arguments, 0, arguments.length);
		}
		return argsCopy;
	}

	public Method getMethod() {
		return method;
	}

	public AccessibleObject getStaticPart() {
		return null;
	}

	public Object getThis() {
		return null;
	}

	public Object proceed() throws Throwable {
		return null;
	}

}