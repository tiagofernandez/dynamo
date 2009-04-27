package dynamo.plugins.maven.output;

/**
 * Listens to code generation events.
 * 
 * @author Tiago Fernandez
 * @since  0.3
 */
public interface CodeGeneratorListener {

	/**
	 * Callback method for listening code generation.
	 * 
	 * @param event the event to process
	 */
	void onGeneratedCode(CodeGenerationEvent event);
	
}