package dynamo.core.interceptor;

import org.hibernate.validator.Email;

/**
 * MyHibernateEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.4
 */
public class MyHibernateEntity {

	@Email
	private String email = "invalid at gmail dot com";
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}