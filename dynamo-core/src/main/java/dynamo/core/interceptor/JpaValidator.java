package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides validation capabilities to JPA annotated fields.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 * @see dynamo.core.interceptor.JpaEntityValidation
 */
public abstract class JpaValidator {

	private static Log logger = LogFactory.getLog(JpaValidator.class);

	/**
	 * Validates a persistent field.
	 * 
	 * @param entity the entity to validate
	 * @param field the field containing the constraints
	 * @param exception to throw whether the field is not valid
	 * @throws Throwable the exception thrown when the field doesn't respect the constraints
	 */
	public void validate(Object entity, Field field, Class<?> exception) throws Throwable {
		try {
			Annotation annotation = getAnnotation(field);
			if (annotation != null) {
				field.setAccessible(true); // so we can get private fields...
				validate(entity, field, annotation, exception);
			}			
		}
		catch (IllegalAccessException ex) {
			logger.error("Error while evaluating field: " + field.getClass() + "." + field.getName(), ex);
		}
	}
	
	/**
	 * Validates a persistent field.
	 * 
	 * @param entity the entity to validate
	 * @param field the field containing the constraints
	 * @param annotation the annotation already bound
	 * @param exception to throw whether the field is not valid
	 * @throws Throwable the exception thrown when the field doesn't respect the constraints
	 */
	protected abstract void validate(Object entity, Field field, Annotation annotation, Class<?> exception)
		throws Throwable;

	/**
	 * Gets the specific annotation class.
	 * 
	 * @return the target annotation
	 */
	protected abstract Class<? extends Annotation> getAnnotationClass();

	/**
	 * Gets the annotation handled by this validator, when it's present in the field.
	 * 
	 * @param field the field to check
	 * @return the specific annotation, or null if isn't present
	 */
	protected Annotation getAnnotation(Field field) {
		Annotation annotation = null;
		Class<? extends Annotation> annotationClass = getAnnotationClass();
		if (field.isAnnotationPresent(annotationClass)) {
			annotation = field.getAnnotation(annotationClass);
		}
		return annotation;
	}
	
	/**
	 * Throws an exception regarding the error message.
	 * 
	 * @param exception to throw whether the field is not valid
	 * @param errorMessage to be wrapped within an exception
	 * @throws Throwable the exception thrown when the field doesn't respect the constraints
	 */
	protected void throwException(Class<?> exception, String errorMessage) throws Throwable {
		try {
			// Get a String-based constructor for the exception class
			Constructor<?> constructor = getStringConstructor(exception);
			
			// Instantiate it and throw the exception
			if (constructor != null) {
				throw (Throwable) constructor.newInstance(errorMessage);
			}
			
			// If we got here, an exception without any error message will be thrown
			throw (Throwable) exception.newInstance();
		}
		catch (InstantiationException ex) {
			logger.error("Error while instantiating class " + exception, ex);
			throw new JpaEntityInvalidException(errorMessage);
		}
		catch (InvocationTargetException ex) {
			logger.error("Error while invocating target exception for class " + exception, ex);
			throw new JpaEntityInvalidException(errorMessage);
		}
	}

	/* Gets the String constructor for Class. */
	private Constructor<?> getStringConstructor(Class<?> clazz) {
		Constructor<?> constructor = null;
		try {
			constructor = clazz.getConstructor(String.class);
		}
		catch (NoSuchMethodException ex) {
			logger.debug("Constructor with String argument not found in class " + clazz, ex);
		}
		return constructor;
	}

}