package com.plugin.clonemanager.contentprovider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.plugin.clonemanager.core.SecondaryMccObject;



public class MccCloneInstanceListContentProvider implements IStructuredContentProvider {

	private ArrayList<SecondaryMccObject> list;
	
	public MccCloneInstanceListContentProvider()
	{
		list=new ArrayList<SecondaryMccObject> ();
	}
	public MccCloneInstanceListContentProvider(ArrayList<SecondaryMccObject> list)
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
		// TODO Auto-generated method stub
		return list.toArray();
	}

}
