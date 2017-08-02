package org.amanga.node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.jar.JarEntry;

import org.apache.commons.io.FileUtils;

public class NodeRunner {

	private String artifact;
	private String jarPath;
	private String fullArtifact;
	private String nodePath = NODE_FOLDER_PATH;
	private String MVN;
	private static final String NODE_JAR_PROJECT_VERSION = "1.0-SNAPSHOT";
	private static final String LOCAL_DEST_FOLDER = System.getProperty("user.dir") + "/tmp";
	private static final String NODE_VERSION = "node-v6.11.0";
	private static final String NODE_FOLDER_PATH = LOCAL_DEST_FOLDER + "/" + NODE_VERSION;

	// constructor
	public NodeRunner() throws IOException, InterruptedException {
		this.operatingSystem();
		// create new folder to clone
		new File(LOCAL_DEST_FOLDER).mkdir(); // this not run error if already
												// exist
		// Delete old folder
		FileUtils.forceDelete(new File(LOCAL_DEST_FOLDER));

		new File(LOCAL_DEST_FOLDER).mkdir();

		System.out.println("LOADING NodeRunner OBJECT..");
		this.downloadNode();

		this.extractJar(jarPath, LOCAL_DEST_FOLDER);

		File nodePathFile = new File(this.nodePath);
		nodePathFile.setExecutable(true);

		if (nodePathFile.exists() == false) {
			throw new IllegalStateException("Node execution file is not correctly downloaded and extracted");

		}
		System.out.println("NodeRunner OBJECT READY");

	};

	/**
	 * method to delete folder created by constructor (it must be called after
	 * all node script executions)
	 * 
	 * @throws IOException
	 */
	public void destroy() throws IOException {

		// create new folder to clone
		new File(LOCAL_DEST_FOLDER).mkdir(); // this not run error if already
												// exist
		// delete folder used to extract
		FileUtils.forceDelete(new File(LOCAL_DEST_FOLDER));

	}

	/**
	 * method to execute node script
	 * 
	 * @param scriptPath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void executeScript(String scriptPath) throws IOException, InterruptedException {
		if (new File(scriptPath).exists() == false) {
			throw new FileNotFoundException("Your script is not found");
		}
		if (new File(nodePath).exists() == false) {
			throw new IllegalStateException("Node execution file is not found");
		}

		System.out.println("Execute " + scriptPath + " node script:");

		System.out.println("-------------------------------------");
		Process process = Runtime.getRuntime().exec(this.nodePath + " " + scriptPath);

		int exitCode = process.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s;
		while ((s = reader.readLine()) != null) {
			System.out.println(s);
		}
		if (exitCode != 0) {
			throw new IllegalStateException(
					"error execute node script " + scriptPath + " ,the exit code is " + exitCode);
		}
		process.destroy();
		System.out.println("-------------------------------------");

	}

	// method to understand the O.S.
	private void operatingSystem() {
		String name = System.getProperty("os.name").toLowerCase();
		String architecture = System.getProperty("os.arch");
		
		if(System.getenv("MAVEN_HOME")!=null){
			this.MVN=System.getenv("MAVEN_HOME") + "/bin/mvn";
			
		}
		else if(System.getenv("M2_HOME")!=null){
			this.MVN=System.getenv("M2_HOME") + "/bin/mvn";
		}
		else{
			throw new IllegalStateException("you don't have a maven environment variable set " );
		}

		if (architecture.contains("64")) {
			this.artifact = "64";
		} else {
			this.artifact = "32";
		}

		if (name.contains("win")) {
			this.artifact = "win" + this.artifact;
			this.nodePath = this.nodePath + "/node.exe";

		} else if (name.contains("lin")) {
			this.artifact = "linux" + this.artifact;
			this.nodePath = this.nodePath + "/bin/node";

		}
		// for mac
		else {

		}

		String jarName = this.artifact + "-" + NODE_JAR_PROJECT_VERSION + ".jar";
		this.jarPath = LOCAL_DEST_FOLDER + "/" + jarName;
		this.fullArtifact = "org.amanga.node:" + artifact + ":" + NODE_JAR_PROJECT_VERSION + ":jar";

	}

	// method to download correct node jar for O.S.
	private void downloadNode() throws IOException, InterruptedException {

		String mavenTask = " dependency:get -DrepoUrl=http://repo.maven.apache.org/maven2/ -Dartifact=" + fullArtifact
				+ " -Dtransitive=false -Ddest=" + LOCAL_DEST_FOLDER;

		Process process = Runtime.getRuntime().exec(MVN + mavenTask);

		int exitCode = process.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s;
		while ((s = reader.readLine()) != null) {
			if (s.contains("[ERROR]")) {
				System.out.println(s);
			}
		}
		if (exitCode != 0) {
			throw new IllegalStateException("error download node version because the exit code is " + exitCode);
		}
		process.destroy();

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
