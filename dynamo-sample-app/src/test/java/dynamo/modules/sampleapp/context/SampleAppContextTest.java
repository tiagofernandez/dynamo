package dynamo.modules.sampleapp.context;

import static org.testng.Assert.*;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Test case for SampleAppContext.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public class SampleAppContextTest {

	//private static Log logger = LogFactory.getLog(SampleAppContextTest.class);
	
	private SampleAppContext sampleAppCtx;
	
	@BeforeTest
	public void init() throws Exception {
		sampleAppCtx = SampleAppContext.getInstance();
	}
	
	@AfterTest
	public void destroy() throws Exception {
		sampleAppCtx = null;
	}

	@Test
	public void getSecurityCoreProperties() {
		assertNotNull(SampleAppContext.getInstance().getBean("sampleappProperties"));
	}

	@Test
	public void getDataSource() {
		DriverManagerDataSource dataSource = (DriverManagerDataSource) sampleAppCtx.getBean("sampleappDataSource");
		assertNotNull(dataSource);
		assertEquals(dataSource.getDriverClassName(), "org.hsqldb.jdbcDriver");
		assertEquals(dataSource.getUrl(), "jdbc:hsqldb:mem:sampleapp");
		assertEquals(dataSource.getUsername(), "sa");
		assertEquals(dataSource.getPassword(), "");
	}

	@Test
	public void getSecurityEntityManager() {
		assertNotNull(SampleAppContext.getInstance().getBean("sampleappEntityManager"));
	}

	@Test
	public void getSecurityTransaction() {
		JpaTransactionManager transactionManager = (JpaTransactionManager) sampleAppCtx.getBean("sampleappTransaction");
		assertNotNull(transactionManager);
		assertNotNull(transactionManager.getEntityManagerFactory());
	}

	@Test
	public void getSecurityProfiler() {
		assertNotNull(SampleAppContext.getInstance().getBean("sampleappProfiler"));
	}

	@Test(expectedExceptions = { NoSuchBeanDefinitionException.class })
	public void getEmailReceiver() {
		SampleAppContext.getInstance().getBean("sampleappEmailReceiver");
	}
	
	@Test(expectedExceptions = { NoSuchBeanDefinitionException.class })
	public void getEmailSender() {
		SampleAppContext.getInstance().getBean("sampleappEmailSender");
	}

	@Test
	public void getSecurityService() {
		assertNotNull(SampleAppContext.getInstance().getBean("securityService"));
	}

}