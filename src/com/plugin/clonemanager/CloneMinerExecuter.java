package com.plugin.clonemanager;


import org.eclipse.core.runtime.IProgressMonitor;

import java.io.File;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.plugin.clonemanager.handlers.SampleHandler;

import java.lang.reflect.InvocationTargetException;

public class CloneMinerExecuter {
	private int stc;
	private boolean isDelim;
	private int groupIndex;
	private long cloneMinerStartTime;
	private int delim;
	
	
	public CloneMinerExecuter(){
		isDelim = false;
		stc = 30;
		delim = 0;
		groupIndex = 0;
	}

	public int getStc() {
		return stc;
	}

	public void setStc(int stc) {
		this.stc = stc;
	}

	public boolean getIsDelim() {
		return isDelim;
	}

	public void setIsDelim(boolean isDelim) {
		this.isDelim = isDelim;
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public long getCloneMinerStartTime() {
		return cloneMinerStartTime;
	}

	public void setCloneMinerStartTime(long cloneMinerStartTime) {
		this.cloneMinerStartTime = cloneMinerStartTime;
	}

	public int getDelim() {
		return delim;
	}

	public void setDelim(int delim) {
		this.delim = delim;
	}

	public int runCloneMiner(String[] cmdArray, IProgressMonitor monitor,
			Initializer initializer) {
		try {
			File dir = new File(getCloneMinerIOPath());
			setCloneMinerStartTime(System.currentTimeMillis());
			Initializer.miner = Runtime.getRuntime().exec(cmdArray, null, dir);
			Initializer.errStream = new ExternalThread(
					Initializer.miner.getErrorStream());
			Initializer.outputStream = new ExternalThread(
					Initializer.miner.getInputStream());
			Initializer.errStream.start();
			Initializer.outputStream.start();
			return Initializer.miner.waitFor();
		} catch (InterruptedException ie) {
			Initializer.miner.destroy();
			System.err.println("Clone Miner terminates with problems...");
			System.err.println(ie.getMessage());
			ie.printStackTrace();
			monitor.setCanceled(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			monitor.setCanceled(true);
		}
		return -1;
	}

	public void ExCloneMiner(IWizardContainer container, int _stc,
			boolean _isDelim, int _groupIndex, final Initializer initializer) {
		stc = _stc;
		isDelim = _isDelim;
		groupIndex = _groupIndex;
		try {
			container.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InterruptedException {
					monitor.beginTask("Loading Clone Detection Results...",
							IProgressMonitor.UNKNOWN);
					final String[] cmdArray = getCmdArray(stc, isDelim,
							groupIndex);
					int result = runCloneMiner(cmdArray, monitor, initializer);
					SampleHandler.runParser();
					if (result != 0) {
						System.err
								.println("Clone Miner terminates with problems...");
					} else {
						printElapsedTime(cloneMinerStartTime);
						Initializer.errStream.join();
						Initializer.outputStream.join();
					}
					monitor.done();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getCloneMinerIOPath() {
		return CloneManagementPlugin.getAbsolutePath(Directories.CLONE_MINER_DIR);
	}

	public void printElapsedTime(long startTime) {
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		long milli = elapsed % 1000;
		long sec = (elapsed / 1000) % 60;
		long mins = elapsed / 60000;
		System.out.println("Elapsed Time for Clone Miner: " + mins + " mins "
				+ sec + " secs " + milli + " millisec");
	}

	public String[] getCmdArray(int stc, boolean isDelim, int groupIndex) {
		String[] cmdArray = new String[4];
		if (isDelim)
			delim = 1;
		else
			delim = 0;
		cmdArray[0] = getCloneMinerExecPath();
		cmdArray[1] = "" + stc;
		cmdArray[2] = "" + 1;
		cmdArray[3] = "" + groupIndex;
		return cmdArray;
	}

	public String getCloneMinerExecPath() {
		return CloneManagementPlugin.getAbsolutePath(Directories.CLONE_MINER);
	}
}