package AL03_201701982_김남호;

public class Edge {
	private int _tailVertex ;
	private int _headVertex ;
	
	//생성자. 주어진 숫자로 각각 TailVertex와 HeadVertex를 초기화 한다  
	public Edge (int givenTailVertex, int givenHeadVertex) {
		this.setTailVertex (givenTailVertex);
		this.setHeadVertex (givenHeadVertex);
	 }
	//_tailVertex의 setter
	public void setTailVertex (int newTailVertex) {
		this._tailVertex = newTailVertex ;
	 }
	//_tailVertex의 getter
	public int tailVertex () {
		return this._tailVertex ; 
	 }
	//_headVertex의 setter
	public void setHeadVertex (int newHeadVertex) {
		this._headVertex = newHeadVertex ;
	 }
	//_headVertex의 getter
	public int headVertex() {
		return this._headVertex ;
	}

}
