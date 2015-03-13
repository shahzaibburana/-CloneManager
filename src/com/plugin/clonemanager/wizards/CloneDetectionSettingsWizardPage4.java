package com.plugin.clonemanager.wizards;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.plugin.clonemanager.handlers.SampleHandler;

public class CloneDetectionSettingsWizardPage4 extends WizardPage implements Listener {
	Tree tree;
	Combo combo;
	StyledText text;
	ScrolledComposite scrolledComposite;
	private CloneDetectionSettingsWizard wizard;
	CloneDetectionSettingsWizardPage5 page5;
	
	/**
	 * Create the wizard.
	 */
	public CloneDetectionSettingsWizardPage4(CloneDetectionSettingsWizard wizard) {
		super("wizardPage");
		setTitle("Method Clones Code Preview");
		setDescription("Select the Method Clone ID and then it's corresponding Method Name from the menus to view the code to be refactored using VCL\n");
		this.wizard = wizard;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		setControl(container);
		
		scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.top = new FormAttachment(0);
		fd_scrolledComposite.left = new FormAttachment(0);
		fd_scrolledComposite.bottom = new FormAttachment(0, 177);
		fd_scrolledComposite.right = new FormAttachment(0, 130);
		scrolledComposite.setLayoutData(fd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		
		combo = new Combo(container, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(scrolledComposite, 0, SWT.RIGHT);
		fd_combo.top = new FormAttachment(scrolledComposite, 6);
		fd_combo.left = new FormAttachment(0);
		combo.setLayoutData(fd_combo);
		
		text = new StyledText(container, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.READ_ONLY);
		text.setBackground(null);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		FormData fd_text_1 = new FormData();
		fd_text_1.left = new FormAttachment(scrolledComposite, 24);
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.bottom = new FormAttachment(100, -10);
		fd_text_1.top = new FormAttachment(0, 10);
		text.setLayoutData(fd_text_1);
		
//		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
//		gridData.heightHint = 5 * text.getLineHeight();
//		text.setLayoutData(gridData);
	
		
		
		final Tree tree = new Tree(scrolledComposite, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setContent(tree);
		scrolledComposite.setMinSize(tree.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		Populate_Method_Tree(tree);
		
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String string = "";
				TreeItem[] selection = tree.getSelection();
				for (int i = 0; i < selection.length; i++)
					string += selection[i] + " ";
				String parts[] = string.split("[{ : }]");
				page5 = wizard.getPage5();
				page5.mccid=Integer.parseInt(parts[4]);
				System.out.println("THE MCCID I SEND IS: " + Integer.parseInt(parts[4]));
				Populate_Drop_Down(Integer.parseInt(parts[4]));
			}
		});

		text.addLineStyleListener(new LineStyleListener() {
			@Override
			public void lineGetStyle(LineStyleEvent e) {
				// Set the line number
				e.bulletIndex = text.getLineAtOffset(e.lineOffset);

				// Set the style, 12 pixles wide for each digit
				StyleRange style = new StyleRange();
				style.metrics = new GlyphMetrics(0, 0, Integer.toString(
						text.getLineCount() + 1).length() * 12);

				// Create and set the bullet
				e.bullet = new Bullet(ST.BULLET_NUMBER, style);
			}
		});
		text.addListener(SWT.Resize, new Listener(){
			public void handleEvent(Event e){
				
			}
		});
		combo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String n_id = combo.getText();
				String[] parts = n_id.split("[\\s+]");
				int MID = Integer.parseInt(parts[1]);
				Fill_Code(MID);
				Color_Code(text);
			}
		});
	}

	protected void Populate_Drop_Down(int id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
					for (int i = 0; i < SampleHandler.f.mccList.size(); i++) {
						if (id == SampleHandler.f.mccList.get(i).getMCCID()) {
							ArrayList<String> Method_Names = new ArrayList<>();
							for (int j = 0; j < SampleHandler.f.mccList.get(i)
									.getMCCInstances().size(); j++) {
								String name = SampleHandler.f.mccList.get(i)
										.getMCCInstances().get(j).getMethod()
										.getFilePath();
								String[] parts = name.split("[\\\\ .]");
								Method_Names.add(parts[parts.length - 2]
										+ " "
										+ Integer
												.toString(SampleHandler.f.mccList
														.get(i).getMCCInstances()
														.get(j).getMethod()
														.getMethodId()));
							}
							String[] Methds = new String[Method_Names.size()];
							Methds = Method_Names.toArray(Methds);
							combo.setItems(Methds);
							combo.setText(Methds[0]);
							String[] parts = Methds[0].split("[\\s+]");
							int MID = Integer.parseInt(parts[1]);
							Fill_Code(MID);
						}
					}
		
	}

	private void Color_Code(StyledText text) {
		// TODO Auto-generated method stub
		//System.out.println("dffkjDGkljBSGDKJBSGKbjasg");
		Shell shell = text.getShell();
	    Display display = shell.getDisplay();
//
//	    String[] lines = text.getText().split("\\n");
//
//	    String keyWord = "public";
//	    Color red = display.getSystemColor(SWT.COLOR_RED);
//
//	    int offset = 0;
//	    for (String line : lines)
//	    {
//	        int index = line.indexOf(keyWord);
//	        if (index != -1)
//	        {
//	            StyleRange range = new StyleRange(index + offset, keyWord.length(), red, null, SWT.BOLD);
//	            text.setStyleRange(range);
//	        }
//
//	        offset += line.length() + 1; // +1 for the newline character
//	    }
    
//	    shell.pack();
//	    shell.open();
//	    while (!shell.isDisposed())
//	    {
//	        if (!display.readAndDispatch())
//	        {
//	            display.sleep();
//	        }
//	    }
//	    display.dispose();
		
		Color orange = new Color(display, 255, 127, 0);
		Color lime = new Color(display, 127, 255, 127);

		// make "This" bold and orange
		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = 40;
		styleRange.fontStyle = SWT.BOLD;
		styleRange.foreground = orange;
		text.setStyleRange(styleRange);

		// make "StyledText" bold and lime
		styleRange = new StyleRange();
		styleRange.start = 12;
		styleRange.length = 10;
		styleRange.fontStyle = SWT.BOLD;
		styleRange.foreground = lime;
		text.setStyleRange(styleRange);

//		styleRange = new StyleRange(12, 10, null, null, SWT.NORMAL);		
//		widget.setStyleRange(styleRange);	// set the bold, lime colored text back to normal
//		lime.dispose();				// lime is no longer used by the widget so it can be disposed

		shell.open();
		while (!shell.isDisposed())
		if (!display.readAndDispatch()) {
			display.sleep();
		}

		orange.dispose();
		lime.dispose();				//
	    System.out.println("ENSDFBKEAJRFB>KASJDFBJSGF");
	}

	private void Fill_Code(int MID) {
		// TODO Auto-generated method stub
		for (int i = 0; i < SampleHandler.f.methodList.size(); i++) {
			if (SampleHandler.f.methodList.get(i).getMethodId() == MID) {
				text.setText(SampleHandler.f.methodList.get(i).getCodeSegment());
			}
		}
	}

	private void Populate_Method_Tree(Tree tree) {
		// TODO Auto-generated method stub
		for (int i = 0; i < SampleHandler.f.mccList.size(); i++) {
			int id = SampleHandler.f.mccList.get(i).getMCCID();
			TreeItem temp = new TreeItem(tree, SWT.V_SCROLL);
			temp.setText("MCCID: " + id);
			Populate_Drop_Down(id);
		}
	}

	public IWizardPage getNextPage() {
		//saveDataToWizard();
		
		page5 = wizard.getPage5();
		try {
			page5.setMethod();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//page5.setMethod();
		return page5;
	}
	
    @Override
    public IWizardPage getPreviousPage() {
    	return null;
    }

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

	}
}
