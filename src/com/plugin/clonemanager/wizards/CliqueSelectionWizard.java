package com.plugin.clonemanager.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.plugin.clonemanager.CloneManagementPlugin;
import com.plugin.clonemanager.MCC;
import com.plugin.clonemanager.MCCInstance;
import com.plugin.clonemanager.MCCsAnalyser;
import com.plugin.clonemanager.Method;
import com.plugin.clonemanager.handlers.SampleHandler;

public class CliqueSelectionWizard extends Wizard implements INewWizard {

	CliqueSelectionWizardPage1 page1;
	CliqueSelectionWizardPage2 page2;
	String path = CloneManagementPlugin.getAbsolutePath("\\vcl_output\\mcc_template_temp\\");
		
	ArrayList<Integer> clique;
	MCC mcc;
	int mccid=-1;
	private int mccIndex=0;
	
	public CliqueSelectionWizard() {
		
		super();
		// TODO Auto-generated constructor stub
	}

	public void setMCCID(int _mccid){
		this.mccid=_mccid;
		
	}
	
	public int getMCCID(){
		return mccid;
		
	}
	
	@Override
	public void addPages(){
	
		
		page1 = new CliqueSelectionWizardPage1(this);
		addPage(page1);
		
		page2= new CliqueSelectionWizardPage2(this);
		addPage(page2);
		
	}
	
	
	public CliqueSelectionWizardPage2 getPage2() {
		return page2;
	}

		
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

		this.setNeedsProgressMonitor(true);
		this.setHelpAvailable(false);
		this.setForcePreviousAndNextButtons(true);
	
		
	}

	@Override
	public boolean performFinish() {

		File file=new File(path);
		String[]entries = file.list();
		for(String s: entries){
			File currentFile = new File(file.getPath(),s);
			currentFile.delete();
		}
		markMethods();
		return true;
	}

	
	public boolean canFinish()
	{
	//if(getContainer().getCurrentPage() != page5)
	//return false;
	//else 
	//return true;
		
		if(getContainer().getCurrentPage() == page2)
			return true;
		else 
			return false;
		
	}
	
	
	public void createPageControls(Composite pageContainer) {
	}
	
	private void markMethods(){
	  mcc = SampleHandler.f.mccList.get(mccIndex);
	  for(MCCInstance instance : mcc.getMCCInstances()){
		  Method currentMethod = instance.getMethod();
		  File fileToOpen = new File(currentMethod.getFilePath());
			String text = getFileContent(fileToOpen);    
			IFile ifile= getIFile(fileToOpen);
			try{
				IMarker marker = ifile.createMarker("com.plugin.clonemanager.customtextmarker");
				int startChar = text.indexOf(currentMethod.getCodeSegment());
				int offset = currentMethod.getCodeSegment().length();
				marker.setAttribute(IMarker.CHAR_START, startChar);
				marker.setAttribute(IMarker.CHAR_END, startChar+offset);
			}
			catch(Exception e){
				e.printStackTrace();
			}
	  }
	}
	
	private IFile getIFile(File fileToOpen){
		IPath location= Path.fromOSString(fileToOpen.getAbsolutePath()); 
		IWorkspace workspace= ResourcesPlugin.getWorkspace();    
		return workspace.getRoot().getFileForLocation(location);
	}

	private String getFileContent(File fileToOpen) {
		String content="";
		try {
			FileInputStream fis = new FileInputStream(fileToOpen);
			byte[] data = new byte[(int) fileToOpen.length()];
			fis.read(data);
			fis.close();
			content = new String(data, "UTF-8");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return content;
	}

	public int getMccIndex() {
		return mccIndex;
	}

	public void setMccIndex(int mccIndex) {
		this.mccIndex = mccIndex;
	}
	
	
}
