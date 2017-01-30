/* WUGraph.java */

package graph;

import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
        private HashTableChained vertices;
        private HashTableChained edges;
	private DList vertexList;
	
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vertices = new HashTableChained(1);
	  edges = new HashTableChained(1);
	  vertexList = new DList();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
	  return vertexList.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
	  return edges.size() / 2;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
	  int index = 0;
	  Object[] vertices = new Object[vertexList.length()];
		  if (vertexCount() > 0){
			  try {
				  DListNode vertex = (DListNode) vertexList.front();
				  while(vertex.isValidNode()) {
					  vertices[index] = ((Vertex)vertex.item()).getVertex(); 
					  vertex = (DListNode) vertex.next();
					  index++;
				  }
			  }catch(InvalidNodeException e){
				  System.out.println(e);
			  }
		  }
	  return vertices;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  if (vertex != null && !isVertex(vertex)){
		  // Creating new Vertex
		  Vertex v = new Vertex(vertex);
		
                  // Inserting in Adjancy list
		  vertexList.insertFront(v);

		  v.setNode((DListNode) vertexList.front());
		  // Adding to Hash
		  vertices.insert(vertex, v);
	  }  
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
	  if(!isVertex(vertex))
		return;

	  // Get vertex from Hash
	  Vertex v = (Vertex) vertices.find(vertex).value();
	  // Remove From hash
	  vertices.remove(vertex);
	  
	  try{
		  // Remove edges from the vertex
		  DListNode node = (DListNode) v.getEdges().front();
		  while(node.isValidNode()){
		       // Duplicated Code from remove Edge but it is a faster and keeps O(d)
		       Edge e = (Edge) node.item();
		       VertexPair key1 = new VertexPair(e.getV1().getVertex(), e.getV2().getVertex());
		       VertexPair key2 = new VertexPair(e.getV2().getVertex(), e.getV1().getVertex());

		       if (!e.getV1().getVertex().equals(e.getV2().getVertex()))
			  e.getV2().removeEdge(e.getPartner());

		       edges.remove(key1);
		       edges.remove(key2);
		       node = (DListNode) node.next();
		  }
		  
		  // Remove from list
		  v.getNode().remove();
	  }catch(InvalidNodeException e){
		  System.out.println(e);
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
	  return vertices.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  int degree = 0;
	  if (isVertex(vertex)){
		  Vertex v = (Vertex) vertices.find(vertex).value();
		  degree = v.getEdges().length();
	  }
	  return degree;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
	  if (degree(vertex) == 0)
	  	return null;		

	  Vertex v = (Vertex)vertices.find(vertex).value();
	  Neighbors nbs = new Neighbors();
          nbs.neighborList = new Object[degree(vertex)];
	  nbs.weightList = new int[degree(vertex)];
	  DListNode node = (DListNode) v.getEdges().front();
	  int i = 0;

	  try{
		  while (node.isValidNode()) {
			  nbs.neighborList[i] = ((Edge) node.item()).getV2().getVertex();
			  nbs.weightList[i] = ((Edge) node.item()).getWeight();
			  node = (DListNode) node.next();
			  i++;
		  }
	  }catch(InvalidNodeException e){
		  System.out.println(e);
	  }
	  return nbs;
	  
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
	  VertexPair key1 = new VertexPair(u, v);
	  VertexPair key2 = new VertexPair(v, u);
	  if (isVertex(u) && isVertex(v)) {
		  if (isEdge(u, v)) {
			Edge e = (Edge) edges.find(key1).value();
			e.setWeight(weight);
			e.getPartner().setWeight(weight);
			  
		  } else {
		  	Vertex v1 = (Vertex) vertices.find(u).value();
		  	Vertex v2 = (Vertex) vertices.find(v).value();
		  	Edge e1 = new Edge(v1, v2, weight);
		  	Edge e2 = new Edge(v2, v1, weight);
		  	e1.partner = e2;
		  	e2.partner = e1;

		  	v1.getEdges().insertFront(e1);
			e1.node = (DListNode) v1.getEdges().front();
		  	if (!u.equals(v)) {
		  		v2.getEdges().insertFront(e2);
		  	}
		  	e2.node = (DListNode) v2.getEdges().front();
			this.edges.insert(key1, e1);
			this.edges.insert(key2, e2);			  
		  }
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
	  if (!isEdge(u, v) || !isVertex(u) || !isVertex(v))
		  return;
		  
	  VertexPair key1 = new VertexPair(u, v);
	  VertexPair key2 = new VertexPair(v, u);
	
	  Edge e = (Edge)edges.find(key1).value();

	  Vertex v1 = (Vertex) vertices.find(u).value();
	  Vertex v2 = (Vertex) vertices.find(v).value();

	  
	  v1.removeEdge(e);
	  if (!u.equals(v)) {
	      v2.removeEdge(e.getPartner());
	  }
	  edges.remove(key1);
          edges.remove(key2);
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  if (!isVertex(u) || !isVertex(v))
		  return false;
	  return edges.find(new VertexPair(u, v)) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  VertexPair pair = new VertexPair(u, v);
	  int weight = (isEdge(u, v)) ? ((Edge) edges.find(pair).value()).getWeight() : 0; 
	  return weight;
  }

}
