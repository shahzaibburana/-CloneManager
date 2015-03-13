package com.plugin.clonemanager;

import java.util.ArrayList;

public class MCC {
	private int _instanceCount;
	private int _mccId;
	private ArrayList<Integer> _scs;
	private ArrayList<MCCInstance> _mccInstances;

	public MCC(int mccId, int instanceCount,
			ArrayList<Integer> sCCIDs_int) {
		// TODO Auto-generated constructor stub
		_instanceCount = instanceCount;
		_mccId = mccId;
		_scs = sCCIDs_int;
		_mccInstances = new ArrayList<>();
	}

	public int getMCCInstanceCount() {
		return _instanceCount;
	}

	public void setMCCInstanceCount(int count) {
		this._instanceCount = count;
	}

	public int getMCCID() {
		return _mccId;
	}

	public void setMCCID(int mccId) {
		
		_mccId = mccId;
	}

	public ArrayList<Integer> getSCS() {
		return _scs;
	}

	public void setSCS(ArrayList<Integer> scs) {
		_scs = scs;
	}

	public ArrayList<MCCInstance> getMCCInstances() {
		return _mccInstances;
	}

	public void setMCCInstances(ArrayList<MCCInstance> mccInstances) {
		_mccInstances = mccInstances;
	}

	public void addMCCInstance(MCCInstance instance) {
		this._mccInstances.add(instance);
	}

	public void addToSCS(int sccId) {
		this._scs.add(sccId);
	}
}
