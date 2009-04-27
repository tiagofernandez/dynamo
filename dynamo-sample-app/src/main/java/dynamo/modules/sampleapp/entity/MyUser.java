package dynamo.modules.sampleapp.entity;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * MyUser entity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
@Entity
@Table(name = "MY_USER")
public class MyUser
			extends MyUserBase {

	/**
	 * @param group to add
	 */
	public void addMyGroup(MyGroup group) {
		if (groups == null) {
			groups = new HashSet<MyGroup>();
		}
		groups.add(group);
	}

	/**
	 * @param group to remove
	 */
	public void removeGroup(MyGroup group) {
		if (groups != null) {
			groups.remove(group);
		}
	}

	/**
	 * @param plainPassword the plain password to encrypt
	 */
	public void encryptPassword(String plainPassword) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		password = passwordEncryptor.encryptPassword(plainPassword);
	}

	/**
     * @param plainPassword the plain password to check
     * @return true if the passwords match
	 */
	public boolean checkPassword(String plainPassword) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		return passwordEncryptor.checkPassword(plainPassword, password);
	}
	
}