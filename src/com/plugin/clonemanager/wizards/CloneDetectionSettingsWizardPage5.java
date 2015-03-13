package com.plugin.clonemanager.wizards;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;





import com.plugin.clonemanager.ClonesParser;
import com.plugin.clonemanager.MCC;
import com.plugin.clonemanager.MCCInstance;
import com.plugin.clonemanager.SCCInstance;

//import our_plugin.Parser;
import com.plugin.clonemanager.handlers.SampleHandler;

public class CloneDetectionSettingsWizardPage5 extends WizardPage implements Listener{
	public static MCC mcc;
	static ArrayList<Integer> SccsSelected;
	int mccid=-1,instance1id,instance2id;
	private StyleRange style;
	ArrayList<Color> colorsused;
	private StyledText text_1;
	Combo combo,combo_1,combo_2;
		StyledText text;
	Label label,lblNewLabel;
	/**
	 * Create the wizard.
	 */
	public CloneDetectionSettingsWizardPage5(CloneDetectionSettingsWizard wizard) {
		super("wizardPage");
		setPageComplete(false);
		colorsused=new ArrayList<Color>();
		SccsSelected = new ArrayList<Integer>();
		setTitle("Method Clone selected is " );
		setDescription("Select the Clique of Simple Clones that you wish to view and refactor using VCL\n");
	}

	public void setMethod() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("THE MCCID I GET IS: " + mccid);
		if(mccid==-1)
		{
			System.out.println("SOME PROBLEM");
		}
		for(int i = 0 ; i < SampleHandler.f.mccList.size() ; i++)
		{
			if(SampleHandler.f.mccList.get(i).getMCCID()==mccid)
			{
				mcc=SampleHandler.f.mccList.get(i);
				break;
			}
		}
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new FormLayout());
		try {
			setMethod();
			setTitle("Method Clone selected is " + mccid);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setControl(container);
		((WizardDialog) this.getWizard().getContainer()).setPageSize(400, 400);
		
		text = new StyledText(container, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		//fd_text_1.left = new FormAttachment(text, 546);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0);
		text.setLayoutData(fd_text);
		fd_text.right = new FormAttachment(100, -464);
		combo = new Combo(container, SWT.READ_ONLY);
		fd_text.left = new FormAttachment(combo, 0, SWT.LEFT);
		FormData fd_combo = new FormData();
		fd_combo.bottom = new FormAttachment(100, -5);
		fd_combo.left = new FormAttachment(0, 8);
		combo.setLayoutData(fd_combo);
		text_1 = new StyledText(container,SWT.BORDER | SWT.READ_ONLY  | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		fd_text.right = new FormAttachment(text_1, -10);
		FormData fd_text_1 = new FormData();
		fd_text_1.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		fd_text_1.top = new FormAttachment(0);
		text_1.setLayoutData(fd_text_1);
		
		label = new Label(container, SWT.NONE);
		fd_text_1.left = new FormAttachment(label, -108, SWT.LEFT);
		fd_text_1.right = new FormAttachment(label, 0, SWT.RIGHT);
		label.setText("coverage: ");
		FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(combo, 0, SWT.BOTTOM);
		label.setLayoutData(fd_label);
		
		lblNewLabel = new Label(container, SWT.NONE);
		fd_label.left = new FormAttachment(lblNewLabel, 334);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(100, -721);
		fd_lblNewLabel.left = new FormAttachment(combo, 88);
		fd_lblNewLabel.bottom = new FormAttachment(combo, 0, SWT.BOTTOM);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("coverage: ");
		
		Button btnSelect = new Button(container, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SetData();
				System.out.println("done");
			}	
		});
		FormData fd_btnSelect = new FormData();
		fd_btnSelect.top = new FormAttachment(text, 6);
		btnSelect.setLayoutData(fd_btnSelect);
		btnSelect.setText("Select");
		
		combo_1 = new Combo(container, SWT.NONE);
		fd_text.bottom = new FormAttachment(combo_1, -6);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.bottom = new FormAttachment(lblNewLabel, -6);
		combo_1.setLayoutData(fd_combo_1);
		
		combo_2 = new Combo(container, SWT.NONE);
		fd_btnSelect.right = new FormAttachment(combo_2, -138);
		FormData fd_combo_2 = new FormData();
		fd_combo_2.left = new FormAttachment(0, 631);
		fd_combo_2.bottom = new FormAttachment(label, -6);
		combo_2.setLayoutData(fd_combo_2);
		Populate_Drop_Down();
		
		Label lblSelectClique = new Label(container, SWT.NONE);
		fd_combo_1.left = new FormAttachment(lblSelectClique, 118);
		FormData fd_lblSelectClique = new FormData();
		fd_lblSelectClique.bottom = new FormAttachment(combo, -6);
		fd_lblSelectClique.left = new FormAttachment(combo, 0, SWT.LEFT);
		lblSelectClique.setLayoutData(fd_lblSelectClique);
		lblSelectClique.setText("Select Clique");
		SetData();
		combo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
			}
		});
	}


		private void SetData() {
		// TODO Auto-generated method stub
			setClique();
			setCode();
			setLabels();
			try {
				Highlight();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

		void Populate_Drop_Down() {
		// TODO Auto-generated method stub
			Collection<Set<Integer>> cliques = mcc.getMCCInstances().get(0).getSortedCliques();
			String[] cliqes=new String[cliques.size()];
			String[] instances=new String[mcc.getMCCInstances().size()];
			for(int i=0;i<cliques.size();i++)
			{
				int effective=i+1;
				cliqes[i]="Clique" + " " + effective;
			}
			for(int i=0;i< mcc.getMCCInstances().size();i++)
			{
				int effective=i+1;
				instances[i]="Instance: " + effective;
			}
			combo_1.setItems(instances);
			combo_1.setText(instances[0]);
			combo_2.setItems(instances);
			combo_2.setText(instances[1]);
			combo.setItems(cliqes);
			combo.setText(cliqes[0]);
	}

		private void highlight1(ArrayList<Color> colorsused) throws IOException {
			MCCInstance mci=mcc.getMCCInstances().get(instance1id);
			String code=mci.getMethod().getCodeSegment();
			String path=ClonesParser.getFilePath(mci.getMethod().getFileId());
			for(Integer k : SccsSelected)
			{
				int i1=0,i2=0,i3=0;
				while(i1+i2+i3<100 || i1+i2+i3>550)
				{
					Random r1 = new Random();
					i1 = r1.nextInt(255);
					Random r2 = new Random();
					i2 = r2.nextInt(255);
					Random r3 = new Random();
					i3 = r3.nextInt(255);	
				}
				Color temp = new Color(null,i1,i2,i3);
				colorsused.add(temp);
				int length=0;
				int start=0;
				for(SCCInstance scc: mci.getSCCs())
				{
					if(scc.getSCCID()==k)
					{
						length=scc.getCodeSegment().length()-1;
						if(mci.getMethod().getStartLine()==scc.getStartLine())
						{
							String completelinecode=ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,mci.getMethod().getStartLine()+1,1,null);
							String intermediatecode=completelinecode.substring(0,scc.getStartCol()-1);
							start= intermediatecode.length();
						}
						else
						{
							String intermediatecode=ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,scc.getStartLine(),scc.getStartCol()-1,null);
							start=intermediatecode.length()-1;	
						}
					}
				}
				style = new StyleRange();
				style.start = start;
				style.length = length;
				style.fontStyle = SWT.BOLD;
				style.background = temp;
				text.setStyleRange(style);
			}
		}
		private void Highlight() throws IOException {
			colorsused.clear();
			highlight1(colorsused);
			highlight2(colorsused);
		}
		
		private void highlight2(ArrayList<Color> colorsused) throws IOException {
			MCCInstance mci=mcc.getMCCInstances().get(instance2id);
			String code=mci.getMethod().getCodeSegment();
			String path=ClonesParser.getFilePath(mci.getMethod().getFileId());
			int count=0;
			for(Integer k : SccsSelected)
			{
				Color temp = colorsused.get(count);
				colorsused.add(temp);
				int length=0;
				int start=0;
				for(SCCInstance scc: mci.getSCCs())
				{
					if(scc.getSCCID()==k)
					{
						length=scc.getCodeSegment().length()-1;
						if(mci.getMethod().getStartLine()==scc.getStartLine())
						{
							String completelinecode=ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,mci.getMethod().getStartLine()+1,1,null);
							String intermediatecode=completelinecode.substring(0,scc.getStartCol()-1);
							start= intermediatecode.length();
						}
						else
						{
							String intermediatecode=ClonesParser.getCodeSegment(path,mci.getMethod().getStartLine(),1,scc.getStartLine(),scc.getStartCol()-1,null);
							start=intermediatecode.length()-1;	
						}							
					}
				}
				style = new StyleRange();
				style.start = start;
				style.length = length;
				style.fontStyle = SWT.BOLD;
				style.background = temp;
				text_1.setStyleRange(style);
				count++;
			}
		}

		private void setLabels() {
			// TODO Auto-generated method stub
			System.out.println("setting labels");
			int scccount1=0;
			int scccount2=0;
			MCCInstance mcc1=mcc.getMCCInstances().get(instance1id);
			MCCInstance mcc2=mcc.getMCCInstances().get(instance2id);
			for(Integer k : SccsSelected)
			{
				for(SCCInstance scc : mcc1.getSCCs())
				{
						if(scc.getSCCID()==k)
						{
							scccount1=scccount1+scc.getCodeSegment().length();
							break;
						}
				}
				for(SCCInstance scc : mcc2.getSCCs())
				{
						if(scc.getSCCID()==k)
						{
							scccount2=scccount2+scc.getCodeSegment().length();
							break;
						}
				}
				
			}
			int methodcount1=mcc.getMCCInstances().get(instance1id).getMethod().getCodeSegment().length();
			int methodcount2=mcc.getMCCInstances().get(instance2id).getMethod().getCodeSegment().length();
			double percentage1=(double)scccount1/(double)methodcount1;
			double percentage2=(double)scccount2/(double)methodcount2;
			percentage1=percentage1*100;
			percentage2=percentage2*100;
			DecimalFormat df = new DecimalFormat("#.#");
			String percent1=df.format(percentage1);
			String percent2=df.format(percentage2);
			lblNewLabel.setText("coverage: " + percent1 + "%");
			label.setText("coverage: " + percent2 + "%");
		}

		private void setCode() {
			// TODO Auto-generated method stub
			System.out.println("setting code");
			String id1 = combo_1.getText();
			String [] parts = id1.split(" ");
			instance1id=Integer.parseInt(parts[1])-1;
			text.setText(mcc.getMCCInstances().get(instance1id).getMethod().getCodeSegment());
			String id2 = combo_2.getText();
			String [] parts2 = id2.split(" ");
			instance2id=Integer.parseInt(parts2[1])-1;
			text_1.setText(mcc.getMCCInstances().get(instance2id).getMethod().getCodeSegment());
		}

		private void setClique() {
			// TODO Auto-generated method stub
			System.out.println("setting clique");
			SccsSelected.clear();
			String n_id = combo.getText();
			String [] parts = n_id.split(" ");
			int cliquenumber;
			//System.out.println(Integer.parseInt(parts[1]));
			Iterator<Set<Integer>> iterator = mcc.getMCCInstances().get(0).getSortedCliques().iterator();
			int count=0;
			cliquenumber=Integer.parseInt(parts[1]);
			while(iterator.hasNext()) // ITERATE THROUGH CLIQUES
			{
				count++;
				if(count==cliquenumber)
				{
					Set<Integer> set=iterator.next();
					Iterator<Integer> itr=set.iterator();
					while(itr.hasNext())
					{
						int sccid=mcc.getMCCInstances().get(0).getSCCs().get(itr.next()).getSCCID();
						SccsSelected.add(sccid);
					}
					break;
				}
				else
				{
					iterator.next();
				}
			}
		}
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			
		}

		
}

