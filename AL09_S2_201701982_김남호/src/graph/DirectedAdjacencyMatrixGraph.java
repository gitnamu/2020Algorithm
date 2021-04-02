package graph;

import list.Iterator;

public class DirectedAdjacencyMatrixGraph<E extends Edge> 
	extends AdjacencyGraph<E>
{
	// Private instance variables
	private int[][] _adjacency;
	
	// Getters & Setters: Encapsuliation of private instance variables
	protected int[][] adjacency(){
		return this._adjacency;
	}
	protected void setAdjacecny(int[][] newAdjacency) {
		this._adjacency = newAdjacency;
	}
	
	// Getting & Setting an element of Adjacency Matrix
	protected int adjacencyOfEdge(int tailVertex, int headVertex) {
		return this.adjacency()[tailVertex][headVertex];
	}
	protected void setAdjacencyOfEdgeAs(int tailVertex, int headVertex, int anAdjacencyOfEdge) {
		this.adjacency()[tailVertex][headVertex] = anAdjacencyOfEdge;
	}
	protected void setAdjacencyOfEdgeAsExist(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, AdjacencyGraph.EDGE_EXIST);
	}
	protected void setAdjacencyOfEdgeAsNone(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, AdjacencyGraph.EDGE_NONE);
	}
	
	// Constructor
	public DirectedAdjacencyMatrixGraph() {
	}
	
	// Constructor
	public DirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);
		this.setNumberOfEdges(0);
		this.setAdjacecny(new int[givenNumberOfVertices][givenNumberOfVertices]);
		for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.numberOfVertices(); headVertex++)
			this.setAdjacencyOfEdgeAsNone(tailVertex, headVertex);
			// only for the class "AdjacencyMatrixGraph"
		}
	}
	
	// Protected method
	protected boolean adjacencyOfDoesExist(int tailVertex, int headVertex) {
		return (this.adjacencyOfEdge(tailVertex, headVertex) !=
				AdjacencyGraph.EDGE_NONE);
	}
	// Public methods
	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {
			return (this.adjacencyOfDoesExist(aTailVertex, aHeadVertex));
		}
		return false;
	}
	@Override
	public boolean edgeDoesExist(E anEdge) {
		if(anEdge != null) {
			return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
		}
		return false;
	}
	@Override
	public boolean addEdge(E anEdge) {
	if(anEdge != null) {
		if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {
			int tailVertex = anEdge.tailVertex();
			int headVertex = anEdge.headVertex();
			this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);
			this.setNumberOfEdges(this.numberOfEdges()+1);
			return true;
		}
	}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public E edge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {
			// CAUTION:
			// The following type casting from "Edge" to "E" is correct only if E is just the "E".
			// But, the casting is not correct if E is a class inherited from "Edge"
			// The "nextElement" would have lost something.
			return (E) new Edge(aTailVertex, aHeadVertex);
		}
		return null;
	}
	
	@Override
	public Iterator<E> neighborIteratorOf(int aTailVertex) {
		return new IteratorForNeighborsOf(aTailVertex);
	}
	
	private class IteratorForNeighborsOf implements Iterator<E>{
		
		// Private instance variables
		private int _tailVertex;
		private int _nextHeadVertex;
		
		// Getters & Setters
		private int tailVertex() {
			return this._tailVertex;
		}
		private void setTailVertex(int newTailVertex) {
			this._tailVertex = newTailVertex;
		}
		private int nextHeadVertex() {
			return this._nextHeadVertex;
		}
		private void setNextHeadVeretx(int newNextHeadVertex) {
			this._nextHeadVertex = newNextHeadVertex;
		}
		// Constructor
		private IteratorForNeighborsOf(int givenTailVertex) {
			this.setTailVertex(givenTailVertex);
			this.setNextHeadVeretx(0);
		}
		
		// hasNext() & next()
		@Override
		public boolean hasNext() {
			while(this.nextHeadVertex() < DirectedAdjacencyMatrixGraph.this.numberOfVertices()) {
				if(DirectedAdjacencyMatrixGraph.this.adjacencyOfDoesExist(this.tailVertex(), this.nextHeadVertex())) {
					return true;
				}
				this.setNextHeadVeretx(this.nextHeadVertex() + 1);
			}
			return false;
		}
		@Override
		public E next() {
			@SuppressWarnings("unchecked")
			// CAUTION:
			// The following type casting from "Edge" to "E" is correct only if E is just the "E".
			// BUT, the casting is not correct if E is a class inherited from "Edge"
			// The "nextElement" would have lost something.
			E nextElement = (E) new Edge(this.tailVertex(), this.nextHeadVertex());
			this.setNextHeadVeretx(this.nextHeadVertex()+1);
			return nextElement;
		}
	}
}
