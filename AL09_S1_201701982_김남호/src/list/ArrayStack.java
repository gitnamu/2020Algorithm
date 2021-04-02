package list;

public class ArrayStack<E> implements Stack<E> {
	
	// Constants
	private static final int DEFAULT_CAPACITY = 100;
	
	// Private instance variables
	private int		_capacity;
	private int 	_top;
	private E[]		_elements;
	
	// Getters & Setters
	private int capacity() {
		return this._capacity;
	}
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	private int top() {
		return this._top;
	}
	private void setTop(int newTop) {
		this._top = newTop;
	}
	protected E[] elements() {
		return this._elements;
	}
	private void setElements(E[] newElements) {
		this._elements = newElements;
	}
	
	// Constructors
	public ArrayStack() {
		this(ArrayStack.DEFAULT_CAPACITY);
	}
	@SuppressWarnings("unchecked")
	public ArrayStack(int givenCapacity) {
		this.setCapacity(givenCapacity);
		this.setElements((E[]) new Object[this.capacity()]);
		this.setTop(-1);
	}
	
	@Override
	public void reset() {
		this.setTop(-1);
	}
	
	public int size() {
		return this.top()+1;
	}
	@Override
	public boolean isEmpty() {
		return (this.top() == -1);
	}
	@Override
	public boolean isFull() {
		return (this.size() == this.capacity());
	}
	@Override
	public boolean push(E anElement) {
		if (this.isFull()) return false;
		else {
			this.elements()[this.size()] = anElement;
			this.setTop(this.size());
	        return true;
	      }
	}
	@Override
	public E pop() {
		  if (this.isEmpty()) return null;
		  else {
			  E removedElement = this.elements()[this.top()];
			  this.elements()[this.top()] = null;
			  this.setTop(this.top() - 1);
			  return removedElement;
		  }
	}
	@Override
	public E peek() {
		if(!this.isEmpty()) { 
			return this.elements()[this.top()];
		}
		return null;
	}
}
