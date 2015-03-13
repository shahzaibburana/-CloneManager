package com.plugin.clonemanager.labelprovider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.plugin.clonemanager.core.SecondaryMccObject;


	class MccCloneInstanceListColumn
	{
		public static final int id=0;
		public static final int fId=1;
		public static final int mId=2;
		public static final int tc=3;
		public static final int pc=4;
		public static final int methodName=5;
		public static final int fileName=6;
	}
public class MccCloneInstanceListLabelProvider implements ITableLabelProvider {

	public MccCloneInstanceListLabelProvider()
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
		SecondaryMccObject object = (SecondaryMccObject) element;
	    String text = "";
	    switch (columnIndex) {
	    case MccCloneInstanceListColumn.id:
	      text = String.valueOf(object.getId());
	      break;
	    case MccCloneInstanceListColumn.fId:
	      text = String.valueOf(object.getFId());
	      break;
	    case MccCloneInstanceListColumn.mId:
	    	text = String.valueOf(object.getMId());
	      break;
	    case MccCloneInstanceListColumn.pc:
	      text = String.valueOf(object.getPc());
	      break;
	    case MccCloneInstanceListColumn.tc:
	      text = String.valueOf(object.getTc());
	      break;
	    case MccCloneInstanceListColumn.methodName:
	    	text=(object.getMethodName());
	    	break;
	    case MccCloneInstanceListColumn.fileName:
	    	text=(object.getFileName());
	    	break;
	    }
	    return text;
	}

}
