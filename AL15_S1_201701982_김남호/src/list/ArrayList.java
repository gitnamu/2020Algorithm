package list;

public class ArrayList<T> implements List<T>{
	private static final int DEFAULT_CAPACITY = 100;

    private int _capacity;	// PointSet�� �ִ� ũ��
    private int _size;		// point�� ����
    private T[] _elements;

    public ArrayList() {
        this(ArrayList.DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int givenCapacity) {
        this._capacity = givenCapacity;
        this._size = 0;
        this._elements = (T[]) new Object[this._capacity];
    }
    
    // Protected method
    protected void setElementAt(T element, int order) {
    	
        this._elements[order] = element;
    }
    
    // protected method
    @Override
    public int capacity() {
        return this._capacity;
    }

    @Override
    public int size() {
        return this._size;
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
        return this._elements[order];
    }

    @Override
    public boolean add(T elementForAdd) {
    	// �迭�� �� ���� �����Ѵ�.
    	// ������ ������ ���� ���Կ� �����ϸ� false�� ��´�.
        if(!isFull()){
            this._elements[this.size()] = elementForAdd;
            this._size++;
            return true;
        }
        return false;
    }
}
