package com.plugin.clonemanager.labelprovider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugin.clonemanager.core.PrimaryMccObject;


	class MccListColumn
	{
		public static final int id=0;
		public static final int mccId=1;
		public static final int structure=2;
		public static final int numberOfInstance=3;
		public static final int atc=4;
		public static final int apc=5;
		
	}

public class MccListLabelProvider implements ITableLabelProvider{

	
	public MccListLabelProvider()
	{
		super();
	}
	
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex)
	{
		PrimaryMccObject object = (PrimaryMccObject) element;
	    String text = "";
	    switch (columnIndex) {
	    case MccListColumn.id:
	      text = String.valueOf(object.getId());
	      break;
	    case MccListColumn.mccId:
	      text = String.valueOf(object.getMccId());
	      break;
	    case MccListColumn.structure:
	    	String result="";
	    	for(int i=0;i<object.getStructure().size();i++)
	    	{
	    		result=result+String.valueOf(object.getStructure().get(i));
	    		if(i<object.getStructure().size()-1)
	    		{
	    			result=result+",";
	    		}
	    	}
	      text = result;
	      break;
	    case MccListColumn.numberOfInstance:
	      text = String.valueOf(object.getNumberOfInstance());
	      break;
	    case MccListColumn.atc:
	      text = String.valueOf(object.getAtc());
	      break;
	    case MccListColumn.apc:
	    	text=String.valueOf(object.getApc());
	    }
	    return text;
	}

}
