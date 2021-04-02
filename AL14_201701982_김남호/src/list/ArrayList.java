package list;

public class ArrayList<T> implements List<T>{
	private static final int DEFAULT_CAPACITY = 100;

    private int capacity;	// PointSet�� �ִ� ũ��
    private int size;		// point�� ����
    private T[] elements;

    public ArrayList() {
        this(ArrayList.DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int givenCapacity) {
        this.capacity = givenCapacity;
        this.size = 0;
        this.elements = (T[]) new Object[this.capacity];
    }
    
    // Protected method
    protected void setElementAt(T element, int order) {
    	
        this.elements[order] = element;
    }
    
    // protected method
    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean isFull() {
        return this.size() == this.capacity();
    }

    @Override
    public boolean orderIsValid(int order) {
    	// order�� ��ȿ�� ���Ҹ� ����Ű�� �ִ����� �˻��Ѵ�(0 <= order < size())
        if (order >= 0 || order < this.size()) {
            return true;
        }
        return false;
    }

    @Override
    public T elementAt(int order) {
    	// order�� ��ġ�� ���Ҹ� ��´�. order�� ��ȿ���� ������ null�� ��´�.
        return this.elements[order];
    }

    @Override
    public boolean add(T elementForAdd) {
    	// �迭�� �� ���� �����Ѵ�.
    	// ������ ������ ���� ���Կ� �����ϸ� false�� ��´�.
        if(!isFull()){
            this.elements[this.size()] = elementForAdd;
            this.size++;
            return true;
        }
        return false;
    }
}
