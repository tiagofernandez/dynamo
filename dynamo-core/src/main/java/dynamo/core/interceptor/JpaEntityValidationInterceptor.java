package dynamo.core.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.AopUtils;

/**
 * Provides JPA-based validation for EJB3 entities, when the intercepted method is annotated with
 * JpaEntityValidation annotation and the JPA annotations are put in fields level (not in getters).
 * 
 * @author Tiago Fernandez
 * @since 0.1
 * @see dynamo.core.interceptor.JpaValidator
 * @see dynamo.core.interceptor.JpaEntityValidation
 * @see dynamo.core.interceptor.JpaEntityValidationUtils
 */
public class JpaEntityValidationInterceptor
			implements MethodInterceptor {

	private List<JpaValidator> defaultValidators = new ArrayList<JpaValidator>();
	
	/**
	 * Constructor.
	 */
	public JpaEntityValidationInterceptor() {
		defaultValidators.add(new JpaBasicValidator());
		defaultValidators.add(new JpaColumnValidator());
		defaultValidators.add(new JpaJoinColumnsValidator());
		defaultValidators.add(new JpaJoinColumnValidator());
		defaultValidators.add(new JpaManyToOneValidator());
		defaultValidators.add(new JpaOneToOneValidator());
	}
	
	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {		
		
		// Get @JpaEntityValidation when it's present
		JpaEntityValidation annotation = getJpaEntityValidationAnnotation(methodInvocation);
		
		if (annotation != null) {
			
			// Get attributes
			Class<?>[] targets = annotation.targets();
			Class<? extends JpaValidator>[] customValidators = annotation.customValidators();
			Class<?> exception = annotation.exception();
			
			if (targets != null) {

				// Include custom validators
				List<JpaValidator> validators = new ArrayList<JpaValidator>(defaultValidators);
				if (customValidators != null) {
					for (Class<? extends JpaValidator> customValidator : customValidators) {
						validators.add(customValidator.newInstance());
					}
				}

				// Get method arguments
				Object[] arguments = methodInvocation.getArguments();
				
				// Perform validation for each target entity
				for (Class<?> entity : targets) {
					List<?> args = getArgumentsForClass(entity, arguments);
					for (Iterator<?> iterator = args.iterator(); iterator.hasNext();) {
						Object entityArgument = iterator.next();
						validateFields(entityArgument, exception, validators);
					}
				}				
			}			
		}
		
		return methodInvocation.proceed();
	}

	/* Verifies whether the JpaEntityValidation annotation is present and get it if so. */
	private JpaEntityValidation getJpaEntityValidationAnnotation(MethodInvocation methodInvocation) throws Throwable {
		
		JpaEntityValidation annotation = null;
		Method method = methodInvocation.getMethod();
		
		// Try to read the annotation directly from the method
		if (method.isAnnotationPresent(JpaEntityValidation.class)) {
			annotation = method.getAnnotation(JpaEntityValidation.class);
		}
		
		// If it's a RelfectiveMethodInvocation it means we're facing a Spring Proxy
		else if (methodInvocation instanceof ReflectiveMethodInvocation) {
			ReflectiveMethodInvocation reflectiveInvocation = (ReflectiveMethodInvocation) methodInvocation;
			Object proxy = reflectiveInvocation.getThis();
			Class<?> targetClass = AopUtils.getTargetClass(proxy);
			Method proxyMethod = targetClass.getMethod(method.getName(), method.getParameterTypes());
			if (proxyMethod.isAnnotationPresent(JpaEntityValidation.class)) {
				annotation = proxyMethod.getAnnotation(JpaEntityValidation.class);
			}
		}
		
		return annotation;
	}

	/* Regarding the method's args, gets the ones matching the given class. */
	private List<?> getArgumentsForClass(Class<?> clazz, Object[] args) {
		
		List<Object> list = new ArrayList<Object>();

		// Iterate through the arguments
		if (args != null) {
			for (Object obj : args) {
				
				// Ignore null values
				if (obj == null) {
					continue;
				}
				
				// Check if the arg is an instance of given class
				if (clazz.equals(obj.getClass())) {
					list.add(obj);
				}
			}
		}
		
		return list;
	}

	/* Iterate through entity's fields and check value's integrity. */
	private void validateFields(Object entity, Class<?> exception, List<JpaValidator> validators) throws Throwable {
		
		// Get the declared fields
		List<Field> fields = getDeclaredFields(entity.getClass());
		
		// Perform validation for each one of them
		if (fields != null) {
			for (Field field : fields) {
				for (JpaValidator validator : validators) {
					validator.validate(entity, field, exception);
				}
			}
		}
		
	}
	
	/* Gets declared fields, including the superclasse's ones. */
	private List<Field> getDeclaredFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		
		// Check superclasse's fields
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null) {
			List<Field> superclassFields = getDeclaredFields(superclass);
			fieldList.addAll(superclassFields);
		}
		
		// Get the current classe's fields
		fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return fieldList;
	}

}