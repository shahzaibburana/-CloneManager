package com.plugin.clonemanager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;

import com.plugin.clonemanager.handlers.SampleHandler;

public class Initializer {
	private CloneMinerExecuter cloneMinerExecuter;
	private CloneMinerSettings minerSettings;
	public static Process miner;
	private static Process vcl;
	//private static int mintokensize = 50;
	//private static int methodunique=1;
	//private static int groupindex=0;
	public static ExternalThread errStream;
	public static ExternalThread outputStream;
	public Initializer()
	{
		minerSettings = new CloneMinerSettings();
		cloneMinerExecuter = new CloneMinerExecuter();
		//ExecuteCloneMiner();
		//CloneDB.Initiate();
	}
	
	public void ExCloneMiner(IWizardContainer container, int _stc, boolean _isDelim, int _groupIndex)
	{
		cloneMinerExecuter.ExCloneMiner(container, _stc, _isDelim, _groupIndex,
				this);
	}

	/*public static void ExecuteCloneMiner()
	{
		try
		{
			String cloneminerexecpath=Activator.getAbsolutePath(Directories.CLONE_MINER);
			String cloneminerpath=Activator.getAbsolutePath(Directories.CLONE_MINER_DIR);
			final String[] strArray = new String[4];
			strArray[0] = cloneminerexecpath;

			strArray[1] = "" + mintokensize;
			strArray[2] = "" + methodunique;
			strArray[3] = "" + groupindex;
			File dir = new File(cloneminerpath);
			miner = Runtime.getRuntime().exec(strArray, null, dir);
			errStream = new ExternalThread(miner.getErrorStream());
			outputStream = new ExternalThread(miner.getInputStream());				
			errStream.start();
			outputStream.start();
			int result = miner.waitFor();
			if(result != 0){
				System.err.println("Clone Miner terminates with problems...");
			}
			else{
				errStream.join();
				outputStream.join();
			}
		}catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			miner.destroy();
		}
	}*/
	
	public static void ExecuteVCL(String filename) // RUNS VCL PROCESSOR FOR A SPECIFIC SPC FILE
	{
		try {
			String vclexecpath=CloneManagementPlugin.getAbsolutePath(Directories.VCL_PATH);
			String vclpath=CloneManagementPlugin.getAbsolutePath(Directories.VCL_PATH_DIR);
			final String[] strArray = new String[2];
			strArray[0] = vclexecpath;
			strArray[1] = "" + filename;
			File dir = new File(vclpath);
			vcl = Runtime.getRuntime().exec(strArray, null, dir);
			errStream = new ExternalThread(vcl.getErrorStream());
			outputStream = new ExternalThread(vcl.getInputStream());				
			errStream.start();
			outputStream.start();
			int result = vcl.waitFor();
			if(result != 0){
				System.err.println("VCL PROCESSOR terminates with problems...");
			}
			else{
				errStream.join();
				outputStream.join();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			vcl.destroy();
		}
	}
	
	
	/**
	 * Set the value of CloneMiner Parameters
	 */
	public void setMinerSettings(int groupChoice, int groupSize, boolean methodAnalyze, int minScTok, int minFcTok, 
			int minFcTokPtg, int minMcTok, int minMcTokPtg){
		minerSettings.setMinerSettings(groupChoice, groupSize, methodAnalyze,
				minScTok, minFcTok, minFcTokPtg, minMcTok, minMcTokPtg);
	}
	
	public void stop()
	{
		// needed when finishing up //
	}


	
}
