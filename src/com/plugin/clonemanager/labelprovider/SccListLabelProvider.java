package com.plugin.clonemanager.labelprovider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugin.clonemanager.core.PrimarySccObject;




class SccListColumn
{
	public static final int id=0;
	public static final int sccId=1;
	public static final int length=2;
	public static final int benefit=3;
	public static final int numberOfClone=4;
	
}

public class SccListLabelProvider implements ITableLabelProvider{

	
	public SccListLabelProvider()
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
		PrimarySccObject object = (PrimarySccObject) element;
	    String text = "";
	    switch (columnIndex) {
	    case SccListColumn.id:
	      text = String.valueOf(object.getId());
	      break;
	    case SccListColumn.sccId:
	      text = String.valueOf(object.getSccId());
	      break;
	    case SccListColumn.length:
	    	text = String.valueOf(object.getLength());
	      break;
	    case SccListColumn.benefit:
	      text = String.valueOf(object.getBenefit());
	      break;
	    case SccListColumn.numberOfClone:
	      text = String.valueOf(object.getNumberOfClone());
	      break;
	    }
	    return text;
	}

}
