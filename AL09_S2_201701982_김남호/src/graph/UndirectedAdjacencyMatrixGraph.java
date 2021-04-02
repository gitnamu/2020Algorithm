package graph;

import list.Iterator;

public class UndirectedAdjacencyMatrixGraph<E extends Edge> 
extends DirectedAdjacencyMatrixGraph<E>{

	// Constructor
	public UndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		super(givenNumberOfVertices);
	}
		
	// "addEdge()" should be overridden since this class is for "undirected" graphs.
	@Override
	public boolean addEdge(E anEdge) {
		if(anEdge != null) {
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);	
				this.setAdjacencyOfEdgeAsExist(headVertex, tailVertex);
				this.setNumberOfEdges(this.numberOfEdges() + 1);
			}
			return true;
		}
		return false;
	}
}
