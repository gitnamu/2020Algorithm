package sort;

public class QuickSortByPivotMid<E extends Comparable<E>> extends QuickSort<E> {
	protected int pivot(E[] aList, int left, int right) {
		return ((left + right) / 2);
	}

	public QuickSortByPivotMid(boolean givenSortingOrder) {
		super(givenSortingOrder);
	}
}
