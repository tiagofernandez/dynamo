package dynamo.core.template;

import static org.testng.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

/**
 * Test case for VelocityReportManager.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class VelocityTemplateManagerTest {

	private static Log logger = LogFactory.getLog(VelocityTemplateManagerTest.class);
	
	private VelocityTemplateManager velocityMgr = new VelocityTemplateManager();
	
	@Test
	public void generateOutput() throws Exception {
		TemplateInfo info = new TemplateInfo();
		info.setSourcePath("dynamo/core/template/Celebrities.vm");
		info.addBean("celebrityList", new Celebrity("Angelina Jolie", "Los Angeles", "United States"));
		info.addBean("celebrityList", new Celebrity("Gisele Bundchen", "Horizontina", "Brazil"));
		info.addBean("celebrityList", new Celebrity("Monica Bellucci", "Citta di Castello", "Italia"));
		String output = (String) velocityMgr.generateOutput(info);
		assertNotNull(output);
		assertTrue(output.length() > 0);
		logger.trace(output);
	}

}