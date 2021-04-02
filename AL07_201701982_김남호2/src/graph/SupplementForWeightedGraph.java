package graph;

public interface SupplementForWeightedGraph<E> {
	//�ش� edge�� weight�� ��ȯ�Ѵ�
	public int weightOfEdge(E anEdge);
	public int weightOfEdge(int aTailVertex, int aHeadVertex);
}
