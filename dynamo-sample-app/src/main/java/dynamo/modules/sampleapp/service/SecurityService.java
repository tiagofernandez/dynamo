package dynamo.modules.sampleapp.service;

import java.util.List;

import dynamo.modules.sampleapp.entity.MyGroup;
import dynamo.modules.sampleapp.entity.MyRole;
import dynamo.modules.sampleapp.entity.MyUser;
import dynamo.core.service.Service;

/**
 * Services related to security.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public interface SecurityService
			extends Service {

	/**
	 * Deletes a group.
	 * 
	 * @param id the group's ID
	 */
	void deleteGroup(Long id);

	/**
	 * Deletes an user.
	 * 
	 * @param id the user's ID
	 */
	void deleteUser(Long id);

	/**
	 * Gets a group by ID.
	 * 
	 * @param name the group's ID
	 * @return the group object
	 */
	MyGroup getGroupById(Long id);

	/**
	 * Gets a group by name.
	 * 
	 * @param name the group name
	 * @return the group object
	 */
	MyGroup getGroupByName(String name);
	
	/**
	 * Gets an user by email.
	 * 
	 * @param email the e-mail
	 * @return the user object
	 */
	MyUser getUserByEmail(String email);

	/**
	 * Gets an user by ID.
	 * 
	 * @param id the user's ID
	 * @return the user object
	 */
	MyUser getUserById(Long id);

	/**
	 * Gets an user by username.
	 * 
	 * @param username the user name
	 * @return the user object
	 */
	MyUser getUserByUsername(String username);

	/**
	 * Lists all groups.
	 * 
	 * @return the group list
	 */
	List<MyGroup> listGroups();

	/**
	 * Lists all users.
	 * 
	 * @return the user list
	 */
	List<MyUser> listUsers();

	/**
	 * Lists users by role.
	 * 
	 * @param role the role type
	 * @return the user list
	 */
	List<MyUser> listUsersByRole(MyRole role);

	/**
	 * Performs login with username and password.
	 * 
	 * @param username the user name
	 * @param password the password
	 * @return the user object
	 * @throws LoginException when the username and/or password are/is invalid
	 */
	MyUser login(String username, String password) throws LoginException;

	/**
	 * Saves or updates a group.
	 * 
	 * @param group to persist
	 * @return the saved group
	 * @throws InvalidGroupException when the user object has invalid or missing data
	 * @throws ExistingGroupNameException when attempting to save group with an existing name 
	 */
	MyGroup saveGroup(MyGroup group) throws InvalidGroupException, ExistingGroupNameException;

	/**
	 * Saves or updates an user.
	 * 
	 * @param user to persist
	 * @return the saved user
	 * @throws InvalidUserException when the user object has invalid or missing data
	 * @throws ExistingEmailException when attempting to save user with an existing e-mail 
	 * @throws ExistingUsernameException when attempting to save user with an existing username
	 */
	MyUser saveUser(MyUser user) throws InvalidUserException, ExistingEmailException, ExistingUsernameException;

}