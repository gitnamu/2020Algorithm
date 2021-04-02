package AL03_201701982_김남호;

public class AdjacencyMatrixGraph {
	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices ;
	private int _numberOfEdges ;
	private int[][] _adjacency ;
	
	//생성자
	public AdjacencyMatrixGraph(int givenNumberOfVertices) {
		//vertex 수를 givenNumberOfVertices로 초기화
		this.setNumberOfVertices(givenNumberOfVertices);
		//edge의 수를 0으로 초기화
		this.setNumberOfEdges(0);
		//vertex의 수를 크기로 하는 이차원 배열 _adjacency 생성 
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
		//배열의 원소들을 전부 0으로 초기화
		for(int tailVertex = 0;tailVertex<this.numberOfVertices();tailVertex++) {
			for(int headVertex = 0;headVertex<this.numberOfVertices();headVertex++) {
				this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_NONE;
			}
		}
	}
	//_numberOfVertices의 getter
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	//_numberOfEdges의 getter
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	//aVertex가 유효한 vertex인지 확인
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());
	}
	//anEdge가 존재하는 edge인지 확인
	public boolean edgeDoesExist(Edge anEdge) {
		if(anEdge != null) {
			int tailVertex = anEdge.tailVertex();
			int headVertex = anEdge.headVertex();
			//adjacencyOfEdgeDoesExist()를 이용하여 tail vertex와 head vertex에 연결된 edge가 있는지 확인
			if(this.vertexDoesExist(tailVertex)&& this.vertexDoesExist(headVertex)) {
				return(this.adjacencyOfEdgeDoesExist(tailVertex,headVertex));
			}
		}
		return false;
	}
	//edge를 추가하는 메소드
	//이미 존재하는 edge가 아니라면 배열 _adjacency의 해당 vertex쌍 위치에 1 저장
	public boolean addEdge(Edge anEdge) {
		if(anEdge != null) {
			int tailVertex = anEdge.tailVertex();
			int headVertex = anEdge.headVertex();
			if(this.vertexDoesExist(tailVertex)&& this.vertexDoesExist(headVertex)) {
				if(!this.adjacencyOfEdgeDoesExist(tailVertex, headVertex)) {
					//무방향 그래프이므로 [tailVertex][headVertex]와 [headVertex][tailVertex] 둘 다 1 저장 
					this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_EXIST;
					this.adjacency()[headVertex][tailVertex] = AdjacencyMatrixGraph.EDGE_EXIST;
					this.setNumberOfEdges(this.numberOfEdges()+1);
					return true;
				}
			}
		}
		return false;
	}
	//_numberOfVertices의 setter
	private void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	//_numberOfEdges의 setter 
	private void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	//배열 _adjacency의 getter
	private int[][] adjacency(){
		return this._adjacency;
	}
	//배열 _adjacency의 setter
	private void setAdjacency(int[][] newAdjacency) {
		this._adjacency = newAdjacency;
	}
	//배열 _adjacency안에 해당 vertex들을 tail vertex, head vertex로 하는 edge가 존재하는지 확인
	private boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacency()[tailVertex][headVertex] != AdjacencyMatrixGraph.EDGE_NONE);
	}

}
