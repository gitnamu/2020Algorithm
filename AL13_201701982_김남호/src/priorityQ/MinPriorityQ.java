package priorityQ;

public abstract class MinPriorityQ<E> {
	public abstract int size();

	public abstract boolean isEmpty();

	public abstract boolean isFull();

	public abstract boolean add(E anElement);

	public abstract E main();

	public abstract E removeMin();
}
