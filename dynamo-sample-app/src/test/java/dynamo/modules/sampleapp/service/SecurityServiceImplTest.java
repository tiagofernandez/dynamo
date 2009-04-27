package dynamo.modules.sampleapp.service;

import static org.testng.Assert.*;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.InvalidStateException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dynamo.modules.sampleapp.context.SampleAppContext;
import dynamo.modules.sampleapp.entity.MyGroup;
import dynamo.modules.sampleapp.entity.MyGroupBase;
import dynamo.modules.sampleapp.entity.MyRole;
import dynamo.modules.sampleapp.entity.MyUser;
import dynamo.modules.sampleapp.entity.MyUserBase;

/**
 * Test case for SecurityServiceImpl.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class SecurityServiceImplTest {

	private static Log logger = LogFactory.getLog(SecurityServiceImplTest.class);

	private SecurityService securityService;
	
	private MyUser user;
	private MyGroup group;

	@BeforeTest
	public void init() throws Exception {
		securityService = (SecurityService) SampleAppContext.getInstance().getBean("securityService");
	}
	
	@AfterTest
	public void destroy() throws Exception {
		securityService = null;
	}
	
	/* * * * * User/Role tests * * * * */

	@Test
	public void createUser() throws Exception {
		user = new MyUser();
		user.setEmail("foo@bar.com");
		user.setName("Foo Bar");
		user.encryptPassword("foobar");
		user.setUsername("foobar");
		user.setRole(MyRole.POWER_USER);
		user = securityService.saveUser(user);
		assertNotNull(user.getId());
	}

	@Test(dependsOnMethods = { "createUser" })
	public void loginSuccess() throws Exception {
		assertNotNull(securityService.login("foobar", "foobar"));
	}

	@Test(dependsOnMethods = { "createUser" })
	public void loginFailure() throws Exception {
		try {
			securityService.login("foobar", "xxxyyyzzz");
		}
		catch (LoginException ex) {
			logger.debug("loginFailure() passed");
		}
	}

	@Test(expectedExceptions = { InvalidUserException.class })
	public void createUserMissingUsername() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail("xyz@xyz.org");
		invalidUser.setName("Xyz");
		invalidUser.encryptPassword("xyzorg");
		invalidUser.setUsername(null);
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(expectedExceptions = { InvalidUserException.class })
	public void createUserMissingPassword() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail("xyz@xyz.org");
		invalidUser.setName("Xyz");
		invalidUser.encryptPassword(null);
		invalidUser.setUsername("xyz");
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(expectedExceptions = { InvalidUserException.class })
	public void createUserMissingName() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail("xyz@xyz.org");
		invalidUser.setName(null);
		invalidUser.encryptPassword("This00Is12Gonna34Be56AHuge78Password910");
		invalidUser.setUsername("xyz");
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(expectedExceptions = { InvalidUserException.class })
	public void createUserMissingEmail() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail(null);
		invalidUser.setName("Xyz");
		invalidUser.encryptPassword("xyzorg");
		invalidUser.setUsername("xyz");
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(expectedExceptions = { InvalidStateException.class })
	public void createUserInvalidEmail() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail("invalid at email dot com");
		invalidUser.setName("Mister Invalid");
		invalidUser.encryptPassword("invalidpwd");
		invalidUser.setUsername("invalidusr");
		invalidUser.setRole(MyRole.GUEST);
		securityService.saveUser(invalidUser);
	}

	@Test(dependsOnMethods = { "createUser" }, expectedExceptions = { ExistingEmailException.class })
	public void createUserExistingEmail() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail(user.getEmail());
		invalidUser.setName("Abc");
		invalidUser.encryptPassword("abcorg");
		invalidUser.setUsername("abc");
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(dependsOnMethods = { "createUser" }, expectedExceptions = { ExistingUsernameException.class })
	public void createUserExistingUsername() throws Exception {
		MyUser invalidUser = new MyUser();
		invalidUser.setEmail("abc@abc.org");
		invalidUser.setName("Abc");
		invalidUser.encryptPassword("abcorg");
		invalidUser.setUsername(user.getUsername());
		invalidUser.setRole(MyRole.USER);
		securityService.saveUser(invalidUser);
	}

	@Test(dependsOnMethods = { "createUser", "createUserExistingEmail", "createUserExistingUsername" })
	public void listUsers() throws Exception {
		List<MyUser> userList = securityService.listUsers();
		assertNotNull(userList);
		assertTrue(userList.contains(user));
	}

	@Test(dependsOnMethods = { "listUsers" })
	public void listUsersByRole() throws Exception {
		List<MyUser> userList = securityService.listUsersByRole(MyRole.POWER_USER);
		assertNotNull(userList);
		assertTrue(userList.contains(user));
	}

	@Test(dependsOnMethods = { "listUsers" })
	public void getUserById() throws Exception {
		MyUserBase userById = securityService.getUserById(user.getId());
		assertNotNull(userById);
	}

	@Test(dependsOnMethods = { "listUsers" })
	public void getUserByEmail() throws Exception {
		MyUser userByEmail = securityService.getUserByEmail(user.getEmail());
		assertNotNull(userByEmail);
	}

	@Test(dependsOnMethods = { "listUsers" })
	public void getUserByUsername() throws Exception {
		MyUser userByUsername = securityService.getUserByUsername(user.getUsername());
		assertNotNull(userByUsername);
	}

	@Test(dependsOnMethods = { "listUsers", "listUsersByRole", "getUserById", "getUserByEmail", "getUserByUsername" })
	public void updateUser() throws Exception {
		Long oldId = user.getId();
		String oldEmail = user.getEmail();
		String newEmail = "bar@foo.com";
		user.setEmail(newEmail);
		securityService.saveUser(user);
		MyUser updatedUser = securityService.getUserByEmail(newEmail);
		assertNotNull(updatedUser);
		assertEquals(updatedUser.getId(), oldId);
		assertFalse(updatedUser.getEmail().equals(oldEmail));
	}

	@Test(dependsOnMethods = { "updateUser" })
	public void updateUserExistingEmail() throws Exception {
		String previousEmail = user.getEmail();
		try {
			MyUser anotherUser = new MyUser();
			anotherUser.setEmail("mno@pqr.org");
			anotherUser.setName("Mno");
			anotherUser.encryptPassword("mnoorg");
			anotherUser.setUsername("mnopqr");
			anotherUser.setRole(MyRole.USER);
			securityService.saveUser(anotherUser);
			user.setEmail(anotherUser.getEmail());
			securityService.saveUser(user);
			fail("MyUser with existing e-mail is supposed to be rejected");
		}
		catch (ExistingEmailException ex) {
			user.setEmail(previousEmail);
		}
	}
	
	@Test(dependsOnMethods = { "updateUser" })
	public void updateUserExistingUsername() throws Exception {
		String previousUsername = user.getUsername();
		try {
			MyUser anotherUser = new MyUser();
			anotherUser.setEmail("pqr@stu.org");
			anotherUser.setName("Pqr");
			anotherUser.encryptPassword("pqrorg");
			anotherUser.setUsername("pqrstu");
			anotherUser.setRole(MyRole.USER);
			securityService.saveUser(anotherUser);
			user.setUsername(anotherUser.getUsername());
			securityService.saveUser(user);
			fail("MyUser with existing username is supposed to be rejected");
		}
		catch (ExistingUsernameException ex) {
			user.setUsername(previousUsername);
		}
	}

	@Test(dependsOnMethods = { "updateUserExistingEmail", "updateUserExistingUsername", "loginSuccess" })
	public void deleteUser() throws Exception {
		String currentEmail = user.getEmail();
		securityService.deleteUser(user.getId());
		assertNull(securityService.getUserByEmail(currentEmail));
	}

	/* * * * * User/MyGroup tests * * * * */

	@Test
	public void createGroup() throws Exception {
		MyUser freshman = new MyUser();
		freshman.setEmail("freshman@domain.com");
		freshman.setName("Freshman");
		freshman.encryptPassword("fmraensh");
		freshman.setUsername("freshman");
		freshman.setRole(MyRole.USER);
		securityService.saveUser(freshman);
		group = new MyGroup();
		group.setName("Administrators");
		group.setDescription("MyGroup for administrative purposes");
		group.addUser(freshman);
		securityService.saveGroup(group);
		assertNotNull(group.getId());
	}

	@Test(expectedExceptions = { InvalidGroupException.class })
	public void createGroupMissingName() throws Exception {
		MyGroup invalidGroup = new MyGroup();
		invalidGroup.setName(null);
		invalidGroup.setDescription("Invalid group");
		securityService.saveGroup(invalidGroup);
	}

	@Test(dependsOnMethods = { "createGroup" }, expectedExceptions = { ExistingGroupNameException.class })
	public void createGroupExistingName() throws Exception {
		MyGroup invalidGroup = new MyGroup();
		invalidGroup.setName(group.getName());
		invalidGroup.setDescription("Another invalid group");
		securityService.saveGroup(invalidGroup);
	}

	@Test(dependsOnMethods = { "createGroup" })
	public void listGroups() throws Exception {
		List<MyGroup> groupList = securityService.listGroups();
		assertNotNull(groupList);
		assertTrue(groupList.contains(group));
	}

	@Test(dependsOnMethods = { "createGroup" })
	public void getGroupById() throws Exception {
		MyGroupBase groupById = securityService.getGroupById(group.getId());
		assertNotNull(groupById);
		assertNotNull(groupById.getUsers());
		MyUser freshman = securityService.getUserByUsername("freshman");
		assertTrue(groupById.getUsers().contains(freshman));
	}

	@Test(dependsOnMethods = { "createGroup" })
	public void getGroupByName() throws Exception {
		MyGroup groupByName = securityService.getGroupByName(group.getName());
		assertNotNull(groupByName);
		assertNotNull(groupByName.getUsers());
		MyUser freshman = securityService.getUserByUsername("freshman");
		assertTrue(groupByName.getUsers().contains(freshman));
	}

	@Test(dependsOnMethods = { "createGroupExistingName", "listGroups", "getGroupById", "getGroupByName" })
	public void updateGroup() throws Exception {
		Long oldId = group.getId();
		String oldName = group.getName();
		String newName = "Admins";
		group.setName(newName);
		MyUser freshman = securityService.getUserByUsername("freshman");
		group.removeUser(freshman);
		securityService.saveGroup(group);
		MyGroup updatedGroup = securityService.getGroupByName(newName);
		assertNotNull(updatedGroup);
		assertEquals(updatedGroup.getId(), oldId);
		assertFalse(updatedGroup.getName().equals(oldName));
		assertFalse(updatedGroup.getUsers().contains(freshman));
	}

	@Test(dependsOnMethods = { "updateGroup" })
	public void updateGroupExistingName() throws Exception {
		String previousName = group.getName();
		try {
			MyGroup anotherGroup = new MyGroup();
			anotherGroup.setName("Power users");
			securityService.saveGroup(anotherGroup);
			group.setName(anotherGroup.getName());
			securityService.saveGroup(group);
			fail("MyGroup with existing name is supposed to be rejected");
		}
		catch (ExistingGroupNameException ex) {
			group.setName(previousName);
		}
	}

	@Test(dependsOnMethods = { "updateGroupExistingName" })
	public void deleteGroup() throws Exception {
		String currentName = group.getName();
		securityService.deleteGroup(group.getId());
		assertNull(securityService.getGroupByName(currentName));
		assertNotNull(securityService.getUserByUsername("freshman"));
	}

}