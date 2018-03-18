package es.ucm.fdi.util;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ucm.fdi.launcher.ExampleMain;

public class FullTest {
	private static final String BASE = "src/main/resources/";
	
	@Test
	public void testError() throws Exception {
		try {
			ExampleMain.test(BASE + "examples/err");
			fail("Expected an exception while parsing a wrong-formed Ini File");
		} catch (IllegalArgumentException e) {
			//Ya contabamos con esta exception
		}
	}
	
	@Test
	public void testBasic() throws Exception {
		ExampleMain.test(BASE + "examples/basic");
	}
	
	/*@Test
	public void testAdvanced() throws Exception {
		ExampleMain.test(BASE + "examples/advanced");
	}
*/
}
