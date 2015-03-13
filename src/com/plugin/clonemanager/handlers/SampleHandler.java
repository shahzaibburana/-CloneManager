package com.plugin.clonemanager.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.plugin.clonemanager.Processor;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public static Processor f;
	public SampleHandler() 
	{
		//runParser();
	}
	
	public static void runParser()
	{
		f = new Processor();
		try {
			f.run();
			
			// I need to see MethodClone //
			//f.MCCCLONES.get(31).getMethod_Clones().get(0).DisplayAnalysisDetails();
			//for(int i = 0; i < f.SCCCLONES.size()-1; i++)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return null;
	}
}
