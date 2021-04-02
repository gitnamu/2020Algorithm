package graph;

public class UndirectedAdjacencyListGraph<E extends Edge> 
extends DirectedAdjacencyListGraph<E>{
	
	// Constructor
	public UndirectedAdjacencyListGraph(int givenNumberOfVertices) {
		super(givenNumberOfVertices);
	}
	
	// "addEdge()" should be overridden since this class is for "undirected" graphs.
	@Override
	public boolean addEdge (E anEdge) {
		if(this.edgeIsValid(anEdge) && (! this.edgeDoesExist(anEdge))) {
			this.neighborListOf(anEdge.tailVertex()).add(anEdge);
			// The following lines should be required for the "undirected graphs".
			@SuppressWarnings("unchecked")
			E reversedEdge = (E) anEdge.reversed();
			this.neighborListOf(reversedEdge.tailVertex()).add(reversedEdge);
			this.setNumberOfEdges(this.numberOfEdges()+1);
			return true;
		}
		return false;
	}
}
