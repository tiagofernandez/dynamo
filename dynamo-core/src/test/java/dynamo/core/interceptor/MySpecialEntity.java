package dynamo.core.interceptor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * MySpecialEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MySpecialEntity {

	@ManyToOne
	@JoinColumn(nullable = false)
	private MyEntity requiredRelationship;
	
	public MyEntity getRequiredRelationship() {
		return requiredRelationship;
	}

	public void setRequiredRelationship(MyEntity requiredRelationship) {
		this.requiredRelationship = requiredRelationship;
	}
	
}