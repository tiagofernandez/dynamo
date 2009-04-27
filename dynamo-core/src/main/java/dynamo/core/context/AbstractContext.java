package dynamo.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

/**
 * Wrapper for Spring ApplicationContext interface.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public abstract class AbstractContext {

	protected ApplicationContext appContext;

	/**
	 * Gets the ApplicationContext, regarding the configuration files.
	 * 
	 * @see #getConfigLocations()
	 * @return the ApplicationContext
	 */
	public ApplicationContext getApplicationContext() {
		if (appContext == null) {
			String[] configLocations = getConfigLocations();
			Assert.notEmpty(configLocations, "Config locations array must not be empty");
			appContext = new ClassPathXmlApplicationContext(configLocations);
		}
		return appContext;
	}

	/**
	 * Gets bean by name.
	 * 
	 * @param name identifying the bean
	 * @return the bean
	 */
	public Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * Gets the config files location, relative to classpath.
	 * 
	 * @return the Spring config files
	 */
	public abstract String[] getConfigLocations();

}