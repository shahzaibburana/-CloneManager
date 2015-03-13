package com.plugin.clonemanager;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class GraphSolver {
	
	public static void SolveGraph(MCCInstance mci) {
		// TODO Auto-generated method stub
		int[][] matrix = mci.getAdjeceny_Matrix_For_Overlaps();
		UndirectedGraph<Integer, DefaultEdge> graph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
		addVertices(mci, graph);
		addEdges(mci, matrix, graph);
		Collection<Set<Integer>> set = findAllCliques(graph); // THIS ALGORITHM WILL FIND ALL CLIQUES
			//System.out.println("size : " + set.size());
		findWeightedCliqueSets(mci, set);
		 // mci.getSortedCliques().add(null);
	/*	if(mci.getSCCs().size()>1)
		{
			System.out.println();
		}*/
	}

	private static void findWeightedCliqueSets(MCCInstance mci,
			Collection<Set<Integer>> set) {
		mci.getSortedCliques().clear();
		int count=0;
		Iterator<Set<Integer>> iterator1 = set.iterator();
		while(iterator1.hasNext()) // ITERATE THROUGH CLIQUES
		{  
			mci.getAllCliques().add(iterator1.next());
		}
		//	mci.setAllCliques(set);
	//		System.out.println(set.isEmpty());
		while(!set.isEmpty())
		{
//			System.out.println("lol");
			Iterator<Set<Integer>> iterator = set.iterator();
			int maxweight=0;
			int currweight;
			Set<Integer> maxweightedclique = null;
			Iterator<Integer> itr;
			while(iterator.hasNext()) // ITERATE THROUGH CLIQUES
			{  
				Set<Integer> currset=iterator.next();
				currweight=0;
				itr = currset.iterator();
				while(itr.hasNext()) // ITERATE THROUGH EACH INDIVIDUAL CLIQUE
				{
					int index=itr.next();
		//			System.out.println("haha " + mci.getSCCs().get(index).getCode().length());
					if(mci.getSCCs().get(index).getCodeSegment().length()==0)
					{
						SCCInstance scc = mci.getSCCs().get(index);
		//				int lol=0;
					}
					currweight=currweight+mci.getSCCs().get(index).getCodeSegment().length();
	     		}
		//		System.out.println(currweight+ " "  + maxweight);
				if(currweight>maxweight)
				{
					maxweight=currweight;
		//			System.out.println(maxweight);
					maxweightedclique=currset;
					//count++;
				}
			}
			count++;
			if(count==1)
			{
				mci.maxclique=maxweightedclique;
				Iterator<Integer> itr2 = maxweightedclique.iterator();
				addNewSCCInstances(mci, itr2);
			}
/*			if(mci.getSCCs().size()>1)
			{
				System.out.println("hehehe ");
			}*/
			mci.getSortedCliques().add(maxweightedclique);
			set.remove(maxweightedclique);
		}
	}

	private static void addNewSCCInstances(MCCInstance mci,
			Iterator<Integer> itr2) {
		while(itr2.hasNext()) // NOW ADD THE NEW SCC INSTNACES 
		{
			int index=itr2.next();
			mci.getNewSccs_Contained().add(mci.getSCCs().get(index));
		}
	}

	private static Collection<Set<Integer>> findAllCliques(
			UndirectedGraph<Integer, DefaultEdge> graph) {
		BronKerboschCliqueFinder check= new BronKerboschCliqueFinder(graph);
		Collection<Set<Integer>> set = check.getAllMaximalCliques();
		return set;
	}

	private static void addVertices(MCCInstance mci,
			UndirectedGraph<Integer, DefaultEdge> graph) {
		for(int i=0;i < mci.getSCCs().size();i++) // ADD ALL SCC INSTANCE CUSTOMS ( AS VERTICES )  
		{
			graph.addVertex(i);
		}
	}

	private static void addEdges(MCCInstance mci, int[][] matrix,
			UndirectedGraph<Integer, DefaultEdge> graph) {
		for(int i = 0; i < mci.getSCCs().size();i++) // ADD ALL EDGES NOW
		{
			for(int j = 0 ; j < mci.getSCCs().size();j++)
			{
				if(matrix[i][j]==1)
				{
					graph.addEdge(i, j);	
				}
			}
		}
	}
	
}
