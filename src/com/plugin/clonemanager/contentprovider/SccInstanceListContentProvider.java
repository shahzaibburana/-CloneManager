package com.plugin.clonemanager.contentprovider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.plugin.clonemanager.core.SecondarySccObject;


public class SccInstanceListContentProvider implements IStructuredContentProvider {

	private ArrayList<SecondarySccObject> list;
	
	public SccInstanceListContentProvider()
	{
		list=new ArrayList<SecondarySccObject> ();
	}
	public SccInstanceListContentProvider(ArrayList<SecondarySccObject> list)
	{
		this.list=list;
	}
	
	public void setList(ArrayList<SecondarySccObject> list)
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