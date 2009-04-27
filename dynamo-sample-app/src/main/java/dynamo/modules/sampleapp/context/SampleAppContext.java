package dynamo.modules.sampleapp.context;

import dynamo.core.context.AbstractContext;

/**
 * Provides read-only operations to Spring context.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class SampleAppContext
			extends AbstractContext {

	private static SampleAppContext instance;

	private SampleAppContext() {
		super();
	}

	/**
	 * @return the singleton
	 */
	public static synchronized SampleAppContext getInstance() {
		if (instance ==  null) {
			instance = new SampleAppContext();
		}
		return instance;
	}

	/**
	 * @see dynamo.core.context.AbstractContext#getConfigLocations()
	 */
	@Override
	public String[] getConfigLocations() {
		return new String[] {
        "/dynamo/modules/sampleapp/context/sampleapp-context.xml"
		};
	}

}