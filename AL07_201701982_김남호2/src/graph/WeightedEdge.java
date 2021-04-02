package graph;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {

	public static final int DEFAULT_WEIGHT = 0;
	private int _weight;
	
	//_weight�� getter
	public int weight() {
		return this._weight;
	}
	//_weight�� setter
	public void setWeight(int newWeight) {
		this._weight = newWeight;
	}
	//������
	public WeightedEdge(int givenTailVertex, int givenHeadVertex) {
		//superclass�� �����ڸ� ��������� ȣ���Ͽ� givenTailVertex, givenHeadVertex �ʱ�ȭ
		super(givenTailVertex, givenHeadVertex);
		//_weight�� 0���� �ʱ�ȭ
		this.setWeight(WeightedEdge.DEFAULT_WEIGHT);
	}
	//������
	public WeightedEdge(int givenTailVertex, int givenHeadVertex, int givenWeight) {
		//superclass�� �����ڸ� ��������� ȣ���Ͽ� givenTailVertex, givenHeadVertex �ʱ�ȭ
		super(givenTailVertex, givenHeadVertex);
		//_weight�� �־��� ������ ����
		this.setWeight(givenWeight);
	}
	@Override
	// weightedEdge ��ü������ ��. ���� ��ü�� �� ũ�� +1, ������ -1, ������ 0�� ��ȯ
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
