package com.input;

import java.io.File;

import com.gui.driver;

public class DirectoryFiles {
	
	File dir;
	
	boolean makeHeading = true;
	
	public DirectoryFiles(File f){
		getDir(f);
		listFilesForFolder(dir);
	}
	
	private void getDir(File f){
		dir = new File(f.getParent());
	}
	
	private void listFilesForFolder(File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	    	if(fileEntry.toString().endsWith(".TXR")){
	            ProcessTXR(fileEntry);
	    	}
	    }
	    driver.infoBox();
	}
	
	private void ProcessTXR(File f){
		
		InputTXR a = new InputTXR(f, makeHeading);
		a.makeHeadingsFullDir();
		makeHeading = false;
	}

}
