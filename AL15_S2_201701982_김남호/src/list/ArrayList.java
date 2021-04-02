package list;

public class ArrayList<T> implements List<T>{
	private static final int DEFAULT_CAPACITY = 100;

    private int _capacity;	// PointSet의 최대 크기
    private int _size;		// point의 개수
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
    	// order가 유효한 원소를 가리키고 있는지를 검사한다(0 <= order < size())
        if (order >= 0 || order < this.size()) {
            return true;
        }
        return false;
    }

    @Override
    public T elementAt(int order) {
    	// order의 위치의 원소를 얻는다. order가 유효하지 않으면 null을 얻는다.
        return this._elements[order];
    }

    @Override
    public boolean add(T elementForAdd) {
    	// 배열의 맨 끝에 삽입한다.
    	// 삽입할 공간이 없어 삽입에 실패하면 false를 얻는다.
        if(!isFull()){
            this._elements[this.size()] = elementForAdd;
            this._size++;
            return true;
        }
        return false;
    }
}
