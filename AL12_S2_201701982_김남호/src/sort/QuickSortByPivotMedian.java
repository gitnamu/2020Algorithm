package sort;

public class QuickSortByPivotMedian<E extends Comparable<E>> extends QuickSort<E> {
	
	// Constructor
	public QuickSortByPivotMedian(boolean givenSortingOrder) {
		super(givenSortingOrder);
	}
	
	// protected method
	@Override
	protected int pivot(E[] aList, int left, int right) {
		if ((right - left) < 3) {
			return left;
		}
		int mid = (left + right) / 2;
		if (this.compare(aList[left], aList[mid]) < 0) {
			if (this.compare(aList[mid], aList[right]) < 0) {
				return mid;
			}
			else {	// (left < mid) && (right < mid)
				if(this.compare(aList[left], aList[right]) < 0) {	// left < right < mid
					return right;
				}
				else {	// right < left < mid
					return left;
				}
			}
		}
		else {	// mid < left
			if(this.compare(aList[left], aList[right]) < 0) {	// mid < left < right
				return left;
			}
			else {	// (mid < left) && (right < left)
				if(this.compare(aList[mid], aList[right]) < 0) {	// mid < right < left
					return right;
				}
				else {	// right < mid < left
					return mid;
				}
			}
		}
	}
}

