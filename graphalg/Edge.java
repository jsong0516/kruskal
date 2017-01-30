package graphalg;

class Edge implements Comparable {
    Object u, v;
    int weight;
    
    /**
	 * Edge(Object u, Object v, int weight) this Edge implements the interface Comparable.
	 * @param u: the Vertex that are being connected by edge
	 * @param v: the Vertex that are being connected by edge
	 * @param weight: a weight in this Edge
	 */
    public Edge(Object u, Object v, int weight){
	this.u = u;
	this.v = v;
	this.weight = weight;
    }
    
    /**
     * u() returns the v1 vertex of this edge
     * @return one vertex
     **/
    public Object u() {
    	return u;
    }
    
    /**
     * v2() returns the v2 vertex of this edge
     * @return one vertex
     **/
    public Object v() {
    	return v;
    }
    
    /**
     * weight() returns the weight of this edge
     * @return int weight of this edge
     **/
    public int weight(){
      return weight;
    }
    
   
   /**
    * CompareTo method override the interface's method
    * this method will takes an edge and compare it weight between two weight
    * @param edge an Objet edge that in graph's edge
    */
    public int compareTo(Object edge) {
    	if((this.weight) < ((Edge)edge).weight) {
    		return -1;
    	} else if(this.weight == ((Edge)edge).weight){
    		return 0;
    	} else {
    		return 1;
    	}
    }
}
