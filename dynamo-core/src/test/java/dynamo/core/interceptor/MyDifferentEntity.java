package dynamo.core.interceptor;

import javax.persistence.OneToOne;

/**
 * MyDifferentEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyDifferentEntity {

	@OneToOne(optional = false)
	private MyEntity requiredRelationship;
	
	public MyEntity getRequiredRelationship() {
		return requiredRelationship;
	}

	public void setRequiredRelationship(MyEntity requiredRelationship) {
		this.requiredRelationship = requiredRelationship;
	}
	
}