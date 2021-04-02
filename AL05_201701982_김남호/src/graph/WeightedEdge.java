package graph;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {

	public static final int DEFAULT_WEIGHT = 0;
	private int _weight;
	
	//_weight의 getter
	public int weight() {
		return this._weight;
	}
	//_weight의 setter
	public void setWeight(int newWeight) {
		this._weight = newWeight;
	}
	//생성자
	public WeightedEdge(int givenTailVertex, int givenHeadVertex) {
		//superclass의 생성자를 명시적으로 호출하여 givenTailVertex, givenHeadVertex 초기화
		super(givenTailVertex, givenHeadVertex);
		//_weight를 0으로 초기화
		this.setWeight(WeightedEdge.DEFAULT_WEIGHT);
	}
	//생성자
	public WeightedEdge(int givenTailVertex, int givenHeadVertex, int givenWeight) {
		//superclass의 생성자를 명시적으로 호출하여 givenTailVertex, givenHeadVertex 초기화
		super(givenTailVertex, givenHeadVertex);
		//_weight를 주어진 값으로 설정
		this.setWeight(givenWeight);
	}
	@Override
	// weightedEdge 객체끼리의 비교. 현재 객체가 더 크면 +1, 작으면 -1, 같으면 0을 반환
	public int compareTo(WeightedEdge otherEdge) {
		if(this.weight() < otherEdge.weight()) {
			return -1;
		}
		else if(this.weight() > otherEdge.weight()) {
			return +1;
		}
		else {
			return 0;
		}
	}

}
