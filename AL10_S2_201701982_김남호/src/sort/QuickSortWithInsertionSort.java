package sort;

public class QuickSortWithInsertionSort<E extends Comparable<E>> extends QuickSortByPivotRandom<E> {
	
	public QuickSortWithInsertionSort(boolean givenSortingOrder) {
		super(givenSortingOrder);
	}
	
	private static final int MAX_SIZE_FOR_INSERTION_SORT = 20;
	
	private boolean insetrionSort(E[] aList, int left, int right) {
		// Insertion direction is from left to right.
		// The direction is opposite compared to the usual "Insertion Sort".
		// This is because aList[right+1] is the biggest
		// among all the elements in the sorting interval [left, right]
		// So, the aList[right+1] can play a role of a sentinel element with INFINITE value.
		
		for(int i=(right-1); i >= left; i--) {
			E insertedElement = aList[i];
			int j = i + 1;
			while(this.compare(aList[j], insertedElement) < 0) {
				aList[j-1] = aList[j];
				j++;
			}
			aList[j-1] = insertedElement;
		}
		return true;
	}
	
	@Override
	protected void quickSortRecursively(E[] aList, int left, int right) {
		int currentSize = right - left;
		if(currentSize > 0) {
			if(currentSize <= QuickSortWithInsertionSort.MAX_SIZE_FOR_INSERTION_SORT) {
				this.insetrionSort(aList, left, right);
			}
			else {
				int mid = partition(aList,left,right);		//DIVIDE
				quickSortRecursively(aList,left,mid-1);		//CONQUER
				quickSortRecursively(aList,mid+1,right);		//CONQUER
			}
		}
	}
}
