package dynamo.plugins.maven.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Generates code while running Maven build.
 * 
 * @goal codegen
 * @phase generate-sources
 */
public class CodeGeneratorMojo
			extends AbstractMojo
			implements CodeGeneratorListener {

	private static final String DSL_PATH = "/src/dynamo/application.dsl";
	private static final String OUTPUT_PATH = "/src/main/java/";

	private CodeGenerator codeGen = new CodeGenerator();
	
   /**
    * The maven project.
    *
    * @parameter expression="${project}"
    * @required
    * @readonly
    */
	protected MavenProject project;
	
	/**
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		getLog().info("Started generating code using Dynamo-Maven-Plugin...");
		try {
			String dsl = readApplicationDSL();
			codeGen.addListener(this);
			codeGen.parse(dsl);
		}
		catch (IOException ex) {
			getLog().error("Failed reading " + DSL_PATH, ex);
		}
		catch (CodeGeneratorException ex) {
			getLog().error("Failed parsing DSL and generating code", ex);
		}
		getLog().info("Code generation completed!");
	}

	/**
	 * @see dynamo.plugins.maven.output.CodeGeneratorListener#onGeneratedCode(dynamo.plugins.maven.output.CodeGenerationEvent)
	 */
	public void onGeneratedCode(CodeGenerationEvent event) {
		String filePath = buildFilePath(event);
		try {
			writeGeneratedSource(event, filePath);
		}
		catch (IOException ex) {
			getLog().error("Failed writing generated code to " + filePath, ex);
		}
	}

	/**
	 * Reads the application's domain-specific language file.
	 */
	private String readApplicationDSL() throws IOException {
		StringBuilder dsl = new StringBuilder();
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		String dslPath = project.getBasedir().getPath() + DSL_PATH;
		try {
			// Initialize readers
			fileReader = new FileReader(new File(dslPath));
			bufReader = new BufferedReader(fileReader);
			
			// Read line by line
			String line = bufReader.readLine();
			while (line != null) {
				dsl.append(line);
				line = bufReader.readLine();
			}
			
			if (getLog().isInfoEnabled()) {
				getLog().info("Read " + dslPath + " file, lenght = " + dsl.length());
			}
		}
		finally {
			// Close readers
			if (bufReader != null) {
				bufReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return dsl.toString();
	}

	/**
	 * Writes generated source typically to /src/main/java.
	 */
	private void writeGeneratedSource(CodeGenerationEvent event, String filePath) throws IOException {
		FileWriter fileWriter = null;
		try {
			// Create directory tree
			String dirs = filePath.substring(0, filePath.lastIndexOf('/'));
			new File(dirs).mkdirs();
			
			// Write generated source to disk
			fileWriter = new FileWriter(new File(filePath));
			fileWriter.write(event.getFileContent());
			fileWriter.flush();
			
			if (getLog().isInfoEnabled()) {
				getLog().info("Wrote " + filePath + " file, lenght = " + event.getFileContent().length());
			}
		}
		finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

	/**
	 * Builds file absolute path.
	 */
	private String buildFilePath(CodeGenerationEvent event) {
		StringBuilder sb = new StringBuilder();
		sb.append(project.getBasedir().getPath());
		sb.append(OUTPUT_PATH);
		sb.append(StringUtils.replace(event.getPackageName(), ".", "/"));
		sb.append("/").append(event.getFileName()).append(".java");
		return sb.toString();
	}

}