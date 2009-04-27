package dynamo.plugins.mavenmodel;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Entity object.
 *
 * @author Tiago Fernandez
 * @since 0.3
 */
@SuppressWarnings("unchecked")
public class Entity
			implements Serializable {

	private static final long serialVersionUID = -7822202373827027116L;

	private Application application;

	private String name;

	private Map<String, String> attributeAnnotations = new LinkedMap();
	private Map<String, String> attributeTypes = new LinkedMap();

	/**
	 * Constructor.
	 *
	 * @param name the name
	 */
	public Entity(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Entity name must not be blank");
		}
		this.name = name;
		this.attributeAnnotations.put("id", "@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)");
		this.attributeAnnotations.put("version", "@javax.persistence.Version");
		this.attributeTypes.put("id", "Long");
		this.attributeTypes.put("version", "Long");
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
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
	 * Adds an attribute annotation to this entity.
	 *
	 * @param name the attribute name
	 * @param annotation the attribute annotation
	 */
	public void addAttributeAnnotation(String name, String annotation) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(annotation) && annotation.length() > 2) {
			attributeAnnotations.put(name, annotation.substring(1, annotation.length() - 1));
		}
	}

	/**
	 * Removes an attribute annotation from this entity.
	 *
	 * @param name the attribute name
	 */
	public void removeAttributeAnnotation(String name) {
		if (StringUtils.isNotBlank(name)) {
			attributeAnnotations.remove(name);
		}
	}

	/**
	 * Gets the attribute annotations.
	 *
	 * @return the attribute annotations
	 */
	public Map<String, String> getAttributeAnnotations() {
		return attributeAnnotations;
	}

	/**
	 * Sets the attribute annotations.
	 *
	 * @param attributeAnnotations the attribute annotations
	 */
	public void setAttributeAnnotations(Map<String, String> attributeAnnotations) {
		this.attributeAnnotations = attributeAnnotations;
	}

	/**
	 * Adds an attribute type to this entity.
	 *
	 * @param name the attribute name
	 * @param type the attribute type
	 */
	public void addAttributeType(String name, String type) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(type)) {
			attributeTypes.put(name, type);
		}
	}

	/**
	 * Removes an attribute type from this entity.
	 *
	 * @param name the attribute name
	 */
	public void removeAttributeType(String name) {
		if (StringUtils.isNotBlank(name)) {
			attributeTypes.remove(name);
		}
	}

	/**
	 * Gets the attribute types.
	 *
	 * @return the attribute types
	 */
	public Map<String, String> getAttributeTypes() {
		return attributeTypes;
	}

	/**
	 * Sets the attribute types.
	 *
	 * @param attributeTypes the attribute types
	 */
	public void setAttributeTypes(Map<String, String> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}

	/**
	 * @return the attribute names
	 */
	public Set<String> getAttributeNames() {
		return attributeTypes.keySet();
	}

	/**
	 * @return the attribute names
	 */
	public Set<String> getAttributeUniqueNames() {
		Set<String> attrUniqueNames = new ListOrderedSet();
		Set<String> attrNames = attributeTypes.keySet();
		for (String attr : attrNames) {
			String annotation = getAnnotation(attr);
			boolean isUnique = StringUtils.remove(annotation, ' ').indexOf("unique=true") > 0;
			if (isUnique || annotation.indexOf("@javax.persistence.Id") >= 0) {
				attrUniqueNames.add(attr);
			}
		}
		return attrUniqueNames;
	}

	/**
	 * @param name the attribute name
	 * @return the annotation
	 */
	public String getAnnotation(String name) {
		return attributeAnnotations.get(name);
	}

	/**
	 * @param name the attribute name
	 * @return the type
	 */
	public String getType(String name) {
		return attributeTypes.get(name);
	}

	/**
	 * Useful for getters and setters method names.
	 *
	 * @param str usually an attribute name
	 * @return e.g. for 'myAttr' we get 'MyAttr'
	 */
	public String getFirstCaseUpper(String str) {
		if (str == null) {
			throw new IllegalArgumentException("String value must not be null");
		}
		if (str.length() < 2) {
			throw new IllegalArgumentException("String value must have at least 2 characters");
		}
		return new StringBuilder(str.substring(0, 1).toUpperCase()).append(str.substring(1, str.length())).toString();
	}

	/**
	 * Useful for getting name with additional suffix.
	 *
	 * @param suffix to add to entity name
	 * @return the entity name with suffix included
	 */
	public String getNameAndAppendSuffix(String suffix) {
		return new StringBuilder(name).append(suffix).toString();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("entityName", getName())
			.append("applicationName", getApplication().getName())
			.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;
		if (other instanceof Entity) {
			Entity castOther = (Entity) other;
			equals = new EqualsBuilder()
				.append(this.getName(), castOther.getName())
				.append(this.getApplication().getName(), castOther.getApplication().getName())
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
			.append(getApplication().getName())
			.toHashCode();
	}

}