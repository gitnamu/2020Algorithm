package AL03_201701982_�賲ȣ;

public class AdjacencyMatrixGraph {
	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices ;
	private int _numberOfEdges ;
	private int[][] _adjacency ;
	
	//������
	public AdjacencyMatrixGraph(int givenNumberOfVertices) {
		//vertex ���� givenNumberOfVertices�� �ʱ�ȭ
		this.setNumberOfVertices(givenNumberOfVertices);
		//edge�� ���� 0���� �ʱ�ȭ
		this.setNumberOfEdges(0);
		//vertex�� ���� ũ��� �ϴ� ������ �迭 _adjacency ���� 
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
		//�迭�� ���ҵ��� ���� 0���� �ʱ�ȭ
		for(int tailVertex = 0;tailVertex<this.numberOfVertices();tailVertex++) {
			for(int headVertex = 0;headVertex<this.numberOfVertices();headVertex++) {
				this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_NONE;
			}
		}
	}
	//_numberOfVertices�� getter
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	//_numberOfEdges�� getter
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	//aVertex�� ��ȿ�� vertex���� Ȯ��
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());
	}
	//anEdge�� �����ϴ� edge���� Ȯ��
	public boolean edgeDoesExist(Edge anEdge) {
		if(anEdge != null) {
			int tailVertex = anEdge.tailVertex();
			int headVertex = anEdge.headVertex();
			//adjacencyOfEdgeDoesExist()�� �̿��Ͽ� tail vertex�� head vertex�� ����� edge�� �ִ��� Ȯ��
			if(this.vertexDoesExist(tailVertex)&& this.vertexDoesExist(headVertex)) {
				return(this.adjacencyOfEdgeDoesExist(tailVertex,headVertex));
			}
		}
		return false;
	}
	//edge�� �߰��ϴ� �޼ҵ�
	//�̹� �����ϴ� edge�� �ƴ϶�� �迭 _adjacency�� �ش� vertex�� ��ġ�� 1 ����
	public boolean addEdge(Edge anEdge) {
		if(anEdge != null) {
			int tailVertex = anEdge.tailVertex();
			int headVertex = anEdge.headVertex();
			if(this.vertexDoesExist(tailVertex)&& this.vertexDoesExist(headVertex)) {
				if(!this.adjacencyOfEdgeDoesExist(tailVertex, headVertex)) {
					//������ �׷����̹Ƿ� [tailVertex][headVertex]�� [headVertex][tailVertex] �� �� 1 ���� 
					this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_EXIST;
					this.adjacency()[headVertex][tailVertex] = AdjacencyMatrixGraph.EDGE_EXIST;
					this.setNumberOfEdges(this.numberOfEdges()+1);
					return true;
				}
			}
		}
		return false;
	}
	//_numberOfVertices�� setter
	private void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	//_numberOfEdges�� setter 
	private void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	//�迭 _adjacency�� getter
	private int[][] adjacency(){
		return this._adjacency;
	}
	//�迭 _adjacency�� setter
	private void setAdjacency(int[][] newAdjacency) {
		this._adjacency = newAdjacency;
	}
	//�迭 _adjacency�ȿ� �ش� vertex���� tail vertex, head vertex�� �ϴ� edge�� �����ϴ��� Ȯ��
	private boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacency()[tailVertex][headVertex] != AdjacencyMatrixGraph.EDGE_NONE);
	}

}
