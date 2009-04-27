package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.OneToOne;

/**
 * JpaManyToOneValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaOneToOneValidator
			extends JpaValidator {

	//private static Log logger = LogFactory.getLog(JpaOneToOneValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		OneToOne oneToOne = (OneToOne) annotation;
		checkOptional(entity, field, oneToOne, exception);
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return OneToOne.class;
	}

	void checkOptional(Object entity, Field field, OneToOne oneToOne, Class<?> exception) throws Throwable {
		if (!oneToOne.optional() && field.get(entity) == null) {
			StringBuilder errorMsg = new StringBuilder("Required field isn't supposed to be null: ");
			errorMsg.append(field.getClass()).append(".").append(field.getName());
			throwException(exception, errorMsg.toString());
		}		
	}

}
