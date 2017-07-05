package org.amanga.node;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class NodeRunner {

	private String artifact;
	private String localDestFolder;

	// constructor
	public NodeRunner() throws IOException {
		artifact = this.operatingSystem();

		// define localDestFolder to write, read and delete

		this.downloadNode(artifact);

		// extract jar
		this.extractJar(localDestFolder, localDestFolder);

	};

	public void executeScript(String scriptPath) throws IOException {
		
		String nodeFolderPath="";
		if (artifact.contains("linux")) {

		} else if (artifact.contains("win")) {

		}

		else {
			//mac
		}
		
		Runtime.getRuntime().exec(nodeFolderPath +" " + scriptPath);

		// delete folder used to extract

	}

	// method to understand the O.S.
	private String operatingSystem() {
		String name = System.getProperty("os.name").toLowerCase();
		String architecture = System.getProperty("os.arch");

		return "artifact";
	}

	// method to download correct node jar for O.S.
	private void downloadNode(String artifact) {
		// instantiate maven

		// "mvn dependency:get -DrepoUrl=http://repo.maven.apache.org/maven2/
		// -Dartifact=org.springframework:spring-context:4.0.4.RELEASE:jar
		// -Dtransitive=false -Ddest=localDestFolder"
		// il jar giusto (con node portable per la piattaforma)

	}

	/**
	 * method to extract jar file
	 * 
	 * @param jarFile
	 * @param destDir
	 * @throws IOException
	 */
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
