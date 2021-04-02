package sort;

public abstract class Sort<E extends Comparable<E>>{
	
	// private instance variable
	private boolean _nonDecreasingOrder;
		// This id private
		// Only used in "compare()" inside this class
		// Any inherited class do not need to access directly this information.
		// It uses indirectly via "compare()"
		// From the outside of the class,
		// only the getter/setter of this instance varialble should be used.
	
	// Getters/Setters
	public boolean nonDecreasingOrder() {
		return this._nonDecreasingOrder;
	}
	public void setNonDecreasingOrder(boolean newNonDecreasingOrder) {
		this._nonDecreasingOrder = newNonDecreasingOrder;
	}
	
	// protected methods
	protected void swap(E[] aList, int i, int j) {
		E tempElement = aList[i];
		aList[i] = aList[j];
		aList[j] = tempElement;
	}
	
	protected int compare(E anElement, E theOtherElement) {
		if(this.nonDecreasingOrder()) {
			return anElement.compareTo(theOtherElement);
		}
		else {
			return -anElement.compareTo(theOtherElement);
		}
	}
	
	// Constructor
	public Sort(boolean givenSortingOrder) {
		this.setNonDecreasingOrder(givenSortingOrder);
	}
	
	public abstract boolean sort(E[] aList);
}
