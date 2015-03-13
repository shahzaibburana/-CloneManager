package com.plugin.clonemanager;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.plugin.clonemanager.handlers.SampleHandler;

public class UIStart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220245853859377741L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIStart frame = new UIStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UIStart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new GridLayout(9, 9, 0, 0));
    	scrollPane.setPreferredSize(new Dimension(450,300));
    	contentPane.add(scrollPane);
    	 DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
         //create the child nodes
         addNodes(root);
          
         //create the tree by passing in the root node
         final JTree tree = new JTree(root);
         tree.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent me) {
        		 doMouseClicked(me);
        	 }

			private void doMouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				javax.swing.tree.TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
				//String fname= (String) ;
				if(!tp.equals("Root")){
				String fname = tp.getLastPathComponent().toString();
				showcode(fname);
				}
			}

			private void showcode(String fname) {
				// TODO Auto-generated method stub
				for(int i=0;i<SampleHandler.f.mccList.size();i++){
					for(int j=0;j<SampleHandler.f.mccList.get(i).getMCCInstances().size();j++){
						if(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName().equalsIgnoreCase(fname)){
							JPanel temp = new JPanel();
							JTextPane textPane = new JTextPane();
							String data ="****************************START***************************\n" + (SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getCodeSegment()) + "****************************END***************************\n";
							textPane.setText(data);
							temp.add(textPane);
							temp.revalidate();
							temp.repaint();
							temp.setVisible(true);
							add(temp);
							add(new JScrollPane(temp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
							revalidate();
							repaint();
							setVisible(true);
						}
							
					}
				}
			}
         });
         getContentPane().add(new JScrollPane(tree));
         this.pack();
         this.setVisible(true);
         contentPane.setVisible(true);
	}	 
	private void addNodes(DefaultMutableTreeNode root) {
		// TODO Auto-generated method stub
		String file_name;
		ArrayList<String> paths_added = new ArrayList<>();
		for(int i=0;i<SampleHandler.f.mccList.size();i++)
		{
			for(int j=0;j<SampleHandler.f.mccList.get(i).getMCCInstances().size();j++)
			{
				file_name=SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getFilePath();
				if(notadded(file_name,paths_added))
				{
					DefaultMutableTreeNode temp = new DefaultMutableTreeNode(file_name);
					paths_added.add(file_name);
					addmethods(file_name,temp);
					root.add(temp);
				}
			}
		}
	}

	private void addmethods(String file_name, DefaultMutableTreeNode temp) {
		ArrayList<String> methods = new ArrayList<>();
			for(int i=0;i<SampleHandler.f.mccList.size();i++)
			{
				for(int j=0;j<SampleHandler.f.mccList.get(i).getMCCInstances().size();j++)
				{
					if(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getFilePath().equalsIgnoreCase(file_name))
					{
						if(notadded(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName(),methods)){
						temp.add(new DefaultMutableTreeNode(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName()));
						methods.add(SampleHandler.f.mccList.get(i).getMCCInstances().get(j).getMethod().getMethodName());
						}
					}
				}
			}
	}

	private boolean notadded(String path, ArrayList<String> paths_added) {
		// TODO Auto-generated method stub
		for(String check : paths_added)
		{
			if(check.contains(path))
			{
				return false;
			}
		}
		return true;
	}
}
