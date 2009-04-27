package dynamo.core.template;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

/**
 * Test case for FreemarkerTemplateManager.
 *
 * @author Tiago Fernandez
 * @since 0.4
 */
public class FreemarkerTemplateManagerTest {

	private static Log logger = LogFactory.getLog(FreemarkerTemplateManagerTest.class);
	
	private FreemarkerTemplateManager freemarkerMgr = new FreemarkerTemplateManager();
	
	private String output;
	
	@Test
	public void generateOutput() throws Exception {
		TemplateInfo info = new TemplateInfo();
		info.setSourcePath("dynamo/core/template/LatestProduct.ftl");
		
		// Add parameters
		Map<String, Object> latest = new HashMap<String, Object>();
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		info.addParam("latestProduct", latest);
		info.addParam("user", "Big Joe");
		
		// Generate output
		output = (String) freemarkerMgr.generateOutput(info);
		assertNotNull(output);
		assertTrue(output.length() > 0);
		logger.trace(output);
	}

}