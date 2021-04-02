package sort;

public class QuickSortWithInsertionSort<E extends Comparable<E>> extends QuickSortByPivotRandom<E> {
	
	private static final int DEFAULT_MAX_SIZE_FOR_INSERTION_SORT = 20;
	private int _maxSizeForInsertionSort;
	
	// getter/setter
	public int maxSizeForInsertionSort() {
		return _maxSizeForInsertionSort;
	}

	public void setMaxSizeForInsertionSort(int newMaxSizeForInsertionSort) {
		this._maxSizeForInsertionSort = newMaxSizeForInsertionSort;
	}

	private boolean insertionSort(E[] aList, int left, int right) {
		// Insertion direction is from left to right.
		// The direction is opposite compared to the usual "Insertion Sort".
		// This is because aList[right+1] id the biggest
		// among all the elements in the sorting interval [left, right]
		// So, the aList[right+1] can play a role of a sentinel element with INFINITE value.
		for (int i = (right - 1); i >= left; i--) {
			E insertedElement = aList[i];
			int j = i + 1;
			while (this.compare(aList[j], insertedElement) < 0) {
				aList[j - 1] = aList[j];
				j++;
			}
			aList[j - 1] = insertedElement;
		}
		return true;
	}

	@Override
	protected void quickSortRecursively(E[] aList, int left, int right) {
		int currentSize = right - left;
		if (currentSize > 0) {
			if (currentSize <= this.maxSizeForInsertionSort()) {
				this.insertionSort(aList, left, right);
			} else {
				int mid = partition(aList, left, right);	// DIVIDE
				quickSortRecursively(aList, left, mid);		// CONQUER
				quickSortRecursively(aList, mid + 1, right);// CONQUER
			}
		}
	}
	
	// Constructor
	public QuickSortWithInsertionSort(boolean givenSortingOrder) {
		super(givenSortingOrder);
		this.setMaxSizeForInsertionSort(DEFAULT_MAX_SIZE_FOR_INSERTION_SORT);
	}
}
