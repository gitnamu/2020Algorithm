package list;

public class LinkedList<T> extends List<T>{
	
	// Private instance variables
	private LinkedNode<T> _head;
	private int _size;
	
	// Getter & Setters
	private LinkedNode<T> head(){
		return this._head;
	}
	private void setHead(LinkedNode<T> newHead) {
		this._head = newHead;
	}
	@Override
	public int size() {
		return this._size;
	}
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	// 생성자로서 size를 0, _head를 null로 초기화 한다
	public LinkedList() {
		this.setSize(0);
		this.setHead(null);
	}
	@Override
	// _size가 0이면 리스트가 비어있다는 의미이므로 true를 반환한다
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	@Override
	// 연결 리스트는 가득 차지 않으므로 false를 반환한다
	public boolean isFull() {
		return false;
	}
	@Override
	// 연결 리스트에 추가하는 메소드
	public boolean add(T anElement) {
		LinkedNode<T> addedNode = new LinkedNode<T>(anElement, this.head());	// 연결 노드 객체를 생성한다
		addedNode.setNext(this.head());
		this.setHead(addedNode);	// _head를 위에서 생성한 노드 객체로 설정한다
		this.setSize(this.size()+1);	// _size를 +1한다
		return true;
	}
	@Override
	// 노드 하나르 삭제하는 메소드
	public T removeAny() {
		if(this.isEmpty()) {
			return null;
		}	// _size가 0이면 지울 것이 없으므로 null 반환 
		else {
			T removedElement = this.head().element();	//삭제할 원소인 head를 변수에 저장
			this.setHead(this.head().next());	// head를 다음 노드로 지정
			this.setSize(this.size()-1);	//사이즈 1 감소
			return removedElement;	// 삭제한 원소 반환
		}
	}
	@Override
	// size와 head를 null로 초기화한다
	public void reset() {
		this.setSize(0);
		this.setHead(null);
	}
	@Override
	// Itreator 객체 생성을 위한 메소드
	public Iterator<T> listIterator() {
		return new LinkedListIterator<T>();
	}
	// Inner Class
	public class LinkedListIterator<E> implements Iterator<E>{
		LinkedNode<E> _nextNode;
		
		// Getter & Setter
		private LinkedNode<E> nextNode(){
			return this._nextNode;
		}
		private void setNextNode(LinkedNode<E> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}
		// Constructor
		@SuppressWarnings("unchecked")
		private LinkedListIterator() {
			this.setNextNode((LinkedNode<E>)LinkedList.this.head());
		}
		
		@Override
		// next node가 있는지 확인
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		@Override
		// Iterator를 다음 노드로 옮기는 메소드이다
		public E next() {
			E nextElement = this.nextNode().element();
			this.setNextNode(this.nextNode().next());
			return nextElement;
		}
	}
}
