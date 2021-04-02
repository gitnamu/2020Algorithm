package AL03_201701982_�賲ȣ;

public class PairwiseDisjointSets {
	private static final int INITIAL_SINGLETON_SET_SIZE = 1;
	private int _numberOfElements;
	private int[] _parentTree;
	//_numberOfElements�� getter
	private int numberOfElements() {
		return this._numberOfElements;
	}
	//_numberOfElements�� setter
	private void setNumberOfElements(int newNumberOfElements) {
		this._numberOfElements = newNumberOfElements;
	}
	//_parentTree�� getter
	private int[] parentTree() {
		return this._parentTree;
	}
	//_parentTree�� setter
	private void setParentTree(int[] newParentTree) {
		this._parentTree = newParentTree;
	}
	//aMember�� �θ� ��ȯ
	private int parentOf(int aMember) {
		return this.parentTree()[aMember];
	}
	//aChildMember�� �θ� newParentMember�� ����
	private void setParentOf(int aChildMember, int newParentMember) {
		this.parentTree()[aChildMember] = newParentMember;
	}
	//aMember�� �θ� �����ϸ� true ��ȯ
	private boolean parentDoesExist(int aMember) {
		return (this.parentOf(aMember)>=0);
	}
	//aRoot�� ������ ũ�� ��ȯ
	private int sizeOfSetFor(int aRoot) {
		return -this.parentOf(aRoot);
	}
	//aRoot�� ũ�⸦ -newSize�� ����
	private void setSizeOfSetFor(int aRoot, int newSize) {
		this.setParentOf(aRoot, -newSize);
	}
	//������
	public PairwiseDisjointSets(int givenNumberOfElements) {
		//������ ũ�⸦ givenNumberOfElements�� �����ϰ� ũ�⿡ �ش��ϴ� ������ �����Ѵ�
		this.setNumberOfElements(givenNumberOfElements);
		this.setParentTree(new int[this.numberOfElements()]);
		//������ ũ�⸸ŭ singleton set�� �����Ѵ�
		for(int rootOfSingleSet = 0;
				rootOfSingleSet<this.numberOfElements();
				rootOfSingleSet++)
		{
			this.setSizeOfSetFor(rootOfSingleSet, INITIAL_SINGLETON_SET_SIZE);
		}
	}
	//aMember�� �θ� ��ȯ
	public int find(int aMember) {
		int rootCandidate = aMember;
		//rootCandidate�� �θ� ���������� rootCandidate�� �θ� ����  
		while(this.parentDoesExist(rootCandidate)) {
			rootCandidate = this.parentOf(rootCandidate);
		}
		int root = rootCandidate;
		
		//collapsing rule ����
		int child = aMember;
		int parent = this.parentOf(child);
		//�θ� root�� �ƴ� ��� vertex���� root�� ����
		if(parent >= 0) {
			while(parent != root) {
				this.setParentOf(child, root);
				child = parent;
				parent = this.parentOf(child);
			}
		}
		return root;
	}
	//aMemberA�� ���� ���հ� aMemberB�� ���� ������ ���Ͽ� �ϳ��� �������� ����
	public void union(int aMemberA, int aMemberB) {
		//aMemberA�� aMemberB�� root�� ã�´�
		int rootOfSetA = find(aMemberA);
		int rootOfSetB = find(aMemberB);
		
		//aMemberA�� aMemberB�� ũ�⸦ ã�´�
		int sizeOfSetA = this.sizeOfSetFor(rootOfSetA);
		int sizeOfSetB = this.sizeOfSetFor(rootOfSetB);
		
		//ũ�⸦ ���Ͽ� �� ū ���� ��Ʈ�� ���� ������ ��Ʈ�� parent�� �ǰ� �Ѵ�
		if(sizeOfSetA < sizeOfSetB) {
			this.setParentOf(rootOfSetA, rootOfSetB);
			this.setSizeOfSetFor(rootOfSetB, sizeOfSetA+sizeOfSetB);
		}
		else {
			this.setParentOf(rootOfSetB, rootOfSetA);
			this.setSizeOfSetFor(rootOfSetA, sizeOfSetA+sizeOfSetB);
		}
	}
}
