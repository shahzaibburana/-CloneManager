package com.plugin.clonemanager.core;

import java.util.ArrayList;

public class SccList {
	
private ArrayList<PrimarySccObject> list;
	
	public SccList()
	{
		list=new ArrayList<PrimarySccObject> ();
	}
	public SccList(ArrayList<PrimarySccObject> list)
	{
		this.list=list;
	}
	public void addToList(PrimarySccObject object)
	{
		list.add(object);
	}
	public ArrayList<PrimarySccObject> getList()
	{
		return list;
	}

}
