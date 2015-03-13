package com.plugin.clonemanager;

import java.util.ArrayList;

public class MatchingContainer {
	
	public ArrayList<ArrayList<String>> getTokensOneSCC(ArrayList<SCCInstance> temp, int SCCID)
	{
		ArrayList<ArrayList<String>> tokens = new ArrayList<>();
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getSCCID()==SCCID)
			{
				tokens.add(temp.get(i).getTokens());
			}
		}
		return tokens;
	}
	
}
