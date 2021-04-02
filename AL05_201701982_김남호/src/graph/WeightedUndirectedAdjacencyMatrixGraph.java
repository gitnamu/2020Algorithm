package graph;

public class WeightedUndirectedAdjacencyMatrixGraph<WE extends WeightedEdge> 
	extends UndirectedAdjacencyMatrixGraph<WE> 
	implements SupplementForWeightedGraph<WE>
{
	private static final int WEIGHTED_EDGE_NONE = -1;
	
	//�־��� edge�� weight�� newWeight�� �����Ѵ�
	private void setWeightOfEdge(int aTailVertex, int aHeadVertex, int newWeight) {
		this.adjacency()[aTailVertex][aHeadVertex] = newWeight;
	}
	//�־��� edge�� weight�� -1�� �ʱ�ȭ�Ѵ�
	private void setWeightOfEdgeAsNone(int aTailVertex, int aHeadVertex) {
		this.setWeightOfEdge(aTailVertex, aHeadVertex, WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);
	}
	//�����ڷμ� _numberOfVertices�� �־��� ����, _numberOfEdge�� 0����, �迭�� ���Ҹ� -1�� ���� �ʱ�ȭ�Ѵ�
	public WeightedUndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		super();	//superclass�� �����ڸ� ��������� ȣ���Ѵ�
		this.setNumberOfVertices(givenNumberOfVertices);
		this.setNumberOfEdges(0);
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
		for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.numberOfVertices(); headVertex++) {
				this.setWeightOfEdgeAsNone(tailVertex, headVertex);
			}
		}
	}
	@Override
	//�־��� edge�� weight�� -1�� �ƴϸ� true�� ��ȯ�Ѵ�
	protected boolean adjacencyOfEdgeDoesExist(int aTailVertex, int aHeadVertex) {
		return(this.adjacencyOfEdge(aTailVertex, aHeadVertex) !=
				WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);
	}
	@Override
	//�־��� edge�� null�� �ƴϸ� �ش� edge�� weight�� ��ȯ�Ѵ�
	public int weightOfEdge(WE anEdge) {
		if(anEdge != null) {
			return this.weightOfEdge(anEdge.tailVertex(), anEdge.headVertex());
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}

	@Override
	//�־��� edge�� null�� �ƴϸ� �ش� edge�� weight�� ��ȯ�Ѵ�
	public int weightOfEdge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {
			return this.adjacencyOfEdge(aTailVertex, aHeadVertex);
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}
	//superclass�� addEdge()�� override �ϴ� �Լ��̴�
	//edge�� null�� �ƴϰ� ��ȿ�� edge�̰�, �̹� �����ϴ� edge�� �ƴϸ� �߰��Ѵ�
	public boolean addEdge(WE anEdge) {
		if(anEdge != null) {
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setWeightOfEdge(tailVertex, headVertex, anEdge.weight());
				this.setWeightOfEdge(headVertex, tailVertex, anEdge.weight());
				this.setNumberOfEdges(this.numberOfEdges() + 1);
				return true;
			}
		}
		return false;
	}
}
