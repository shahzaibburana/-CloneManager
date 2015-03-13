package com.plugin.clonemanager.core;

import java.util.ArrayList;

public class MccList {
	
	private ArrayList<PrimaryMccObject> list;
	
	public MccList()
	{
		list=new ArrayList<PrimaryMccObject> ();
		/*ArrayList<Integer> temp=new ArrayList<Integer>();
		temp.add(5);temp.add(6);temp.add(7);temp.add(8);
		addToList(new PrimaryMccObject(1,1,temp,5,91,89));
		addToList(new PrimaryMccObject(2,4,temp,8,61,37));
		addToList(new PrimaryMccObject(3,6,temp,12,47,75));*/
	}
	public MccList(ArrayList<PrimaryMccObject> list)
	{
		this.list=list;
	}
	public void addToList(PrimaryMccObject object)
	{
		list.add(object);
	}
	public ArrayList<PrimaryMccObject> getList()
	{
		return list;
	}

}
