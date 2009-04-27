package dynamo.core.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import dynamo.core.io.ResourceLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreemarkerTemplateManager.
 * 
 * @author Tiago Fernandez
 * @since 0.4
 */
public final class FreemarkerTemplateManager
			implements TemplateManager {
	
	private Configuration config;
	
	/**
	 * @see dynamo.core.template.TemplateManager#generateOutput(dynamo.core.template.TemplateInfo)
	 */
	public Object generateOutput(TemplateInfo input) throws TemplateException {
		Assert.notNull(input, "TemplateInfo must not be null");
		StringWriter output = new StringWriter();
		
		try {
		   StringReader reader = new StringReader(ResourceLoader.getResourceAsString(input.getSourcePath()));
		   Template template = new Template("T" + System.currentTimeMillis(), reader, getConfiguration());
			Map<String, Object> params = new HashMap<String, Object>();
	
			// Read beans
			for (Map.Entry<String, Collection<Object>> entry : input.getBeansMap().entrySet()) {
				params.put(entry.getKey(), entry.getValue());
			}
			
			// Read parameters
			for (Map.Entry<String, Object> entry : input.getParamsMap().entrySet()) {
				params.put(entry.getKey(), entry.getValue());
			}
			
			// Merge data-model with template
			template.process(params, output);
			output.flush();
		}
		catch (IOException ex) {
			throw new TemplateException("I/O exception while processing template", ex);
		}
		catch (freemarker.template.TemplateException ex) {
			throw new TemplateException("Could not generate output using Freemarker", ex);
		}
		
		return output.toString();
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		if (config == null) {
			config = new Configuration();
		}
		return config;
	}

	/**
	 * @param config the configuration to set
	 */
	public void setConfiguration(Configuration config) {
		this.config = config;
	}

}