package dynamo.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Designed for loading resources using the best suitable classloader.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class ResourceLoader {

	private static Log logger = LogFactory.getLog(ResourceLoader.class);

	private ResourceLoader() {
		super();
	}

	/**
	 * Get the resource bundle with the given name.
	 * 
	 * @param baseName the resource name, separated by ".", without the extension
	 * @return the resource bundle
	 */
	public static ResourceBundle getBundle(String baseName) {
		return getBundle(baseName, Locale.getDefault());
	}

	/**
	 * Get the resource bundle with the given name.
	 * 
	 * @param baseName the resource name, separated by ".", without the extension
	 * @param locale the desired locale
	 * @return the resource bundle
	 */
	public static ResourceBundle getBundle(String baseName, Locale locale) {
		return ResourceBundle.getBundle(baseName, locale, getContextClassLoader());
	}

	/**
	 * Get a resource as stream.
	 * 
	 * @param resource the resource in classpath
	 * @return the stream representing the resource
	 */
	public static InputStream getResourceAsStream(String resource) {
		return getContextClassLoader().getResourceAsStream(resource);
	}

	/**
	 * Get a resource as string.
	 * 
	 * @param resource the resource in classpath
	 * @return the string with the content
	 */
	public static String getResourceAsString(String resource) {
		String content = null;
		byte[] byteArray = getResourceAsByteArray(resource);
		if (byteArray != null) {
			content = new String(byteArray);
		}
		return content;
	}

	/**
	 * Get a resource as a byte array.
	 * 
	 * @param resource the name of the desired resource
	 * @return the given resource, or null if it doesn't exist
	 */
	public static byte[] getResourceAsByteArray(String resource) {
		byte[] byteArray = null;
		try {
			InputStream stream = getResourceAsStream(resource);
			if (stream != null) {
				byteArray = new byte[stream.available()];
				stream.read(byteArray);
			}
		}
		catch (IOException ex) {
			logger.error("Error while getting resource", ex);
		}
		return byteArray;
	}

	/**
	 * Get the given resource URL, looking up by name.
	 * 
	 * @param resourceName the name of the desired resource
	 * @return the given resource
	 */
	public static URL getResourceAsUrl(String resourceName) {
		return getContextClassLoader().getResource(resourceName);
	}

	/**
	 * Check if the given resource exists.
	 * 
	 * @param resourceName the name of the desired resource
	 * @return if the resource exists, or not
	 */
	public static boolean resourceExists(String resourceName) {
		return getContextClassLoader().getResource(resourceName) != null;
	}

	/**
	 * Get the preferred classLoader for loading resources.
	 * 
	 * @return the preferred classLoader value
	 */
	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

}