package dynamo.core.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

/**
 * Simple AOP profiler.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class SimpleAopProfiler {

	private static Log logger = LogFactory.getLog(SimpleAopProfiler.class);

   /**
    * Performs profiling for a given joint point.
    *
    * @param call the intercepted call
    * @return the proceeding call
    * @throws Throwable in
    */
	public Object profile(ProceedingJoinPoint call) throws Throwable {
		Object obj = null;
		StopWatch clock = new StopWatch();
		try {
			clock.start(call.toLongString());
			obj = call.proceed();
		}
		finally {
			clock.stop();
			logger.debug(getTarget(call) + " - " + clock.getTotalTimeSeconds() + " seconds");
		}
		return obj;
	}

	/* Gets the target. */
	private String getTarget(ProceedingJoinPoint call) {
		return getClassName(call) + "." + getMethodName(call) + getParameterNames(call);
	}

	/* Gets the class name. */
	private String getClassName(ProceedingJoinPoint call) {
		String target = call.getTarget().toString();
		int indexOfAt = target.indexOf('@');
		if (indexOfAt > -1) {
			target = target.substring(0, indexOfAt); // ignore "@..." part when present
		}
		return target;
	}

	/* Gets the method name. */
	private String getMethodName(ProceedingJoinPoint call) {
		MethodSignature signature = (MethodSignature) call.getSignature();
		return signature.getMethod().getName();
	}

	/* Gets the parameter names. */
	private String getParameterNames(ProceedingJoinPoint call) {		
		StringBuilder paramNames = new StringBuilder("(");
		
		// Read parameter types
		MethodSignature signature = (MethodSignature) call.getSignature();
		Method method = signature.getMethod();
		Class<?>[] paramTypes = method.getParameterTypes();
		
		// Append parameter names
		if (paramTypes != null) {			
			for (int i = 0; i < paramTypes.length; i++) {
				paramNames.append(paramTypes[i].getName());
				if (i < paramTypes.length - 1) {
					paramNames.append(", ");
				}
			}			
		}		
		// Make sure we close the parenthesis
		paramNames.append(")");
		
		return paramNames.toString();
	}

}