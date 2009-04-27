package dynamo.core.template;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dynamo.core.io.ResourceLoader;

/**
 * TemplateInfo.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class TemplateInfo {

	private String sourcePath;

	private Map<String, Collection<Object>> beansMap = new HashMap<String, Collection<Object>>();
	private Map<String, Object> paramsMap = new HashMap<String, Object>();

	/**
	 * @param collectionName the collectionName
	 * @param bean the bean to add
	 */
	public void addBean(String collectionName, Object bean) {
		if (collectionName != null && bean != null) {
			if (!beansMap.containsKey(collectionName)) {
				beansMap.put(collectionName, new ArrayList<Object>());
			}
			beansMap.get(collectionName).add(bean);
		}
	}

	/**
	 * @param collectionName the collectionName
	 * @param bean the bean to remove
	 */
	public void removeBean(String collectionName, Object bean) {
		if (collectionName != null && bean != null && beansMap.containsKey(collectionName)) {
			beansMap.get(collectionName).remove(bean);
		}
	}
	
	/**
	 * @param collectionName the collectionName to remove
	 */
	public void removeCollection(String collectionName) {
		if (collectionName != null && beansMap.containsKey(collectionName)) {
			beansMap.remove(collectionName);
		}
	}

	/**
	 * @return the beans map
	 */
	public Map<String, Collection<Object>> getBeansMap() {
		return beansMap;
	}

	/**
	 * @return all beans
	 */
	public Collection<Object> getAllBeans() {
		Collection<Object> allBeans = new ArrayList<Object>();
		for (Collection<Object> collection : beansMap.values()) {
			allBeans.addAll(collection);
		}
		return allBeans;
	}

	/**
	 * @param key the key to add
	 * @param value the value to add
	 */
	public void addParam(String key, Object value) {
		if (key != null && value != null) {
			paramsMap.put(key, value);
		}
	}

	/**
	 * @param key the key to add
	 * @param path the classpath resource to add
	 */
	public void addImageParam(String key, String path) {
		if (key != null && path != null && ResourceLoader.resourceExists(path)) {
			paramsMap.put(key, ResourceLoader.getResourceAsStream(path));
		}
	}

	/**
	 * @param key the key to remove
	 */
	public void removeParam(String key) {
		if (key != null && paramsMap.containsKey(key)) {
			paramsMap.remove(key);
		}
	}

	/**
	 * @return the parameters map copy
	 */
	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	/**
	 * @return the source stream from classpath
	 */
	public InputStream getSourceFromClasspath() {
		return ResourceLoader.getResourceAsStream(sourcePath);
	}

}