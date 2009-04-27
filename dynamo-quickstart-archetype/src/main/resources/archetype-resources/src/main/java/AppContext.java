package ${package};

import dynamo.core.context.AbstractContext;

/**
 * Provides read-only operations to Spring context.
 */
public final class AppContext
			extends AbstractContext {

	private static AppContext instance;
	
	private AppContext() {
		super();
	}
	
	/**
	 * @return the singleton
	 */
	public static synchronized AppContext getInstance() {
		if (instance ==  null) {
			instance = new AppContext();
		}
		return instance;
	}
	
	/**
	 * @see dynamo.core.context.AbstractContext#getConfigLocations()
	 */
	@Override
	public String[] getConfigLocations() {
		return new String[] {
			"app-context.xml"
		};
	}

}