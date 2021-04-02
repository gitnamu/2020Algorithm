package graph;

public abstract class AdjacencyGraph <E extends Edge> implements Graph<E>{
	// This is the first class for any inherited graphs.
	
	// Constants
	protected static final int EDGE_EXSIT = 1;
	protected static final int EDGE_NONE = 0;
	
	// Private instance variables
	private int _numberOfVertices;
	private int _numberOfEdges;
	
	// Public getters & setters
	@Override
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	@Override
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	
	// protected getters & setters
	protected void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	protected void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	
	// Public methods
	@Override
	public boolean vertexDoesExist(int aVertex) {
		return ((aVertex >= 0) && (aVertex < this.numberOfVertices()));
	}
	@Override
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex) {
		return (this.vertexDoesExist(aTailVertex) && 
				this.vertexDoesExist(aHeadVertex));
	}
	@Override
	public boolean edgeIsValid(E anEdge) {
		if(anEdge != null) {
			return (this.vertexDoesExist(anEdge.tailVertex()) && 
					this.vertexDoesExist(anEdge.headVertex()));
		}
		return false;
	}
	
}
