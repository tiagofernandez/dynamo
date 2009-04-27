package dynamo.core.interceptor;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 * MyRegularEntity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyRegularEntity {

	@ManyToOne
	@JoinColumns(value = { @JoinColumn(nullable = false) })
	private MyEntity requiredRelationship;
	
	public MyEntity getRequiredRelationship() {
		return requiredRelationship;
	}

	public void setRequiredRelationship(MyEntity requiredRelationship) {
		this.requiredRelationship = requiredRelationship;
	}
	
}