package com.plugin.clonemanager.core;

import java.util.ArrayList;

public class SccCloneInstanceList {
	
private ArrayList<SecondarySccObject> list;
	
	public SccCloneInstanceList()
	{
		list=new ArrayList<SecondarySccObject> ();
	}
	public SccCloneInstanceList(ArrayList<SecondarySccObject> list)
	{
		this.list=list;
	}
	public void addToList(SecondarySccObject object)
	{
		list.add(object);
	}
	public ArrayList<SecondarySccObject> getList()
	{
		return list;
	}

}
