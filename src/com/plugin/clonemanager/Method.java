package com.plugin.clonemanager;

import java.io.IOException;
import java.util.ArrayList;

public class Method {
	private String _methodName;
	private int _methodId;
	private int _fileId;
	private int _startLine;
	private int _endLine;
	private int _tokenCount;
	private ArrayList<Integer> _sccList;
	private String _codeSegment;
	private String _filePath;
	
	public Method(String methodName, int methodId, int fileId, int startLine, int endLine, int tokenCount) throws IOException
	{
		_methodName =methodName;
		_methodId = methodId;
		_fileId = fileId;
		_startLine = startLine;
		_endLine = endLine;
		_tokenCount = tokenCount;
		_sccList = new ArrayList<>();
		_codeSegment = "";
		_filePath=ClonesParser.getFilePath(fileId);
	}
	public String getMethodName() {
		return _methodName;
	}
	public void setMethodName(String methodName) {
		this._methodName = methodName;
	}
	public int getMethodId() {
		return _methodId;
	}
	public void setMethodId(int methodId) {
		_methodId = methodId;
	}
	public int getFileId() {
		return _fileId;
	}
	
	public void setFileId(int fileId) {
		this._fileId = fileId;
	}
	public int getStartLine() {
		return _startLine;
	}
	public void setStartLine(int startLine) {
		this._startLine = startLine;
	}
	public int getEndLine() {
		return _endLine;
	}
	public void setEndLine(int endLine) {
		this._endLine = endLine;
	}
	public int getTokenCount() {
		return _tokenCount;
	}
	public void setTokenCount(int tokenCount) {
		this._tokenCount = tokenCount;
	}
	public ArrayList<Integer> getSCCList() {
		return _sccList;
	}
	public void setSCCList(ArrayList<Integer> sccList) {
		_sccList = sccList;
	}
	public void addToSCCList(int sccId)
	{
		this._sccList.add(sccId);
	}
	public String getCodeSegment() {
		return _codeSegment;
	}
	public void setCodeSegment(String codeSegment) {
		this._codeSegment = codeSegment;
	}
	public void displaySCCList() {
		// TODO Auto-generated method stub
		for(int i=0;i<this._sccList.size();i++)
		{
			System.out.print(this._sccList.get(i) + " ");
		}
		System.out.println();
	}
	public String getFilePath() {
		return _filePath;
	}
	public void setFilePath(String fileName) {
		this._filePath = fileName;
	}
}
