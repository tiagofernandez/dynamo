package dynamo.modules.sampleapp.entity;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.builder.*;
import org.hibernate.validator.*;

/**
 * MyGroupBase object.
 */
@MappedSuperclass
public class MyGroupBase {

	@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@javax.persistence.Version
	protected Long version;

	@Column(unique = true, nullable = false, length = 30)
	protected String name;

	@Column(length = 200)
	protected String description;

	@ManyToMany(fetch = FetchType.LAZY)
	protected Set<MyUser> users;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the users
	 */
	public Set<MyUser> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<MyUser> users) {
		this.users = users;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;
		if (other instanceof MyGroup) {
			MyGroup castOther = (MyGroup) other;
			equals = new EqualsBuilder()
				.append(this.getId(), castOther.getId())
				.append(this.getName(), castOther.getName())
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
			.append(getName())
			.toHashCode();
	}
	
}