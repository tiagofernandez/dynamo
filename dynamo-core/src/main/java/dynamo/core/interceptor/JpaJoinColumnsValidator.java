package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;

/**
 * JpaJoinColumnValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaJoinColumnsValidator
			extends JpaJoinColumnValidator {

	//private static Log logger = LogFactory.getLog(JpaJoinColumnsValidator.class);
	
	/**
	 * @see dynamo.core.interceptor.JpaValidator#validate(Object, Field, Annotation, Class)
	 */
	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		JoinColumns joinColumns = (JoinColumns) annotation;
		JoinColumn[] joinColumnArray = joinColumns.value();
		if (joinColumnArray != null) {
			for (JoinColumn joinColumn : joinColumnArray) {
				super.checkNullable(entity, field, joinColumn, exception);
			}
		}
	}

	/**
	 * @see dynamo.core.interceptor.JpaValidator#getAnnotationClass()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return JoinColumns.class;
	}
	
}
