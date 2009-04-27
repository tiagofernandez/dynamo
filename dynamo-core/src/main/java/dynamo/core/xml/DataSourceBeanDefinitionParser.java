package dynamo.core.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for the "dataSource" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class DataSourceBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(DataSourceBeanDefinitionParser.class);
	
	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		Class<?> clazz = DriverManagerDataSource.class;
		if (isJndiDataSource(element)) {
			clazz = JndiObjectFactoryBean.class;
		}
		return clazz;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		
		// Handle JNDI-based datasource
		if (isJndiDataSource(element)) {
			builder.addPropertyValue("jndiName", element.getAttribute("jndiName"));
		}		
		// Handle direct datasource definition
		else {
			builder.addPropertyValue("driverClassName", element.getAttribute("driverClassName"));
			builder.addPropertyValue("url", element.getAttribute("url"));
			builder.addPropertyValue("username", element.getAttribute("username"));
			builder.addPropertyValue("password", element.getAttribute("password"));
		}		
	}

	private boolean isJndiDataSource(Element element) {
		return element.hasAttribute("jndiName");
	}

}