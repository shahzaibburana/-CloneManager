package com.plugin.clonemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.plugin.clonemanager.handlers.SampleHandler;

public class Processor {

	public ArrayList<SCC> sscList;
	public ArrayList<Method> methodList;
	public ArrayList<MCC> mccList;

	public Processor() {
		sscList = new ArrayList<>();
		methodList = new ArrayList<>();
		mccList = new ArrayList<>();
	}

	public void run() throws Exception {
		
		System.out.println("Refreshing files...");
		Parser.refresh();
		System.out.println("Parsing clones.txt...");
		getInstance("ClonesParser").parse();
		System.out.println("Parsing combinedtokens.txt...");
		getInstance("CombinedTokensParser").parse();
		System.out.println("Parsing methodsinfo.txt...");
		getInstance("MethodsInfoParser").parse();
		System.out.println("Parsing clonesbymethod.txt...");
		getInstance("ClonesByMethodParser").parse();
		System.out.println("Updating methods with code....");
		//Parser.updateMETHODS(METHODS);
		updateMETHODS(methodList);
		System.out.println("Parsing Method Clusters...");
		getInstance("MethodClustersXXParser").parse();
		// System.out.println("Parsing finished....Displaying data...");
		populateSCCs();
		MCCsAnalyser.analyze(SampleHandler.f.mccList);
	 	System.out.println("***** TESTING ANALYZER NOW , CHECK OUTPUT FOLDER ********...");
//		testAnalyzer();
		System.out.println("***** ANALYZER TESTING FINISHED ******");
		//display(SCCCLONES);
//		 DisplayMethods();
//		 DisplayMethodClones();
		System.out.println("All Done....Starting GUI");
	}
	

	
	
	private static Parser getInstance(String className){
		Parser parser = null;
		String classPath;
		classPath="com.plugin.clonemanager.".concat(className);
		try {
			parser = (Parser) Class.forName(classPath).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parser;
	}

	private void testAnalyzer() throws IOException {
		String path = "\\My Output\\";
		path = CloneManagementPlugin.getAbsolutePath(path);
		File f1 = new File(path+"test2.txt");
		File f2 = new File(path+"test1.txt");
		File f3 = new File(path+"test3.txt");
		f1.delete();
		f3.delete();
		f2.delete();
		for (MCC mc : mccList) {
			for (MCCInstance mci : mc.getMCCInstances()) {
				mci.DisplayAnalysisDetails();
			}
		}
	}

	void populateSCCs() {
		for (int i = 0; i < SampleHandler.f.mccList.size(); i++) {

			for (int j = 0; j < SampleHandler.f.mccList.get(i)
					.getMCCInstances().size(); j++) {
				for (int k = 0; k < SampleHandler.f.mccList.get(i)
						.getSCS().size(); k++) {
					int SCID = SampleHandler.f.mccList.get(i).getSCS()
							.get(k);
					int fnum = SampleHandler.f.mccList.get(i)
							.getMCCInstances().get(j).getMethod()
							.getFileId();
					String name = SampleHandler.f.mccList.get(i)
							.getMCCInstances().get(j).getMethod().getMethodName();
					int sl = SampleHandler.f.mccList.get(i)
							.getMCCInstances().get(j).getMethod()
							.getStartLine();
					int el = SampleHandler.f.mccList.get(i)
							.getMCCInstances().get(j).getMethod()
							.getEndLine();
					SCCInstance add = getSCCInst(SCID, fnum, sl, el);
					SampleHandler.f.mccList.get(i).getMCCInstances().get(j)
							.addSCCs_Contained(add);
				}
			}
		}
	}

	private SCCInstance getSCCInst(int sCCID, int fid, int sl, int el) {
		for (int i = 0; i < SampleHandler.f.sscList.size(); i++) {
			for (int j = 0; j < SampleHandler.f.sscList.get(i)
					.getSCCInstances().size(); j++) {
				int SCCIDin = SampleHandler.f.sscList.get(i).getSCCID();
				int fnum = SampleHandler.f.sscList.get(i)
						.getSCCInstances().get(j).getFileID();
				int start = SampleHandler.f.sscList.get(i)
						.getSCCInstances().get(j).getStartLine();
				int end = SampleHandler.f.sscList.get(i).getSCCInstances()
						.get(j).getEndLine();
				if (sCCID == SCCIDin && fnum == fid && sl <= start && el >= end) {
					return SampleHandler.f.sscList.get(i)
							.getSCCInstances().get(j);
				}
			}
		}
		return null;
	}

	public void DisplayMethodClones() {
		for (int i = 0; i < mccList.size(); i++) {
			if (mccList.get(i).getMCCID() == 1) {
				for (int j = 0; j < mccList.get(i).getMCCInstances().size(); j++) {
					System.out.println();
					System.out.println(mccList.get(i).getMCCInstances().get(j).getMethod().getFilePath());
					System.out
							.println("*********************Method Code****************");
					System.out.println(mccList.get(i).getMCCInstances()
							.get(j).getMethod().getMethodName());
					System.out.println(mccList.get(i).getMCCInstances()
							.get(j).getMethod().getCodeSegment());
					System.out
							.println("*********************END Code****************");
					System.out.println();
				}
			}
		}
	}

	private static void display(ArrayList<SCC> SCCinstances) {
		for (int i = 0; i < SCCinstances.size(); i++) {
				if (SCCinstances.get(i).getSCCID() == 13) {
					for (int j = 0; j < SCCinstances.get(i).getSCCInstances().size(); j++) {
					System.out.println(SCCinstances.get(i).getSCCInstances()
							.get(j).getFileID()
							+ " "
							+ SCCinstances.get(i).getSCCInstances().get(j)
									.getCodeSegment()
							+ " "
							+ SCCinstances.get(i).getSCCInstances().get(j)
									.getStartLine()
							+ " "
							+ SCCinstances.get(i).getSCCInstances().get(j)
									.getEndLine());
					for(String str : SCCinstances.get(i).getSCCInstances().get(j).getTokens())
					{
						System.out.println("hehe " + str);
					}
					System.out.println(i+ " " + j);
				}
			}
		}
				
	}

	public ArrayList<SCC> getClones() {
		return sscList;
	}

	public ArrayList<Method> getMethods() {
		return methodList;
	}

	public void addSCC(SCC temp) {
		sscList.add(temp);
	}

	public void addMethod(Method m) {
		methodList.add(m);
	}

	public void genSCCTemplate(int SCCID) {
		SCCTemplate vcl = null;
		for (SCC SC : sscList) {
			if (SC.getSCCID() == SCCID) {
				System.out
						.println("About to generate SCC Template for SCCID: "
								+ SCCID);
				vcl = new SCCTemplate(SCCID, SC, new ArrayList<Integer>());
				return;
			}
		}
		System.out.println("Could not find specified SCCID!");
	}

	public void genMCCTemplate(int MCCID) {
		MCCTemplate vcl = null;
		for (MCC MC : mccList) {
			if (MC.getMCCID() == MCCID) {
				System.out
						.println("About to generate MCC Template for MCCID: "
								+ MCCID);
				vcl = new MCCTemplate(MCCID, MC);
				return;
			}
		}
		System.out.println("Could not find specified MCCID!");
	}

	public void DisplayMethods() {
		System.out.println("size = " + methodList.size());
		for (Method m : methodList) {
			// System.out.println(m.getCode());
			m.displaySCCList();
		}
		System.out.println();
	}
	
	private static String getCodeSegment(String path, int start_line,
			int end_line) throws Exception {
		// TODO Auto-generated method stub
		FileReader frdr = null;
		BufferedReader reader = null;
		String data="";
		frdr = new FileReader(path);
		reader = new BufferedReader(frdr);
		
		String line;
		int linenumber=0;
		while((line=reader.readLine())!=null)
		{
			if(linenumber>=start_line-1 && linenumber<=end_line)
			{
				data+=line + "\n";
			}
			linenumber++;
		}
		reader.close();
		return data;
	}

			
	public static void updateMETHODS(ArrayList<Method> mETHODS)
			throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < mETHODS.size(); i++) {
			int fnum = mETHODS.get(i).getFileId();
			String fname = ClonesParser.getFilePath(fnum);
			int start_line = mETHODS.get(i).getStartLine();
			int end_line = mETHODS.get(i).getEndLine();
			String code_set = getCodeSegment(fname, start_line, end_line);
			mETHODS.get(i).setCodeSegment(code_set);
		}

	}
}
