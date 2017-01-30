/* Kruskal.java */

package graphalg;

import queue.*;
import graph.*;
import set.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
	  WUGraph graph = new WUGraph();
	  Object[] vertices = g.getVertices(); 
	  LinkedQueue allEdges = new LinkedQueue();
	  HashTableChained hash = new HashTableChained(g.vertexCount());
	  int i = 0;
	  
	  //part1 and part2
	  for(Object vertex : vertices) {
		  graph.addVertex(vertex);
		  hash.insert(vertices[i], i);
		  Neighbors n = g.getNeighbors(vertex);
		  i++;
		  
		  for(int j = 0; j < n.neighborList.length; j++) {
			  Edge edge = new Edge(vertex, n.neighborList[j], n.weightList[j]);
			  allEdges.enqueue(edge);			  
		  }
	  }
	  
	  //part3
	  ListSorts.quickSort(allEdges);
	  DisjointSets set = new DisjointSets(g.vertexCount());
	  
	  //part4
	  while (!allEdges.isEmpty()) {
		  try {
			  Edge e = (Edge) allEdges.dequeue();
			  Entry v1 = hash.find(e.u());
			  Entry v2 = hash.find(e.v());
			  int root1 = set.find(((Integer) v1.value()).intValue());
			  int root2 = set.find(((Integer) v2.value()).intValue());
			  if (root1 != root2) {
				  set.union(root1, root2);
				  graph.addEdge(e.u(), e.v(), e.weight());
			  }
		  } catch (QueueEmptyException e) {
		        System.err.println(e);
	      }
	  }
	  
	  return graph;
  
  }
}

	
	    
