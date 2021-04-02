package kruskal;

public class MinPriorityQ<E extends Comparable<E>> {
	// Constants
	private static final int DEFAULT_CAPACITY = 100;
	private static final int HEAP_ROOT = 1;
	
	// Private instance variables
	private int _size;
	private int _capacity;
	private E[] _heap;
	
	// Getters & Setters
	public int size() {
		return this._size;
	}
	public void setSize(int newSize) {
		this._size = newSize;
	}
	public int capacity() {
		return this._capacity;
	}
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	private E[] heap() {
		return this._heap;
	}
	private void setHeap(E[] newHeap) {
		this._heap = newHeap;
	}
	
	// Constructor
	public MinPriorityQ() {
		this(MinPriorityQ.DEFAULT_CAPACITY);
	}
	@SuppressWarnings("unchecked")
	public MinPriorityQ(int givenCapacity) {
		this.setCapacity(givenCapacity);
		this.setHeap((E[]) new Comparable[givenCapacity + 1]);
		this.setSize(0);
	}
	
	// 배열이 비어있는지 확인하는 메소드
	public boolean isEmpty() {
		return (this.size()==0);
	}
	// 배열이 가득 차있는지 확인하는 메소드
	public boolean isFull() {
		return (this.size() == this.capacity());
	}
	// 배열이 가득 차있지 않다면, 새 원소를 추가하는 메소드
	public boolean add(E anElement) {
		if(this.isFull()) {
			return false;
		}
		else {
			int positionForAdd = this.size()+1;
			this.setSize(positionForAdd);
			while((positionForAdd > 1)&&
					(anElement.compareTo(this.heap()[positionForAdd/2]) < 0)){
				this.heap()[positionForAdd] = this.heap()[positionForAdd/2];
				positionForAdd = positionForAdd / 2;
			}
			this.heap()[positionForAdd] = anElement;
			return true;
		}
	}
	// 큐가 가지고 있는 최소값을 갖는 원소를 반환
	public E min() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			return this.heap()[MinPriorityQ.HEAP_ROOT];
		}
	}
	// 큐가 가지고 있는 최소값을 갖는 원소를 반환하고 삭제
	public E removeMin() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			E rootElement = this.heap()[MinPriorityQ.HEAP_ROOT];
			this.setSize(this.size()-1);
			if(this.size() > 0) {
				E lastElement = this.heap()[this.size()+1];
				int parent = MinPriorityQ.HEAP_ROOT;
				while((parent*2) <= this.size()) {
					int smallerChild = parent*2;
					if((smallerChild < this.size()) && 
							(this.heap()[smallerChild].compareTo(this.heap()[smallerChild+1]) > 0))
					{
						smallerChild++;
					}
					if(lastElement.compareTo(this.heap()[smallerChild]) <= 0) {
						break;
					}
					this.heap()[parent] = this.heap()[smallerChild];
					parent = smallerChild;
				}
				this.heap()[parent] = lastElement;
			}
			return rootElement;
		}
	}
}
