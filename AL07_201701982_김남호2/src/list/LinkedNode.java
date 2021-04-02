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
	
	// _element�� _next�� null�� �ʱ�ȭ�Ѵ�
	public LinkedNode() {
		this.setElement(null);
		this.setNext(null);
	}
	// _element�� _next�� �־��� ������ �ʱ�ȭ�Ѵ�
	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {
		this.setElement(givenElement);
		this.setNext(givenNext);
	}
}
