package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

class MethodsInfoParser extends Parser{



public void parse()
			/*throws IOException*/ {
		// TODO Auto-generated method stub
		BufferedReader reader = null;
		reader=getBufferReader(Directories.METHODSINFOFILE);
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				Method m = new Method(parts[2],
						Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
						Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
						Integer.parseInt(parts[5]));
				SampleHandler.f.addMethod(m);
			}
		} catch (Exception e) {
			System.out.println("Error2");
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