package AL04_201701982_�賲ȣ;

public class Coloring {
	private AdjacencyMatrixGraph _graph;
	private VertexColor[] _vertexColors;
	private int _startingVertex;
	private LinkedList<Edge> _sameColorEdges;
	
	//_graph�� getter
	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	//_graph�� setter
	private void setGraph (AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}
	//_startingVertex�� getter
	private int startingVertex() {
		return this._startingVertex;
	}
	//_startingVertex�� setter
	private void setStartingVertex(int newVertex) {
		this._startingVertex = newVertex;
	}
	//_vertexColors�� getter
	private VertexColor[] vertexColors() {
		return this._vertexColors;
	}
	//_vertexColors�� setter
	private void setVertexColors(VertexColor[] newVertexColors) {
		this._vertexColors = newVertexColors;
	}
	
	// _vertexColor�� aVertex��° ���Ҹ� ��ȯ�ϴ� getter
	public VertexColor vertexColor(int aVertex) {
		return this.vertexColors()[aVertex];
	}
	// _vertexColor�� aVertex��° ���Ҹ� newColor�� �����ϴ� setter
	private void setVertexColor(int aVertex, VertexColor newColor) {
		this.vertexColors()[aVertex] = newColor;
	}
	//_sameColorEdges�� getter
	public LinkedList<Edge> sameColorEdges(){
		return this._sameColorEdges;
	}
	//_sameColorEdges�� setter 
	private void setSameColorEdges(LinkedList<Edge> newLinkedList) {
		this._sameColorEdges = newLinkedList;
	}
	
	//
	public Coloring(AdjacencyMatrixGraph givenGraph) {
		this.setGraph(givenGraph);	// �־��� �׷��� ����
		this.setVertexColors(new VertexColor[this.graph().numberOfVertices()]);	// vertex�� color�� ������ �迭�� ����
		for(int vertex = 0; vertex < this.graph().numberOfVertices();vertex++) {
			this.setVertexColor(vertex, VertexColor.NONE);
		}//�迭�� NONE���� �ʱ�ȭ�Ѵ�
		this.setSameColorEdges(new LinkedList<Edge>());	//�� �� ���� ���� edge���� ������ ���Ḯ��Ʈ ����
		this.setStartingVertex(0);	//��ĥ�� ������ vertex�� 0���� ����
	}
	//paintColorOfVertices()�� findSameColorEdges() ����
	public void runColoring() {
		this.paintColorOfVertices();
		this.findSameColorEdges();
	}
	//bfs�� �̿��� ��� vertex�� ��ĥ�ϱ� ����
	public void paintColorOfVertices() {
		this.setVertexColor(this.startingVertex(), VertexColor.RED);	//ù���� vertex�� ���� RED�� ����
		
		CircularQueue<Integer> breadthFirstSearchQueue = 
				new CircularQueue<Integer>(this.graph().numberOfVertices());	//bfs�� ���� ���� ť �ʱ�ȭ
		breadthFirstSearchQueue.add(this.startingVertex());	//���� ť�� ù��° vertex �߰�
		
		//���� ť�� �������� ����
		while(! breadthFirstSearchQueue.isEmpty()) {
			int tailVertex = breadthFirstSearchQueue.remove();	//ť�� ���� �ϳ� ���� �� tailVertex�� ����
			//������ ������ ���Ұ� RED������ BLUE, �ƴϿ����� RED ���� VertexColor ��ü ����
			VertexColor headVertexColor = (this.vertexColor(tailVertex) == VertexColor.RED) 
					? VertexColor.BLUE : VertexColor.RED;
			//tailVertex�� ����Ǿ��ִ� edge�� �� ���� ���� NONE�� vertex���� ���� ������ ������ ����� �����Ѵ�
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
	//�� ���� vertex ���� ������ edge�� ã�´�
	private void findSameColorEdges() {
		for(int tailVertex = 0;tailVertex < this.graph().numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {
				Edge visitingEdge = new Edge(tailVertex, headVertex);	//��� vertex�� ���� edge�� �����Ѵ�
				if(this.graph().edgeDoesExist(visitingEdge)) {	//���� �����ϴ� edge�̰�
					if(this.vertexColor(tailVertex) == this.vertexColor(headVertex)) {	//���� �� �� vetex�� ���� ������
						this.sameColorEdges().add(visitingEdge);	//sameColorEdges�� �ش� edge�� �����Ѵ�
					}
				}
			}
		}
	}
}
