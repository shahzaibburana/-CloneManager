package com.plugin.clonemanager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class MCCInstance {

	private double coverage;
	private int methodCloneClassID;
	private Method method;
	private ArrayList<String> analysisDetails;
	public Set<Integer> maxclique;
	private Collection<Set<Integer>> sortedCliques;
	private Collection<Set<Integer>> allCliques;
	private ArrayList<SCCInstance> SCCs_Contained;
	private ArrayList<SCCInstance> newSccs_Contained;
	private int[][] Adjeceny_Matrix_For_Overlaps;

	public MCCInstance(int mCCID, double coverage2,
			Method method_) {
		// TODO Auto-generated constructor stub
		Adjeceny_Matrix_For_Overlaps=null;
		coverage = coverage2;
		methodCloneClassID = mCCID;
		method = method_;
		sortedCliques = new ArrayList<>();
		allCliques = new ArrayList<>();
		analysisDetails = new ArrayList<>();
		SCCs_Contained = new ArrayList<>();
		newSccs_Contained = new ArrayList<>();
	}

	public void addSCCs_Contained(SCCInstance sccInstanceCustom) {
		// TODO Auto-generated method stub
		this.SCCs_Contained.add(sccInstanceCustom);
	}

	public double getCoverage() {
		return coverage;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	public int getMethodCloneID() {
		return methodCloneClassID;
	}

	public void setMethodCloneID(int methodCloneID) {
		methodCloneClassID = methodCloneID;
	}

	public Method getMethod() {
		return method;
	}

	public void setClone(Method clone) {
		method = clone;
	}

	public ArrayList<String> getAnalysisDetails() {
		return analysisDetails;
	}

	public void setAnalysisDetails(ArrayList<String> analysis) {
		analysisDetails = analysis;
	}

	public ArrayList<SCCInstance> getSCCs() {
		return SCCs_Contained;
	}

	public void setSCCs(ArrayList<SCCInstance> sCCs) {
		SCCs_Contained = sCCs;
	}
	
	public void DisplayAnalysisDetails() throws IOException {
		// TODO Auto-generated method stub
		
		if(this.getSCCs().size()==1)
		{
			String path = "\\My Output\\";
			path = CloneManagementPlugin.getAbsolutePath(path);
			FileWriter name = new FileWriter(path + "test1.txt", true);
			BufferedWriter out = new BufferedWriter(name);
			out.write(this.getMethod().getCodeSegment());
			out.write("************THIS IS THE END OF THE METHOD*****************");
			out.write("\n");
			for(SCCInstance instance : this.getSCCs())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS SCC CLONE INSTANCE CODE**************");
			}
			out.write("\n");
			for(SCCInstance instance : getNewSccs_Contained())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS NEW SCC CLONE INSTANCE CODE**************");
			}
			
			for(int i=0;i<getAnalysisDetails().size();i++)
			{
				out.write("\n");
				out.write("AT INDEX : " + i + " : " + getAnalysisDetails().get(i));
			}
			out.write("****************** THATS IT FOR THIS SPECIFIC METHOD CLONE INSTANCE*********************");
			out.write("\n");	
			out.close();
		}
		if(this.getSCCs().size()==2)
		{
			String path = "\\My Output\\";
			path = CloneManagementPlugin.getAbsolutePath(path);
			FileWriter name = new FileWriter(path + "test2.txt", true);
			BufferedWriter out = new BufferedWriter(name);
			out.write(this.getMethod().getCodeSegment());
			out.write("************THIS IS THE END OF THE METHOD*****************");
			out.write("\n");
			for(SCCInstance instance : this.getSCCs())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS SCC CLONE INSTANCE CODE**************");
			}
			out.write("\n");
			for(SCCInstance instance : getNewSccs_Contained())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS NEW SCC CLONE INSTANCE CODE**************");
			}
			
			for(int i=0;i<getAnalysisDetails().size();i++)
			{
				out.write("\n");
				out.write("AT INDEX : " + i + " : " + getAnalysisDetails().get(i));
			}
			out.write("****************** THATS IT FOR THIS SPECIFIC METHOD CLONE INSTANCE*********************");
			out.write("\n");	
			out.close();
		}
		
		if(this.getSCCs().size()>2)
		{
			String path = "\\My Output\\";
			path = CloneManagementPlugin.getAbsolutePath(path);
			FileWriter name = new FileWriter(path + "test3.txt", true);
			BufferedWriter out = new BufferedWriter(name);
			out.write(this.getMethod().getCodeSegment());
			out.write("************THIS IS THE END OF THE METHOD*****************");
			out.write("\n");
			for(SCCInstance instance : this.getSCCs())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS SCC CLONE INSTANCE CODE**************");
			}
			out.write("\n");
			for(SCCInstance instance : getNewSccs_Contained())
			{
				out.write(instance.getCodeSegment());
				out.write("***********THIS IS THE END OF THIS NEW SCC CLONE INSTANCE CODE**************");
			}
			
			for(int i=0;i<getAnalysisDetails().size();i++)
			{
				out.write("\n");
				out.write("AT INDEX : " + i + " : " + getAnalysisDetails().get(i));
			}
			out.write("****************** THATS IT FOR THIS SPECIFIC METHOD CLONE INSTANCE*********************");
			out.write("\n");	
			out.close();
		}
		
	}

	public int[][] getAdjeceny_Matrix_For_Overlaps() {
		return Adjeceny_Matrix_For_Overlaps;
	}

	public void setAdjeceny_Matrix_For_Overlaps(
			int[][] adjeceny_Matrix_For_Overlaps) {
		Adjeceny_Matrix_For_Overlaps = adjeceny_Matrix_For_Overlaps;
	}

	public ArrayList<SCCInstance> getNewSccs_Contained() {
		return newSccs_Contained;
	}

	public void setNewSccs_Contained(ArrayList<SCCInstance> newSccs_Contained) {
		this.newSccs_Contained = newSccs_Contained;
	}

	public Collection<Set<Integer>> getSortedCliques() {
		return sortedCliques;
	}

	public void setSortedCliques(Collection<Set<Integer>> sortedCliques) {
		this.sortedCliques = sortedCliques;
	}

	public Collection<Set<Integer>> getAllCliques() {
		return allCliques;
	}

	public void setAllCliques(Collection<Set<Integer>> allCliques) {
		this.allCliques = allCliques;
	}

	public void analyzeMethodCloneInstance(MCC parentClone)
			throws IOException {
		String path = ClonesParser.getFilePath(getMethod().getFileId());
		GraphSolver.SolveGraph(this);
		getAnalysisDetails().clear();
		for (SCCInstance k : getNewSccs_Contained()) {
			getAnalysisDetails().add(" ");
			getAnalysisDetails().add(
					Integer.toString(k.getSCCID()) + "," + k.getCodeSegment());
		}
		getAnalysisDetails().add(" ");
		try {
			if (getMethod().getStartLine() == getNewSccs_Contained().get(0)
					.getStartLine()) {
				String completelinecode = ClonesParser.getCodeSegment(path,
						getMethod().getStartLine(), 1, getMethod()
								.getStartLine() + 1, 1, null);
				String stringtobeadded = completelinecode.substring(0,
						getNewSccs_Contained().get(0).getStartCol() - 1);
				getAnalysisDetails().set(0, stringtobeadded);
			} else {
				getAnalysisDetails()
						.set(0,
								ClonesParser.getCodeSegment(path, getMethod()
										.getStartLine(), 1,
										getNewSccs_Contained().get(0)
												.getStartLine(),
										getNewSccs_Contained().get(0)
												.getStartCol() - 1, null));
			}
			getAnalysisDetails().set(
					getAnalysisDetails().size() - 1,
					ClonesParser.getCodeSegment(path, MCCsAnalyser
							.getSCCInstanceWithLastLine(this).getEndLine(),
							MCCsAnalyser.getSCCInstanceWithLastLine(this)
									.getEndCol() + 1, getMethod()
									.getEndLine() + 1, 1, null));
			int currIndex = 2;
			for (int i = 2; i < getAnalysisDetails().size() - 2; i = i + 2) {
				SCCInstance PrevInstance = MCCsAnalyser.getInstance(
						getAnalysisDetails().get(currIndex - 1), this);
				SCCInstance NextInstance = MCCsAnalyser.getInstance(
						getAnalysisDetails().get(currIndex + 1), this);
				boolean check = MCCsAnalyser.checkInBetweenSCCInstances(
						this, currIndex, PrevInstance, NextInstance, path);
				if (check == true) {
					currIndex = currIndex + 2;
				}
				if (currIndex > getAnalysisDetails().size() - 3) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
