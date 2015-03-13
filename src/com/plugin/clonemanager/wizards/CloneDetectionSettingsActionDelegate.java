package com.plugin.clonemanager.wizards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.internal.Workbench;

import com.plugin.clonemanager.Directories;

public class CloneDetectionSettingsActionDelegate extends Action implements
		IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	private CloneDetectionSettingsWizard cdsWizard;
	
	/*private IJavaProject selectedProject;
	private IPackageFragmentRoot selectedPackageFragmentRoot;
	private IPackageFragment selectedPackageFragment;
	private ICompilationUnit selectedCompilationUnit;
	private IType selectedType;
	boolean flag = false;
	
	
	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection)selection;
				Object element = structuredSelection.getFirstElement();
				IJavaProject javaProject = null;
				if(element instanceof IJavaProject) {
					javaProject = (IJavaProject)element;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
				}
				else if(element instanceof IPackageFragmentRoot) {
					IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot)element;
					javaProject = packageFragmentRoot.getJavaProject();
					selectedPackageFragmentRoot = packageFragmentRoot;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
					selectedType = null;
				}
				else if(element instanceof IPackageFragment) {
					IPackageFragment packageFragment = (IPackageFragment)element;
					javaProject = packageFragment.getJavaProject();
					selectedPackageFragment = packageFragment;
					selectedPackageFragmentRoot = null;
					selectedCompilationUnit = null;
					selectedType = null;
				}
				else if(element instanceof ICompilationUnit) {
					ICompilationUnit compilationUnit = (ICompilationUnit)element;
					javaProject = compilationUnit.getJavaProject();
					selectedCompilationUnit = compilationUnit;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedType = null;
				}
				else if(element instanceof IType) {
					IType type = (IType)element;
					javaProject = type.getJavaProject();
					selectedType = type;
					selectedPackageFragmentRoot = null;
					selectedPackageFragment = null;
					selectedCompilationUnit = null;
				}
				if(javaProject != null && !javaProject.equals(selectedProject)) {
					selectedProject = javaProject;
					flag = true;
				}
			}
		}
	};
	
	*/
	
	public CloneDetectionSettingsActionDelegate() {
		super();
		//fileGroups = new Vector<String[]>();
	}

	public void dispose() {
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		
		/*window.getSelectionService().addSelectionListener(selectionListener);
		JavaCore.addElementChangedListener(new ElementChangedListener());
		window.getWorkbench().getOperationSupport().getOperationHistory().addOperationHistoryListener(new IOperationHistoryListener() {
			public void historyNotification(OperationHistoryEvent event) {
					int eventType = event.getEventType();
					if(eventType == OperationHistoryEvent.UNDONE  || eventType == OperationHistoryEvent.REDONE || 
							eventType == OperationHistoryEvent.OPERATION_ADDED || eventType == OperationHistoryEvent.OPERATION_REMOVED) {
						flag = true;
					}
				}
			});
		if (flag == true)
		{
			//listInputFiles();
			cdsWizard = new CloneDetectionSettingsWizard();
			ISelection selection = window.getSelectionService().getSelection();
	        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
	        if (selection instanceof IStructuredSelection){
	            selectionToPass = (IStructuredSelection) selection;
	        }
			cdsWizard.init(window.getWorkbench(), selectionToPass);
			Shell shell= window.getWorkbench().getActiveWorkbenchWindow().getShell();
			WizardDialog dialog= new WizardDialog(shell, cdsWizard);
			try{
				dialog.create();
				int res = dialog.open();
				notifyResult(res == Window.OK);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		else {
			 MessageBox box = new MessageBox(window.getShell(),SWT.ICON_INFORMATION);
             box.setMessage("Please Select a Project!!!");
             box.open();
		}*/
		
		if(getCurrentProject() == null){
			MessageBox box = new MessageBox(window.getShell(),SWT.ICON_INFORMATION);
            box.setMessage("Please Select a Project!!!");
            box.open();
		}
		else{
			cdsWizard = new CloneDetectionSettingsWizard();
			ISelection selection = window.getSelectionService().getSelection();
	        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
	        if (selection instanceof IStructuredSelection){
	            selectionToPass = (IStructuredSelection) selection;
	        }
			cdsWizard.init(window.getWorkbench(), selectionToPass);
			Shell shell= window.getWorkbench().getActiveWorkbenchWindow().getShell();
			WizardDialog dialog= new WizardDialog(shell, cdsWizard);
			try{
				dialog.create();
				int res = dialog.open();
				notifyResult(res == Window.OK);
			}catch(Exception e){
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		
	}
	
	/*private void listInputFiles(){
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String pathStr = Directories.getFilePath(Directories.CLONES_INPUT_FILES);
		//System.out.println(pathStr);	
		try {
				File file = new File(pathStr);
				file.createNewFile();
				FileOutputStream fileout = new FileOutputStream(file);
				PrintWriter stdout = new PrintWriter(fileout);
				IPackageFragment[] packages = selectedProject.getPackageFragments();
				for (IPackageFragment myPackage : packages) {
					ICompilationUnit[] files = myPackage.getCompilationUnits();
					for (ICompilationUnit javaFile : files) {
						IResource res = root.findMember(javaFile.getPath().toString());
						//System.out.println(res.getLocation().toString());
						stdout.println(res.getLocation().toString()+";");
						//selectionList.add(res.getLocation().toString());
						//count++;
						//System.out.println(count);
					}
				}
				
				stdout.flush();
				stdout.close();
				fileout.close();
		 	}
		 	catch (JavaModelException|IOException e) {
		 		// TODO Auto-generated catch block
		 		e.printStackTrace();
		 	}
		}
	*/
	public static IProject getCurrentProject(){    
        ISelectionService selectionService = Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();    
        ISelection selection = selectionService.getSelection();    
        IProject project = null;    
        if(selection instanceof IStructuredSelection) {    
            Object element = ((IStructuredSelection)selection).getFirstElement();    

            if (element instanceof IResource) {    
                project= ((IResource)element).getProject();    
            } else if (element instanceof PackageFragmentRootContainer) {    
                IJavaProject jProject = ((PackageFragmentRootContainer)element).getJavaProject();    
                project = jProject.getProject();    
            } else if (element instanceof IJavaElement) {    
                IJavaProject jProject= ((IJavaElement)element).getJavaProject();    
                project = jProject.getProject();    
            }    
        }     
        return project;    
    }
	
	
}
