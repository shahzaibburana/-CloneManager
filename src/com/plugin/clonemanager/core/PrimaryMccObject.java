package com.plugin.clonemanager.core;

import java.util.ArrayList;

public class PrimaryMccObject {
	
	private int id;
	private int mccId;
	private ArrayList <Integer> structure;
	private int numberOfInstance;
	private double atc;
	private double apc;
	private MccCloneInstanceList cloneList;
	
	public PrimaryMccObject()
	{
		id=0;
		mccId=0;
		structure=null;
		numberOfInstance=0;
		atc=0;
		apc=0;
		cloneList=null;
	}
	public PrimaryMccObject(int id,int mccId,ArrayList<Integer> structure,int numberOfInstance,
			double atc,double apc)
	{
		this.id=id;
		this.mccId=mccId;
		this.structure=structure;
		this.numberOfInstance=numberOfInstance;
		this.apc=apc;
		this.atc=atc;
	}
	public void setCloneList(MccCloneInstanceList cloneList)
	{
		this.cloneList=cloneList;
	}
	public int getId()
	{
		return id;
	}
	public int getMccId()
	{
		return mccId;
	}
	public ArrayList<Integer> getStructure()
	{
		return structure;
	}
	public int getNumberOfInstance()
	{
		return numberOfInstance;
	}
	public double getApc()
	{
		return apc;
	}
	public double getAtc()
	{
		return atc;
	}
	public MccCloneInstanceList getCloneList()
	{
		return cloneList;
	}
	

}
