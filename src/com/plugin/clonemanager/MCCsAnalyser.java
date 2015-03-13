package com.plugin.clonemanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.plugin.clonemanager.handlers.SampleHandler;

public class MCCsAnalyser{
	
	public MCCsAnalyser() throws IOException
	{
		//Analyze(SampleHandler.f.MCCCLONES); // Analyse the universal list of all method clones
	}
	
	public static void analyze(ArrayList<MCC> methodclones) throws IOException // Analyse the universal list of all method clones
	{
		for( MCC mc : methodclones)
		{
			analyzeMethodClone(mc); // Now Analyse each Method clone set ( MCC CLUSTER )
		}
	}
	
	private static void analyzeMethodClone(MCC mc) throws IOException
	{
		ArrayList<MCCInstance> methodcloneinstances = mc.getMCCInstances();
		for( MCCInstance mci : methodcloneinstances)
		{
			sortMethodCloneInstance(mci);
			analyzeForOverlaps(mci); // check all the partial,complete overlaps for this specific instance
		}
		stabilizeAdjacencyMartix(mc);
		for( MCCInstance mci : methodcloneinstances)
		{
			mci.analyzeMethodCloneInstance(mc); // Now analyze each Method clone instance
		}
	}
	
	private static void analyzeForOverlaps(MCCInstance mci) {
		// TODO Auto-generated method stub
		int[][] temp=new int[mci.getSCCs().size()][mci.getSCCs().size()];
		mci.setAdjeceny_Matrix_For_Overlaps(temp);
		adjencyMatrixInitializer(mci.getAdjeceny_Matrix_For_Overlaps(),mci);
	}

	private static void adjencyMatrixInitializer(int[][] adjeceny_Matrix_For_Overlaps, MCCInstance mci) {
		// TODO Auto-generated method stub
		for(int i = 0; i < mci.getSCCs().size();i++)
		{
			for(int j = i ; j < mci.getSCCs().size();j++)
			{
				mci.getSCCs().get(i).compareSCCInstances(adjeceny_Matrix_For_Overlaps, mci.getSCCs().get(j),i,j);
			}
		}
	}

	public static boolean checkInBetweenSCCInstances(MCCInstance mci, int index,
			SCCInstance prevInstance, SCCInstance nextInstance, String path) throws IOException {
		// TODO Auto-generated method stub
		if(prevInstance.getEndLine()==nextInstance.getStartLine() && prevInstance.getEndCol()==nextInstance.getStartCol())
		{
			nextInstance.setStartCol(nextInstance.getStartCol()+1);
			nextInstance.updateCode(path);
		}
	
		if(prevInstance.getEndLine()>=nextInstance.getEndLine()) // COMPLETE OVERLAP
		{
			mci.getAnalysisDetails().remove(index+1);
			mci.getAnalysisDetails().remove(index+1);
			return false;
		}
		else if(prevInstance.getEndLine()>nextInstance.getStartLine()) // PARTIAL OVERLAP
		{
			mci.getAnalysisDetails().set(index,"PARTIALOVERLAP");
			int x=0;
			while(true)
			{
				System.out.println("OVERLAP DETECTED. BIG PROBLEM");
			}
			//return true;
		}
		else if(prevInstance.getEndLine()==nextInstance.getStartLine())// SAME LINE
		{
			if(prevInstance.getEndCol()>nextInstance.getStartCol())// PARTIAL OVERLAP ON SAME ENDING LINES 
			{
				mci.getAnalysisDetails().set(index,"PARTIALOVERLAP");
				int x=0;
				while(x<100)
				{
					System.out.println("OVERLAP DETECTED. BIG PROBLEM");
				}
				return true;
			}
			else if(prevInstance.getEndCol()<nextInstance.getStartCol()-1)// GAP ON SAME LINE BETWEEN CONSECUTIVE SCC INSTANCES
			{
				int effectivestartcol=prevInstance.getEndCol()+1;
				int effectivecol=nextInstance.getStartCol()-1;
				mci.getAnalysisDetails().set(index,("GAP" + "," + prevInstance.getEndLine() + "," + effectivestartcol + "," + nextInstance.getStartLine()
						+ ","+ effectivecol + "," + ClonesParser.getCodeSegment(path, prevInstance.getEndLine(), effectivestartcol, nextInstance.getStartLine(), effectivecol,null)));
				return true;
//				mci.getAnalysisDetails().set(index,("GAP" + "," + effectivestartcol + "," + prevInstance.getEnd_col() + "," + nextInstance.getStart_line()
	//					+ ","+ effectivecol + "," + Parser.getCodeSegment(path, prevInstance.getEnd_line(), effectivestartcol, nextInstance.getStart_line(), effectivecol,null)));
//				return true;
			}
			else // The TWO SCCINSTANCES HAVE NOTHING IN BETWEEN THEM AS THE SECOND STARTS RIGHT AFTER THE FIRST ONE
			{
				mci.getAnalysisDetails().set(index,"");
				return true;
			}
		}
		else if(prevInstance.getEndLine()<nextInstance.getStartLine()) // GAPPED CLONES WITH ATLEAST 1 LINE IN BETWEEN THEM
		{
			int effectivestartcol=prevInstance.getEndCol()+1;
			int effectivecol=nextInstance.getStartCol()-1;
			mci.getAnalysisDetails().set(index,("GAP" + "," + prevInstance.getEndLine() + "," + effectivestartcol + "," + nextInstance.getStartLine()
					+ ","+ effectivecol + "," + ClonesParser.getCodeSegment(path, prevInstance.getEndLine(), effectivestartcol, nextInstance.getStartLine(), effectivecol,null)));
			return true;
		}
		return true;
		
	}

	public static SCCInstance getInstance(String string, MCCInstance mci) {
		// TODO Auto-generated method stub
		String[] parts = string.split(",");
		SCCInstance scc = getSCC(Integer.parseInt(parts[0]),mci);
		return scc;
		
	}

	public static SCCInstance getSCCInstanceWithLastLine(MCCInstance mci)
	{
		int minimum=0;
		SCCInstance last=null;
		for(SCCInstance scc : mci.getNewSccs_Contained())
		{
			if(scc.getEndLine()>minimum)
			{
				minimum=scc.getEndLine();
				last=scc;
			}
		}
		return last;
	}
	
	private static SCCInstance getSCC(int sccid, MCCInstance mci)
	{
		for(SCCInstance scc : mci.getNewSccs_Contained())
		{
			if(sccid==scc.getSCCID())
			{
				return scc;
			}
		}
		return null;
	}

	private static void sortMethodCloneInstance(MCCInstance mci) {// This method will sort the SCCIDS according to their startlines
		Collections.sort(mci.getSCCs());
	}

	public static void modifymcc(MCC clone, ArrayList<Integer> clique) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<MCCInstance> methodcloneinstances = clone.getMCCInstances();
		for( MCCInstance mci : methodcloneinstances)
		{
			modifyInstnace(mci,clique);
		}
	}

	private static void stabilizeAdjacencyMartix(MCC clone) {
		// TODO Auto-generated method stub
		for(int i=0;i<clone.getMCCInstances().get(0).getSCCs().size();i++)
		{
			for(int j=0;j<clone.getMCCInstances().get(0).getSCCs().size();j++)
			{
				int temp=clone.getMCCInstances().get(0).getAdjeceny_Matrix_For_Overlaps()[i][j];
				for(MCCInstance mci:clone.getMCCInstances())
				{
					if(temp!=mci.getAdjeceny_Matrix_For_Overlaps()[i][j])
					{
						for(MCCInstance mcii:clone.getMCCInstances())
						{
							mcii.getAdjeceny_Matrix_For_Overlaps()[i][j]=0;
						}
					}
				}
			}
		}
	}

	private static void modifyInstnace(MCCInstance mci,
			ArrayList<Integer> clique) throws IOException {
		// TODO Auto-generated method stub
		test(mci);
		Iterator<Integer> itr = mci.maxclique.iterator();
		int next;
		Iterator<Set<Integer>> iterator = mci.getAllCliques().iterator();
		/*System.out.println("SCC instance 8 :" + mci.getSCCs().get(3).getCode());
		SCCInstanceCustom instance3=mci.getSCCs().get(3);
		System.out.println("SCC instance 9 :" + mci.getSCCs().get(4).getCode());
		System.out.println();
		SCCInstanceCustom instance4=mci.getSCCs().get(4);*/
		mci.getNewSccs_Contained().clear();
		for(Integer k : clique)
		{
			for(SCCInstance scc: mci.getSCCs())
			{
				if(scc.getSCCID()==k)
				{
					mci.getNewSccs_Contained().add(scc);
					break;
				}
			}
		}
		Collections.sort(mci.getNewSccs_Contained());
		String path=ClonesParser.getFilePath(mci.getMethod().getFileId()); // The path to the file which contains the method clone instance;
		mci.getAnalysisDetails().clear();
		for(SCCInstance k : mci.getNewSccs_Contained())
		{
			mci.getAnalysisDetails().add(" ");
			mci.getAnalysisDetails().add(Integer.toString(k.getSCCID()) + "," + k.getCodeSegment());//+ "," + Parser.getCodeSegment(path, start_line, start_col, end_line, end_col));
		}
		mci.getAnalysisDetails().add(" "); // THE LAST INDEX THAT WILL HOLD THE CODE AFTER LAST SSC and till the end of method
		try {
			if(mci.getMethod().getStartLine()==mci.getNewSccs_Contained().get(0).getStartLine())
			{
				String completelinecode=ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,mci.getMethod().getStartLine()+1,1,null);
				//String scclinecode = Parser.getCodeSegment(path,getSCC(SortedSCCIDS.get(0),mci).getStart_line(),getSCC(SortedSCCIDS.get(0),mci).getStart_col(),getSCC(SortedSCCIDS.get(0),mci).getStart_line()+1,1);
				String stringtobeadded=completelinecode.substring(0,mci.getNewSccs_Contained().get(0).getStartCol()-1);
				mci.getAnalysisDetails().set(0, stringtobeadded);
			}
			else
			{
				mci.getAnalysisDetails().set(0,ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,mci.getNewSccs_Contained().get(0).getStartLine(),mci.getNewSccs_Contained().get(0).getStartCol()-1,null));
			}
			// The above line adds the code to the first index of Analysis details which is the code from the start of the method till the start of the first sccid;
			mci.getAnalysisDetails().set(mci.getAnalysisDetails().size()-1,ClonesParser.getCodeSegment(path,getSCCInstanceWithLastLine(mci).getEndLine(),getSCCInstanceWithLastLine(mci).getEndCol()+1,mci.getMethod().getEndLine()+1,1,null));
			// the above line adds the last piece of code to the last index of the getAnalysisDetails() that has the code which starts from the end of the last SCC INSTANCE in the method till the end of the method
			int currIndex=2;
			for(int i=2;i<mci.getAnalysisDetails().size()-2;i=i+2) // This loops now fills the in between gaps between each sccinstances with gaps or partial overlaps markers
			{
				SCCInstance PrevInstance = getInstance(mci.getAnalysisDetails().get(currIndex-1),mci);
				SCCInstance NextInstance = getInstance(mci.getAnalysisDetails().get(currIndex+1),mci);
				boolean check = checkInBetweenSCCInstances(mci,currIndex,PrevInstance,NextInstance,path); // if check is false , it means there is complete overlap\
				if(check==true) // if check is false it means that a complete overlap is detected and we don't want to increment our current pointer
				{
					currIndex=currIndex+2;
				}
				if(currIndex>mci.getAnalysisDetails().size()-3)
				{
					break;
				}
			}
			mci.DisplayAnalysisDetails();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void test(MCCInstance mci) throws IOException {
		// TODO Auto-generated method stub
		String path2 = "\\My Output\\";
		path2 = CloneManagementPlugin.getAbsolutePath(path2);
		File f1 = new File(path2+"test2.txt");
		File f2 = new File(path2+"test1.txt");
		File f3 = new File(path2+"test3.txt");
		f1.delete();
		f3.delete();
		f2.delete();
		mci.DisplayAnalysisDetails();
	}
}
