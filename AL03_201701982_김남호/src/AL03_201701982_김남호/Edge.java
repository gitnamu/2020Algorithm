package AL03_201701982_�賲ȣ;

public class Edge {
	private int _tailVertex ;
	private int _headVertex ;
	
	//������. �־��� ���ڷ� ���� TailVertex�� HeadVertex�� �ʱ�ȭ �Ѵ�  
	public Edge (int givenTailVertex, int givenHeadVertex) {
		this.setTailVertex (givenTailVertex);
		this.setHeadVertex (givenHeadVertex);
	 }
	//_tailVertex�� setter
	public void setTailVertex (int newTailVertex) {
		this._tailVertex = newTailVertex ;
	 }
	//_tailVertex�� getter
	public int tailVertex () {
		return this._tailVertex ; 
	 }
	//_headVertex�� setter
	public void setHeadVertex (int newHeadVertex) {
		this._headVertex = newHeadVertex ;
	 }
	//_headVertex�� getter
	public int headVertex() {
		return this._headVertex ;
	}

}
