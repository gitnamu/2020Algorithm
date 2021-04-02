package graph;

public interface Graph<E> {
	// vertex�� �� ��ȯ
	public int numberOfVertices();	
	// edge�� �� ��ȯ
	public int numberOfEdges();		
	
	//vertex�� �����ϴ��� Ȯ��
	public boolean vertexDoesExist(int aVertex);
	//edge�� �����ϴ��� Ȯ��
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex);
	public boolean edgeDoesExist(E anEdge);
	// ��ȿ�� edge���� Ȯ��
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex);
	public boolean edgeIsValid(E anEdge);
	//edge ����
	public E edge(int aTailVertex, int aHeadVertex);
	//edge �߰�
	public boolean addEdge(E anEdge);
}
