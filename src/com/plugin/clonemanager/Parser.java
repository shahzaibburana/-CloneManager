package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

abstract public class Parser {

	public Parser() {

	}
	
	abstract public void parse();
	
	static void refresh() {
		for (int i = 0; i < 115; i++) {
			String path = CloneManagementPlugin.getAbsolutePath(".\\My Output\\");
			File f = new File(path + i + ".txt");
			f.delete();
		}

	}

	protected static BufferedReader getBufferReader(String filename){
		
		FileReader frdr= null;
		BufferedReader reader=null;
		
		try {
			String path = filename;
			path = CloneManagementPlugin.getAbsolutePath(path);
			frdr = new FileReader(path);
			reader = new BufferedReader(frdr);
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}	
		
		return reader;
	}
	
	

			


	
	
	/*
	public static String getFile(int file_number2, SCCInstanceCustom sccinstancecustom) {
		
		String name = null;
		SCCInstanceCustom temp= sccinstancecustom;
		temp.setFile_number(0);
		BufferedReader reader = null;
		reader=getContent(Directories.INPUTFILE);
		try {
			String line = reader.readLine();
			while (line != null) {
				if (temp.getFile_number() == file_number2) {
					name = line;
					// System.out.println(name);
					reader.close();
					return name;
				}
				temp.setFile_number(temp.getFile_number() + 1);
				line = reader.readLine();
			}
		} catch (Exception e) {
			System.out.println("Error finding file Name");
		}
		try {
			reader.close();
		} catch (IOException e) {
		}
		System.out.println("File not found");
		return "File not found";
	}
*/
	
	
}
