package dynamo.modules.sampleapp.entity;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MyGroup entity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
@Entity
@Table(name = "MY_GROUP")
public class MyGroup
			extends MyGroupBase {

	/**
	 * @param user to add
	 */
	public void addUser(MyUser user) {
		if (users == null) {
			users = new HashSet<MyUser>();
		}
		users.add(user);
	}

	/**
	 * @param user to remove
	 */
	public void removeUser(MyUser user) {
		if (users != null) {
			users.remove(user);
		}
	}
	
}