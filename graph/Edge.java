/* Edge.java */

package graph;

import list.*;


/**
 * A Edge class to represent the Edge in WUGraph,
 * a partner file is  a double linked list ADT
 * the Edge class has the field of 
 * partner : to represent to partner of this edge, partner has to opposite direction
 * u: the Vertex that are being connected by edge
 * v: the Vertex that are being connected by edge
 * node : a node of this vertex to be store in the vertexList in the WUGraph
 * weight: a weight to represent the weight
 */
public class Edge {
	protected Edge partner;
	protected DListNode node;
	private Vertex u;
	private Vertex v;
	private int weight;
	
	/**
	 * Edge(Vertex u, Vertex v, int weight) constructs a Edge.
	 * @param u: the Vertex that are being connected by edge
	 * @param v: the Vertex that are being connected by edge
	 * @param weight: a weight in this Edge
	 */
	public Edge(Vertex u, Vertex v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	/**
	 * getV1() return the Vertex u.
	 */
	public Vertex getV1(){
		return u;
	}
	
	/**
	 * getV2() return the Vertex v.
	 */
	public Vertex getV2(){
		return v;
	}
	
	/**
	 * getNode() return this node that store the Edge in .
	 */
	public DListNode getNode(){
		return node;
	}
	
	/**
	 * getWeight() return the weight of the Edge .
	 */
	public int getWeight(){
		return weight;
	}
	
	/**
	 * setWeight() set the weight of the Edge .
	 */
	public void setWeight(int w){
		weight = w;
	}
	
	/**
	 * getPartner() return the partner of this edge
	 */
	public Edge getPartner(){
		return partner;
	}
	

	/**
	 * setNode(DListNode n) return the partner of this edge
	 * @param n : set this Node to be n
	 */
	public void setNode(DListNode n){
		node = n;
	}
	
	/**
	 * removePartner() remove the partner of this edge
	 */
	public void RemovePartner(){
		partner = null;
	}
}
