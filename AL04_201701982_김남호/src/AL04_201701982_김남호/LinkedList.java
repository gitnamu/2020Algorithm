package AL04_201701982_�賲ȣ;

public class LinkedList<T> {
	private LinkedNode<T> _head;
	private int _size;
	
	//_head�� getter
	private LinkedNode<T> head(){
		return this._head;
	}
	//_head�� setter
	private void setHead(LinkedNode<T> newHead) {
		this._head = newHead;
	}
	//_size�� getter
	public int size() {
		return this._size;
	}
	//_size�� setter
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	// �����ڷμ� size�� 0, _head�� null�� �ʱ�ȭ �Ѵ�
	public LinkedList() {
		this.setSize(0);
		this.setHead(null);
	}
	// _size�� 0�̸� ����Ʈ�� ����ִٴ� �ǹ��̹Ƿ� true�� ��ȯ�Ѵ�
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	// ���� ����Ʈ�� ���� ���� �����Ƿ� false�� ��ȯ�Ѵ�
	public boolean isFull() {
		return false;
	}
	// ���� ����Ʈ�� �߰��ϴ� �޼ҵ�
	public boolean add(T anElement) {
		LinkedNode<T> newHeadNode = new LinkedNode<T>(anElement, this.head());	// ���� ��� ��ü�� �����Ѵ�
		this.setHead(newHeadNode);	//_head�� ������ ������ ��� ��ü�� �����Ѵ�
		this.setSize(this.size()+1);	//_size�� +1�Ѵ�
		return true;
	}
	//��� �ϳ��� �����ϴ� �޼ҵ�
	public T removeAny() {
		if(this.isEmpty()) {
			return null;
		}	//_size�� 0�̸� ���� ���� �����Ƿ� null ��ȯ 
		else {
			T removedElement = this.head().element();	//������ ������ head�� ������ ����
			this.setHead(this.head().next());	// head�� ���� ���� ����
			this.setSize(this.size()-1);	//������ 1 ����
			return removedElement;	// ������ ���� ��ȯ
		}
	}
	//iterator ��ü�� ��ȯ�ϴ� �޼ҵ�
	public IteratorForLinkedList iterator() {
		return new IteratorForLinkedList();
	}
	//���� ����Ʈ�� iterator ������ ���� ���� Ŭ����
	public class IteratorForLinkedList implements Iterator<T>{
		LinkedNode<T> _nextNode;	//�ݺ��ڰ� �̵��� ���� ���
		//_nextNod�� getter
		private LinkedNode<T> nextNode() {
			return this._nextNode;
		}
		// _nextNode�� setter
		private void setNextNode(LinkedNode<T> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}
		//�����ڷμ� head�� �ݺ��ڰ� �̵��� ���� ���� ����
		private IteratorForLinkedList() {
			this.setNextNode(LinkedList.this.head());
		}
		//�ݺ��ڰ� �̵��� ���� ��尡 �����ϴ��� Ȯ��
		@Override
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		// �ݺ��ڰ� ���� ���� �̵� �� �ش� ���� ��ȯ
		@Override
		public T next() {
			T nextElement = this.nextNode().element();	//���� ����� ���Ҹ� �����Ѵ�
			this.setNextNode(this.nextNode().next());	//_nextNode�� _nextNode�� _nextNode�� �����Ѵ�
			return nextElement;
		}
	}
}
