package com.plugin.clonemanager.views;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.dialogs.ViewComparator;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.plugin.clonemanager.SCCInstance;
import com.plugin.clonemanager.contentprovider.MccInstanceListContentProvider;
import com.plugin.clonemanager.contentprovider.SccInstanceListContentProvider;
import com.plugin.clonemanager.contentprovider.SccListContentProvider;
import com.plugin.clonemanager.core.MccCloneInstanceList;
import com.plugin.clonemanager.core.PrimaryMccObject;
import com.plugin.clonemanager.core.PrimarySccObject;
import com.plugin.clonemanager.core.SccCloneInstanceList;
import com.plugin.clonemanager.core.SccList;
import com.plugin.clonemanager.core.SecondaryMccObject;
import com.plugin.clonemanager.core.SecondarySccObject;
import com.plugin.clonemanager.handlers.SampleHandler;
import com.plugin.clonemanager.labelprovider.MccCloneInstanceListLabelProvider;
import com.plugin.clonemanager.labelprovider.SccCloneInstanceListLabelProvider;
import com.plugin.clonemanager.labelprovider.SccListLabelProvider;


public class SccView extends ViewPart {


	private TableViewer primarySccViewer;
	private TableViewer secondarySccViewer;
	private SccList sccList;
	private SccCloneInstanceList cloneList;
	Table sccListTable;
	Table sccInstanceListTable;
	static int ASCENDING=1;
	static int DECENDING=2;
	int cloneNumberOrder;
	int sccIndex=0;

	public SccView() {
		// TODO Auto-generated constructor stub
		super();
		primarySccViewer=null;
		secondarySccViewer=null;
		sccList=new SccList();
		cloneList=new SccCloneInstanceList();
		sccListTable=null;
		sccInstanceListTable=null;
		cloneNumberOrder=ASCENDING;

	}



	@Override
	public void createPartControl(Composite parent){
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		createSccTables(parent);
		sccListTable.setHeaderVisible(true);
		sccListTable.setLinesVisible(true);
		sccInstanceListTable.setHeaderVisible(true);
		sccInstanceListTable.setLinesVisible(true);
		final TableCursor sccListTableCursor= new TableCursor(sccListTable,SWT.NULL);
		final TableCursor sccInstanceListTableCursor= new TableCursor(sccInstanceListTable,SWT.NULL);


		primarySccViewer.getTable().getColumn(4).addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e){
				if(cloneNumberOrder==ASCENDING){
					cloneNumberOrder=DECENDING;
				}
				else
					if(cloneNumberOrder==DECENDING){
						cloneNumberOrder=ASCENDING;
					}
				primarySccViewer.setComparator(new ViewComparator(null){
					public int compare(Viewer viewer, Object e1, Object e2) {
						PrimarySccObject object1 = (PrimarySccObject) e1;
						PrimarySccObject object2 = (PrimarySccObject) e2;
						int result=0;
						if(object1.getNumberOfClone() == object2.getNumberOfClone()){
							//result=1;
						}
						else{
							result= object1.getNumberOfClone()>=(object2.getNumberOfClone())? -1 : 1;
						}
						if(cloneNumberOrder==ASCENDING){
							result=-result;
							return result;
						}
						if(cloneNumberOrder==DECENDING){
							return result;	
						}
						return result;
					};    	
				});

				primarySccViewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});



		primarySccViewer.getTable().getColumn(2).addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				if(cloneNumberOrder==ASCENDING)
				{
					cloneNumberOrder=DECENDING;

				}
				else
					if(cloneNumberOrder==DECENDING)
					{
						cloneNumberOrder=ASCENDING;
					}
				primarySccViewer.setComparator(new ViewComparator(null)
				{
					public int compare(Viewer viewer, Object e1, Object e2) {
						PrimarySccObject object1 = (PrimarySccObject) e1;
						PrimarySccObject object2 = (PrimarySccObject) e2;
						int result=0;
						if(object1.getLength() == object2.getLength())
						{
							//result=1;
						}
						else
						{
							result= object1.getLength()>=(object2.getLength())? -1 : 1;
						}
						if(cloneNumberOrder==ASCENDING)
						{
							result=-result;
							return result;
						}
						if(cloneNumberOrder==DECENDING)
						{
							return result;	
						}
						return result;
					};    	
				});

				primarySccViewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});



		primarySccViewer.getTable().addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				int row=Integer.parseInt((sccListTableCursor.getRow().getText()));
				int column=(sccListTableCursor.getColumn());
				//System.out.println(row);
				sccIndex=row;
				SccInstanceListContentProvider sccCloneInstanceListContentProvider=new SccInstanceListContentProvider();
				sccCloneInstanceListContentProvider.setList(sccList.getList().get(row).getCloneList().getList());
				secondarySccViewer.setContentProvider(sccCloneInstanceListContentProvider);
				secondarySccViewer.setLabelProvider(new SccCloneInstanceListLabelProvider());
				System.out.println(sccList.getList().get(row).getCloneList().getList().size());
				secondarySccViewer.setInput(sccList.getList().get(row).getCloneList().getList());
				//secondarySccViewer.setInput(cloneList.getList());
				secondarySccViewer.refresh();

			}
		});

		secondarySccViewer.getTable().addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				int row=Integer.parseInt((sccInstanceListTableCursor.getRow().getText()));
				int column=(sccInstanceListTableCursor.getColumn());
				//if(column==0){
				SCCInstance sccInstance = SampleHandler.f.sscList.get(sccIndex).getSCCInstances().get(--row);		
				File fileToOpen = new File(sccInstance.getFilePath());
				String content = getFileContent(fileToOpen);
				//StringBuffer contentB = new StringBuffer(content);
				//System.out.println(content);
				IFile ifile = getIFile(fileToOpen);
				if (fileToOpen.exists() && fileToOpen.isFile()) {
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						ITextEditor sourceEditor = (ITextEditor)IDE.openEditorOnFileStore( page, fileStore );
						int startChar = content.indexOf(sccInstance.getCodeSegment());
						int offset = sccInstance.getCodeSegment().length();
						//highlightCode(ifile, sourceEditor, startChar, offset);
						sourceEditor.selectAndReveal(startChar, offset);
						//printInPopup(sccInstance.getCodeSegment());
						//printInPopup("CharStart: "+startChar+"\nOffset: "+ offset);

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


	public void createSccTables(Composite parent)
	{
		primarySccViewer=new TableViewer(parent,SWT.MULTI | SWT.FULL_SELECTION);
		createSccTableColumns(parent, primarySccViewer);
		setSccInput();
		SccListContentProvider sccListContentProvider=new SccListContentProvider();
		sccListContentProvider.setList(sccList.getList());
		primarySccViewer.setContentProvider(sccListContentProvider);
		primarySccViewer.setLabelProvider(new SccListLabelProvider());
		primarySccViewer.setInput(sccList.getList());
		primarySccViewer.refresh();


		secondarySccViewer=new TableViewer(parent,SWT.MULTI | SWT.FULL_SELECTION);
		createSccCloneListTableColumns(parent,secondarySccViewer);


	}


	private void createSccTableColumns(Composite parent, TableViewer primarySccViewer)
	{
		String[] titles = { "No. ", "SCCID", "Length", "Benefit","Number of Clones "};
		int[] bounds = { 50,50,50,50,150};
		sccListTable = primarySccViewer.getTable();
		sccListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewerColumn sccListColumns=createSccTableColumn(titles[0], bounds[0], 0);
		sccListColumns = createSccTableColumn(titles[1], bounds[1], 1);
		sccListColumns = createSccTableColumn(titles[2], bounds[2], 2);
		sccListColumns = createSccTableColumn(titles[3], bounds[3], 3);
		sccListColumns = createSccTableColumn(titles[4], bounds[4], 4);

	}

	private TableViewerColumn createSccTableColumn(String title, int bound, final int colNumber) 
	{
		final TableViewerColumn viewerColumn = new TableViewerColumn(primarySccViewer,SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}


	private void createSccCloneListTableColumns(Composite parent, TableViewer secondartSccViewer)
	{
		String[] titles = { "No. ", "FID", "Start Line", "End Line"," File Name "};
		int[] bounds = { 50,50,50,50,200};
		sccInstanceListTable = secondarySccViewer.getTable();
		sccInstanceListTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		TableViewerColumn sccCloneListColumns=createSccCloneListTableColumn(titles[0], bounds[0], 0);
		sccCloneListColumns = createSccCloneListTableColumn(titles[1], bounds[1], 1);
		sccCloneListColumns = createSccCloneListTableColumn(titles[2], bounds[2], 2);
		sccCloneListColumns = createSccCloneListTableColumn(titles[3], bounds[3], 3);
		sccCloneListColumns = createSccCloneListTableColumn(titles[4], bounds[4], 4);

	}

	private TableViewerColumn createSccCloneListTableColumn(String title, int bound, final int colNumber) 
	{
		final TableViewerColumn viewerColumn = new TableViewerColumn(secondarySccViewer,SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}


	@Override
	public void setFocus() 
	{
		primarySccViewer.getControl().setFocus();
		secondarySccViewer.getControl().setFocus();
	}

	public void setSccInput()
	{
		if(SampleHandler.f!=null)
			populateSccList();


		//sccList.addToList(new PrimarySccObject(1,1,1,1,1,cloneList));
		//sccList.addToList(new PrimarySccObject(2,2,2,2,2,cloneList));
		//sccList.addToList(new PrimarySccObject(3,3,3,3,3,cloneList));
	}
	public void setSccCloneListInput(int sscid)
	{


		cloneList=new SccCloneInstanceList();
		for (int i = 0; i < SampleHandler.f.sscList.size(); i++)
		{
			if (sscid == SampleHandler.f.sscList.get(i).getSCCID())
			{

				for (int j = 0; j < SampleHandler.f.sscList.get(i).getSCCInstances().size(); j++)
				{
					//String filePath = handler.f.sscList.get(i).getSCCInstances().get(j).get.getFilePath();
					//String[] parts = filePath.split("[\\\\ ]");

					String filePath = SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getFilePath();
					String[] parts = filePath.split("[\\\\ ]");
					String fileName=parts[parts.length - 1];
					//String fileName= SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getFilePath();
					//String fileName=handler.f.sscList.get(i).getSCCInstances().get(j).getFileName();
					//String methodName=handler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName();
					int fId=SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getFileID();
					int startLine=SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getStartLine();
					int endLine=SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getEndLine();
					int startColoumn=SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getStartCol();
					int endColoumn=SampleHandler.f.sscList.get(i).getSCCInstances().get(j).getEndCol();
					SecondarySccObject temp=new SecondarySccObject(j+1,fId,fileName,startLine,endLine,startColoumn,endColoumn);
					cloneList.addToList(temp);


				}
				sccList.getList().get(i).setSccCloneList(cloneList);
				/*String[] Methds = new String[Method_Names.size()];
				Methds = Method_Names.toArray(Methds);
				combo.setItems(Methds);
				combo.setText(Methds[0]);
				String[] parts = Methds[0].split("[\\s+]");
				int MID = Integer.parseInt(parts[1]);
				Fill_Code(MID);*/
			}
		}



		/*cloneList.addToList(new SecondarySccObject(1,1,"file1",10,25));
		cloneList.addToList(new SecondarySccObject(2,2,"file1",35,67));
		cloneList.addToList(new SecondarySccObject(3,3,"file1",70,95));*/
	}


	private void populateSccList()
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < SampleHandler.f.sscList.size(); i++) 
		{
			int sccid = SampleHandler.f.sscList.get(i).getSCCID();
			int length=SampleHandler.f.sscList.get(i).getTokenCount();
			int benefit=0;
			int instanceCount=SampleHandler.f.sscList.get(i).getSCCInstanceCount();
			//ArrayList <Integer> integerList=SampleHandler.f.sscList.get(i);
			//MccCloneInstanceList cloneInstanceList=new MccCloneInstanceList();
			PrimarySccObject temp=new PrimarySccObject(i,sccid,length,benefit,instanceCount);
			sccList.addToList(temp);
			setSccCloneListInput(sccid);
			//Populate_Drop_Down(sccid);
		}

		//for (int i = 0; i < SampleHandler.f.mccList.size(); i++) 
		{
			//Populate_Drop_Down(SampleHandler.f.mccList.get(i).getMCCID()-1);
		}
		/*
		setMccCloneListInput();
		mccList.getList().get(0).setCloneList(cloneList);
		mccList.getList().get(1).setCloneList(cloneList);
		mccList.getList().get(2).setCloneList(cloneList);*/
	}

	private void printInPopup(String in){
		MessageBox box = new MessageBox(getSite().getWorkbenchWindow().getShell(),SWT.ICON_INFORMATION);
		box.setMessage(in);
		box.open();
	}

	private String getFileContent(File fileToOpen) {
		String text="";
		try {
			FileInputStream fis = new FileInputStream(fileToOpen);
			byte[] data = new byte[(int) fileToOpen.length()];
			fis.read(data);
			fis.close();
			text = new String(data, "UTF-8");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return text;
	}
	private IFile getIFile(File fileToOpen){
		IPath location= Path.fromOSString(fileToOpen.getAbsolutePath()); 
		IWorkspace workspace= ResourcesPlugin.getWorkspace();    
		return workspace.getRoot().getFileForLocation(location);
	}

	private void highlightCode(IFile ifile, ITextEditor sourceEditor,
			int startChar, int offset) throws CoreException {
		IMarker marker = ifile.createMarker("com.plugin.clonemanager.customtextmarker");
		marker.setAttribute(IMarker.CHAR_START, startChar);
		marker.setAttribute(IMarker.CHAR_END, startChar+offset);
		sourceEditor.selectAndReveal(startChar, 0);
		
	}
}


