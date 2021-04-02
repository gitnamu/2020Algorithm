package AL04_201701982_김남호;

public class LinkedNode<T> {
	private T _element;
	private LinkedNode<T> _next;
	
	//_element의 getter
	public T element() {
		return this._element;
	}
	//_element의 setter
	public void setElement(T newElement) {
		this._element = newElement;
	}
	//_next의 getter
	public LinkedNode<T> next(){
		return this._next;
	}
	//_next의 setter
	public void setNext(LinkedNode<T> newNext) {
		this._next = newNext;
	}
	
	//_element와 _next를 null로 초기화한다
	public LinkedNode() {
		this.setElement(null);
		this.setNext(null);
	}
	//_element와 _next를 주어진 값으로 초기화한다
	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {
		this.setElement(givenElement);
		this.setNext(givenNext);
	}
}
