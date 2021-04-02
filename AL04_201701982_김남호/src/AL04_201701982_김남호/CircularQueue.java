package AL04_201701982_�賲ȣ;

public class CircularQueue<T> implements Queue<T>{
	private T[] _elements;
	private int _capacity;
	private int _front;
	private int _rear;
	
	// _element�� getter
	private T[] elements() {
		return this._elements;
	}
	// _element�� setter
	private void setElements(T[] newElements) {
		this._elements = newElements;
	}
	// _capacity�� getter
	private int capacity() {
		return this._capacity;
	}
	// _capacity�� setter
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	// _front�� setter
	private int front() {
		return this._front; 
	}
	// _front�� getter
	private void setFront(int newFront) {
		this._front = newFront;
	}
	// _rear�� getter 
	private int rear() {
		return this._rear;
	}
	// _rear�� setter
	private void setRear(int newRear) {
		this._rear = newRear;
	}
	//���� ť�� ���� rear�� ��ȯ�Ѵ�
	private int nextRear() {
		return (this.rear()+1) % this.capacity();
	}
	//���� ť�� ���� front�� ��ȯ�Ѵ�
	private int nextFront() {
		return (this.front()+1) % this.capacity();
	}
	//�����ڷμ� �� �ν��Ͻ� �������� �ʱ�ȭ �Ѵ�
	@SuppressWarnings("unchecked")
	public CircularQueue(int maxNumberOfElements) {
		this.setCapacity(maxNumberOfElements+1);	//�迭�� ũ�⸦ �־��� ������ �ʱ�ȭ�Ѵ�
		this.setElements((T[]) new Object[this.capacity()]);	//�־��� ũ���� �迭�� �����Ѵ�
		this.reset();	//front�� rear�� 0���� �ʱ�ȭ �Ѵ�
	}
	// front�� rear�� 0���� �ʱ�ȭ�Ѵ�
	@Override
	public void reset() {
		this.setFront(0);
		this.setRear(0);
	}
	//�迭�� ���� ����� ��ȯ�Ѵ�
	@Override
	public int size() {
		if(this.front() <= this.rear()) {
			return (this.rear() - this.front());
		}	// rear�� front���� ũ�ٸ� rear-front ��ȯ
		else {
			return (this.capacity() + this.rear() - this.front());
		}	// rear�� front���� �۴ٸ� �迭�� ũ��+rear+front ��ȯ
	}
	//�迭�� ����ִ��� Ȯ��
	@Override
	public boolean isEmpty() {
		return (this.front() == this.rear());	// �迭�� rear�� front�� ������ ����ִٰ� �Ǵ��Ͽ� true��ȯ
	}
	//�迭�� ���� �� �ִ��� Ȯ��
	@Override
	public boolean isFull() {
		return (this.front() == this.nextRear());	//�迭�� front�� ���� rear�� ������ ��ȭ�Ȱ��̹Ƿ� true ��ȯ
	}
	// �迭�� �� ���Ҹ� ���ϴ� �޼ҵ�
	@Override
	public boolean add(T anElement) {
		if(this.isFull()) {
			return false;
		}	// �迭�� ���� �� �ִٸ� �� �߰��� �� �����Ƿ� false ��ȯ
		else {
			this.setRear(this.nextRear());	//rear�� ���� rear�� ����
			this.elements()[this.rear()] = anElement;	//����� rear�� ������ ���� �߰�
			return true;
		}
	}
	// �迭���� ���Ҹ� �����ϴ� �޼ҵ�
	@Override
	public T remove() {
		if(this.isEmpty()) {
			return null;
		}	// �迭�� ����ִٸ� ���� ���� �����Ƿ� false ��ȯ
		else {
			this.setFront(this.nextFront());	// front�� ���� font�� ����
			return this.elements()[this.front()];	// ����� front ��ȯ
		}
	}
}
