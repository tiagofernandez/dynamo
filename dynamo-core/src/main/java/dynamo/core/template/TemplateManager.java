package dynamo.core.template;

/**
 * Defines generic operations for handling templates.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public interface TemplateManager {

	/**
	 * Generates template output.
	 * 
	 * @param input the template input info
	 * @return the template's output
	 * @throws TemplateException when the output could not be generated
	 */
	Object generateOutput(TemplateInfo input) throws TemplateException;
	
}