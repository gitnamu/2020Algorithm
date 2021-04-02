package AL04_201701982_�賲ȣ;

public class LinkedNode<T> {
	private T _element;
	private LinkedNode<T> _next;
	
	//_element�� getter
	public T element() {
		return this._element;
	}
	//_element�� setter
	public void setElement(T newElement) {
		this._element = newElement;
	}
	//_next�� getter
	public LinkedNode<T> next(){
		return this._next;
	}
	//_next�� setter
	public void setNext(LinkedNode<T> newNext) {
		this._next = newNext;
	}
	
	//_element�� _next�� null�� �ʱ�ȭ�Ѵ�
	public LinkedNode() {
		this.setElement(null);
		this.setNext(null);
	}
	//_element�� _next�� �־��� ������ �ʱ�ȭ�Ѵ�
	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {
		this.setElement(givenElement);
		this.setNext(givenNext);
	}
}
