package dynamo.plugins.maven.output;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dynamo.plugins.mavenmodel.Application;
import dynamo.plugins.mavenmodel.Entity;
import dynamo.core.io.ResourceLoader;

/**
 * Test case for CodeGenerator.
 *
 * @author Tiago Fernandez
 * @since 0.3
 */
public class CodeGeneratorTest {

	private CodeGenerator codeGen;

	@BeforeMethod
	public void initCodeGenerator() throws Exception {
		codeGen = new CodeGenerator();
	}

	@AfterMethod
	public void destroyCodeGenerator() throws Exception {
		codeGen = null;
	}

	@Test
	public void generateEntityCode() throws Exception {
		Entity entity = buildSampleEntity();
		assertEquals(codeGen.generateEntityFileName(entity), "DogBase");
		String generatedCode = codeGen.generateEntityFileContent(entity);
		assertNotNull(generatedCode);
		assertFalse(generatedCode.indexOf('$') > 0);
	}

	@Test
	public void generateRepositoryCode() throws Exception {
		Entity entity = buildSampleEntity();
		assertEquals(codeGen.generateRepositoryFileName(entity), "DogRepositoryBase");
		String generatedCode = codeGen.generateRepositoryFileContent(entity);
		assertNotNull(generatedCode);
		assertFalse(generatedCode.indexOf('$') > 0);
	}

	@Test
	public void listenCodeGenerationEventsAndCheckOutput() throws Exception {
		final List<CodeGenerationEvent> genEntities = new ArrayList<CodeGenerationEvent>();
		final List<CodeGenerationEvent> genRepositories = new ArrayList<CodeGenerationEvent>();

		// Add an anonymous listener
		codeGen.addListener(new CodeGeneratorListener() {
			public void onGeneratedCode(CodeGenerationEvent event) {
				if (event.getFileName().indexOf("Repository") > -1) {
					genRepositories.add(event);
				}
				else {
					genEntities.add(event);
				}
			}
		});

		// Parst the sample DSL
		codeGen.parse(ResourceLoader.getResourceAsString("Market.dsl"));

		// Check whether we have the expected number of events
		assertEquals(genEntities.size(), 3);
		assertEquals(genRepositories.size(), 3);
		
		// Check generated contents
		assertEquals(removeWhitespaces(getEvent("BuyerBase", genEntities).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/BuyerBase.output")));
		assertEquals(removeWhitespaces(getEvent("BuyerRepositoryBase", genRepositories).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/BuyerRepositoryBase.output")));
		assertEquals(removeWhitespaces(getEvent("SellerBase", genEntities).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/SellerBase.output")));
		assertEquals(removeWhitespaces(getEvent("SellerRepositoryBase", genRepositories).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/SellerRepositoryBase.output")));
		assertEquals(removeWhitespaces(getEvent("StockBase", genEntities).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/StockBase.output")));
		assertEquals(removeWhitespaces(getEvent("StockRepositoryBase", genRepositories).getFileContent()),
			removeWhitespaces(ResourceLoader.getResourceAsString("dynamo/plugins/maven/output/StockRepositoryBase.output")));
	}

	private CodeGenerationEvent getEvent(String fileName, List<CodeGenerationEvent> eventList) {
		CodeGenerationEvent foundEvent = null;
		for (CodeGenerationEvent event : eventList) {
			if (fileName.equals(event.getFileName())) {
				foundEvent = event;
				break;
			}
		}
		assertNotNull(foundEvent);
		return foundEvent;
	}

	private Entity buildSampleEntity() {
		Entity entity = new Entity("Dog");
		entity.setApplication(new Application("PetShop"));
		entity.addAttributeType("name", "String");
		entity.addAttributeAnnotation("name", "[@Column(unique = true)]");
		entity.addAttributeType("owner", "Owner");
		entity.addAttributeAnnotation("owner", "[@ManyToOne]");
		return entity;
	}

	private String removeWhitespaces(String str) {
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray()) {
			if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}