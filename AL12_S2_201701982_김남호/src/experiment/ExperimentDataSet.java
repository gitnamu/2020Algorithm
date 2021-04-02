package experiment;

public class ExperimentDataSet {
	
	// Private instance vsariables
	private int _maxDataSize;
	
	private Integer[] _randomList;
	private Integer[] _ascendingList;
	private Integer[] _descendingList;
	
	// Getters & Setters
	public int maxDataSize() {
		return _maxDataSize;
	}
	public void setMaxDataSize(int newMaxDataSize) {
		this._maxDataSize = newMaxDataSize;
	}
	public Integer[] randomList() {
		return _randomList;
	}
	public void setRandomList(Integer[] newRandomList) {
		this._randomList = newRandomList;
	}
	public Integer[] ascendingList() {
		return _ascendingList;
	}
	public void setAscendingList(Integer[] newAscendingList) {
		this._ascendingList = newAscendingList;
	}
	public Integer[] descendingList() {
		return _descendingList;
	}
	public void setDescendingList(Integer[] newDescendingList) {
		this._descendingList = newDescendingList;
	}
	
	// Constructor
	public ExperimentDataSet(){
		this.setRandomList(null);
		this.setAscendingList(null);
		this.setDescendingList(null);
	}
	
	public ExperimentDataSet(int givenMaxDataSize){
		if(! this.generate(givenMaxDataSize)){
			this.setRandomList(null);
			this.setAscendingList(null);
			this.setDescendingList(null);
		}
	}
	
	// public method
	public boolean generate(int aMaxDataSize){
		// There are two ways to get a data set:
		// (1) Create a new "ExperimentDataSet" object with an appropriate data size;
		// (2) Call this method directly for the existing "ExerimentDataSet" object
		//	   with out creating a new "ExerimentDataSet" object.
		// Therefore, this method is used for replacing the existing data set with a new data set.
		if(aMaxDataSize <= 0){
			// If "aMaxDataSize" is not valid,
			// the current status of this object does not change.
			// And so, this method return false.
			return false;
		}
		else{
			// "aMaxDataSize" is valid.
			// So, this method generates a new data set, and returns true;
			this.setMaxDataSize(aMaxDataSize);
			this.setRandomList(DataGenerator.randomList(this.maxDataSize()));
			this.setAscendingList(DataGenerator.ascendingList(this.maxDataSize()));
			this.setDescendingList(DataGenerator.descendingList(this.maxDataSize()));
			return true;
		}
	}
	
	public Integer[] listWithOrder(ListOrder anOrder){
		if(anOrder.equals(ListOrder.Random)){
			return this.randomList();
		}
		else if(anOrder.equals(ListOrder.Ascending)){
			return this.ascendingList();
		}
		else if(anOrder.equals(ListOrder.Descending)){
			return this.descendingList();
		}
		else{
			return null;
		}
	}
	
}
