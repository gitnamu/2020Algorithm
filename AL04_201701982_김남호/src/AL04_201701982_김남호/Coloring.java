package AL04_201701982_김남호;

public class Coloring {
	private AdjacencyMatrixGraph _graph;
	private VertexColor[] _vertexColors;
	private int _startingVertex;
	private LinkedList<Edge> _sameColorEdges;
	
	//_graph의 getter
	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	//_graph의 setter
	private void setGraph (AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}
	//_startingVertex의 getter
	private int startingVertex() {
		return this._startingVertex;
	}
	//_startingVertex의 setter
	private void setStartingVertex(int newVertex) {
		this._startingVertex = newVertex;
	}
	//_vertexColors의 getter
	private VertexColor[] vertexColors() {
		return this._vertexColors;
	}
	//_vertexColors의 setter
	private void setVertexColors(VertexColor[] newVertexColors) {
		this._vertexColors = newVertexColors;
	}
	
	// _vertexColor의 aVertex번째 원소를 반환하는 getter
	public VertexColor vertexColor(int aVertex) {
		return this.vertexColors()[aVertex];
	}
	// _vertexColor의 aVertex번째 원소를 newColor로 설정하는 setter
	private void setVertexColor(int aVertex, VertexColor newColor) {
		this.vertexColors()[aVertex] = newColor;
	}
	//_sameColorEdges의 getter
	public LinkedList<Edge> sameColorEdges(){
		return this._sameColorEdges;
	}
	//_sameColorEdges의 setter 
	private void setSameColorEdges(LinkedList<Edge> newLinkedList) {
		this._sameColorEdges = newLinkedList;
	}
	
	//
	public Coloring(AdjacencyMatrixGraph givenGraph) {
		this.setGraph(givenGraph);	// 주어진 그래프 저장
		this.setVertexColors(new VertexColor[this.graph().numberOfVertices()]);	// vertex의 color를 저장할 배열을 생성
		for(int vertex = 0; vertex < this.graph().numberOfVertices();vertex++) {
			this.setVertexColor(vertex, VertexColor.NONE);
		}//배열을 NONE으로 초기화한다
		this.setSameColorEdges(new LinkedList<Edge>());	//양 끝 색이 같은 edge들을 저장할 연결리스트 생성
		this.setStartingVertex(0);	//색칠을 시작할 vertex를 0으로 지정
	}
	//paintColorOfVertices()와 findSameColorEdges() 실행
	public void runColoring() {
		this.paintColorOfVertices();
		this.findSameColorEdges();
	}
	//bfs를 이용해 모든 vertex에 색칠하기 실행
	public void paintColorOfVertices() {
		this.setVertexColor(this.startingVertex(), VertexColor.RED);	//첫번쩨 vertex의 색을 RED로 지정
		
		CircularQueue<Integer> breadthFirstSearchQueue = 
				new CircularQueue<Integer>(this.graph().numberOfVertices());	//bfs를 위해 원형 큐 초기화
		breadthFirstSearchQueue.add(this.startingVertex());	//원형 큐에 첫번째 vertex 추가
		
		//원형 큐가 빌때까지 실행
		while(! breadthFirstSearchQueue.isEmpty()) {
			int tailVertex = breadthFirstSearchQueue.remove();	//큐의 원소 하나 삭제 후 tailVertex에 저장
			//삭제한 이전의 원소가 RED였으면 BLUE, 아니였으면 RED 색의 VertexColor 객체 생성
			VertexColor headVertexColor = (this.vertexColor(tailVertex) == VertexColor.RED) 
					? VertexColor.BLUE : VertexColor.RED;
			//tailVertex와 연결되어있는 edge들 중 색이 아직 NONE인 vertex들의 색을 위에서 생성한 색깔로 지정한다
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {
				Edge visitingEdge = new Edge(tailVertex, headVertex);
				if(this.graph().edgeDoesExist(visitingEdge)) {
					if(this.vertexColor(headVertex) == VertexColor.NONE) {
						this.setVertexColor(headVertex, headVertexColor);
						breadthFirstSearchQueue.add(headVertex);	
					}
				}
			}
		}
	}
	//양 끝의 vertex 색이 동일한 edge를 찾는다
	private void findSameColorEdges() {
		for(int tailVertex = 0;tailVertex < this.graph().numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {
				Edge visitingEdge = new Edge(tailVertex, headVertex);	//모든 vertex에 대해 edge를 생성한다
				if(this.graph().edgeDoesExist(visitingEdge)) {	//만약 존재하는 edge이고
					if(this.vertexColor(tailVertex) == this.vertexColor(headVertex)) {	//만약 양 끝 vetex의 색이 같으면
						this.sameColorEdges().add(visitingEdge);	//sameColorEdges에 해당 edge를 저장한다
					}
				}
			}
		}
	}
}
