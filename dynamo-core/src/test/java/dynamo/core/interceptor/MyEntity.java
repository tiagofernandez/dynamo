package dynamo.core.interceptor;

import javax.persistence.Column;

/**
 * MyEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyEntity {

	@Column(nullable = false)
	private String notNullableField;
	
	public String getNotNullableField() {
		return notNullableField;
	}

	public void setNotNullableField(String notNullableField) {
		this.notNullableField = notNullableField;
	}
	
}