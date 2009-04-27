package dynamo.modules.sampleapp.entity;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;

/**
 * MyUserBase object.
 */
@MappedSuperclass
public class MyUserBase {

	@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@javax.persistence.Version
	protected Long version;

	@Column(unique = true, nullable = false, length = 30)
	protected String username;

	@Column(nullable = false, length = 100)
	protected String password;

	@Column(nullable = false, length = 50)
	protected String name;

	@Column(unique = true, nullable = false, length = 50) @Email
	protected String email;

	@Basic(optional = false) @Enumerated(EnumType.STRING)
	protected MyRole role;

	@ManyToMany(fetch = FetchType.LAZY)
	protected Set<MyGroup> groups;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the role
	 */
	public MyRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(MyRole role) {
		this.role = role;
	}

	/**
	 * @return the groups
	 */
	public Set<MyGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<MyGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;
		if (other instanceof MyUser) {
			MyUser castOther = (MyUser) other;
			equals = new EqualsBuilder()
				.append(this.getId(), castOther.getId())
				.append(this.getUsername(), castOther.getUsername())
				.append(this.getEmail(), castOther.getEmail())
				.isEquals();
		}
		return equals;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.append(getUsername())
			.append(getEmail())
			.toHashCode();
	}
	
}