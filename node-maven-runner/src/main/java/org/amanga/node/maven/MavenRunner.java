package org.amanga.node.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * Class to run maven tasks
 * 
 * @author amanganiello90
 */
public class MavenRunner {

	/**
	 * method to execute maven task
	 * 
	 * @param mavenTask
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void execute(String mavenTask) throws IOException, InterruptedException {
		MavenRunner.internalExecute(mavenTask, null);

	}

	/**
	 * method to execute maven task
	 * 
	 * @param mavenTask
	 * @param directory
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void execute(String mavenTask, String directory) throws IOException, InterruptedException {
		MavenRunner.internalExecute(mavenTask, directory);

	}

	private static void internalExecute(String mavenTask, String directory) throws IOException, InterruptedException {
		Process process;
		String homeDir = System.getProperty("user.home");
		String operatingSystem = System.getProperty("os.name").toLowerCase();

		// for windows
		File mavenPathFile = new File("src/main/resources/apache-maven-3.3.9/bin/mvn.cmd");

		if (operatingSystem.contains("lin")) {
			mavenPathFile = new File("src/main/resources/apache-maven-3.3.9/bin/mvn");
		}
		// System.out.println(file.getAbsolutePath());

		if (directory != null) {
			// set directory
			homeDir = directory;

		}

		String[] commands = { mavenPathFile.getAbsolutePath(), "-f", homeDir, mavenTask };

		process = Runtime.getRuntime().exec(commands);

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			throw new IllegalStateException("error to run maven because the exit code is " + exitCode);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s;
		while ((s = reader.readLine()) != null) {
			System.out.println(s);
		}
		process.destroy();

	}

}
