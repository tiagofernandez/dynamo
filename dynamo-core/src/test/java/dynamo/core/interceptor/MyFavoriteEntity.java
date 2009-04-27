package dynamo.core.interceptor;

import javax.persistence.ManyToOne;

/**
 * MyFavoriteEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyFavoriteEntity {

	@ManyToOne(optional = false)
	private MyEntity requiredRelationship;
	
	public MyEntity getRequiredRelationship() {
		return requiredRelationship;
	}

	public void setRequiredRelationship(MyEntity requiredRelationship) {
		this.requiredRelationship = requiredRelationship;
	}
	
}