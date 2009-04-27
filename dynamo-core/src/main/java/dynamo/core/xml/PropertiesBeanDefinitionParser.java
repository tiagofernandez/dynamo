package dynamo.core.xml;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for the "properties" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class PropertiesBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(PropertiesBeanDefinitionParser.class);
	
	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return PropertyPlaceholderConfigurer.class;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, 
	 * 	org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		
		// Parse the property elements
		List<?> propertyElements = DomUtils.getChildElementsByTagName(element, "property");
		for (Iterator<?> iterator = propertyElements.iterator(); iterator.hasNext();) {
			Element property = (Element) iterator.next();
			parserContext.getDelegate().parsePropertyElement(property, builder.getBeanDefinition());
		}
		
		// Overriden properties
		builder.addPropertyValue("ignoreResourceNotFound", true);
		builder.addPropertyValue("ignoreUnresolvablePlaceholders", true);
	}

}