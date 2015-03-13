package com.plugin.clonemanager.core;

public class SecondarySccObject {
	
	private int id;
	private int fId;
	private int startLine;
	private int endLine;
	private String fileName;
	private int startColumn;
	private int endColumn;
	
	public SecondarySccObject()
	{
		id=0;
		fId=0;
		startLine=0;
		endLine=0;
		fileName=null;
		
	}
	public SecondarySccObject(int id,int mId,String methodName,int startLine,int endLine, int startColumn, int endColumn)
	{
		this.id=id;
		this.fId=mId;
		this.startLine=startLine;
		this.endLine=endLine;
		this.fileName=methodName;
		this.startColumn =startColumn;
		this.endColumn =endColumn;
	}
	public String getFileName()
	{
		return  fileName;
	}
	public int getFId()
	{
		return  fId;
	}
	public int getStartLine()
	{
		return  startLine;
	}
	public int getEndLine()
	{
		return  endLine;
	}
	public int getId()
	{
		return  id;
	}
	private int getStartColoumn() {
		return startColumn;
	}
	private void setStartColoumn(int startColoumn) {
		this.startColumn = startColoumn;
	}
	private int getEndColoumn() {
		return endColumn;
	}
	private void setEndColoumn(int endColoumn) {
		this.endColumn = endColoumn;
	}

}
