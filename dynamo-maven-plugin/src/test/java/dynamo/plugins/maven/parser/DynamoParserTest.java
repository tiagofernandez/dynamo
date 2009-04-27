package dynamo.plugins.mavenparser;

import static org.testng.Assert.*;

import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.testng.annotations.Test;

import dynamo.plugins.mavenmodel.Application;
import dynamo.plugins.mavenmodel.Entity;
import dynamo.core.io.ResourceLoader;

/**
 * Test case for DynamoParser.
 *
 * @author Tiago Fernandez
 * @since 0.3
 */
public class DynamoParserTest {

	@Test
	public void parseDSL() throws Exception {
		// Parse the DSL sample
		CharStream input = new ANTLRStringStream(ResourceLoader.getResourceAsString("Market.dsl"));
		DynamoLexer lexer = new DynamoLexer(input);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		DynamoParser parser = new DynamoParser(tokens);

		// Check the application
		Application app = parser.aplication();
		assertNotNull(app);
		assertEquals(app.getName(), "Market");

		// Check the entities
		List<Entity> entities = app.getEntities();
		assertNotNull(entities);
		assertEquals(entities.size(), 3);

		// Check the 1st entity
		Entity firstEntity = entities.get(0);
		Map<String, String> firstEntityAttr = firstEntity.getAttributeTypes();
		Map<String, String> firstEntityAnn = firstEntity.getAttributeAnnotations();
		assertEquals(firstEntity.getName(), "Buyer");
		assertEquals(firstEntityAttr.size(), 4);
		assertTrue(firstEntityAttr.containsKey("id"));
		assertEquals(firstEntityAttr.get("id"), "Long");
		assertTrue(firstEntityAttr.containsKey("version"));
		assertEquals(firstEntityAttr.get("version"), "Long");
		assertTrue(firstEntityAttr.containsKey("name"));
		assertEquals(firstEntityAttr.get("name"), "String");
		assertTrue(firstEntityAttr.containsKey("stocks"));
		assertEquals(firstEntityAttr.get("stocks"), "List<Stock>");
		assertEquals(firstEntityAnn.size(), 4);
		assertTrue(firstEntityAnn.containsKey("id"));
		assertEquals(firstEntityAnn.get("id"), "@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)");
		assertTrue(firstEntityAnn.containsKey("version"));
		assertEquals(firstEntityAnn.get("version"), "@javax.persistence.Version");
		assertTrue(firstEntityAnn.containsKey("name"));
		assertEquals(firstEntityAnn.get("name"), "@Column(nullable = false, unique = true, length = 50)");
		assertTrue(firstEntityAnn.containsKey("stocks"));
		assertEquals(firstEntityAnn.get("stocks"), "@ManyToMany(fetch = FetchType.LAZY)");

		// Check the 2nd entity
		Entity secondEntity = entities.get(1);
		Map<String, String> secondEntityAttr = secondEntity.getAttributeTypes();
		Map<String, String> secondEntityAnn = secondEntity.getAttributeAnnotations();
		assertEquals(secondEntity.getName(), "Seller");
		assertEquals(secondEntityAttr.size(), 4);
		assertTrue(secondEntityAttr.containsKey("id"));
		assertEquals(secondEntityAttr.get("id"), "Long");
		assertTrue(secondEntityAttr.containsKey("version"));
		assertEquals(secondEntityAttr.get("version"), "Long");
		assertTrue(secondEntityAttr.containsKey("name"));
		assertEquals(secondEntityAttr.get("name"), "String");
		assertTrue(secondEntityAttr.containsKey("stocks"));
		assertEquals(secondEntityAttr.get("stocks"), "List<Stock>");
		assertEquals(secondEntityAnn.size(), 4);
		assertTrue(secondEntityAnn.containsKey("id"));
		assertEquals(secondEntityAnn.get("id"), "@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)");
		assertTrue(secondEntityAnn.containsKey("version"));
		assertEquals(secondEntityAnn.get("version"), "@javax.persistence.Version");
		assertTrue(secondEntityAnn.containsKey("name"));
		assertEquals(secondEntityAnn.get("name"), "@Column(nullable = false, unique = true, length = 50)");
		assertTrue(secondEntityAnn.containsKey("stocks"));
		assertEquals(secondEntityAnn.get("stocks"), "@ManyToMany(fetch = FetchType.LAZY)");

		// Check the 3rd entity
		Entity thirdEntity = entities.get(2);
		Map<String, String> thirdEntityAttr = thirdEntity.getAttributeTypes();
		Map<String, String> thirdEntityAnn = thirdEntity.getAttributeAnnotations();
		assertEquals(thirdEntity.getName(), "Stock");
		assertEquals(thirdEntityAttr.size(), 5);
		assertTrue(thirdEntityAttr.containsKey("id"));
		assertEquals(thirdEntityAttr.get("id"), "Long");
		assertTrue(thirdEntityAttr.containsKey("version"));
		assertEquals(thirdEntityAttr.get("version"), "Long");
		assertTrue(thirdEntityAttr.containsKey("code"));
		assertEquals(thirdEntityAttr.get("code"), "String");
		assertTrue(thirdEntityAttr.containsKey("marketValue"));
		assertEquals(thirdEntityAttr.get("marketValue"), "Double");
		assertTrue(thirdEntityAttr.containsKey("lastUpdate"));
		assertEquals(thirdEntityAttr.get("lastUpdate"), "Date");
		assertEquals(thirdEntityAnn.size(), 5);
		assertEquals(thirdEntityAnn.get("id"), "@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = GenerationType.AUTO)");
		assertTrue(thirdEntityAnn.containsKey("version"));
		assertEquals(thirdEntityAnn.get("version"), "@javax.persistence.Version");
		assertTrue(thirdEntityAnn.containsKey("code"));
		assertEquals(thirdEntityAnn.get("code"), "@Column(nullable = false, unique = true, length = 5)");
		assertTrue(thirdEntityAnn.containsKey("marketValue"));
		assertEquals(thirdEntityAnn.get("marketValue"), "@Basic");
		assertTrue(thirdEntityAnn.containsKey("lastUpdate"));
		assertEquals(thirdEntityAnn.get("lastUpdate"), "@Transient");
	}

}