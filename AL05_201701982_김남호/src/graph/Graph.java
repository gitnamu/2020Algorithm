package graph;

public interface Graph<E> {
	// vertex의 수 반환
	public int numberOfVertices();	
	// edge의 수 반환
	public int numberOfEdges();		
	
	//vertex가 존재하는지 확인
	public boolean vertexDoesExist(int aVertex);
	//edge가 존재하는지 확인
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex);
	public boolean edgeDoesExist(E anEdge);
	// 유효한 edge인지 확인
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex);
	public boolean edgeIsValid(E anEdge);
	//edge 생성
	public E edge(int aTailVertex, int aHeadVertex);
	//edge 추가
	public boolean addEdge(E anEdge);
}
