package com.plugin.clonemanager;


public class CloneMinerSettings {
	private int groupChoice;
	private int groupSize;
	private int minScTok;
	private boolean methodAnalyze;
	private int minFcTokPtg;
	private int minFcTok;
	private int minMcTokPtg;
	private int minMcTok;

	public CloneMinerSettings(){
		groupChoice=0;
		groupSize=0;
		minScTok=0;
		minFcTokPtg=0;
		minFcTok=0;
		minMcTok=0;
	}
	public int getGroupChoice() {
		return groupChoice;
	}

	public void setGroupChoice(int groupChoice) {
		this.groupChoice = groupChoice;
	}

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public int getMinScTok() {
		return minScTok;
	}

	public void setMinScTok(int minScTok) {
		this.minScTok = minScTok;
	}

	public boolean getMethodAnalyze() {
		return methodAnalyze;
	}

	public void setMethodAnalyze(boolean methodAnalyze) {
		this.methodAnalyze = methodAnalyze;
	}

	public int getMinFcTokPtg() {
		return minFcTokPtg;
	}

	public void setMinFcTokPtg(int minFcTokPtg) {
		this.minFcTokPtg = minFcTokPtg;
	}

	public int getMinFcTok() {
		return minFcTok;
	}

	public void setMinFcTok(int minFcTok) {
		this.minFcTok = minFcTok;
	}

	public int getMinMcTokPtg() {
		return minMcTokPtg;
	}

	public void setMinMcTokPtg(int minMcTokPtg) {
		this.minMcTokPtg = minMcTokPtg;
	}

	public int getMinMcTok() {
		return minMcTok;
	}

	public void setMinMcTok(int minMcTok) {
		this.minMcTok = minMcTok;
	}

	/**
	 * Set the value of CloneMiner Parameters
	 */
	public void setMinerSettings(int groupChoice, int groupSize,
			boolean methodAnalyze, int minScTok, int minFcTok, int minFcTokPtg,
			int minMcTok, int minMcTokPtg) {
		this.groupChoice = groupChoice;
		this.groupSize = groupSize;
		this.methodAnalyze = methodAnalyze;
		this.minScTok = minScTok;
		this.minFcTok = minFcTok;
		this.minFcTokPtg = minFcTokPtg;
		this.minMcTok = minMcTok;
		this.minMcTokPtg = minMcTokPtg;
	}
}