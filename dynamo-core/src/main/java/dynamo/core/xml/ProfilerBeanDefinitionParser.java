package dynamo.core.xml;

import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import dynamo.core.aop.SimpleAopProfiler;

/**
 * BeanDefinitionParser for the "profiler" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class ProfilerBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(ProfilerBeanDefinitionParser.class);

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return SimpleAopProfiler.class;
	}

}