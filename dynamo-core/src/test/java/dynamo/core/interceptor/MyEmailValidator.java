package dynamo.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.hibernate.validator.Email;
import org.hibernate.validator.EmailValidator;

/**
 * MyEmailValidator.
 * 
 * @author Tiago Fernandez
 * @since 0.4
 */
public class MyEmailValidator
			extends JpaValidator {

	@Override
	public void validate(Object entity, Field field, Annotation annotation, Class<?> exception) throws Throwable {
		Email column = (Email) annotation;
		checkEmail(entity, field, column, exception);
	}

	@Override
	protected Class<? extends Annotation> getAnnotationClass() {
		return Email.class;
	}

	void checkEmail(Object entity, Field field, Email column, Class<?> exception) throws Throwable {
		Object value = field.get(entity);
		EmailValidator validator = new EmailValidator();
		validator.initialize(column);

		if (!validator.isValid(value)) {
			throwException(exception, "Invalid e-mail address");
		}
	}
}
