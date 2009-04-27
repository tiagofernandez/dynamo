package dynamo.core.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import dynamo.core.interceptor.JpaEntityValidationInterceptor;

/**
 * BeanDefinitionParser for the "service" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class ServiceBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	private static Log logger = LogFactory.getLog(ServiceBeanDefinitionParser.class);
	
	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return ProxyFactoryBean.class;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, 
	 * 	org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		try {
			BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
			AbstractBeanDefinition definition = builder.getBeanDefinition();

			// Set proxy interfaces
			builder.addPropertyValue("proxyInterfaces", new Class[] { Class.forName(element.getAttribute("interface")) });
			
			// Register and set service target
			RootBeanDefinition serviceTarget = new RootBeanDefinition(Class.forName(element.getAttribute("target")));
			String serviceTargetId = element.getAttribute("id") + "Target";
			parserContext.registerBeanComponent(new BeanComponentDefinition(serviceTarget, serviceTargetId));
			builder.addPropertyValue("target", serviceTarget);
			
			// Register JpaEntityValidationInterceptor
			RootBeanDefinition jpaEvInt = new RootBeanDefinition(JpaEntityValidationInterceptor.class);
			String jpaEvIntId = element.getAttribute("id") + "JpaEntityValidationInterceptor";
			parserContext.registerBeanComponent(new BeanComponentDefinition(jpaEvInt, jpaEvIntId));
			
			// Set interceptor names and add JpaEntityValidationInterceptor by default
			ManagedList interceptors = new ManagedList();
			interceptors.add(new TypedStringValue(jpaEvIntId));
			builder.addPropertyValue("interceptorNames", interceptors);
			
			// Handle internal properties
			List<?> propertyElements = DomUtils.getChildElementsByTagName(element, "property");
			for (Iterator<?> iterator = propertyElements.iterator(); iterator.hasNext();) {
				Element property = (Element) iterator.next();
				
				// Extract the previously defined interceptors
				if ("interceptorNames".equals(property.getAttribute("name"))) {
					Object interceptorNames = delegate.parsePropertyValue(property, definition, "interceptorNames");
					interceptors.addAll((ManagedList) interceptorNames);
				}
				
				// Perform regular property parsing
				else {
					delegate.parsePropertyElement(property, definition);
				}
			}
		}
		catch (ClassNotFoundException ex) {
			logger.error("ClassNotFoundException while parsing service element", ex);
		}
	}

}