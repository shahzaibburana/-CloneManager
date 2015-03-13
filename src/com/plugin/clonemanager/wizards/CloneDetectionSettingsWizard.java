package com.plugin.clonemanager.wizards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.plugin.clonemanager.CloneManagementPlugin;
import com.plugin.clonemanager.Directories;
import com.plugin.clonemanager.Initializer;
import com.plugin.clonemanager.MCC;
import com.plugin.clonemanager.MCCInstance;
import com.plugin.clonemanager.MCCsAnalyser;
import com.plugin.clonemanager.handlers.SampleHandler;

public class CloneDetectionSettingsWizard extends Wizard implements INewWizard {
	private final static Initializer init = CloneManagementPlugin.getInitializer();
	private Process miner;
	private MyExternalThread errStream, outputStream;
	private boolean isDelim = false; 
	private int stc = 30;
	private int langIndex = 0;
	private int groupIndex = 0;
	private Vector<String[]> fileList = null;
	private boolean savePage1 = false, savePage2 = false;
	ArrayList<Integer> clique;
	MCC mcc;
	CloneDetectionSettingsWizardPage1 page1;
	CloneDetectionSettingsWizardPage2 page21, page22,page24,page25,page26,page27;
	CloneDetectionSettingsWizardPage3 page31, page32,page34,page35,page36, page37;
	CloneDetectionSettingsWizardPage4 page4;
	CloneDetectionSettingsWizardPage5 page5;
	
	
	public CloneDetectionSettingsWizard() {
		super();
	}
	
	public boolean get_isDelim()
	{
		return isDelim;
	}
	
	public int get_stc()
	{
		return stc;
	}
	
	public int get_groupIndex()
	{
		return groupIndex;
	}
	
	public void setDelim(boolean isDelim) {
		this.isDelim = isDelim;
	}

	public void setStc(int stc) {
		this.stc = stc;
	}
	
	public void setLangIndex(int langIndex){
		this.langIndex = langIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public void setFileList(Vector<String[]> fileList){
		this.fileList = fileList;
	}
	
	
	public void setSavePage1(boolean savePage1) {
		this.savePage1 = savePage1;
	}

	public void setSavePage2(boolean savePage2) {
		this.savePage2 = savePage2;
	}

	@Override
	public void addPages() {
		page1 = new CloneDetectionSettingsWizardPage1(this);
		page21 = new CloneDetectionSettingsWizardPage2(0, this);
		page22 = new CloneDetectionSettingsWizardPage2(1, this);
		page24 = new CloneDetectionSettingsWizardPage2(3, this);
		page25 = new CloneDetectionSettingsWizardPage2(4, this);
		page26 = new CloneDetectionSettingsWizardPage2(5, this);
		page27 = new CloneDetectionSettingsWizardPage2(6, this);
		
		page31 = new CloneDetectionSettingsWizardPage3(0, this);
		page32 = new CloneDetectionSettingsWizardPage3(1, this);
		page34 = new CloneDetectionSettingsWizardPage3(3, this);
		page35 = new CloneDetectionSettingsWizardPage3(4, this);
		page36 = new CloneDetectionSettingsWizardPage3(5, this);
		page37 = new CloneDetectionSettingsWizardPage3(6, this);
		
		page4 = new CloneDetectionSettingsWizardPage4(this);
		page5 = new CloneDetectionSettingsWizardPage5(this);
		
		addPage(page1);
		addPage(page21);
		addPage(page22);
		addPage(page24);
		addPage(page25);
		addPage(page26);
		addPage(page27);
		
		addPage(page31);
		addPage(page32);
		addPage(page34);
		addPage(page35);
		addPage(page36);
		addPage(page37);
		
		addPage(page4);
		addPage(page5);
	}
	
	public void refresh_page4(){
		page4 = new CloneDetectionSettingsWizardPage4(this);
	}
	
	public CloneDetectionSettingsWizardPage2 getPage21() {
		return page21;
	}

	public CloneDetectionSettingsWizardPage2 getPage22() {
		return page22;
	}
	
	public CloneDetectionSettingsWizardPage2 getPage24() {
		return page24;
	}

	public CloneDetectionSettingsWizardPage2 getPage25() {
		return page25;
	}
	public CloneDetectionSettingsWizardPage2 getPage26() {
		return page26;
	}

	public CloneDetectionSettingsWizardPage2 getPage27() {
		return page27;
	}
	
	public CloneDetectionSettingsWizardPage3 getPage31() {
		return page31;
	}

	public CloneDetectionSettingsWizardPage3 getPage32() {
		return page32;
	}
	
	public CloneDetectionSettingsWizardPage3 getPage34() {
		return page34;
	}

	public CloneDetectionSettingsWizardPage3 getPage35() {
		return page35;
	}
	public CloneDetectionSettingsWizardPage3 getPage36() {
		return page36;
	}

	public CloneDetectionSettingsWizardPage3 getPage37() {
		return page37;
	}
	
	public CloneDetectionSettingsWizardPage4 getPage4() {
		return page4;
	}
	
	public CloneDetectionSettingsWizardPage5 getPage5() {
		return page5;
	}
	
	@Override
	public boolean canFinish()
	{
	//if(getContainer().getCurrentPage() != page5)
	//return false;
	//else 
	//return true;
		
	if(getContainer().getCurrentPage() == page31)
		return true;
	else 
		return false;
	}

	@Override
	public boolean performFinish() {
		page31.saveDataToWizard();
		
		///////will be used later
		/*clique=CloneDetectionSettingsWizardPage5.SccsSelected;
		mcc=CloneDetectionSettingsWizardPage5.clone;
		try {
			MCCsAnalyser.modifymcc(mcc,clique);
			for(MCCInstance instance : mcc.getMCCInstances())
			{
				instance.DisplayAnalysisDetails();
			}
			System.out.println(mcc.getMCCID());
			SampleHandler.f.genMCCTemplate(mcc.getMCCID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		///////
		
		//return false;
		/*	if(!savePage1){
				page1.saveDataToWizard();
			}
			if(langIndex == 0){
				if(!savePage2){
					page21.saveDataToWizard();
				}
				page31.saveDataToWizard();
			}
			else if(langIndex == 1){
				if(!savePage2){
					page22.saveDataToWizard();
				}
				page32.saveDataToWizard();
			}
			
			else if(langIndex == 3){
				if(!savePage2){
					page24.saveDataToWizard();
				}
				page34.saveDataToWizard();
			}
			else if(langIndex == 4){
				if(!savePage2){
					page25.saveDataToWizard();
				}
				page35.saveDataToWizard();
			}
			else if(langIndex == 5){
				if(!savePage2){
					page26.saveDataToWizard();
				}
				page36.saveDataToWizard();
			}
			
			else if(langIndex == 6){
				if(!savePage2){
					page27.saveDataToWizard();
				}
				page37.saveDataToWizard();
			}
			
			//init.ExCloneMiner(getContainer(), stc, isDelim, groupIndex);
		try{}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally{}*/
		
		
		///return false;
		
		return true;
		}
		
	//	return true;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		System.out.println("Preparing wizard..");
		this.setWindowTitle("Clone Detection Settings");
		this.setNeedsProgressMonitor(true);
		this.setHelpAvailable(false);
		this.setForcePreviousAndNextButtons(true);
	}
	

	public void createPageControls(Composite pageContainer) {
	}

	public boolean performCancel() {
		if(miner != null){
			miner.destroy();
		}
		
		return super.performCancel();
	}

}
