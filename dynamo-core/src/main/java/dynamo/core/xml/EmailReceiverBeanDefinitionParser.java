package dynamo.core.xml;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import dynamo.core.email.EmailReceiver;

/**
 * BeanDefinitionParser for the "emailReceiver" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailReceiverBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(EmailReceiverBeanDefinitionParser.class);
	
	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return EmailReceiver.class;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, 
	 * 	org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

		// Constructor arguments
		builder.addConstructorArg(element.getAttribute("pop"));
		builder.addConstructorArg(element.getAttribute("username"));
		builder.addConstructorArg(element.getAttribute("password"));
		
		// Parse the nested properties
		List<?> propertyElements = DomUtils.getChildElementsByTagName(element, "property");
		for (Iterator<?> propertyIterator = propertyElements.iterator(); propertyIterator.hasNext();) {
			Element property = (Element) propertyIterator.next();
			parserContext.getDelegate().parsePropertyElement(property, builder.getBeanDefinition());
		}		
	}

}