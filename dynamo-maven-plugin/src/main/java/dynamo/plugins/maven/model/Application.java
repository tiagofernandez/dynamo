package dynamo.plugins.mavenmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Application object.
 *
 * @author Tiago Fernandez
 * @since 0.3
 */
public class Application
			implements Serializable {

	private static final long serialVersionUID = -8993671755454270564L;

	private String name;

	private List<Entity> entities = new ArrayList<Entity>();

	/**
	 * Constructor.
	 *
	 * @param name the name
	 */
	public Application(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Application name must not be blank");
		}
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the lower case name
	 */
	public String getNameLowerCase() {
		return name != null ? name.toLowerCase() : null;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds an entity.
	 *
	 * @param entity to add
	 */
	public void addEntity(Entity entity) {
		if (entity != null) {
			entity.setApplication(this);
			entities.add(entity);
		}
	}

	/**
	 * Removes an entity.
	 *
	 * @param entity to remove
	 */
	public void removeEntity(Entity entity) {
		if (entity != null) {
			entity.setApplication(null);
			entities.remove(entity);
		}
	}

	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Sets the entities.
	 *
	 * @param entities the entities to set
	 */
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	/**
	 * @return the full package name
	 */
	public String getPackageName(String suffix) {
		return new StringBuilder("dynamo.modules.").append(name.toLowerCase()).append(suffix).toString();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", getName())
			.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;
		if (other instanceof Application) {
			Application castOther = (Application) other;
			equals = new EqualsBuilder()
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
			.append(getName())
			.toHashCode();
	}

}