/* Vertex.java */

package graph;

import list.*;

/**
 * A Vertex class to represent the vertex in WUGraph, it has field of
 * edgeList : store all the edge that are from this vertex
 * node : a node of this vertex to be store in the vertexList in the WUGraph
 * vertex: a object that takes the object vertex in the WUGraph
 */
class Vertex{
	
        private DListNode node;
	private DList edgeList;
	private Object vertex;
	
	/**
	 * Vertex(object vertex) constructs a Vertex.
	 * the object vertex will be given by the graph
	 * @param vertex an vertex in the WUGraph
	 */
	public Vertex(Object vertex) {
		this.vertex = vertex;
		edgeList = new DList();
		node = null;
	}
	
	/**
	 * insertEdge(Edge e) will insert an edge to a Vertex edgelist
	 * @param Edge e an edge in the WUGraph associated with this vertex
	 */
	public void insertEdge(Edge e) {
		edgeList.insertBack(e);
	}
	
	/**
	 * removeEdge(Edge e) will remove an edge to a Vertex edgelist
	 * @param Edge e an edge in the WUGraph associated with this vertex
	 */
	public void removeEdge(Edge e) {
		try {
		   (e.getNode()).remove();
		}catch(InvalidNodeException f) {
			System.out.println(f);
		}
	}
	

	/**
	 * getEdges() will return the DList edgeList
	 * edgeList is all the edges associate with this Vertex
	 */
	public DList getEdges() {
		return edgeList;
	}
	
	/**
	 * getNode() will return the node that store this Vertex
	 */
	public DListNode getNode() {
		return node;
	}

	public void setNode(DListNode node) {
		if(node instanceof DListNode) 
			this.node = node;
	}
	
	/**
	 * getVertex() will return object vertex that we store from the graph
	 */
	public Object getVertex() {
		return vertex;
	}
}
