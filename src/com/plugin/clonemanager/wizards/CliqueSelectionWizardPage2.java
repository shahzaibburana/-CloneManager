
package com.plugin.clonemanager.wizards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.Page;

import com.plugin.clonemanager.MCCInstance;
import com.plugin.clonemanager.MCCsAnalyser;
import com.plugin.clonemanager.handlers.SampleHandler;



public class CliqueSelectionWizardPage2 extends WizardPage{
	

	//private VCLFrameWizard wizard;
	
	//FileHandler fileHandler = new FileHandler();
	//int totalTabs=fileHandler.listOfVCLFiles.size();
	
	FileHandler fileHandler;
	int totalTabs;
		
	
	ArrayList<String> variableNamesList;
	
	ArrayList<TabItem> tabItems= new ArrayList<TabItem>();
	ArrayList<StyledText> textVCLCodes =  new ArrayList<StyledText>();
		
	
	Button Cancel;
	Button Save;
	Button update;
	Button reset;
	Button showWizard;
	StyledText VCLCode;
	
	
	 Table table;


	protected CliqueSelectionWizardPage2(CliqueSelectionWizard wizard) {
		// TODO Auto-generated constructor stub
		
		
		
		super("VCL Code Frame");
		setTitle("VCL Code Frame");
		setDescription("VCL code is displayed.\n" +
				"User can update the variable names.");
		
		
		
		
	}
	@Override
	public void createControl(Composite parent) {

	// TODO Auto-generated method stub
			/* new */

			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout gridLayout = new GridLayout(1, false);
			composite.setLayout(gridLayout);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.grabExcessHorizontalSpace = true;
			new Label(composite, SWT.NULL);

			// tabs work starts here

			GridData TabgridData = new GridData(GridData.FILL_HORIZONTAL | SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL);
			final TabFolder tabFolder = new TabFolder(composite, SWT.BORDER
					| SWT.V_SCROLL | SWT.H_SCROLL);
			TabgridData.heightHint = 150;
			tabFolder.setLayoutData(TabgridData);
			insertTabs(composite, tabFolder);

			// Vriable table 
			GridData tablegridData = new GridData(GridData.FILL_HORIZONTAL | SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL);
			tablegridData.horizontalSpan = 3;
			//.horizontalAlignment = GridData.;
			tablegridData.heightHint = 80;
			
			table = new Table(composite, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
			table.setHeaderVisible(true);
			TableColumn column1 = new TableColumn(table, SWT.NONE);
			TableColumn column3 = new TableColumn(table, SWT.NONE);
			TableColumn column2 = new TableColumn(table, SWT.NONE);
			column1.setText("Serial No.");
			column3.setText("Variable Name");
			column2.setText("Edit Variable Name");
			variableNamesList = new ArrayList<String>(
					fileHandler.getVariableNames());
			
			insertTable(variableNamesList);
			column1.pack();
			column2.pack();
			column3.pack();
			final TableEditor editor = new TableEditor(table);
			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			editor.minimumWidth = 55;
			
			// editing the second column
			final int EDITABLECOLUMN = 2;
			table.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Clean up any previous editor control
					Control oldEditor = editor.getEditor();
					reset.setGrayed(false);
					reset.setEnabled(true);
					if (oldEditor != null)
						oldEditor.dispose();
					// Identify the selected row
					TableItem item = (TableItem) e.item;
					if (item == null)
						return;
					// The control that will be the editor must be a child of the
					// Table
					Text newEditor = new Text(table, SWT.NONE);
					newEditor.setText(item.getText(EDITABLECOLUMN));
					newEditor.addModifyListener(new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent e) {
							// TODO Auto-generated method stub
							Text text = (Text) editor.getEditor();
							editor.getItem()
									.setText(EDITABLECOLUMN, text.getText());
						}
					});
					newEditor.selectAll();
					newEditor.setFocus();
					editor.setEditor(newEditor, item, EDITABLECOLUMN);
				}
			});
			table.setLayoutData(tablegridData);
			// varialbe table ends here 
			// button table starts here 
			// tabs work starts here

																		
			
			Composite buttonsLayout = new Composite(composite, SWT.NONE);
			FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
			fillLayout.spacing = 10;
			//fillLayout.
			buttonsLayout.setLayout(fillLayout);
			
			update = new Button(buttonsLayout, SWT.PUSH);
			update.setText("Update");

			Save = new Button(buttonsLayout, SWT.PUSH);
			Save.setText("Save");
			
			reset = new Button(buttonsLayout, SWT.PUSH);
			reset.setText("Reset");
			/* click listeners */
			update.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					updateTable();
					//updateText();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
				}
			});

			Save.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
					updateTable();
					//updateText();
					updateCode();
					updateVariableNames();
					
					reset.setEnabled(false);
					reset.setGrayed(true);
									
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
				}
			});

			reset.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					/*** Insert file contents in text field *****/
					ArrayList<String> fileContents;
					
					/******* Reset Tabs Text ********/
					for (int i=0; i<textVCLCodes.size(); i++){
						 fileContents = new ArrayList<String>(
									fileHandler.getFileContents(fileHandler.VCLFilesPaths.get(i)));
						//insertText(VCLCode, fileContents);
						insertText(textVCLCodes.get(i),fileContents);
					
					}
					
					/***** Reset table ****/
					TableItem[] tableItems = table.getItems();
					
					for (int i = 0; i < tableItems.length; i++) {
						TableItem item = table.getItem(i);
						item.setText(new String[] { "" + i,
								variableNamesList.get(i), variableNamesList.get(i) });
						
						
					}
					
					reset.setFocus();
					reset.forceFocus();
				
					
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
			/// buttons ends here 
			setControl(composite);
		}
	
	/****** Insert tabs of vcl files *********/
	public void insertTabs(Composite composite, TabFolder tabFolder) {
		TabItem tabItem;
		StyledText textVCLCode;
		for (int i = 0; i < totalTabs; i++) {
			tabItem = new TabItem(tabFolder, SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL);

			tabItem.setText(fileHandler.listOfVCLFiles.get(i));
			textVCLCode = new StyledText(tabFolder, SWT.BORDER | SWT.V_SCROLL
					| SWT.H_SCROLL);
			textVCLCode.setLayoutData(composite);
			textVCLCode.setEditable(false);
			tabItem.setControl(textVCLCode);
			textVCLCode.setText("");

			ArrayList<String> fileContents = new ArrayList<String>(
					fileHandler.getFileContents(fileHandler.VCLFilesPaths
							.get(i)));

			if (!fileContents.isEmpty()) {
				insertText(textVCLCode, fileContents);
			}
			tabItems.add(tabItem);
			textVCLCodes.add(textVCLCode);
		}
	}
	
	/****** Populate table with variable names *********/
	public void insertTable(ArrayList<String> variableNames) {
		// variableNamesList = fileHandler.getVariableNames();
		if (!variableNames.isEmpty()) {
			for (int i = 0; i < variableNames.size(); i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { "" + i, variableNames.get(i),
						variableNames.get(i) });
			}
		}
	}
	
	/****** Update table contents after user changes *********/
	public void updateTable() {
		ArrayList<String> oldVariableNames = new ArrayList<String>();
		ArrayList<String> newVariableNames = new ArrayList<String>();
		ArrayList<String> updatedVariableNames = new ArrayList<String>();
		String variableName;
		Boolean valid =Boolean.TRUE.booleanValue();
		TableItem[] tableItems = table.getItems();
		
		for (int i = 0; i < tableItems.length; i++) {
			oldVariableNames.add(tableItems[i].getText(1));
			newVariableNames.add(tableItems[i].getText(2));
			variableName = tableItems[i].getText(2);
			valid = isValidIdentifier(variableName);
			
			if (!oldVariableNames.get(i).equals(newVariableNames.get(i))){
				if (valid){
					updatedVariableNames.add(oldVariableNames.get(i) + '_' + newVariableNames.get(i));
				}
				else{
					
					 MessageBox messageBox=new MessageBox(new Shell(),SWT.ICON_WARNING | SWT.OK);
					 messageBox.setText("Error Message");
					 messageBox.setMessage("Invalid Variable Name!");
					 messageBox.open();
					 updatedVariableNames.add(oldVariableNames.get(i));
				}
			}
			else {
				updatedVariableNames.add(oldVariableNames.get(i));
			}
		}
		
		/* Table updates with updated variable names */
		for (int i = 0; i < tableItems.length; i++) {
			TableItem item = table.getItem(i);
			item.setText(new String[] { "" + i, updatedVariableNames.get(i),
					updatedVariableNames.get(i) });
		}
		/**** Update code's text field ****/
		updateText(oldVariableNames);
	}
	
	/******Update Variable names *********/
	public void updateVariableNames(){
		TableItem[] tableItems = table.getItems();
		ArrayList<String> newVariableNames = new ArrayList<String>();
		
		for (int i = 0; i < tableItems.length; i++) {
			newVariableNames.add(tableItems[i].getText(2));
			variableNamesList.add(i, newVariableNames.get(i));
		}
	}

	
	/****** Updates styled text field *********/
	public void updateText(ArrayList<String> oldVariableNames) {
		ArrayList<String> oldVCLCode;
		ArrayList<String> newVCLCode;
		ArrayList<String> newVariableNames = new ArrayList<String>();
		TableItem[] tableItems = table.getItems();
		for (int i = 0; i < tableItems.length; i++) {
			
			newVariableNames.add(tableItems[i].getText(2));
		}
		for (int j=0; j<textVCLCodes.size(); j++){
			 newVCLCode = new ArrayList<String>();
			oldVCLCode = new ArrayList<String>(Arrays.asList(textVCLCodes.get(j).getText()
					.split("\n")));
			String line = "";
			for (int i = 0; i < oldVCLCode.size(); i++) {
				line = oldVCLCode.get(i);
				for (int k = 0; k < oldVariableNames.size(); k++) {
					line = line.replaceAll(oldVariableNames.get(k),
							newVariableNames.get(k));
	
				}
				newVCLCode.add(line);
			}
			/** Update text field with updated contents **/
			insertText(textVCLCodes.get(j), newVCLCode);
		}
		
	}
	
	/****************** Update .vcl files contents *************/
	public void updateCode() {
		TableItem[] tableItems = table.getItems();
		ArrayList<String> changes = new ArrayList<String>();

		// Get updated variable names
		for (int i = 0; i < tableItems.length; i++) {
			changes.add(tableItems[i].getText(2));
		}
		for(int i=0; i<fileHandler.VCLFilesPaths.size(); i++){
			fileHandler.updateFileContents(changes, fileHandler.VCLFilesPaths.get(i) ); 
		}
		

	}

	/*********** Populate text field with file contents *************/
	public void insertText(StyledText VCLCode, ArrayList<String> textContents) {
		VCLCode.setText(""); // Removes old contents
		for (int i = 0; i < textContents.size(); i++) {
			VCLCode.append(textContents.get(i) + "\n");
		}
	}

	
	/*********** Check validity of variable name entered by user **********/
	public static boolean isValidIdentifier(String s) {
	    if (s.isEmpty()) {
	        return false;
	    }
	   
	    for (int i = 0; i < s.length(); i++) {
	        if (!Character.isJavaIdentifierPart(s.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}


	
	public boolean canFlipToNextPage() {
		
		
		
		return false;
	}
}
