package graph;

import list.LinkedList;
import list.Iterator;

public class DirectedAdjacencyListGraph<E extends Edge> extends AdjacencyGraph<E>{
	
	private LinkedList<E>[] _adjacency;
	
	// Getters & Setters
	protected LinkedList<E>[] adjacency(){
		return this._adjacency;
	}
	protected void setAdjacency(LinkedList<E>[] newAdjacency) {
		this._adjacency = newAdjacency;
	}
	
	// protected method
	protected LinkedList<E> neighborListOf(int aTailVertex){
		return this.adjacency()[aTailVertex];
	}
	
	protected int adjacencyOfEdge(int aTailVertex, int aHeadVertex) {
		if(this.vertexDoesExist(aTailVertex) && this.vertexDoesExist(aHeadVertex)) {
			Iterator<E> iterator = this.neighborIteratorOf(aTailVertex);
			while(iterator.hasNext()) {
				E neighborEdge = iterator.next();
				if(aHeadVertex == neighborEdge.headVertex()) {
					return AdjacencyGraph.EDGE_EXSIT;
				}
			}
		}
		return AdjacencyGraph.EDGE_NONE;
	}
	
	@SuppressWarnings("unchecked")
	public DirectedAdjacencyListGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);
		this.setAdjacency(new LinkedList[givenNumberOfVertices]);
		for(int tailVertex = 0;tailVertex < this.numberOfVertices();tailVertex++) {
			this.adjacency()[tailVertex] = new LinkedList<E>();
		}
		this.setNumberOfEdges(0);
	}
	
	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		return (this.adjacencyOfEdge(aTailVertex, aHeadVertex) != AdjacencyGraph.EDGE_NONE);
	}

	@Override
	public boolean edgeDoesExist(E anEdge) {
		if(anEdge != null) {
			return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
		}
		return false;
	}

	@Override
	public E edge(int aTailVertex, int aHeadVertex) {
		if(this.vertexDoesExist(aTailVertex)) {
			Iterator<E> iterator = this.neighborIteratorOf(aTailVertex);
			while(iterator.hasNext()) {
				E neighborEdge = iterator.next();
				if(aHeadVertex == neighborEdge.headVertex()) {
					return neighborEdge;
				}
			}
		}
		return null;
	}

	@Override
	public boolean addEdge(E anEdge) {
		if(this.edgeIsValid(anEdge) && (!this.edgeDoesExist(anEdge))) {
			// The following "add()" id  called only for directed graphs!
			this.neighborListOf(anEdge.tailVertex()).add(anEdge);
			this.setNumberOfEdges(this.numberOfEdges() + 1);
			return true;
		}
		return false;
	}

	@Override
	public Iterator<E> neighborIteratorOf(int aTailVertex) {
		if(this.vertexDoesExist(aTailVertex)) {
			return (Iterator<E>) this.adjacency()[aTailVertex].listIterator();
		}
		return null;
	}

}
