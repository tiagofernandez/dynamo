package dynamo.core.xml;

import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.w3c.dom.Element;

/**
 * BeanDefinitionParser for the "transaction" element.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class TransactionAnnotationBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {

	//private static Log logger = LogFactory.getLog(TransactionBeanDefinitionParser.class);

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return JpaTransactionManager.class;
	}

	/**
	 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(
	 * 	org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext, 
	 * 	org.springframework.beans.factory.support.BeanDefinitionBuilder)
	 */
	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		
		// Register the EntityManagerFactory runtime reference
		RuntimeBeanReference emfBean = new RuntimeBeanReference(element.getAttribute("entityManagerFactoryRef"));
		builder.getBeanDefinition().getPropertyValues().addPropertyValue("entityManagerFactory", emfBean);
		AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);
		
		// Create the TransactionInterceptor definition
		RootBeanDefinition interceptorDef = new RootBeanDefinition(TransactionInterceptor.class);
		interceptorDef.setSource(parserContext.extractSource(element));
		interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		RuntimeBeanReference txMgrBean = new RuntimeBeanReference(element.getAttribute("id"));
		interceptorDef.getPropertyValues().addPropertyValue("transactionManager", txMgrBean);
		
		// Create the AnnotationTransactionAttributeSource definition
		RootBeanDefinition annotationTxAttrSrc = new RootBeanDefinition(AnnotationTransactionAttributeSource.class);
		interceptorDef.getPropertyValues().addPropertyValue("transactionAttributeSource", annotationTxAttrSrc);
		
		// Create the TransactionAttributeSourceAdvisor definition
		RootBeanDefinition advisorDef = new RootBeanDefinition(TransactionAttributeSourceAdvisor.class);
		advisorDef.setSource(parserContext.extractSource(element));
		advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		advisorDef.getPropertyValues().addPropertyValue("transactionInterceptor", interceptorDef);
		String txAdvisorId = element.getAttribute("id") + "TransactionAdvisor";
		parserContext.registerBeanComponent(new BeanComponentDefinition(advisorDef, txAdvisorId));
	}

}