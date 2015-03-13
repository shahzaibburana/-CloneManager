package com.plugin.clonemanager.core;

import java.util.ArrayList;

public class MccCloneInstanceList {
	
	private ArrayList<SecondaryMccObject> list;
	
	public MccCloneInstanceList()
	{
		list=new ArrayList<SecondaryMccObject> ();
	}
	public MccCloneInstanceList(ArrayList<SecondaryMccObject> list)
	{
		this.list=list;
	}
	public void addToList(SecondaryMccObject object)
	{
		list.add(object);
	}
	public ArrayList<SecondaryMccObject> getList()
	{
		return list;
	}

}
