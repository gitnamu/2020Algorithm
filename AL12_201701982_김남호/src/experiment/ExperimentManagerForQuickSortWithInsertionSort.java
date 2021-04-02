package experiment;

import app.AppView;
import sort.QuickSortByPivotRandom;
import sort.QuickSortWithInsertionSort;

public class ExperimentManagerForQuickSortWithInsertionSort extends ExperimentManager {
	
	// Only for controlling debugging messages
	private static final boolean DEBUG_MODE = false;

	private static void showDebugMessage(String aMessage) {
		if (ExperimentManagerForQuickSortWithInsertionSort.DEBUG_MODE) {
			AppView.outputDebugMessage(aMessage);
		}
	}
	
	// Constants for the experiment parameters
	protected static final int DEFAULT_NUMBER_OF_ITERATION = 10;
	protected static final int DEFAULT_INCREMENT_SIZE = 10000;
	protected static final int DEFAULT_STARTING_SIZE = DEFAULT_INCREMENT_SIZE;

	protected static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 10;

	// Constants for Sort Objects
	private static final QuickSortByPivotRandom<Integer> QuickSortByPivotRandom = new QuickSortByPivotRandom<Integer>(true);
	private static final QuickSortWithInsertionSort<Integer> QuickSortWithInsertionSort = new QuickSortWithInsertionSort<Integer>(true);
	
	// Constants for controlling the size of Insertion Sort
	private static final int DEFAULT_INSERTION_SORT_STARTING_SIZE = 10;
	private static final int DEFAULT_INSERTION_SORT_INCREMENT_SIZE = 10;
	private static final int DEFAULT_INSERTION_SORT_NUMBER_OF_ITERATION = 9;

	// Private instance variables
	private ParameterSetForIteration _parameterSetForMaxSizeOfInsertionSort;

	private long[] _measurementForQuickSortByPivotRandom;
	private long[][] _measurementsForQuickSortWithInsertionSort;

	// Getters & Setters for Measurement results
	public ParameterSetForIteration parameterSetForMaxSizeOfInsertionSort() {
		return this._parameterSetForMaxSizeOfInsertionSort;
	}

	public void setParameterSetFormaxSizeOfInsertionSort(ParameterSetForIteration newParameterSet) {
		this._parameterSetForMaxSizeOfInsertionSort = newParameterSet;
	}

	private long[] measurementForQuickSortByPivotRandom() {
		return this._measurementForQuickSortByPivotRandom;
	}

	private void setMeasurementForQuickSortByPivotRandom(long[] newMeasurement) {
		this._measurementForQuickSortByPivotRandom = newMeasurement;
	}

	private long[][] measurementsForQuickSortWithInsertionSort() {
		return this._measurementsForQuickSortWithInsertionSort;
	}

	private void setMeasurementsForQuickSortWithInsertionSort(long[][] newMeasurements) {
		this._measurementsForQuickSortWithInsertionSort = newMeasurements;
	}

	private void setMeasurementForQuickSortWithInsertionSort(long[] newMeasurement, int measurementIndex) {
		this.measurementsForQuickSortWithInsertionSort()[measurementIndex] = newMeasurement;
	}
	
	// Protected method
	@Override
	protected void performMeasuring(ListOrder anOrder) {
		// TODO Auto-generated method stub
		Integer[] list = this.dataSet().listWithOrder(anOrder);

		this.setMeasurementsForQuickSortWithInsertionSort(
				new long[this.parameterSetForMaxSizeOfInsertionSort().numberOfIteration()][]);

		this.setMeasurementForQuickSortByPivotRandom(this.experiment()
				.durationOfSort(ExperimentManagerForQuickSortWithInsertionSort.QuickSortByPivotRandom, list));
		ExperimentManagerForQuickSortWithInsertionSort.showDebugMessage("[Debug] end of QuickSortByPivotRandom\n");
		for (int iteration = 0; iteration < this.parameterSetForMaxSizeOfInsertionSort()
				.numberOfIteration(); iteration++) {
			int size = this.parameterSetForMaxSizeOfInsertionSort().startingSize()
					+ this.parameterSetForMaxSizeOfInsertionSort().incrementSize() * iteration;
			QuickSortWithInsertionSort.setMaxSizeForInsertionSort(size);
			this.setMeasurementForQuickSortWithInsertionSort(
					this.experiment().durationOfSort(
							ExperimentManagerForQuickSortWithInsertionSort.QuickSortWithInsertionSort, list),
					iteration);
			ExperimentManagerForQuickSortWithInsertionSort
					.showDebugMessage("Debug] end of QuickSortWithInsertionSort" + size + "\n");
		}
	}

	// Constructor
	public ExperimentManagerForQuickSortWithInsertionSort() {
		this.setParameterSetForMeasurement(new ParameterSetForMeasurement(DEFAULT_STARTING_SIZE,
				DEFAULT_NUMBER_OF_ITERATION, DEFAULT_INCREMENT_SIZE, DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT));
		this.setParameterSetFormaxSizeOfInsertionSort(new ParameterSetForIteration(DEFAULT_INSERTION_SORT_STARTING_SIZE,
				DEFAULT_INSERTION_SORT_NUMBER_OF_ITERATION, DEFAULT_INSERTION_SORT_INCREMENT_SIZE));
	}

	// public method
	public long measurementForQuickSortByPivotRandomAt(int anIndex) {
		return this.measurementForQuickSortByPivotRandom()[anIndex];
	}

	public long measurementForQuickSortWithInsertionSortAt(int aMeasurementIndex, int anElementIndex) {
		return this.measurementsForQuickSortWithInsertionSort()[aMeasurementIndex][anElementIndex];
	}

	@Override
	public void performExperiment(ListOrder anOrder) {
		// TODO Auto-generated method stub
		this.performMeasuring(anOrder);
	}
}
