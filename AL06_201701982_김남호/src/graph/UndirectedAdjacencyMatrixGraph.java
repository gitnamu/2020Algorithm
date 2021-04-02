package graph;

public class UndirectedAdjacencyMatrixGraph<E extends Edge> implements Graph<E> {
	
	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices;
	private int _numberOfEdges;
	private int[][] _adjacency;
	
	@Override
	//_numberOfVertices의 getter
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	//_numberOfVertices의 setter
	protected void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	
	@Override
	//_numberOfEdges의 getter
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	//_numberOfEdges의 setter
	protected void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	//_adjacency의 getter
	protected int[][] adjacency(){
		return this._adjacency;
	}
	//_adjacency의 setter
	protected void setAdjacency(int[][] newAdjacency ) {
		this._adjacency = newAdjacency;
	}
	//_adjacency 내 원소의 getter
	protected int adjacencyOfEdge(int tailVertex, int headVertex) {
		return this.adjacency()[tailVertex][headVertex];
	}
	//_adjacency 내 원소의 setter
	protected void setAdjacencyOfEdgeAs(int tailVertex, int headVertex, int adAdjacencyOfEdge) {
		this.adjacency()[tailVertex][headVertex] = adAdjacencyOfEdge;
	}
	//_adjacency에 저장된 edge를 1로 변경한다
	private void setAdjacencyOfEdgeAsExist(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_EXIST);
	}
	//_adjacency에 저장된 edge를 0으로 변경한다
	protected void setAdjacencOfEdgeAsNone(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_NONE);
	}
	//기본 생성자
	protected UndirectedAdjacencyMatrixGraph() {
	}
	//생성자로서 _numberOfVertices를 넘겨받은 인자로, _numberOfEdges를 0으로, 배열의 모든 원소를 0으로 초기화한다
	public UndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);
		this.setNumberOfEdges(0);
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
		for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.numberOfVertices(); headVertex++) {
				this.setAdjacencOfEdgeAsNone(tailVertex, headVertex);
			}
		}
	}
	//넘겨받은 인자를 양 끝 vertex로 하는 edge가 존재하는지 확인한다
	protected boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacencyOfEdge(tailVertex, headVertex)!=
				UndirectedAdjacencyMatrixGraph.EDGE_NONE);
	}
	
	@Override
	//aVertex가 0이상 _numberOfVertices미만이면 참을 반환한다
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());
	}

	@Override
	//edge의 양 끝 vertex가 유효한지 확인한다
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex) {
		return (this.vertexDoesExist(aTailVertex) &&
				this.vertexDoesExist(aHeadVertex));
	}
	
	@Override
	//anEdge가 null이 아니라면 양 끝 vertex의 유효성을 검사하여 반환한다 
	public boolean edgeIsValid(E anEdge) {
		if(anEdge != null) {
			return (this.edgeIsValid(anEdge.tailVertex(), anEdge.headVertex()));
		}
		return false;
	}
	@Override
	//넘겨받은 인자를 양 끝 vertex로 하는 edge가 존재하는지 배열 _adjacency에서 찾아 확인한다 
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {
			return (this.adjacencyOfEdgeDoesExist(aTailVertex, aHeadVertex));
		}
		return false;
	}

	@Override
	//넘겨받은 인자가 null이 아니라면 양 끝 vertex를 추출하여 존재하는 edge인지 확인한다
	public boolean edgeDoesExist(E anEdge) {
		if(anEdge != null) {
			return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
		}
		return false;
	}

	@Override
	//anEdge가 null이 아니며 edge와 해당 edge의 양 끝 vertex 모두 유효하다면, 배열에 edge를 추가하고 _numberOfEdge를 +1한다
	public boolean addEdge(E anEdge) {
		if(anEdge != null) {
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);	
				this.setAdjacencyOfEdgeAsExist(headVertex, tailVertex);	//무방향 그래프이기 때문에 두 방향 모두 추가한다
				this.setNumberOfEdges(this.numberOfEdges() + 1);
			}
					return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	//주어진 vertex에 해당하는 edge가 이미 존재하면 null을 반환, 존재하지 않는다면 edge 객체를 생성하여 반환한다
	public E edge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {
			return (E) new Edge(aTailVertex, aHeadVertex);
		}
		return null;
	}
}
