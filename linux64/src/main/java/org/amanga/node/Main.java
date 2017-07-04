package org.amanga.node;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//String homeDirectory = System.getProperty("user.home");
		Process process;
		
		File file = new File("src/main/resources/bin/script.sh");
		System.out.println(file.getAbsolutePath());
		
		String[] commands = {file.getAbsolutePath(),"tess" };
		
		
		    process = Runtime.getRuntime()
		      .exec(commands);
		    
		  
		
		  
		    int exitCode = process.waitFor();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(        
		            process.getInputStream()));                                          
		        String s;                                                                
		        while ((s = reader.readLine()) != null) {                                
		          System.out.println( s);                             
		        }  
		        process.destroy();
		        System.out.println( "exit code: "+ exitCode);   

	}
	
	public void path (){
		
		
	}

}
