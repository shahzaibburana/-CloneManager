package com.plugin.clonemanager.contentprovider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.plugin.clonemanager.core.SecondaryMccObject;



public class MccInstanceListContentProvider implements IStructuredContentProvider {

	private ArrayList<SecondaryMccObject> list;
	
	public MccInstanceListContentProvider()
	{
		list=new ArrayList<SecondaryMccObject> ();
	}
	public MccInstanceListContentProvider(ArrayList<SecondaryMccObject> list)
	{
		this.list=list;
	}
	
	public void setList(ArrayList<SecondaryMccObject> list)
	{
		this.list=list;
	}
	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) 
	{
		if(list!=null)
			return list.toArray();
		else
			return new Object[]{};
	}

}
