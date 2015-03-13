package com.plugin.clonemanager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MCIContainer {
	public ArrayList<SCCTemplate> SCCTemplates;
	public ArrayList<SCCInstance> SCCInstanceList;
	public ArrayList<String> SCC_Order;		// Holds [SCCID,TOKENINDEX]
	public String FRAME_PATH;
	
	
	public MCIContainer(ArrayList<SCCTemplate> TList)
	{
		SCC_Order = new ArrayList();
		SCCTemplates = TList;
	}
	
	public void mapIndexes(String SCInst_CodeSegment)
	{	
		// Get Relevant SCCTemplate //
		String[] array = SCInst_CodeSegment.split(",");
		System.out.println("The required SCCID: " + array[0]);
		int SCCID = Integer.parseInt(array[0]);
		boolean matchFound = false;
		SCCTemplate Template = getTemplate(SCCID);
		
		// Analyze Code Segment To Determine SCC_Instance_Index //
		String CodeSegment = SCInst_CodeSegment.substring(array[0].length()+1);
		System.out.println("The obtained Code Segment: ");
		System.out.print(CodeSegment);
		int numValues = 0;
		if(Template.DynamicTokens.size()>0)
			numValues = Template.DynamicTokens.get(0).getValues().size();
		
		for(int tokenIndex = 0; tokenIndex < numValues; tokenIndex++) // 
		{
			for(int i = 0; i < Template.DynamicTokens.size(); i++)
			{
				DynamicToken Token = Template.DynamicTokens.get(i);
				String TokenValue = Token.tokenValues.get(tokenIndex);
				System.out.println("#######");
				System.out.println("TokenValue: " + TokenValue);
				System.out.println("Token Index: " + tokenIndex);
				System.out.println("#######");
				
				if(CodeSegment.indexOf(TokenValue) != -1)
				{
					System.out.println("Match Found!"); // I should move to next token 
					if(i == Template.DynamicTokens.size()-1)	// If Last!
						matchFound = true;
				}
				else	// Didn't found a match for this tokenIndex, I should continue with the next tokenIndex
				{
					if(i == Template.DynamicTokens.size()-1)
					{
						String subs = RemoveLine(CodeSegment.substring(CodeSegment.length()-2)); 
						if(CodeSegment.substring(CodeSegment.length()-2).equals(TokenValue.substring(0, 1)))
						{
							matchFound = true;
						}
						else
							break;
					}
					else
						break;
				}
			}
			if(matchFound)
			{
				System.out.println("Token Index For This SCC_Instance: " + tokenIndex);
				SCC_Order.add(SCCID + "," + tokenIndex);
				break;
			}
		}		
	}
	
	public static String RemoveLine(String inputText) {
		   Pattern p;
		   Matcher m;
		   System.out.println(" Input Text : " + inputText);
		   p = Pattern.compile("\n");
		   m = p.matcher(inputText);
		   String str = m.replaceAll("");
		   System.out.println(" OUtput Text: " + str);
		   return str;
	}
	
	public SCCTemplate getTemplate(int SCCID)
	{
		for(SCCTemplate Template : SCCTemplates)
		{
			if(Template.curSCCID == SCCID)
			{
				return Template;
			}
		}
		return null;
	}
	
}
