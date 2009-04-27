package dynamo.modules.sampleapp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dynamo.modules.sampleapp.entity.MyGroup;
import dynamo.modules.sampleapp.entity.MyRole;
import dynamo.modules.sampleapp.entity.MyUser;
import dynamo.modules.sampleapp.repository.MyGroupRepository;
import dynamo.modules.sampleapp.repository.MyUserRepository;
import dynamo.core.interceptor.JpaEntityValidation;
import dynamo.core.service.ServiceException;

/**
 * Default SecurityService implementation.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
@Transactional(rollbackFor = { ServiceException.class })
public final class SecurityServiceImpl
			implements SecurityService {

	private static Log logger = LogFactory.getLog(SecurityServiceImpl.class);
	
	private MyGroupRepository groupRepo;
	private MyUserRepository userRepo;

	/**
	 * Sets the administration console entity manager.
	 * 
	 * @param em the entity manager to set
	 */
	@PersistenceContext(unitName = "sampleapp")
	public void setSampleAppEntityManager(EntityManager em) {
		groupRepo = new MyGroupRepository(em);
		userRepo = new MyUserRepository(em);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#deleteGroup(Long)
	 */
	public void deleteGroup(Long id) {
		Assert.notNull(id, "ID must not be null");
		logger.info("Deleting Group [id=" + id + "]");
		groupRepo.removeEntity(id);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#deleteUser(Long)
	 */
	public void deleteUser(Long id) {
		Assert.notNull(id, "ID must not be null");
		logger.info("Deleting User [id=" + id + "]");
		userRepo.removeEntity(id);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#getGroupById(Long)
	 */
	public MyGroup getGroupById(Long id) {
		Assert.notNull(id, "ID must not be null");
		logger.info("Getting Group [id=" + id + "]");
		return groupRepo.getById(id);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#getGroupByName(String)
	 */
	public MyGroup getGroupByName(String name) {
		Assert.hasLength(name, "Group name must not be blank");
		logger.info("Getting Group [name=" + name + "]");
		return groupRepo.getByName(name);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#getUserByEmail(String)
	 */
	public MyUser getUserByEmail(String email) {
		Assert.hasLength(email, "E-mail must not be blank");
		logger.info("Getting User [email=" + email + "]");
		return userRepo.getByEmail(email);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#getUserById(Long)
	 */
	public MyUser getUserById(Long id) {
		Assert.notNull(id, "ID must not be null");
		logger.info("Getting User [id=" + id + "]");
		return userRepo.getById(id);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#getUserByUsername(String)
	 */
	public MyUser getUserByUsername(String username) {
		Assert.hasLength(username, "Username must not be blank");
		logger.info("Getting User [username=" + username + "]");
		return userRepo.getByUsername(username);
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#listGroups()
	 */
	public List<MyGroup> listGroups() {
		logger.info("Listing Groups");
		return groupRepo.list();
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#listUsers()
	 */
	public List<MyUser> listUsers() {
		logger.info("Listing Users");
		return userRepo.list();
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#listUsersByRole(Role)
	 */
	public List<MyUser> listUsersByRole(MyRole role) {
		Assert.notNull(role, "Role must not be null");
		logger.info("Listing Users [role=" + role + "]");
		return userRepo.listByRole(role);
	}
	
	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#login(String, String)
	 */
	public MyUser login(String username, String password) throws LoginException {
		MyUser user = getUserByUsername(username);
		if (user == null || !user.checkPassword(password)) {
			throw new LoginException("Invalid username and/or password");
		}
		return user;
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#saveGroup(Group)
	 */
	@JpaEntityValidation(targets = { MyGroup.class }, exception = InvalidGroupException.class)
	public MyGroup saveGroup(MyGroup group) throws ExistingGroupNameException {
		Assert.notNull(group, "Group must not be null");
		
		// Check whether the given group name is already being used
		MyGroup groupByName = getGroupByName(group.getName());
		if (groupByName != null && !groupByName.getId().equals(group.getId())) {
			throw new ExistingGroupNameException("Group name already being used: " + group.getName());
		}
		
		// Handles create operation
		if (group.getId() == null) {
			logger.info("Saving Group [name=" + group.getName() + "]");
			groupRepo.persistEntity(group);
		}
		// Handles update operation
		else {
			logger.info("Updating Group [name=" + group.getName() + "]");
			groupRepo.mergeEntity(group);
		}
		
		return group;
	}

	/**
	 * @see dynamo.modules.sampleapp.service.SecurityService#saveUser(User)
	 */
	@JpaEntityValidation(targets = { MyUser.class }, exception = InvalidUserException.class)
	public MyUser saveUser(MyUser user) throws ExistingEmailException, ExistingUsernameException {
		Assert.notNull(user, "User must not be null");

		// Check whether the given e-mail is already being used
		MyUser userByEmail = getUserByEmail(user.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(user.getId())) {
			throw new ExistingEmailException("E-mail already being used: " + user.getEmail());
		}

		// Check whether the given username is already being used
		MyUser userByUsername = getUserByUsername(user.getUsername());
		if (userByUsername != null && !userByUsername.getId().equals(user.getId())) {
			throw new ExistingUsernameException("Username already being used: " + user.getEmail());
		}
		
		// Handles create operation
		if (user.getId() == null) {
			logger.info("Saving User [email=" + user.getEmail() + "]");
			userRepo.persistEntity(user);
		}
		// Handles update operation
		else {
			logger.info("Updating User [email=" + user.getEmail() + "]");
			userRepo.mergeEntity(user);
		}
		
		return user;
	}

}