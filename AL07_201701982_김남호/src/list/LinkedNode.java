package list;

public class LinkedNode<T> {
	// Private instance variables
	private T _element;
	private LinkedNode<T> _next;
	
	// Setters & Getters
	public T element() {
		return this._element;
	}
	public void setElement(T newElement) {
		this._element = newElement;
	}
	public LinkedNode<T> next(){
		return this._next;
	}
	public void setNext(LinkedNode<T> newNext) {
		this._next = newNext;
	}
	
	// _element와 _next를 null로 초기화한다
	public LinkedNode() {
		this.setElement(null);
		this.setNext(null);
	}
	// _element와 _next를 주어진 값으로 초기화한다
	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {
		this.setElement(givenElement);
		this.setNext(givenNext);
	}
}
