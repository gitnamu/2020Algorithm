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
	
	// �����ڷμ� size�� 0, _head�� null�� �ʱ�ȭ �Ѵ�
	public LinkedList() {
		this.reset();
	}
	@Override
	// _size�� 0�̸� ����Ʈ�� ����ִٴ� �ǹ��̹Ƿ� true�� ��ȯ�Ѵ�
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	@Override
	// ���� ����Ʈ�� ���� ���� �����Ƿ� false�� ��ȯ�Ѵ�
	public boolean isFull() {
		return false;
	}
	@Override
	// ���� ����Ʈ�� �߰��ϴ� �޼ҵ�
	public boolean add(T anElement) {
		LinkedNode<T> addedNode = new LinkedNode<T>(anElement, this.head());	// ���� ��� ��ü�� �����Ѵ�
		addedNode.setNext(this.head());
		this.setHead(addedNode);	// _head�� ������ ������ ��� ��ü�� �����Ѵ�
		this.setSize(this.size()+1);	// _size�� +1�Ѵ�
		return true;
	}
	@Override
	// ��� �ϳ��� �����ϴ� �޼ҵ�
	public T removeAny() {
		if(this.isEmpty()) {
			return null;
		}	// _size�� 0�̸� ���� ���� �����Ƿ� null ��ȯ 
		else {
			T removedElement = this.head().element();	//������ ������ head�� ������ ����
			this.setHead(this.head().next());	// head�� ���� ���� ����
			this.setSize(this.size()-1);	//������ 1 ����
			return removedElement;	// ������ ���� ��ȯ
		}
	}
	@Override
	// size�� head�� null�� �ʱ�ȭ�Ѵ�
	public void reset() {
		this.setSize(0);
		this.setHead(null);
	}
	@Override
	// Itreator ��ü ������ ���� �޼ҵ�
	public Iterator<T> listIterator() {
		return new IteratorForLinkedList();
	}
	// Inner Class
	private class IteratorForLinkedList implements Iterator<T>{
		private LinkedNode<T> _nextNode;
		
		// Getter & Setter
		private LinkedNode<T> nextNode(){
			return this._nextNode;
		}
		private void setNextNode(LinkedNode<T> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}
		// Constructor
		private IteratorForLinkedList() {
			this.setNextNode(LinkedList.this.head());
		}
		@Override
		// next node�� �ִ��� Ȯ��
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		@Override
		// Iterator�� ���� ���� �ű�� �޼ҵ��̴�
		public T next() {
			T nextElement = this.nextNode().element();
			this.setNextNode(this.nextNode().next());
			return nextElement;
		}
	}
}
