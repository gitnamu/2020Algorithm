package list;

//List로서 필요한 공개함수를 모두 선언한 abstract class
public abstract class List<T> {
	public abstract int size();
	
	public abstract boolean isEmpty();
	public abstract boolean isFull();
	
	public abstract boolean add(T anElement);
	public abstract T removeAny();
	
	public abstract void reset();
	
	public abstract Iterator<T> listIterator();
}
