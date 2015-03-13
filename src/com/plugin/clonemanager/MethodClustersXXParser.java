package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

class MethodClustersXXParser extends Parser{




public void parse() {
		// TODO Auto-generated method stub
		BufferedReader reader = null;
		reader=getBufferReader(Directories.METHODCLUSTERSXX);
		try {
			String line = "";
			ArrayList<Integer> SCCIDs = null;
			int mccId = -99;
			int instances = -99;
			int methodId = -99;
			double coverage = -99.00;
			while((line = reader.readLine())!=null)
			{
				String [] parts = line.split("[; ,]");
				switch(parts.length){
				case 2:
					mccId = Integer.parseInt(parts[0]);
					instances = Integer.parseInt(parts[1]);
					line = reader.readLine();
					parts = line.split(",");
					SCCIDs = new ArrayList<>();
					for(int i=0;i<parts.length;i++)
					{
						SCCIDs.add(Integer.parseInt(parts[i]));
					}
					break;
				case 3:
					methodId = Integer.parseInt(parts[0]);
					coverage = Double.parseDouble(parts[2]);

					Method method = null;
					for(int i=0;i<SampleHandler.f.methodList.size();i++)
					{
						if(SampleHandler.f.methodList.get(i).getMethodId()==methodId)
						{
							method = SampleHandler.f.methodList.get(i);
						}
					}
					boolean added = false;
					MCCInstance temp = new MCCInstance(methodId,
							coverage, method);
					MCC temp2 = new MCC(mccId, instances,
							SCCIDs);
					for (int i = 0; i < SampleHandler.f.mccList.size(); i++) {
						if (SampleHandler.f.mccList.get(i).getMCCID() == mccId) {
							SampleHandler.f.mccList.get(i)
									.addMCCInstance(temp);
							added = true;
						}
					}
					if (!added) {
						temp2.addMCCInstance(temp);
						SampleHandler.f.mccList.add(temp2);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}







}