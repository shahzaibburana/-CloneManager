package com.plugin.clonemanager.contentprovider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.plugin.clonemanager.core.PrimaryMccObject;



public class MccListContentProvider implements IStructuredContentProvider {

	private ArrayList <PrimaryMccObject> list;
	public MccListContentProvider()
	{
		list=new ArrayList<PrimaryMccObject>();
	}
	public MccListContentProvider(ArrayList<PrimaryMccObject> list)
	{
		this.list=list;
	}
	public void setList(ArrayList<PrimaryMccObject> list)
	{
		this.list=list;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(list!=null)
			return list.toArray();
		else
			return new Object[]{};
	}

}
