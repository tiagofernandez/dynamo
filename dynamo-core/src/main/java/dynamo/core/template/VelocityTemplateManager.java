package dynamo.core.template;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.util.Assert;

/**
 * VelocityTemplateManager.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class VelocityTemplateManager
			implements TemplateManager {

	private VelocityEngine engine;
	
	/**
	 * @see dynamo.core.template.TemplateManager#generateOutput(dynamo.core.template.TemplateInfo)
	 */
	public Object generateOutput(TemplateInfo input) throws TemplateException {
		Assert.notNull(input, "TemplateInfo must not be null");
		StringWriter output = new StringWriter();
		
		try {
			// Get the template through the engine
			VelocityEngine engine = getEngine();
			Template template = engine.getTemplate(input.getSourcePath());
			
			// Merge the context
			VelocityContext context = getContext(input);
			template.merge(context, output);
			output.flush();
		}
		catch (ResourceNotFoundException ex) {
			throw new TemplateException("Could not find resource [sourcePath=" + input.getSourcePath() + "]", ex);
		}
		catch (ParseErrorException ex) {
			throw new TemplateException("Could not parse template file", ex);
		}
		catch (MethodInvocationException ex) {
			throw new TemplateException("Could not populate template", ex);
		}
		catch (Exception ex) {
			throw new TemplateException("Could not generate output using Velocity", ex);
		}
		return output.toString();
	}

	private VelocityEngine getEngine() throws Exception {
		if (engine == null) {
			
			// Set the engine properties
	      Properties p = new Properties();
			p.put("resource.loader", "class");
			p.put("class.resource.loader.description", "Template Class Loader");
			p.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			p.put("userdirective", "org.apache.velocity.tools.generic.directive.Ifnull");
			p.put("userdirective", "org.apache.velocity.tools.generic.directive.Ifnotnull");
			
			// Initialize the engine
			engine = new VelocityEngine();
			engine.init(p);
		}
		
		return engine;
	}

	private VelocityContext getContext(TemplateInfo input) {
		VelocityContext context = new VelocityContext();
		
		// Read beans
		for (Map.Entry<String, Collection<Object>> entry : input.getBeansMap().entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		
		// Read parameters
		for (Map.Entry<String, Object> entry : input.getParamsMap().entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		
		return context;
	}

}