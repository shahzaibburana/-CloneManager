package com.plugin.clonemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.plugin.clonemanager.handlers.SampleHandler;

public class MCCTemplate {

	final String OUTPUT_PATH = "\\VCL_Output\\MCC_Template\\";
	final String OUTPUT_PATH_Temp = "\\VCL_Output\\MCC_Template_Temp\\";
	
	
	int curMCCID = -1;
	int curFileID = 0;
	int curMethodID = 0;
	int curVariableID = 0;
	ArrayList<String> FramePaths;
	ArrayList<Integer> MCC_Settings;
	ArrayList<MCCInstance> MCI_List;
	ArrayList<MCIContainer> MCI_Container_List;
	ArrayList<SCCInstance> SCCInstanceList;
	ArrayList<SCCTemplate> SCCTemplates;
	
	public MCCTemplate(int MCCID, MCC MCC)
	{
		SCCInstanceList = new ArrayList();
		FramePaths = new ArrayList();
		SCCTemplates = new ArrayList();
		MCC_Settings = new ArrayList();
		MCI_List = MCC.getMCCInstances();
		MCI_Container_List = new ArrayList();
		MCC_Settings.add(1); MCC_Settings.add(1);
		
		curMCCID = MCCID;
		
		extractSimpleClones(MCC);
	
		
		generateTemplate();
		generateTemplateTemp();
		
		
	}
	
	
	
	public void extractSimpleClones(MCC MCC)
	{
		boolean firstPass = true;
		ArrayList<String> baseFormat = MCI_List.get(0).getAnalysisDetails();
		for(int SCCID : MCC.getSCS())
		{
			if(!isIncluded(SCCID, baseFormat))
				continue;
			
			// Create frames for all required SCCIDs //
			String path = CloneManagementPlugin.getAbsolutePath(OUTPUT_PATH);
			String filename = SCCID + "_Frame.vcl";
			SCCTemplate template = null;
			if(!firstPass)
				MCC_Settings.set(1, 0);
			firstPass = false;
			SCC SimpleClone = findSCC(SCCID);
			if(SimpleClone != null)
				template = new SCCTemplate(SCCID, SampleHandler.f.sscList.get(SCCID), MCC_Settings);
			else
				System.out.println("Critical error, cannot find SCC!");
			SCCTemplates.add(template);
			FramePaths.add(path+filename);
		}
		// All Frames Of SCCs Obtained! //
		System.out.println("I have all the SCC_Templates!");
		
		// Spend A Little Time Extracting SCCID List From New Analysis List //
		int MCI_Index = 0;
		int counter = 0;
		for(MCCInstance MCI : MCI_List)
		{
			MCIContainer container = new MCIContainer(SCCTemplates);
			counter = 0;
			System.out.println("*******MCI #" + MCI_Index + "*******");			
			for(String codeSegment : MCI.getAnalysisDetails())
			{
				if(counter % 2 != 0)	// If Odd Index Encountered //
				{
					// Give This Portion To MCI_Container //
					container.mapIndexes(codeSegment);
				}
				counter++;
			}
			MCI_Container_List.add(container);
			MCI_Index++;
			System.out.println("************************");
		}
		System.out.println("Done processing");
	}
	
	public SCC findSCC(int SCCID)
	{
		ArrayList<SCC> SCCList = SampleHandler.f.sscList;
		for(SCC SimpleClone : SCCList)
		{
			if(SimpleClone.getSCCID() == SCCID)
				return SimpleClone;
		}
		return null;
	}

	public void generateTemplate()
	{
		String path = CloneManagementPlugin.getAbsolutePath(OUTPUT_PATH);
		
		curFileID = curMCCID;
		try {
			String SPC_PATH = curFileID + "_MCC_SPC.vcl";

			refresh(path+SPC_PATH);	// Delete files if they already exist //

			FileWriter spc_file = new FileWriter(path + SPC_PATH, true);
			BufferedWriter spc_output = new BufferedWriter(spc_file);

			// ====== START OF SPC ===== //
			// Specify SPC details //
						
			// The output filename //
			String fileName = "fileName";
			String fileNameValue = "MCCID_" + curMCCID + "_Inst";		// Can ask user to specify method name //
			
			setValues(spc_output, fileName, fileNameValue); //Step 1
			
			specifyMainBody(spc_output, fileName, fileNameValue);//Step 2
			
			RunVCL(".\\MCC_Template\\" + SPC_PATH); // Run VCL Processor

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	
	public void generateTemplateTemp(){
	
	

	String path = CloneManagementPlugin.getAbsolutePath(OUTPUT_PATH_Temp);
	
	curFileID = curMCCID;
	try {
		String SPC_PATH = curFileID + "_MCC_SPC.vcl";

		refresh(path+SPC_PATH);	// Delete files if they already exist //

		FileWriter spc_file = new FileWriter(path + SPC_PATH, true);
		BufferedWriter spc_output = new BufferedWriter(spc_file);

		// ====== START OF SPC ===== //
		// Specify SPC details //
					
		// The output filename //
		String fileName = "fileName";
		String fileNameValue = "MCCID_" + curMCCID + "_Inst";		// Can ask user to specify method name //
		
		setValues(spc_output, fileName, fileNameValue); //Step 1
		
		specifyMainBody(spc_output, fileName, fileNameValue);//Step 2
		
		RunVCL(".\\MCC_Template\\" + SPC_PATH); // Run VCL Processor

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	
}
	
	private void specifyMainBody(BufferedWriter spc_output, String fileName,
			String fileNameValue) throws IOException {
		
		ArrayList<String> BaseFormat = MCI_List.get(0).getAnalysisDetails();
		//int BaseFormatCounter = 0;
		
		// In While Loop //
		
		spc_output.write("\n#while ");
		
		specifyWhileVaribles(spc_output, BaseFormat);

		specifyFileName(spc_output, fileName);
		
		specifyFirstGappedRegion(spc_output, fileNameValue);
		
		specifyRemainingGappedRegions(spc_output, fileNameValue, BaseFormat);
		
		spc_output.write("#endwhile");
		spc_output.close();
	}

	private void specifyRemainingGappedRegions(BufferedWriter spc_output,
			String fileNameValue, ArrayList<String> BaseFormat)
			throws IOException {
		for(int index = 0; index < BaseFormat.size(); index++)
		{
			if(index % 2 != 0)	// Adapt XX_SCC_Frame
			{
				String FRAME_PATH = BaseFormat.get(index).split(",")[0] + "_SCC_Frame.vcl";
				spc_output.write("#adapt: " + "\"" + FRAME_PATH + "\"" + "\n");
				
				spc_output.write("	#select fileName\n");
				for(int numInst = 0; numInst < MCI_List.size(); numInst++)
				{
					spc_output.write("		#option " +  fileNameValue + "_" + numInst + "\n");
					spc_output.write("			#insert-after break_" + BaseFormat.get(index).split(",")[0] + ":");
					if(index+1 == BaseFormat.size()-1) // If Last Index 
					{
						spc_output.write(MCI_List.get(numInst).getAnalysisDetails().get(index+1));
					}
					else
					{
						String[] tempArray = MCI_List.get(numInst).getAnalysisDetails().get(index+1).split(",");
						System.out.println("temparray len: " + tempArray.length);
						if(tempArray.length > 1)
						{
							int len = tempArray[0].length() + tempArray[1].length() + tempArray[2].length() + tempArray[3].length() + tempArray[4].length() + 5;							
							spc_output.write(MCI_List.get(numInst).getAnalysisDetails().get(index+1).substring(len));								
						}
					}
					spc_output.write("			#endinsert\n");
					spc_output.write("		#endoption\n");
				}
				spc_output.write("	#endselect\n");
				spc_output.write("#endadapt\n");
			}				
		}
	}

	private void specifyFirstGappedRegion(BufferedWriter spc_output,
			String fileNameValue) throws IOException {
		spc_output.write("#break break_start\n");

		spc_output.write("	#select fileName\n");

		// Insert First Gapped Region //
		for(int numInst = 0; numInst < MCI_List.size(); numInst++)
		{
			spc_output.write("		#option " +  fileNameValue + "_" + numInst + "\n");
			spc_output.write("			#insert-after break_start:" + (MCI_List.get(numInst).getAnalysisDetails().get(0)));
			spc_output.write("		#endinsert\n");
			spc_output.write("		#endoption\n");
		}
		spc_output.write("	#endselect\n");
	}

	private void specifyFileName(BufferedWriter spc_output, String fileName)
			throws IOException {
		spc_output.write("fileName\n");				
		spc_output.write("#output " + "?@" + fileName + "?" + "\".java\"" + "\n");
	}

	private void specifyWhileVaribles(BufferedWriter spc_output,
			ArrayList<String> BaseFormat) throws IOException {
		for(SCCTemplate Template : SCCTemplates)
		{
			if(Template.DynamicTokens.size() == 0)
				continue;
			
			if(isIncluded(Template.curSCCID, BaseFormat) == false)
				continue;
			
			for(DynamicToken tk : Template.DynamicTokens)
			{
				if(tk.marked)
					continue;
				spc_output.write(tk.varName + ", "); 
			}
		}
	}

	private void setValues(BufferedWriter spc_output, String fileName,
			String fileNameValue) throws IOException {
		
		setFileNames(spc_output, fileName, fileNameValue);
		// Set Variables For Each SCC Instance //
		spc_output.write("% Here I will set all the place-holder variables\n\n");
		//int counter = 0;
		setPlaceHolderVariables(spc_output);
	}

	private void setPlaceHolderVariables(BufferedWriter spc_output)
			throws IOException {
		for(MCIContainer Container: MCI_Container_List)
		{
			for(String SCCInfo : Container.SCC_Order)
			{
				String[] InfoArray = SCCInfo.split(",");
				int SCCID = Integer.parseInt(InfoArray[0]);
				int TokenIndex = Integer.parseInt(InfoArray[1]);
				SCCTemplate Template = Container.getTemplate(SCCID);
				
				int index = 0;
				
				for(DynamicToken tk : Template.DynamicTokens)
				{
					if(tk.marked || tk.repeated)
						continue;
					spc_output.write("#set " + tk.varName + " = "); 
					for(int _index = 0; _index < MCI_List.size(); _index++)
					{
						spc_output.write("\"" + tk.tokenValues.get(_index) + "\"");
						if(_index != MCI_List.size()-1)
							spc_output.write(", ");
						else
							spc_output.write("\n");
					}
					tk.repeated=true;
				}
			}
		}
	}

	private void setFileNames(BufferedWriter spc_output, String fileName,
			String fileNameValue) throws IOException {
		spc_output.write("#set " + fileName + " = ");
		for(int _index = 0; _index < MCI_List.size(); _index++)
		{
			spc_output.write("\"" +  fileNameValue + "_" + _index + "\"");
			if(_index != MCI_List.size()-1)
				spc_output.write(", ");
			else
				spc_output.write("\n");
		}
	}
	
	public void RunVCL(String filename)
	{
		Initializer.ExecuteVCL(filename);
	}

	private static void refresh(String SPC_path) {
		File f1 = new File(SPC_path);
		f1.delete();
	}
	
	public static boolean isIncluded(int SCCID, ArrayList<String> BaseFormat)
	{
		boolean isSet = false;
		for(int index = 0; index < BaseFormat.size(); index++)
		{
			if(index % 2 != 0)
			{
				 if(Integer.parseInt(BaseFormat.get(index).split(",")[0]) == SCCID)
					 isSet = true;
			}
		}
		return isSet;
	}
	
	public static String RemoveLine(String inputText) {
		   Pattern p;
		   Matcher m;
		   System.out.println(" Input Text : " + inputText);
		   p = Pattern.compile("\n");
		   m = p.matcher(inputText);
		   String str = m.replaceAll("");
		   System.out.println(" OUtput Text: " + str);
		   return str;
	}

}
