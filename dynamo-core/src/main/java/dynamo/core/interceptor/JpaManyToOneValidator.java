package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.ManyToOne;

/**
 * JpaManyToOneValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaManyToOneValidator
			extends JpaValidator {

	//private static Log logger = LogFactory.getLog(JpaManyToOneValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		ManyToOne manyToOne = (ManyToOne) annotation;
		checkOptional(entity, field, manyToOne, exception);
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return ManyToOne.class;
	}

	void checkOptional(Object entity, Field field, ManyToOne manyToOne, Class<?> exception) throws Throwable {
		if (!manyToOne.optional() && field.get(entity) == null) {
			StringBuilder errorMsg = new StringBuilder("Required field isn't supposed to be null: ");
			errorMsg.append(field.getClass()).append(".").append(field.getName());
			throwException(exception, errorMsg.toString());
		}		
	}

}
