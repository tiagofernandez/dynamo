package dynamo.plugins.maven.output;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.lang.StringUtils;

import dynamo.plugins.mavenmodel.Application;
import dynamo.plugins.mavenmodel.Entity;
import dynamo.plugins.mavenparser.DynamoLexer;
import dynamo.plugins.mavenparser.DynamoParser;
import dynamo.core.template.TemplateException;
import dynamo.core.template.TemplateInfo;
import dynamo.core.template.TemplateManager;
import dynamo.core.template.VelocityTemplateManager;

/**
 * Provides code generation capabilities.
 * 
 * @author Tiago Fernandez
 * @since 0.3 
 */
public final class CodeGenerator {
	
	private static final String ENTITY_TEMPLATE = "dynamo/plugins/maven/output/template/Entity.vm";
	private static final String REPOSITORY_TEMPLATE = "dynamo/plugins/maven/output/template/Repository.vm";
	
	private TemplateManager velocityMgr = new VelocityTemplateManager();	
	private List<CodeGeneratorListener> listeners = new ArrayList<CodeGeneratorListener>();

	/**
	 * Adds code generator listener.
	 * 
	 * @param listener to add
	 */
	public void addListener(CodeGeneratorListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}
	
	/**
	 * Removes code generator listener.
	 * 
	 * @param listener to remove
	 */
	public void removeListener(CodeGeneratorListener listener) {
		if (listener != null) {
			this.listeners.remove(listener);
		}
	}
	
	/**
	 * Parses a domain-specific language.
	 * 
	 * @param dsl the DSL to parse
	 * @throws CodeGeneratorException when parsing fails due to invalid DSL or template issues
	 */
	public void parse(String dsl) throws CodeGeneratorException {
		if (StringUtils.isBlank(dsl)) {
			throw new IllegalArgumentException("DSL must not be blank");
		}
		
		try {
			// Parse the DSL
			CharStream input = new ANTLRStringStream(dsl);
			DynamoLexer lexer = new DynamoLexer(input);
			CommonTokenStream tokens = new CommonTokenStream();
			tokens.setTokenSource(lexer);
			DynamoParser parser = new DynamoParser(tokens);
			Application app = parser.aplication();
	
			// Process each defined entity
			List<Entity> entities = app.getEntities();
			if (entities != null) {
				for (Entity entity : entities) {
					process(entity);
				}
			}
		}
		catch (RecognitionException ex) {
			throw new CodeGeneratorException("Failed parsing DSL", ex);
		}
		catch (TemplateException ex) {
			throw new CodeGeneratorException("Error while processing templates", ex);
		}
	}
	
	/**
	 * Processes an entity.
	 * 
	 * @param entity the input
	 * @throws TemplateException when failing processing
	 */
	void process(Entity entity) throws TemplateException {
		if (entity != null) {
			fireCodeGenerationEvent(new CodeGenerationEvent(
				generateEntityPackageName(entity),
				generateEntityFileName(entity),
				generateFileContent(entity, ENTITY_TEMPLATE)
			));
			fireCodeGenerationEvent(new CodeGenerationEvent(
				generateRepositoryPackageName(entity),
				generateRepositoryFileName(entity),
				generateFileContent(entity, REPOSITORY_TEMPLATE)
			));
		}
	}

	/**
	 * Fires code generation event.
	 * 
	 * @param event to be processed
	 */
	void fireCodeGenerationEvent(CodeGenerationEvent event) {
		for (CodeGeneratorListener listener : listeners) {
			listener.onGeneratedCode(event);
		}
	}
	
	/**
	 * Generates entity's package name.
	 * 
	 * @param entity the entity
	 * @return the entity package name
	 */
	String generateEntityPackageName(Entity entity) {
		return entity.getApplication().getPackageName(".entity");
	}
	
	/**
	 * Generates repository's package name.
	 * 
	 * @param entity the entity
	 * @return the repository package name
	 */
	String generateRepositoryPackageName(Entity entity) {
		return entity.getApplication().getPackageName(".repository");
	}
	
	/**
	 * Generates entity's file name.
	 * 
	 * @param entity the entity
	 * @return the file name
	 */
	String generateEntityFileName(Entity entity) {
		return new StringBuilder().append(entity.getName()).append("Base").toString();
	}
	
	/**
	 * Generates repository's file name.
	 * 
	 * @param entity the entity
	 * @return the file name
	 */
	String generateRepositoryFileName(Entity entity) {
		return new StringBuilder().append(entity.getName()).append("Repository").append("Base").toString();
	}

	/**
	 * Generates the entity's file content.
	 * 
	 * @param entity the entity
	 * @return the generated content
	 * @throws TemplateException whether the template couldn't be successfully applied
	 */
	String generateEntityFileContent(Entity entity) throws TemplateException {
		return generateFileContent(entity, ENTITY_TEMPLATE);
	}

	/**
	 * Generates the repository's file content.
	 * 
	 * @param entity the entity
	 * @return the generated content
	 * @throws TemplateException whether the template couldn't be successfully applied
	 */
	String generateRepositoryFileContent(Entity entity) throws TemplateException {
		return generateFileContent(entity, REPOSITORY_TEMPLATE);
	}

	/**
	 * Generates the file content by using a template manager.
	 * 
	 * @param entity the entity
	 * @param templatePath the template path
	 * @return the generated content
	 * @throws TemplateException whether the template couldn't be successfully applied
	 */
	String generateFileContent(Entity entity, String templatePath) throws TemplateException {
		TemplateInfo reportInfo = new TemplateInfo();
		reportInfo.setSourcePath(templatePath);
		reportInfo.addParam("entity", entity);
		return (String) velocityMgr.generateOutput(reportInfo);
	}

}