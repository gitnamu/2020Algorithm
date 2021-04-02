package graph;

public interface SupplementForWeightedGraph<E> {
	//해당 edge의 weight를 반환한다
	public int weightOfEdge(E anEdge);
	public int weightOfEdge(int aTailVertex, int aHeadVertex);
}
