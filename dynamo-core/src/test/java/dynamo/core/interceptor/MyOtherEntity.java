package dynamo.core.interceptor;

import javax.persistence.Column;

/**
 * MyOtherEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyOtherEntity {

	@Column(length = 5)
	private String tooManyCharactersField = "123456789";

	public String getTooManyCharactersField() {
		return tooManyCharactersField;
	}

	public void setTooManyCharactersField(String tooManyCharactersField) {
		this.tooManyCharactersField = tooManyCharactersField;
	}

}