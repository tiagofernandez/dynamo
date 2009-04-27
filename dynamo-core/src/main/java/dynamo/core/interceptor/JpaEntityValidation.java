package dynamo.core.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for advising what entities should be validated.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JpaEntityValidation {

	/**
	 * @return the entities to validate
	 */
	Class<?>[] targets();
	
	/**
	 * @return the custom JpaValidators
	 */
	Class<? extends JpaValidator>[] customValidators() default {};
	
	/**
	 * @return the exception to be thrown in case of errors
	 */
	Class<?> exception() default JpaEntityInvalidException.class;
	
}