package com.plugin.clonemanager.labelprovider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugin.clonemanager.core.SecondaryMccObject;
import com.plugin.clonemanager.core.SecondarySccObject;



class SccCloneInstanceListColumn
{
	public static final int id=0;
	public static final int fId=1;
	public static final int startLine=2;
	public static final int endLine=3;
	public static final int fileName=4;
}

public class SccCloneInstanceListLabelProvider implements ITableLabelProvider {

	public SccCloneInstanceListLabelProvider()
	{
		
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
		SecondarySccObject object = (SecondarySccObject) element;
	    String text = "";
	    switch (columnIndex) {
	    case SccCloneInstanceListColumn.id:
	      text = String.valueOf(object.getId());
	      break;
	    case SccCloneInstanceListColumn.fId:
	      text = String.valueOf(object.getFId());
	      break;
	    case SccCloneInstanceListColumn.startLine:
	    	text = String.valueOf(object.getStartLine());
	      break;
	    case SccCloneInstanceListColumn.endLine:
	      text = String.valueOf(object.getEndLine());
	      break;
	    case SccCloneInstanceListColumn.fileName:
	      text = (object.getFileName());
	      break;
	    }
	    return text;
	}

}
