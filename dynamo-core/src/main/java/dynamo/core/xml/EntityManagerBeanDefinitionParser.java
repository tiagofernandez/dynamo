package dynamo.core.xml;

import java.util.Properties;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for the "entityManager" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EntityManagerBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(EntityManagerBeanDefinitionParser.class);
	
	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return LocalContainerEntityManagerFactoryBean.class;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, 
	 * 	org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {		
		
		// Register the bean responsible for injecting the EntityManager with @PersistenceContext
		RootBeanDefinition pabPostProcessor = new RootBeanDefinition(PersistenceAnnotationBeanPostProcessor.class);
		String pabPostProcessorId = element.getAttribute("id") + "PersistenceCtxPostProcessor";
		parserContext.registerBeanComponent(new BeanComponentDefinition(pabPostProcessor, pabPostProcessorId));
		
		// Link DataSource runtime reference
		RuntimeBeanReference dataSourceBean = new RuntimeBeanReference(element.getAttribute("dataSourceRef"));
		builder.getBeanDefinition().getPropertyValues().addPropertyValue("dataSource", dataSourceBean);
		
		// Set the persistence XML location
		if (element.hasAttribute("persistenceXmlLocation")) {
			builder.addPropertyValue("persistenceXmlLocation", element.getAttribute("persistenceXmlLocation"));
		}
		
		// Configure JPA vendor
		HibernateJpaVendorAdapter hibernateAdapter = new HibernateJpaVendorAdapter();
		String showSqlAttr = element.getAttribute("showSql");
		hibernateAdapter.setShowSql(showSqlAttr != null ? Boolean.valueOf(showSqlAttr) : false);
		builder.addPropertyValue("jpaVendorAdapter", hibernateAdapter);
		
		// Handle JPA properties
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", element.getAttribute("dialect"));
		jpaProperties.put("hibernate.hbm2ddl.auto", element.getAttribute("hbm2ddl"));
		builder.addPropertyValue("jpaProperties", jpaProperties);
	}

}