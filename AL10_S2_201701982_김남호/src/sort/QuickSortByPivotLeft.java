package sort;

public class QuickSortByPivotLeft<E extends Comparable<E>> extends QuickSort<E> {
	
	@Override
	protected int pivot(E[] aList, int left, int right) {
		return left;
	}
	
	// Constructor
	public QuickSortByPivotLeft(boolean givenSortingOrder) {
		super(givenSortingOrder);
	}
}
