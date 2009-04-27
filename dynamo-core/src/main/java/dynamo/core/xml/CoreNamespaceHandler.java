package dynamo.core.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * NamespaceHandler for Dynamo Core component.
 *
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class CoreNamespaceHandler
			extends NamespaceHandlerSupport {

	/**
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
		registerBeanDefinitionParser("dataSource", new DataSourceBeanDefinitionParser());
		registerBeanDefinitionParser("emailReceiver", new EmailReceiverBeanDefinitionParser());
		registerBeanDefinitionParser("emailSender", new EmailSenderBeanDefinitionParser());
		registerBeanDefinitionParser("entityManager", new EntityManagerBeanDefinitionParser());
		registerBeanDefinitionParser("properties", new PropertiesBeanDefinitionParser());
		registerBeanDefinitionParser("profiler", new ProfilerBeanDefinitionParser());
		registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser());
		registerBeanDefinitionParser("transaction", new TransactionAnnotationBeanDefinitionParser());
	}

}