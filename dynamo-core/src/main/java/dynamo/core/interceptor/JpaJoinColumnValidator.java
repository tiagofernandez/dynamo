package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.JoinColumn;

/**
 * JpaJoinColumnValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaJoinColumnValidator
			extends JpaValidator {

	//private static Log logger = LogFactory.getLog(JpaJoinColumnValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		JoinColumn joinColumn = (JoinColumn) annotation;
		checkNullable(entity, field, joinColumn, exception);
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return JoinColumn.class;
	}
	
	void checkNullable(Object entity, Field field, JoinColumn annotation, Class<?> exception) throws Throwable {
		if (!annotation.nullable() && field.get(entity) == null) {
			StringBuilder errorMsg = new StringBuilder("Null field not allowed: ");
			errorMsg.append(field.getClass()).append(".").append(field.getName());
			throwException(exception, errorMsg.toString());
		}		
	}

}
