package AL02_201701982_�賲ȣ;

public class Edge {
	private int _tailVertex ;
	private int _headVertex ;

	public Edge (int givenTailVertex, int givenHeadVertex) {
		this.setTailVertex (givenTailVertex);
		this.setHeadVertex (givenHeadVertex);
	 }
	public void setTailVertex (int newTailVertex) {
		this._tailVertex = newTailVertex ;
	 }
	 
	public int tailVertex () {
		return this._tailVertex ; 
	 }

	public void setHeadVertex (int newHeadVertex) {
		this._headVertex = newHeadVertex ;
	 }
	public int headVertex() {
		return this._headVertex ;
	}

}
