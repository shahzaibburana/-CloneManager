package com.plugin.clonemanager.core;

public class PrimarySccObject {
	
	private int id;
	private int sccId;
	private int length;
	private int benefit;
	private int numberOfClone;
	private SccCloneInstanceList cloneList;
	
	public PrimarySccObject()
	{
		id=0;
		sccId=0;
		length=0;
		benefit=0;
		numberOfClone=0;
		cloneList=null;
	}
	public PrimarySccObject(int id,int sccId,int length,int benefit,int numberOfClone)
	{
		this.id=id;
		this.sccId=sccId;
		this.length=length;
		this.benefit=benefit;
		this.numberOfClone=numberOfClone;
		this.cloneList=cloneList;
	}
	public void setSccCloneList(SccCloneInstanceList cloneList)
	{
		this.cloneList=cloneList;
	}
	public int getId()
	{
		return id;
	}
	public int getSccId()
	{
		return sccId;
	}
	public int getLength()
	{
		return length;
	}
	public int getBenefit()
	{
		return benefit;
	}
	public int getNumberOfClone()
	{
		return numberOfClone;
	}
	public SccCloneInstanceList getCloneList()
	{
		return cloneList;
	}
	
	

}
