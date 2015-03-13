package com.plugin.clonemanager.views;





import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;








import java.util.List;

import com.plugin.clonemanager.Method;
import com.plugin.clonemanager.handlers.SampleHandler;
import com.plugin.clonemanager.labelprovider.MccCloneInstanceListLabelProvider;
import com.plugin.clonemanager.labelprovider.MccListLabelProvider;
import com.plugin.clonemanager.wizards.CliqueSelectionWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.ViewComparator;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.markers.internal.IMarkerChangedListener;
import org.eclipse.ui.ide.*;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;

import com.plugin.clonemanager.contentprovider.MccInstanceListContentProvider;
import com.plugin.clonemanager.contentprovider.MccListContentProvider;
import com.plugin.clonemanager.core.MccCloneInstanceList;
import com.plugin.clonemanager.core.MccList;
import com.plugin.clonemanager.core.PrimaryMccObject;
import com.plugin.clonemanager.core.SecondaryMccObject;

public class MccView extends ViewPart {

	private TableViewer primaryMccViewer;
	private TableViewer secondaryMccViewer;
	private MccList mccList;
	private MccCloneInstanceList cloneList;
	private Action cliqueSelectionWizardAction;
	Table mccListTable;
	Table mccInstanceListTable;
	int mccIndex;
	int mccInstanceId;
	static int ASCENDING=1;
	static int DECENDING=2;
	int structureOrder;
	int mccIdOrder;
	int idOrder;
	int sortOrder;
	IFile ifile;

	public MccView() {
		super();
		primaryMccViewer=null;
		secondaryMccViewer=null;
		mccList=new MccList();
		cloneList=new MccCloneInstanceList();
		mccListTable=null;
		mccInstanceListTable=null;
		structureOrder=DECENDING;
		mccIdOrder=DECENDING;
		idOrder=ASCENDING;
		sortOrder=ASCENDING;
		ifile=null;
	}
	
	

	@Override
	public void createPartControl(final Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		createMccTables(parent);
		mccListTable.setHeaderVisible(true);
		mccListTable.setLinesVisible(true);
		mccInstanceListTable.setHeaderVisible(true);
		mccInstanceListTable.setLinesVisible(true);
		makeActions();
		contributeToActionBars();

		final TableCursor mccListTableCursor= new TableCursor(mccListTable,SWT.NULL);
		final TableCursor mccInstanceListTableCursor= new TableCursor(mccInstanceListTable,SWT.NULL);

		primaryMccViewer.getTable().getColumn(2).addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				primaryMccViewer.setComparator(new ViewComparator(null)
				{
					public int compare(Viewer viewer, Object e1, Object e2) {
						PrimaryMccObject object1 = (PrimaryMccObject) e1;
						PrimaryMccObject object2 = (PrimaryMccObject) e2;
						int result=0;
						if(object1.getStructure().size() == object2.getStructure().size())
						{
							if(object1.getStructure().get(0)> object2.getStructure().get(0))
							{
								result= 1;
							}
							else
							{
								result= -1;
							}
						}
						else
						{
							result= object1.getStructure().size()>(object2.getStructure().size())? -1 : 1;
						}
						if(structureOrder==0 )
						{
							return result;
						}
						if(structureOrder==ASCENDING)
						{
							result=-result;
							return result;
						}
						if(structureOrder==DECENDING)
						{
							return result;	
						}
						return result;
					};    	
				});

				primaryMccViewer.refresh();
				if(structureOrder==ASCENDING)
				{
					structureOrder=DECENDING;

				}
				else
					if(structureOrder==DECENDING)
					{
						structureOrder=ASCENDING;
					}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		primaryMccViewer.getTable().getColumn(1).addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(idOrder==ASCENDING)
				{
					idOrder=DECENDING;

				}
				else
					if(idOrder==DECENDING)
					{
						idOrder=ASCENDING;
					}
				primaryMccViewer.setComparator(new ViewComparator(null)
				{
					public int compare(Viewer viewer, Object e1, Object e2) {
						PrimaryMccObject object1 = (PrimaryMccObject) e1;
						PrimaryMccObject object2 = (PrimaryMccObject) e2;
						int result=0;
						result= object1.getMccId()>(object2.getMccId())? -1 : 1;
						if(idOrder==ASCENDING)
						{
							result=-result;
							return result;
						}
						if(idOrder==DECENDING)
						{
							return result;	
						}
						return result;
					};    	
				});

				primaryMccViewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) { 
				// TODO Auto-generated method stub

			}
		});

		primaryMccViewer.getTable().getColumn(0).addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(idOrder==ASCENDING)
				{
					idOrder=DECENDING;

				}
				else
					if(idOrder==DECENDING)
					{
						idOrder=ASCENDING;
					}
				primaryMccViewer.setComparator(new ViewComparator(null)
				{
					public int compare(Viewer viewer, Object e1, Object e2) {
						PrimaryMccObject object1 = (PrimaryMccObject) e1;
						PrimaryMccObject object2 = (PrimaryMccObject) e2;
						int result=0;
						result= object1.getId()>(object2.getId())? -1 : 1;
						if(idOrder==ASCENDING)
						{
							result=-result;
							return result;
						}
						if(idOrder==DECENDING)
						{
							return result;	
						}
						return result;
					};    	
				});

				primaryMccViewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		primaryMccViewer.getTable().addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				int row=Integer.parseInt((mccListTableCursor.getRow().getText()));
				mccIndex=row;
				cliqueSelectionWizardAction.setEnabled(true);
				int column=(mccListTableCursor.getColumn());
				//if(column==1)
				//{
				MccInstanceListContentProvider mccCloneInstanceListContentProvider=new MccInstanceListContentProvider();
				mccCloneInstanceListContentProvider.setList(mccList.getList().get(row).getCloneList().getList());
				secondaryMccViewer.setContentProvider(mccCloneInstanceListContentProvider);
				secondaryMccViewer.setLabelProvider(new MccCloneInstanceListLabelProvider());
				secondaryMccViewer.setInput(mccList.getList().get(row).getCloneList().getList());
				secondaryMccViewer.refresh();
				//}
			}
		});

		secondaryMccViewer.getTable().addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				int row=Integer.parseInt((mccInstanceListTableCursor.getRow().getText()));
				//System.out.println(row);
				int column=(mccInstanceListTableCursor.getColumn());
				//if(column==5){
				Method currentMethod =  SampleHandler.f.mccList.get(mccIndex).getMCCInstances().get(--row).getMethod();
				File fileToOpen = new File(currentMethod.getFilePath());
				String text = getFileContent(fileToOpen);    
				IFile ifile= getIFile(fileToOpen);
				if (fileToOpen.exists() && fileToOpen.isFile()) {
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IEditorPart editorPart = IDE.openEditorOnFileStore( page, fileStore );
						ITextEditor sourceEditor = (ITextEditor)editorPart;
						int startChar = text.indexOf(currentMethod.getCodeSegment());
						int offset = currentMethod.getCodeSegment().length();
						//highlightCode(ifile, sourceEditor, startChar, offset);
						sourceEditor.selectAndReveal(startChar, 0);
						IMarker[] markers = findMarkers(ifile);
						printInPopup(Integer.toString(markers.length));
					} catch ( CoreException ex ) {
						ex.printStackTrace();
					}
				}
				else{
					printInPopup(fileToOpen.getName()+" doesn't Exist!");
				}
				//}
			}
		});

	}

	public void createMccTables(Composite parent){
		primaryMccViewer=new TableViewer(parent,SWT.MULTI | SWT.FULL_SELECTION);
		createMccTableColumns(parent, primaryMccViewer);
		//setMccInput();
		populateMccList();
		MccListContentProvider mccListContentProvider=new MccListContentProvider();
		mccListContentProvider.setList(mccList.getList());
		primaryMccViewer.setContentProvider(mccListContentProvider);
		primaryMccViewer.setLabelProvider(new MccListLabelProvider());
		primaryMccViewer.setInput(mccList.getList());
		primaryMccViewer.refresh();
		secondaryMccViewer=new TableViewer(parent,SWT.MULTI | SWT.FULL_SELECTION);
		createMccCloneListTableColumns(parent,secondaryMccViewer);
	}

	private void createMccTableColumns(Composite parent, TableViewer primaryMccViewer){
		String[] titles = { "No. ", "MCCID", "Structure", "Number of Instance(s)","ATC ","APC" };
		int[] bounds = { 50,50,150,50,50,50 };
		mccListTable = this.primaryMccViewer.getTable();
		mccListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewerColumn mccListColumns=createMccTableColumn(titles[0], bounds[0], 0);
		mccListColumns = createMccTableColumn(titles[1], bounds[1], 1);
		mccListColumns = createMccTableColumn(titles[2], bounds[2], 2);
		mccListColumns = createMccTableColumn(titles[3], bounds[3], 3);
		mccListColumns = createMccTableColumn(titles[4], bounds[4], 4);
		mccListColumns = createMccTableColumn(titles[5], bounds[5], 5);
	}


	private TableViewerColumn createMccTableColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(primaryMccViewer,SWT.MULTI | SWT.FULL_SELECTION);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void createMccCloneListTableColumns(Composite parent, TableViewer secondartMccViewer){
		String[] titles = { "No. ", "FID", "MID", "TC","PC ","Method Name","File Name" };
		int[] bounds = { 50,50,50,50,50,150,150 };
		mccInstanceListTable = this.secondaryMccViewer.getTable();
		mccInstanceListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewerColumn mccCloneListColumns=createMccCloneListTableColumn(titles[0], bounds[0], 0);
		mccCloneListColumns = createMccCloneListTableColumn(titles[1], bounds[1], 1);
		mccCloneListColumns = createMccCloneListTableColumn(titles[2], bounds[2], 2);
		mccCloneListColumns = createMccCloneListTableColumn(titles[3], bounds[3], 3);
		mccCloneListColumns = createMccCloneListTableColumn(titles[4], bounds[4], 4);
		mccCloneListColumns = createMccCloneListTableColumn(titles[5], bounds[5], 5);
		mccCloneListColumns = createMccCloneListTableColumn(titles[6], bounds[6], 6);

	}

	private TableViewerColumn createMccCloneListTableColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(secondaryMccViewer,SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		primaryMccViewer.getControl().setFocus();
		secondaryMccViewer.getControl().setFocus();
	}

	public void setMccInput(){
		ArrayList<Integer> temp=new ArrayList<Integer>();
		temp.add(5);temp.add(6);temp.add(7);temp.add(8);
		mccList.addToList(new PrimaryMccObject(1,1,temp,5,91,89));
		mccList.addToList(new PrimaryMccObject(2,2,temp,8,61,37));
		mccList.addToList(new PrimaryMccObject(3,3,temp,12,47,75));
		setMccCloneListInput();
		mccList.getList().get(0).setCloneList(cloneList);
		mccList.getList().get(1).setCloneList(cloneList);
		mccList.getList().get(2).setCloneList(cloneList);
	}


	private void populateMccList(){
		if(SampleHandler.f != null)
			for (int i = 0; i < SampleHandler.f.mccList.size(); i++) 
			{
				int mccid = SampleHandler.f.mccList.get(i).getMCCID();
				int inctancecount=SampleHandler.f.mccList.get(i).getMCCInstanceCount();
				ArrayList <Integer> integerList=SampleHandler.f.mccList.get(i).getSCS();
				PrimaryMccObject temp=new PrimaryMccObject(i,mccid,integerList,inctancecount,0,0);
				mccList.addToList(temp);
				populate(mccid);
			}
	}

	private void makeActions() {
		cliqueSelectionWizardAction = new Action() {
			public void run() {
				int mccId = SampleHandler.f.mccList.get(mccIndex).getMCCID();
				//printInPopup("MCC Id Selected: "+ mccId);
				CliqueSelectionWizard cliqueWizard = new CliqueSelectionWizard();
				cliqueWizard.setMCCID(mccId);
				cliqueWizard.setMccIndex(mccIndex);
				ISelection selection = getSite().getWorkbenchWindow().getSelectionService().getSelection();
				IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
				if (selection instanceof IStructuredSelection){
					selectionToPass = (IStructuredSelection) selection;
				}
				cliqueWizard.init( getSite().getWorkbenchWindow().getWorkbench(), selectionToPass);
				Shell shell=  getSite().getWorkbenchWindow().getWorkbench().getActiveWorkbenchWindow().getShell();
				WizardDialog dialog= new WizardDialog(shell, cliqueWizard);
				try{
					dialog.create();
					int res = dialog.open();
					notifyResult(res == Window.OK);
				}catch(Exception e){
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
			}
		};
		cliqueSelectionWizardAction.setText("Run Method Analysis");
		cliqueSelectionWizardAction.setToolTipText("Run Method Analysis");
		cliqueSelectionWizardAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		cliqueSelectionWizardAction.setEnabled(false);
	}

	protected void populate(int mccid){
		cloneList=new MccCloneInstanceList();
		for (int i = 0; i < SampleHandler.f.mccList.size(); i++)
		{
			if (mccid == SampleHandler.f.mccList.get(i).getMCCID())
			{

				for (int j = 0; j < SampleHandler.f.mccList.get(i).getMCCInstances().size(); j++)
				{
					String filePath = SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getFilePath();
					String[] parts = filePath.split("[\\\\ ]");
					String fileName=parts[parts.length - 1];
					String methodName=SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName();
					int mId=(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodId());
					int fid = SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getFileId();
					int tokenCoverage = SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getTokenCount();
					double percentageCoverage = SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getCoverage();
					SecondaryMccObject temp=new SecondaryMccObject(j+1,fid,mId,tokenCoverage,percentageCoverage,methodName,fileName);
					cloneList.addToList(temp);


				}
				mccList.getList().get(i).setCloneList(cloneList);
			}
		}

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		//fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(cliqueSelectionWizardAction);
		manager.add(new Separator());
		//drillDownAdapter.addNavigationActions(manager);
	}
	public void setMccCloneListInput(){
		cloneList.addToList(new SecondaryMccObject(1,1,1,1,1,"add","file1"));
		cloneList.addToList(new SecondaryMccObject(2,2,2,2,2,"sub","file1"));
		cloneList.addToList(new SecondaryMccObject(3,3,3,3,3,"muls","file1"));
	}

	private void printInPopup(String in){
		MessageBox box = new MessageBox(getSite().getWorkbenchWindow().getShell(),SWT.ICON_INFORMATION);
		box.setMessage(in);
		box.open();
	}

	/*private static void navigateToLine(IFile file, Integer line)
	{
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put(IMarker.LINE_NUMBER, line);
	    IMarker marker = null;
	    try {
	        marker = file.createMarker(IMarker.TEXT);
	        marker.setAttributes(map);
	        try {
	            IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), marker);
	        } catch ( PartInitException e ) {
	            //complain
	        }
	    } catch ( CoreException e1 ) {
	        //complain
	    } finally {
	        try {
	            if (marker != null)
	                marker.delete();
	        } catch ( CoreException e ) {
	            //whatever
	        }
	    }
	}*/

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

	private void highlightCode(IFile ifile, ITextEditor sourceEditor, int startChar, int offset) throws CoreException {
		IMarker marker = ifile.createMarker("com.plugin.clonemanager.customtextmarker");
		marker.setAttribute(IMarker.CHAR_START, startChar);
		marker.setAttribute(IMarker.CHAR_END, startChar+offset);
		sourceEditor.selectAndReveal(startChar, 0);
	}
	
	private IMarker[] findMarkers(IResource target) throws CoreException {
		   String type = "com.plugin.clonemanager.customtextmarker";
		   IMarker[] markers = target.findMarkers(type, true, IResource.DEPTH_INFINITE);
		   return markers;
		}
}
