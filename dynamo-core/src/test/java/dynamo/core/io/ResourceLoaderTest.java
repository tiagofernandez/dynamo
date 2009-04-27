package dynamo.core.io;

import static org.testng.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.testng.annotations.Test;

/**
 * Test case for ResourceLoader.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class ResourceLoaderTest {

	private static final String DUMMY_BUNDLE = "dynamo/core/io/dummy";
	private static final String DUMMY_PROPERTIES = "dynamo/core/io/dummy.properties";

	@Test
	public void getDefaultBundle() throws Exception {
		ResourceBundle bundle = ResourceLoader.getBundle(DUMMY_BUNDLE);
		assertNotNull(bundle);
		assertEquals(bundle.getString("vehicle.airplane"), "airplane");
		assertEquals(bundle.getString("vehicle.car"), "car");
		assertEquals(bundle.getString("vehicle.ship"), "ship");
	}

	@Test
	public void getCustomBundle() throws Exception {
		ResourceBundle bundle = ResourceLoader.getBundle(DUMMY_BUNDLE, new Locale("pt", "BR"));
		assertEquals(bundle.getString("vehicle.airplane"), "aviao");
		assertEquals(bundle.getString("vehicle.car"), "carro");
		assertEquals(bundle.getString("vehicle.ship"), "navio");
	}

	@Test
	public void getResourceAsStream() throws Exception {
		assertNotNull(ResourceLoader.getResourceAsStream(DUMMY_PROPERTIES));
	}

	@Test
	public void getResourceAsString() throws Exception {
		assertNotNull(ResourceLoader.getResourceAsString(DUMMY_PROPERTIES));
	}

	@Test
	public void getResourceAsByteArray() throws Exception {
		assertNotNull(ResourceLoader.getResourceAsByteArray(DUMMY_PROPERTIES));
	}

	@Test
	public void getResourceAsUrl() throws Exception {
		assertNotNull(ResourceLoader.getResourceAsUrl(DUMMY_PROPERTIES));
	}

	@Test
	public void resourceExists() throws Exception {
		assertNotNull(ResourceLoader.resourceExists(DUMMY_PROPERTIES));
	}

	@Test
	public void getContextClassLoader() throws Exception {
		assertNotNull(ResourceLoader.getContextClassLoader());
	}

}