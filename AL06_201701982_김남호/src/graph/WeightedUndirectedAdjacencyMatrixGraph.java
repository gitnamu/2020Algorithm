package graph;

public class WeightedUndirectedAdjacencyMatrixGraph<WE extends WeightedEdge> 
	extends UndirectedAdjacencyMatrixGraph<WE> 
	implements SupplementForWeightedGraph<WE>
{
	private static final int WEIGHTED_EDGE_NONE = -1;
	
	//주어진 edge의 weight를 newWeight로 설정한다
	private void setWeightOfEdge(int aTailVertex, int aHeadVertex, int newWeight) {
		this.adjacency()[aTailVertex][aHeadVertex] = newWeight;
	}
	//주어진 edge의 weight를 -1로 초기화한다
	private void setWeightOfEdgeAsNone(int aTailVertex, int aHeadVertex) {
		this.setWeightOfEdge(aTailVertex, aHeadVertex, WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);
	}
	//생성자로서 _numberOfVertices를 주어진 수로, _numberOfEdge를 0으로, 배열의 원소를 -1로 전부 초기화한다
	public WeightedUndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		super();	//superclass의 생성자를 명시적으로 호출한다
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
	//주어진 edge의 weight가 -1이 아니면 true를 반환한다
	protected boolean adjacencyOfEdgeDoesExist(int aTailVertex, int aHeadVertex) {
		return(this.adjacencyOfEdge(aTailVertex, aHeadVertex) !=
				WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);
	}
	@Override
	//주어진 edge가 null이 아니면 해당 edge의 weight를 반환한다
	public int weightOfEdge(WE anEdge) {
		if(anEdge != null) {
			return this.weightOfEdge(anEdge.tailVertex(), anEdge.headVertex());
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}

	@Override
	//주어진 edge가 null이 아니면 해당 edge의 weight를 반환한다
	public int weightOfEdge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {
			return this.adjacencyOfEdge(aTailVertex, aHeadVertex);
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}
	//superclass의 addEdge()를 override 하는 함수이다
	//edge가 null이 아니고 유효한 edge이고, 이미 존재하는 edge가 아니면 추가한다
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
