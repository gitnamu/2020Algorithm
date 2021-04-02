package AL04_201701982_김남호;

public class CircularQueue<T> implements Queue<T>{
	private T[] _elements;
	private int _capacity;
	private int _front;
	private int _rear;
	
	// _element의 getter
	private T[] elements() {
		return this._elements;
	}
	// _element의 setter
	private void setElements(T[] newElements) {
		this._elements = newElements;
	}
	// _capacity의 getter
	private int capacity() {
		return this._capacity;
	}
	// _capacity의 setter
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	// _front의 setter
	private int front() {
		return this._front; 
	}
	// _front의 getter
	private void setFront(int newFront) {
		this._front = newFront;
	}
	// _rear의 getter 
	private int rear() {
		return this._rear;
	}
	// _rear의 setter
	private void setRear(int newRear) {
		this._rear = newRear;
	}
	//원형 큐의 다음 rear를 반환한다
	private int nextRear() {
		return (this.rear()+1) % this.capacity();
	}
	//원형 큐의 다음 front를 반환한다
	private int nextFront() {
		return (this.front()+1) % this.capacity();
	}
	//생성자로서 각 인스턴스 변수들을 초기화 한다
	@SuppressWarnings("unchecked")
	public CircularQueue(int maxNumberOfElements) {
		this.setCapacity(maxNumberOfElements+1);	//배열의 크기를 주어진 값으로 초기화한다
		this.setElements((T[]) new Object[this.capacity()]);	//주어진 크기의 배열을 생성한다
		this.reset();	//front와 rear를 0으로 초기화 한다
	}
	// front와 rear를 0으로 초기화한다
	@Override
	public void reset() {
		this.setFront(0);
		this.setRear(0);
	}
	//배열의 현재 사이즈를 반환한다
	@Override
	public int size() {
		if(this.front() <= this.rear()) {
			return (this.rear() - this.front());
		}	// rear가 front보다 크다면 rear-front 반환
		else {
			return (this.capacity() + this.rear() - this.front());
		}	// rear가 front보다 작다면 배열의 크기+rear+front 반환
	}
	//배열이 비어있는지 확인
	@Override
	public boolean isEmpty() {
		return (this.front() == this.rear());	// 배열의 rear와 front가 같으면 비어있다고 판단하여 true반환
	}
	//배열이 가득 차 있는지 확인
	@Override
	public boolean isFull() {
		return (this.front() == this.nextRear());	//배열의 front와 다음 rear가 같으면 포화된것이므로 true 반환
	}
	// 배열에 새 원소를 더하는 메소드
	@Override
	public boolean add(T anElement) {
		if(this.isFull()) {
			return false;
		}	// 배열이 가득 차 있다면 더 추가할 수 없으므로 false 반환
		else {
			this.setRear(this.nextRear());	//rear를 다음 rear로 변경
			this.elements()[this.rear()] = anElement;	//변경된 rear에 삽입할 원소 추가
			return true;
		}
	}
	// 배열에서 원소를 삭제하는 메소드
	@Override
	public T remove() {
		if(this.isEmpty()) {
			return null;
		}	// 배열이 비어있다면 지울 것이 없으므로 false 반환
		else {
			this.setFront(this.nextFront());	// front를 다음 font로 변경
			return this.elements()[this.front()];	// 변경된 front 반환
		}
	}
}
