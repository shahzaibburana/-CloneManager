package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

class ClonesByMethodParser extends Parser{


public void parse()
		{
		// TODO Auto-generated method stub
		BufferedReader reader = null;
		reader=getBufferReader(Directories.CLONESBYMETHODFILE);
		try {
			String line = null;
			int line_number = 0;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] parts = line.split(",");
					for (int i = 0; i < parts.length; i++) {
						SampleHandler.f.getMethods().get(line_number)
								.addToSCCList(Integer.parseInt(parts[i]));
					}
				}
				line_number++;
			}
		} catch (Exception e) {
			System.out.println("Error1");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}





}