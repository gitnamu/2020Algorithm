package AL04_201701982_김남호;

public class LinkedList<T> {
	private LinkedNode<T> _head;
	private int _size;
	
	//_head의 getter
	private LinkedNode<T> head(){
		return this._head;
	}
	//_head의 setter
	private void setHead(LinkedNode<T> newHead) {
		this._head = newHead;
	}
	//_size의 getter
	public int size() {
		return this._size;
	}
	//_size의 setter
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	// 생성자로서 size를 0, _head를 null로 초기화 한다
	public LinkedList() {
		this.setSize(0);
		this.setHead(null);
	}
	// _size가 0이면 리스트가 비어있다는 의미이므로 true를 반환한다
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	// 연결 리스트는 가득 차지 않으므로 false를 반환한다
	public boolean isFull() {
		return false;
	}
	// 연결 리스트에 추가하는 메소드
	public boolean add(T anElement) {
		LinkedNode<T> newHeadNode = new LinkedNode<T>(anElement, this.head());	// 연결 노드 객체를 생성한다
		this.setHead(newHeadNode);	//_head를 위에서 생성한 노드 객체로 설정한다
		this.setSize(this.size()+1);	//_size를 +1한다
		return true;
	}
	//노드 하나르 삭제하는 메소드
	public T removeAny() {
		if(this.isEmpty()) {
			return null;
		}	//_size가 0이면 지울 것이 없으므로 null 반환 
		else {
			T removedElement = this.head().element();	//삭제할 원소인 head를 변수에 저장
			this.setHead(this.head().next());	// head를 다음 노드로 지정
			this.setSize(this.size()-1);	//사이즈 1 감소
			return removedElement;	// 삭제한 원소 반환
		}
	}
	//iterator 객체를 반환하는 메소드
	public IteratorForLinkedList iterator() {
		return new IteratorForLinkedList();
	}
	//연결 리스트의 iterator 정보를 담은 내부 클래스
	public class IteratorForLinkedList implements Iterator<T>{
		LinkedNode<T> _nextNode;	//반복자가 이동할 다음 노드
		//_nextNod의 getter
		private LinkedNode<T> nextNode() {
			return this._nextNode;
		}
		// _nextNode의 setter
		private void setNextNode(LinkedNode<T> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}
		//생성자로서 head를 반복자가 이동할 다음 노드로 지정
		private IteratorForLinkedList() {
			this.setNextNode(LinkedList.this.head());
		}
		//반복자가 이동할 다음 노드가 존재하는지 확인
		@Override
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		// 반복자가 다음 노드로 이동 후 해당 원소 반환
		@Override
		public T next() {
			T nextElement = this.nextNode().element();	//다음 노드의 원소를 저장한다
			this.setNextNode(this.nextNode().next());	//_nextNode를 _nextNode의 _nextNode로 변경한다
			return nextElement;
		}
	}
}
