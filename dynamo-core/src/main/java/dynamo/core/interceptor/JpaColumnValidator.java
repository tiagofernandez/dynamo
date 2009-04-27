package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Column;

/**
 * JpaColumnValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaColumnValidator
			extends JpaValidator {

	//private static Log logger = LogFactory.getLog(JpaColumnValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		Column column = (Column) annotation;
		checkNullable(entity, field, column, exception);
		checkLength(entity, field, column, exception);
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return Column.class;
	}

	void checkNullable(Object entity, Field field, Column column, Class<?> exception) throws Throwable {
		if (!column.nullable() && field.get(entity) == null) {
			StringBuilder errorMsg = new StringBuilder("Null field not allowed: ");
			errorMsg.append(field.getClass()).append(".").append(field.getName());
			throwException(exception, errorMsg.toString());
		}		
	}

	void checkLength(Object entity, Field field, Column column, Class<?> exception) throws Throwable {		
		Object value = field.get(entity);
		
		// Only care about String values
		if (value instanceof String) {
			String strValue = (String) value;
			
			// Throw exception when the length is higher than expected
			if (strValue.length() > column.length()) {
				StringBuilder errorMsg = new StringBuilder("Field has more than ");
				errorMsg.append(column.length()).append(" characters: ");
				errorMsg.append(field.getClass()).append(".").append(field.getName());
				throwException(exception, errorMsg.toString());
			}
		}
	}

}
