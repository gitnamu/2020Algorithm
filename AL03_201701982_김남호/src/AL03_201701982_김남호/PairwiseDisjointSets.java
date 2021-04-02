package AL03_201701982_김남호;

public class PairwiseDisjointSets {
	private static final int INITIAL_SINGLETON_SET_SIZE = 1;
	private int _numberOfElements;
	private int[] _parentTree;
	//_numberOfElements의 getter
	private int numberOfElements() {
		return this._numberOfElements;
	}
	//_numberOfElements의 setter
	private void setNumberOfElements(int newNumberOfElements) {
		this._numberOfElements = newNumberOfElements;
	}
	//_parentTree의 getter
	private int[] parentTree() {
		return this._parentTree;
	}
	//_parentTree의 setter
	private void setParentTree(int[] newParentTree) {
		this._parentTree = newParentTree;
	}
	//aMember의 부모 반환
	private int parentOf(int aMember) {
		return this.parentTree()[aMember];
	}
	//aChildMember의 부모를 newParentMember로 설정
	private void setParentOf(int aChildMember, int newParentMember) {
		this.parentTree()[aChildMember] = newParentMember;
	}
	//aMember의 부모가 존재하면 true 반환
	private boolean parentDoesExist(int aMember) {
		return (this.parentOf(aMember)>=0);
	}
	//aRoot의 집합의 크기 반환
	private int sizeOfSetFor(int aRoot) {
		return -this.parentOf(aRoot);
	}
	//aRoot의 크기를 -newSize로 설정
	private void setSizeOfSetFor(int aRoot, int newSize) {
		this.setParentOf(aRoot, -newSize);
	}
	//생성자
	public PairwiseDisjointSets(int givenNumberOfElements) {
		//집합의 크기를 givenNumberOfElements로 설정하고 크기에 해당하는 집합을 생성한다
		this.setNumberOfElements(givenNumberOfElements);
		this.setParentTree(new int[this.numberOfElements()]);
		//집합의 크기만큼 singleton set을 생성한다
		for(int rootOfSingleSet = 0;
				rootOfSingleSet<this.numberOfElements();
				rootOfSingleSet++)
		{
			this.setSizeOfSetFor(rootOfSingleSet, INITIAL_SINGLETON_SET_SIZE);
		}
	}
	//aMember의 부모를 반환
	public int find(int aMember) {
		int rootCandidate = aMember;
		//rootCandidate의 부모가 없을때까지 rootCandidate에 부모 대입  
		while(this.parentDoesExist(rootCandidate)) {
			rootCandidate = this.parentOf(rootCandidate);
		}
		int root = rootCandidate;
		
		//collapsing rule 적용
		int child = aMember;
		int parent = this.parentOf(child);
		//부모가 root가 아닌 모든 vertex들을 root에 연결
		if(parent >= 0) {
			while(parent != root) {
				this.setParentOf(child, root);
				child = parent;
				parent = this.parentOf(child);
			}
		}
		return root;
	}
	//aMemberA가 속한 집합과 aMemberB가 속한 집합을 합하여 하나의 집합으로 만듬
	public void union(int aMemberA, int aMemberB) {
		//aMemberA와 aMemberB의 root를 찾는다
		int rootOfSetA = find(aMemberA);
		int rootOfSetB = find(aMemberB);
		
		//aMemberA와 aMemberB의 크기를 찾는다
		int sizeOfSetA = this.sizeOfSetFor(rootOfSetA);
		int sizeOfSetB = this.sizeOfSetFor(rootOfSetB);
		
		//크기를 비교하여 더 큰 쪽의 루트를 작은 집합의 루트의 parent가 되게 한다
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
