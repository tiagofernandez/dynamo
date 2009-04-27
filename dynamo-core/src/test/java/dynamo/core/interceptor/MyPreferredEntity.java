package dynamo.core.interceptor;

import javax.persistence.Basic;

/**
 * MyPreferredEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyPreferredEntity {

	@Basic(optional = false)
	private String requiredField;
	
	public String getRequiredField() {
		return requiredField;
	}

	public void setRequiredField(String requiredField) {
		this.requiredField = requiredField;
	}
	
}