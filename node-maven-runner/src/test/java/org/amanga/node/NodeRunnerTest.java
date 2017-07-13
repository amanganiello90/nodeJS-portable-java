package org.amanga.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * JUnit Class for NodeRunnerTest
 * 
 * @author amanganiello90
 */
public class NodeRunnerTest {

	private static NodeRunner node;

	@BeforeClass
	public static void initObjects() throws IOException, InterruptedException {

		System.out.println("Define node object once");
		node = new NodeRunner();
	}

	@Test
	public void executeNodeScriptFirstTest() throws IOException, InterruptedException {

		File test = new File("src/test/resources/test.js");
		node.executeScript(test.getAbsolutePath());

	}

	@Test
	public void executeNodeScriptSecondTest() throws IOException, InterruptedException {

		File test = new File("src/test/resources/test.js");
		node.executeScript(test.getAbsolutePath());

	}
	

	@Test(expected = FileNotFoundException.class)
	public void executeNodeScriptNotFoundTest() throws IOException, InterruptedException {

		File test = new File("src/test/resources/testNotExist.js");
		node.executeScript(test.getAbsolutePath());

	}

}
