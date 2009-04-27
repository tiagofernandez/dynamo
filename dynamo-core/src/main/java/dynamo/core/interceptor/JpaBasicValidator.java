package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Basic;

/**
 * JpaBasicValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaBasicValidator
			extends JpaValidator {

	//private static Log logger = LogFactory.getLog(JpaBasicValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		Basic basic = (Basic) annotation;
		checkOptional(entity, field, basic, exception);
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return Basic.class;
	}

	void checkOptional(Object entity, Field field, Basic basic, Class<?> exception) throws Throwable {
		if (!basic.optional() && field.get(entity) == null) {
			StringBuilder errorMsg = new StringBuilder("Required field isn't supposed to be null: ");
			errorMsg.append(field.getClass()).append(".").append(field.getName());
			throwException(exception, errorMsg.toString());
		}		
	}

}
