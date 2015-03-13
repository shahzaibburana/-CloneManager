package com.plugin.clonemanager;

import java.util.ArrayList;

public class SCC {
	private ArrayList<SCCInstance> _sccInstances;
	private int _sccId = 0, _tokenCount = 0, _instanceCount = 0;

	public SCC()
	{
		_sccInstances=new ArrayList<>();
	}
	
	
	public void addSCCInstance(SCCInstance sccInstance)
	{
		this._sccInstances.add(sccInstance);
	}
	
	public void setSCCinstances(ArrayList<SCCInstance> sccInstances) {
		this._sccInstances = sccInstances;
	}
	
	public void setSCCID(int sccId) {
		_sccId = sccId;
	}
	
	public void setTokenCount(int tokenCount) {
		_tokenCount = tokenCount;
	}
	
	public void setSCCInstanceCount(int instanceCount) {
		_instanceCount = instanceCount;
	}
	
	public int getSCCID() {
		return _sccId;
	}
	
	public int getTokenCount() {
		return _tokenCount;
	}
	
	public int getSCCInstanceCount() {
		return _instanceCount;
	}
	
	public ArrayList<SCCInstance> getSCCInstances() {
		return _sccInstances;
	}
}
