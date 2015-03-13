package com.plugin.clonemanager.wizards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
	
	final File folder;
	//String folderName = "D:\\Academia\\03.1 - RA\\plugin2.4\\CloneManager\\vcl_output\\MCC_Template_Temp";
	
	String folderName;
	
	
	File [] listOfAllFiles;
	ArrayList<String> listOfVCLFiles = new ArrayList<String>();
	ArrayList<String> VCLFilesPaths = new ArrayList<String>();
	
	/**
	 * Use a BufferedReader to read the file line-by-line using readLine(). Then
	 * split each line on whitespace using String.split("\\s") and the size of
	 * the resulting array is the total words count.
	 */
	
	public  FileHandler(String path){

		
		folderName = path;
		
		folder = new File(folderName);
		getFilesList();
		getFilesPaths();
		
}	
	

	/*********** Gives names of all VCL files in VCL output folder ************/
	public void getFilesList(){

		 listOfAllFiles = folder.listFiles();
		
		for (File file : listOfAllFiles) {
		    if (file.isFile()) {
		    	if(file.getName().toLowerCase().endsWith(".vcl")){
		    		listOfVCLFiles.add(file.getName());
		    		
		    	
		       
		    	}
		    }
		}
				
	}
	
	/*********** Gives full path of all VCL files ************/
	public void getFilesPaths(){
		for (int i=0; i<listOfVCLFiles.size(); i++){
			VCLFilesPaths.add(folderName+ "\\" +listOfVCLFiles.get(i));
		}
				
	}
	
	/*********** Get variable names from VCL files ************/
	public ArrayList<String> getVariableNames() {
		File file;
		ArrayList<String> variableNamesList = new ArrayList<String>();
		for (int i=0; i<VCLFilesPaths.size(); i++){
			try {
				file = new File(VCLFilesPaths.get(i));
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				
				while (line != null) {
					line = line.replaceAll("\\t", "");
					String[] parts = line.split(" ");
					for (int j=0; j<parts.length;  j++) {
						if (parts[j].equals("#set")){
							if (!parts[j+1].equalsIgnoreCase("filename")){
								variableNamesList.add(parts[j+1]);				
							}
						}
					}
					line = br.readLine();
				}
				br.close();
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (variableNamesList.isEmpty()){
			variableNamesList.add(" ");
		}
		
		return variableNamesList;

	}

	/*********** Get VCL file's code ************/
	public ArrayList<String> getFileContents(String fileName){
		File file1 = new File(fileName);
		ArrayList<String> fileContents = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(file1);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while (line != null) {
				fileContents.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContents;
	}
	
	/*********** Updates VCL file's code ************/
	public void updateFileContents(ArrayList<String> newVariableNames, String fileName){
		
		ArrayList<String> fileContents = new ArrayList<String>(getFileContents(fileName));
		ArrayList<String> oldVariableNames = new ArrayList<String>(getVariableNames());
		ArrayList<String> newFileContents = new ArrayList<String>();
		
		
		String line ="";	
		for (int i=0; i<fileContents.size(); i++){
			
			 line = fileContents.get(i);
			 for (int j=0; j<oldVariableNames.size(); j++){
			 line = line.replaceAll(oldVariableNames.get(j), newVariableNames.get(j));
			 }
			newFileContents.add(line);
		}
		try {
			FileWriter writer = new FileWriter(fileName); 
			for(String str: newFileContents) {
			  writer.write(str);
			  writer.write('\n');
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
