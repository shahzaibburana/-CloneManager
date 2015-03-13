package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

public class ClonesParser extends Parser {

	public void parse() {
		
		BufferedReader reader = null;
		refresh();
		int count = 0;
		reader=getBufferReader(Directories.CLONESFILE);
		try {
			String line = null;
			SCC tempscc = null;
			SCCInstance tempclone = null;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("[; : . -]");
				switch (parts.length) {
				case 0:
					break;
				case 3:
					tempscc = new SCC();
					tempscc.setSCCID(Integer.parseInt(parts[0]));
					tempscc.setTokenCount(Integer.parseInt(parts[1]));
					tempscc.setSCCInstanceCount(Integer.parseInt(parts[2]));
					count = 0;
					break;
				case 5:
					count++;
					tempclone = new SCCInstance();
					tempclone.setSCCID(tempscc.getSCCID());
					tempclone.setFileNumber(Integer.parseInt(parts[0]));
					tempclone.setStartLine(Integer.parseInt(parts[1]));
					tempclone.setStartCol(Integer.parseInt(parts[2]));
					tempclone.setEndLine(Integer.parseInt(parts[3]));
					tempclone.setEndCol(Integer.parseInt(parts[4]));
					/*tempclone.setCode(getCodeSegment(
							getFile(tempclone.getFile_number(), tempclone),
							tempclone.getStart_line(),
							tempclone.getStart_col(), tempclone.getEnd_line(),
							tempclone.getEnd_col(),tempclone));*/
					tempclone.setCodeSegment(getCodeSegment(
							getFilePath(tempclone.getFileID()),
							tempclone.getStartLine(),
							tempclone.getStartCol(), tempclone.getEndLine(),
							tempclone.getEndCol(),tempclone));
					tempclone.setDirId(getDirID(tempclone.getFileID()));
					tempclone
							.setFilePath(getFilePath(tempclone.getFileID()));
//					Writer.writer(tempclone);
					tempscc.addSCCInstance(tempclone);
					if (count == tempscc.getSCCInstanceCount()) {
						SampleHandler.f.addSCC(tempscc);
						//CloneDB.insertSCC(tempscc);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static int getDirID(int file_number) {
		
		BufferedReader buffer = null;
		buffer=getBufferReader(Directories.FILESINFOFILE);
		try {
			String line = buffer.readLine();
			while (line != null) {
				String[] parts = line.split(",");
				if (Integer.parseInt(parts[0]) == file_number) {
					return Integer.parseInt(parts[1]);
				}
				line = buffer.readLine();
			}
		} catch (Exception e) {
		}
		try {
			buffer.close();
		} catch (IOException e) {
		}
		return 0;
	}
	
	public static String getDirPath(int file_number) {
		
		int dirId = getDirID(file_number);
		BufferedReader buffer = null;
		buffer=getBufferReader(Directories.DIRSINFOFILE);
		try {
			String line = buffer.readLine();
			while (line != null) {
				String[] parts = line.split(",");
				if (Integer.parseInt(parts[0]) == dirId) {
					return parts[1];
				}
				line = buffer.readLine();
			}
		} catch (Exception e) {
		}
		try {
			buffer.close();
		} catch (IOException e) {
		}
		return "Directory name not found";
	}

	

public static String getFilePath(int fid) throws IOException {
		
		BufferedReader buffer = null;
		buffer=getBufferReader(Directories.INPUTFILE);
		try {
			int index = 0;
			String line = null;
			while ((line = buffer.readLine()) != null) {
				if (index == fid) {
					return line;
				}
				index++;
			}
		} catch (Exception e) {
			System.out.println("Error finding file Name");
		}
		try {
			buffer.close();
		} catch (IOException e) {
		}
		return "N/A";
	}

public static String getCodeSegment(String path, int start_line,
		int start_col, int end_line, int end_col,SCCInstance inst) throws IOException {
//	if(inst.getSCCID()==8)
//	{
//		int x=0;
//	}
	File check = new File(path);
	FileReader frdr = null;
	BufferedReader buff = null;
	String data = "";
	if (check.exists()) {
		frdr = new FileReader(path);
		buff = new BufferedReader(frdr);
		
		
		
	} else if (!check.exists()) {
		String path2 = Directories.CHECKFILE;
		path2 = CloneManagementPlugin.getAbsolutePath(path2);
		frdr = new FileReader(path2);
		buff = new BufferedReader(frdr);
		System.out.println("File not found: " + path2);
	}
	int line_number = 0;
	try {
		String line = null;
		while ((line = buff.readLine()) != null) {
			line_number++;
			if(start_line == end_line)
			{
				if(line_number == start_line)
				{
					line=line.substring(start_col - 1, end_col);
					data += (line + "\n");
					frdr.close();
					buff.close();
					return data;
				}
			}
			else if (line_number >= start_line && line_number <= end_line) {
				if (line_number == start_line) {
					line = line.substring(start_col - 1);
				} else if (line_number == end_line) {
					if(inst != null)
					{
						String temp = line.substring(0, end_col);
						line=temp;
						
					}
					else
					{
						if(end_col<2)
						{
							line="";
						}
						else
						{
							line=line.substring(0,end_col);
						}
					}
				}
				data += (line + "\n");
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		frdr.close();
		buff.close();

	}
	return data;
}
	




}