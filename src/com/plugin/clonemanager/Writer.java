package com.plugin.clonemanager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	public static void writer(SCCInstance temp) {
		try {
			String path = "\\My Output\\";
			path = CloneManagementPlugin.getAbsolutePath(path);
			FileWriter name = new FileWriter(path + temp.getSCCID() + ".txt", true);
			BufferedWriter out = new BufferedWriter(name);
			out.write("SCCID is " + temp.getSCCID() + "\n");
			//out.write("Length in tokens is " + temp.getLength_in_tokens() + "\n");
			//out.write("The number of clones found is " + temp.getClone_count() + "\n");
			out.write("File Number: " + temp.getFileID() + "\n");
			//out.write("File Name: " + Parser.getFilePath(temp.getFile_number()) + "\n");
			//out.write("Directory ID: " + Parser.getDirID(temp.getFile_number()) + "\n");
			//out.write("Directory Name: " + Parser.getDirPath(temp.getFile_number()) + "\n");
			out.write("Starting line is " + temp.getStartLine() + "\n");
			out.write("Starting Column: " + temp.getStartCol() + "\n");
			out.write("Ending line is " + temp.getEndLine() + "\n");
			out.write("Ending Column: " + temp.getEndCol() + "\n");
			out.write("**************************CODE STARTS HERE*************************************\n");
			out.write(temp.getCodeSegment() + "\n");
			out.write("*************************CODE ENDS HERE****************************************\n");
			out.write("\n");
			out.write("\n");
			out.write("\n");
			out.close();
		} catch (IOException ioe) {
			System.out.println("file writer error");
		}
	}
}
