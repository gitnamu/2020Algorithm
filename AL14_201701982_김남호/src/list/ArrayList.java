package list;

public class ArrayList<T> implements List<T>{
	private static final int DEFAULT_CAPACITY = 100;

    private int capacity;	// PointSet의 최대 크기
    private int size;		// point의 개수
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
    	// order가 유효한 원소를 가리키고 있는지를 검사한다(0 <= order < size())
        if (order >= 0 || order < this.size()) {
            return true;
        }
        return false;
    }

    @Override
    public T elementAt(int order) {
    	// order의 위치의 원소를 얻는다. order가 유효하지 않으면 null을 얻는다.
        return this.elements[order];
    }

    @Override
    public boolean add(T elementForAdd) {
    	// 배열의 맨 끝에 삽입한다.
    	// 삽입할 공간이 없어 삽입에 실패하면 false를 얻는다.
        if(!isFull()){
            this.elements[this.size()] = elementForAdd;
            this.size++;
            return true;
        }
        return false;
    }
}
