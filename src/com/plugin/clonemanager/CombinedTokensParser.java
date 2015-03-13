package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

class CombinedTokensParser extends Parser{


public void parse() {
		
		BufferedReader reader = null;
		reader=getBufferReader(Directories.COMBINEDTOKENSFILE);
		try {
			String line = null;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				if(line.charAt(0)!=' ')
				{
					line = " " + line;
				}
				String[] parts = line.split("\\s+");
				if(Integer.parseInt(parts[1])==9 || Integer.parseInt(parts[1])==99999)
				{
					int x=0;
				}
				if (isNumber(parts[2]) && isNumber(parts[3])
						&& isNumber(parts[4])) {
					SCCInstance instance = getSCCInstance(
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							Integer.parseInt(parts[4]),null);
					while(instance!=null)
					{
							if (parts[9].equals("STARTMETHOD")
									|| parts[9].equals("ENDMETHOD")
									|| parts[9].equals("ENDFILE")) {
								instance=null;
								continue;
							}
							if(parts[9].equals("?")){
								instance.addToken("/?");
								instance=getSCCInstance(
										Integer.parseInt(parts[2]),
										Integer.parseInt(parts[3]),
										Integer.parseInt(parts[4]),instance);
								continue;
							}
							instance.addToken(parts[9]);
							instance=getSCCInstance(
									Integer.parseInt(parts[2]),
									Integer.parseInt(parts[3]),
									Integer.parseInt(parts[4]),instance);
//							System.out.println("hahaha3");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





private static SCCInstance getSCCInstance(int filenumber,
			int linenumber, int colnumber, SCCInstance prevInstance) throws IOException {
		int index1=0;
		if(prevInstance!=null)
		{
			for (int i = 0; i < SampleHandler.f.getClones().size(); i++) {
				for (int j = 0; j < SampleHandler.f.getClones().get(i)
						.getSCCInstances().size(); j++) {
					if(SampleHandler.f.getClones().get(i).getSCCInstances().get(j)==prevInstance)
					{
						index1=i+1;
						if(j+1==SampleHandler.f.getClones().get(i).getSCCInstances().size())
						{
							index1=i+1;
						}
						break;
					}
				}
			}
		}
		for (int i = index1; i < SampleHandler.f.getClones().size(); i++) {
			for (int j = 0; j < SampleHandler.f.getClones().get(i)
					.getSCCInstances().size(); j++) {
				if (SampleHandler.f.getClones().get(i).getSCCInstances()
						.get(j).getFileID() == filenumber
						&& SampleHandler.f.getClones().get(i)
								.getSCCInstances().get(j).getStartLine() <= linenumber
						&& SampleHandler.f.getClones().get(i)
								.getSCCInstances().get(j).getEndLine() >= linenumber) {
					if (SampleHandler.f.getClones().get(i).getSCCInstances()
							.get(j).getStartLine() == linenumber) {
						if (SampleHandler.f.getClones().get(i)
								.getSCCInstances().get(j).getStartCol() <= colnumber) {
							return SampleHandler.f.getClones().get(i)
									.getSCCInstances().get(j);
						} else {
						}
					} else if (SampleHandler.f.getClones().get(i)
							.getSCCInstances().get(j).getEndLine() == linenumber) {
						if (SampleHandler.f.getClones().get(i)
								.getSCCInstances().get(j).getEndCol() >= colnumber) {
							return SampleHandler.f.getClones().get(i)
									.getSCCInstances().get(j);
						} else {
						}
					} else {
						return SampleHandler.f.getClones().get(i)
								.getSCCInstances().get(j);
					}
				}
			}
		}
		return null;
	}

	private static boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true && string.length() > 0;
	}




}