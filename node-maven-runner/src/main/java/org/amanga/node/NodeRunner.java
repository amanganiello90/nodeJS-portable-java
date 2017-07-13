package org.amanga.node;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;

import org.apache.commons.io.FileUtils;

public class NodeRunner {

	private String artifact;
	private static final String LOCAL_DEST_FOLDER = System.getProperty("user.dir") + "/target";
	private static final String NODE_VERSION = "node-v6.11.0";
	private static final String NODE_FOLDER_PATH = LOCAL_DEST_FOLDER + "/" + NODE_VERSION;

	// constructor
	public NodeRunner() throws IOException {
		artifact = this.operatingSystem();

		// create new folder to clone
		new File(LOCAL_DEST_FOLDER).mkdir(); // this not run error if already
												// exist
		// Delete old folder
		FileUtils.forceDelete(new File(LOCAL_DEST_FOLDER));

		new File(LOCAL_DEST_FOLDER).mkdir();

		// define localDestFolder to write, read and delete

	};

	/**
	 * method to execute node script
	 * 
	 * @param scriptPath
	 * @throws IOException
	 */
	public void executeScript(String scriptPath) throws IOException {

		this.downloadNode(artifact);
		File folder = new File(NODE_FOLDER_PATH);

		if (folder.exists()) {

		} else {
			// extract jar
			this.extractJar(LOCAL_DEST_FOLDER, LOCAL_DEST_FOLDER);
		}

		String nodePath = "";
		if (artifact.contains("linux")) {
			nodePath = "/bin/node";

		} else if (artifact.contains("win")) {
			nodePath = "/node.exe";
		}

		else {
			// mac
		}

		Runtime.getRuntime().exec(NODE_FOLDER_PATH + nodePath + " " + scriptPath);

		// delete folder used to extract
		FileUtils.forceDelete(new File(LOCAL_DEST_FOLDER));

	}

	// method to understand the O.S.
	private String operatingSystem() {
		String name = System.getProperty("os.name").toLowerCase();
		String architecture = System.getProperty("os.arch");

		return "linux64";
	}

	// method to download correct node jar for O.S.
	private void downloadNode(String artifact) {
		// instantiate maven

		// "mvn dependency:get -DrepoUrl=http://repo.maven.apache.org/maven2/
		// -Dartifact=org.springframework:spring-context:4.0.4.RELEASE:jar
		// -Dtransitive=false -Ddest=localDestFolder"
		// il jar giusto (con node portable per la piattaforma)

	}

	private void extractJar(String jarFile, String destDir) throws IOException {
		@SuppressWarnings("resource")
		java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile);
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			java.util.jar.JarEntry file = (java.util.jar.JarEntry) entries.nextElement();

			// create another folders
			String folders[] = file.toString().split("/");
			if (folders.length > 1) {
				String folder = "";
				for (int i = 0; i < folders.length - 1; i++) {
					folder = folder + "/" + folders[i];
				}
				java.io.File f = new java.io.File(destDir + java.io.File.separator + folder);
				f.mkdirs();
			}

			java.io.File f = new java.io.File(destDir + java.io.File.separator + file.getName());

			if (file.isDirectory()) { // if its a directory, create it
				f.mkdir();

				continue;
			}
			java.io.InputStream is = jar.getInputStream(file); // get the input
																// stream
			java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
			while (is.available() > 0) { // write contents of 'is' to 'fos'
				fos.write(is.read());
			}
			fos.close();
			is.close();
		}

	}

}
