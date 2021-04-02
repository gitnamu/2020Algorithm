package experiment;

public class ParameterSetForMeasurement extends ParameterSetForIteration {
	// Constants for parameter
	private static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 1;

	// Instants Variables
	private int _numberOfRepetitionOfSingleSort;
	
	// getters & setters
	public int numberOfRepetitionOfSingleSort() {
		return this._numberOfRepetitionOfSingleSort;
	}

	public void setNumberOfRepetitionOfSingleSort(int newNumberOfRepetitionOfSingleSort) {
		this._numberOfRepetitionOfSingleSort = newNumberOfRepetitionOfSingleSort;
	}
	
	// Constructor
	public ParameterSetForMeasurement() {
		super();
		this.setNumberOfRepetitionOfSingleSort(DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT);
	}

	public ParameterSetForMeasurement(int givenStartingSize, int givenNumberOfIteration, int givenIncrementSize,
			int givenNumberOfRepetitionOfSingleSort) {
		super(givenStartingSize, givenNumberOfIteration, givenIncrementSize);
		this.setNumberOfRepetitionOfSingleSort(givenNumberOfRepetitionOfSingleSort);
	}
}
