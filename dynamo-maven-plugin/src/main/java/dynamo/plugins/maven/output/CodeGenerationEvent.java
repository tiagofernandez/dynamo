package dynamo.plugins.maven.output;

/**
 * Encapsulates code generation details.
 * 
 * @author Tiago Fernandez
 * @since 0.3
 */
public class CodeGenerationEvent {

	private String fileContent;
	private String fileName;
	private String packageName;

	/**
	 * Constructor.
	 */
	public CodeGenerationEvent() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param fileName the file name
	 * @param fileContent the file content
	 */
	public CodeGenerationEvent(String packageName, String fileName, String fileContent) {
		this();
		this.packageName = packageName;
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	
	/**
	 * @return the fileContent
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}