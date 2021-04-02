package list;

public class LinkedStack<E> implements Stack<E>{

	// Private instance variables
	private int _size;
	private LinkedNode<E> _top;
	
	// Getters & Setters: Encapsulation of private instance variables
	private LinkedNode<E> top(){
		return this._top;
	}
	private void setTop(LinkedNode<E> newTop) {
		this._top = newTop;
	}
	@Override
	public int size() {
		return this._size;
	}
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	// Constructor
	public LinkedStack() {
		this.setSize(0);
		this.setTop(null);
	}
	
	//public methods
	@Override
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	@Override
	public boolean isFull() {
		return false;
	}
	@Override
	public boolean push(E anElement) {
		LinkedNode<E> newTop = new LinkedNode<E>(anElement,this.top());
		this.setTop(newTop);
		this.setSize(this.size()+1);
		return true;
	}
	@Override
	public E pop() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			E removedElement = this.top().element();
			this.setTop(this.top().next());
			this.setSize(this.size()-1);
			return removedElement;
		}
	}
	
	public IteratorForLinkedStack iterator() {
		return new IteratorForLinkedStack();
	}
	
	public class IteratorForLinkedStack implements Iterator<E>{
		LinkedNode<E> _nextNode;
		
		private LinkedNode<E> nextNode(){
			return this._nextNode;
		}
		private void setNextNode(LinkedNode<E> aLinkedNode) {
			this._nextNode = aLinkedNode;
		}
		
		// Constructor
		private IteratorForLinkedStack() {
			this.setNextNode(LinkedStack.this.top());
		}
		
		// hasNext() & next()
		@Override
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		@Override
		public E next() {
			E nextElement = this.nextNode().element();
			this.setNextNode(this.nextNode().next());
			return nextElement;
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
