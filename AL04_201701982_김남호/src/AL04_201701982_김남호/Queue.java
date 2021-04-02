package AL04_201701982_�賲ȣ;

public interface Queue<T> {
	public void reset();
	
	public int size();
	
	public boolean isEmpty();
	public boolean isFull();
	
	public boolean add(T anElement);
	public T remove();
}
