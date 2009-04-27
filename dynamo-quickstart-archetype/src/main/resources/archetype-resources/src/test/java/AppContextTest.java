package ${package};

import static org.testng.Assert.*;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Test case for AppContext.
 */
public class AppContextTest {

	//private static Log logger = LogFactory.getLog(AppContextTest.class);
	
	private AppContext appCtx;
	
	@BeforeTest
	public void init() throws Exception {
		appCtx = AppContext.getInstance();
	}
	
	@AfterTest
	public void destroy() throws Exception {
		appCtx = null;
	}

	@Test
	public void getProperties() {
		assertNotNull(AppContext.getInstance().getBean("${artifactId}Properties"));
	}

	@Test
	public void getDataSource() {
		DriverManagerDataSource dataSource = (DriverManagerDataSource) appCtx.getBean("${artifactId}DataSource");
		assertNotNull(dataSource);
		assertEquals(dataSource.getDriverClassName(), "org.hsqldb.jdbcDriver");
		assertEquals(dataSource.getUrl(), "jdbc:hsqldb:mem:${artifactId}");
		assertEquals(dataSource.getUsername(), "sa");
		assertEquals(dataSource.getPassword(), "");
	}

	@Test
	public void getEntityManager() {
		assertNotNull(AppContext.getInstance().getBean("${artifactId}EntityManager"));
	}

	@Test
	public void getTransaction() {
		JpaTransactionManager transactionManager = (JpaTransactionManager) appCtx.getBean("${artifactId}Transaction");
		assertNotNull(transactionManager);
		assertNotNull(transactionManager.getEntityManagerFactory());
	}

	/*
	@Test
	public void getMyService() {
		assertNotNull(AppContext.getInstance().getBean("myService"));
	}
	*/

}