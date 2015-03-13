package com.plugin.clonemanager;

import java.io.IOException;
import java.util.ArrayList;

public class SCCInstance implements Comparable<SCCInstance>{

	private int _sccId=0,
			_fileNumber = 0, _startLine = 0, _startCol = 0, _endLine = 0,
			_endCol = 0, _dirId = 0;
	private String _filePath;
	private String _codeSegment;
	private ArrayList<String> _tokens;

	public SCCInstance()
	{
		_sccId=0;
		_tokens=new ArrayList<>();
		_fileNumber = 0;
		_startLine = 0;
		_startCol = 0;
		_endLine = 0;
		_endCol = 0;
		_dirId = 0;
	}
	public SCCInstance(int sccId, int fileNumber, int startLine,
			int startCol, int endLine, int endCol, String fileName,
			int dirId) {
		_sccId = sccId;
		_fileNumber = fileNumber;
		_startLine = startLine;
		_startCol = startCol;
		_endLine = endLine;
		_endCol = endCol;
		_filePath = fileName;
		_dirId = dirId;
		_tokens = new ArrayList<>();
	}
	
	public void setFileNumber(int fileNumber) {
		_fileNumber = fileNumber;
	}
	
	public void setStartLine(int startLine) {
		_startLine = startLine;
	}
	
	public void setSCCID(int sccId) {
		_sccId = sccId;
	}

	public void setStartCol(int startCol) {
		_startCol = startCol;
	}
	
	public void setEndLine(int endLine) {
		_endLine = endLine;
	}
	
	public void setEndCol(int endCol) {
		_endCol = endCol;
	}
	
	public void setFilePath(String filePath) {
		_filePath = filePath;
	}
	
	public void setCodeSegment(String codeSegment) {
		_codeSegment = codeSegment;
	}
	
	public void setDirId(int dirId)
	{
		_dirId=dirId;
	}
	
	public void setTokens(ArrayList<String> tokenList) {
		_tokens = tokenList;
	}
	
	public int getSCCID() {
		return _sccId;
	}

	public int getFileID() {
		return _fileNumber;
	}

	public int getStartLine() {
		return _startLine;
	}

	public int getStartCol() {
		return _startCol;
	}

	public int getEndLine() {
		return _endLine;
	}

	public int getEndCol() {
		return _endCol;
	}

	public int getDirId() {
		return _dirId;
	}

	public  String getFilePath() {
		return _filePath;
	}

	public String getCodeSegment() {
		return _codeSegment;
	}

	public ArrayList<String> getTokens() {
		return _tokens;
	}
	
	public void addToken(String token)
	{
		this._tokens.add(token);
	}
	
	@Override
	public int compareTo(SCCInstance sccInstance) {
		int startLine=((SCCInstance)sccInstance).getStartLine();
		return this._startLine-startLine;
	}
	
	public void updateCode(String path) throws IOException {
		_codeSegment=ClonesParser.getCodeSegment(path, _startLine, _startCol, _endLine, _endCol, null);
	}
	public boolean checkForGapWith(SCCInstance nextInstance) {
		if (getEndLine() < nextInstance.getStartLine()) {
			return true;
		} else if (getEndLine() == nextInstance.getStartLine()) {
			if (getEndCol() <= nextInstance.getStartCol()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	public void compareSCCInstances(int[][] matrix,
			SCCInstance nextInstance, int index1, int index2) {
		if (index1 == index2) {
			matrix[index1][index2] = 0;
			return;
		} else if (checkForGapWith(nextInstance)) {
			matrix[index1][index2] = 1;
			matrix[index2][index1] = 1;
			return;
		} else {
			matrix[index1][index2] = 0;
			matrix[index2][index1] = 0;
			return;
		}
	}

	/*public static ArrayList<ArrayList<String>> getClone_List() {
		return Clone_List;
	}*/
}
