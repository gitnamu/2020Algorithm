package graph;

public class UndirectedAdjacencyMatrixGraph<E extends Edge> implements Graph<E> {
	
	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices;
	private int _numberOfEdges;
	private int[][] _adjacency;
	
	@Override
	//_numberOfVertices�� getter
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	//_numberOfVertices�� setter
	protected void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	
	@Override
	//_numberOfEdges�� getter
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	//_numberOfEdges�� setter
	protected void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	//_adjacency�� getter
	protected int[][] adjacency(){
		return this._adjacency;
	}
	//_adjacency�� setter
	protected void setAdjacency(int[][] newAdjacency ) {
		this._adjacency = newAdjacency;
	}
	//_adjacency �� ������ getter
	protected int adjacencyOfEdge(int tailVertex, int headVertex) {
		return this.adjacency()[tailVertex][headVertex];
	}
	//_adjacency �� ������ setter
	protected void setAdjacencyOfEdgeAs(int tailVertex, int headVertex, int adAdjacencyOfEdge) {
		this.adjacency()[tailVertex][headVertex] = adAdjacencyOfEdge;
	}
	//_adjacency�� ����� edge�� 1�� �����Ѵ�
	private void setAdjacencyOfEdgeAsExist(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_EXIST);
	}
	//_adjacency�� ����� edge�� 0���� �����Ѵ�
	protected void setAdjacencOfEdgeAsNone(int tailVertex, int headVertex) {
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_NONE);
	}
	//�⺻ ������
	protected UndirectedAdjacencyMatrixGraph() {
	}
	//�����ڷμ� _numberOfVertices�� �Ѱܹ��� ���ڷ�, _numberOfEdges�� 0����, �迭�� ��� ���Ҹ� 0���� �ʱ�ȭ�Ѵ�
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
	//�Ѱܹ��� ���ڸ� �� �� vertex�� �ϴ� edge�� �����ϴ��� Ȯ���Ѵ�
	protected boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacencyOfEdge(tailVertex, headVertex)!=
				UndirectedAdjacencyMatrixGraph.EDGE_NONE);
	}
	
	@Override
	//aVertex�� 0�̻� _numberOfVertices�̸��̸� ���� ��ȯ�Ѵ�
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());
	}

	@Override
	//edge�� �� �� vertex�� ��ȿ���� Ȯ���Ѵ�
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex) {
		return (this.vertexDoesExist(aTailVertex) &&
				this.vertexDoesExist(aHeadVertex));
	}
	
	@Override
	//anEdge�� null�� �ƴ϶�� �� �� vertex�� ��ȿ���� �˻��Ͽ� ��ȯ�Ѵ� 
	public boolean edgeIsValid(E anEdge) {
		if(anEdge != null) {
			return (this.edgeIsValid(anEdge.tailVertex(), anEdge.headVertex()));
		}
		return false;
	}
	@Override
	//�Ѱܹ��� ���ڸ� �� �� vertex�� �ϴ� edge�� �����ϴ��� �迭 _adjacency���� ã�� Ȯ���Ѵ� 
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {
			return (this.adjacencyOfEdgeDoesExist(aTailVertex, aHeadVertex));
		}
		return false;
	}

	@Override
	//�Ѱܹ��� ���ڰ� null�� �ƴ϶�� �� �� vertex�� �����Ͽ� �����ϴ� edge���� Ȯ���Ѵ�
	public boolean edgeDoesExist(E anEdge) {
		if(anEdge != null) {
			return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
		}
		return false;
	}

	@Override
	//anEdge�� null�� �ƴϸ� edge�� �ش� edge�� �� �� vertex ��� ��ȿ�ϴٸ�, �迭�� edge�� �߰��ϰ� _numberOfEdge�� +1�Ѵ�
	public boolean addEdge(E anEdge) {
		if(anEdge != null) {
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);	
				this.setAdjacencyOfEdgeAsExist(headVertex, tailVertex);	//������ �׷����̱� ������ �� ���� ��� �߰��Ѵ�
				this.setNumberOfEdges(this.numberOfEdges() + 1);
			}
					return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	//�־��� vertex�� �ش��ϴ� edge�� �̹� �����ϸ� null�� ��ȯ, �������� �ʴ´ٸ� edge ��ü�� �����Ͽ� ��ȯ�Ѵ�
	public E edge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {
			return (E) new Edge(aTailVertex, aHeadVertex);
		}
		return null;
	}
}
