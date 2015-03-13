package com.plugin.clonemanager.core;

public class SecondaryMccObject {
	
	private int id;
	private int fId;
	private int mId;
	private int tc;
	private double pc;
	private String methodName;
	private String fileName;
	
	public SecondaryMccObject()
	{
		id=0;
		fId=0;
		mId=0;
		tc=0;
		pc=0d;
		methodName=null;
		fileName=null;
	}
	public SecondaryMccObject(int id,int fId,int mId,int tc,double pc,String methodName,String fileName)
	{
		this.id=id;
		this.fId=fId;
		this.mId=mId;
		this.tc=tc;
		this.pc=pc;
		this.methodName=methodName;
		this.fileName=fileName;
	}
	public int getId()
	{
		return id;
	}
	public int getFId()
	{
		return fId;
	}
	public int getMId()
	{
		return mId;
	}
	public int getTc()
	{
		return tc;
	}
	public double getPc()
	{
		return pc;
	}
	public String getMethodName()
	{
		return methodName;
	}
	public String getFileName()
	{
		return fileName;
	}

}
