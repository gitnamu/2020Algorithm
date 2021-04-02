package experiment;

import app.AppView;
import sort.Sort;

public class Experiment {
	
	// Only for controlling debugging messages
	private static final boolean DEBUG_MODE = false;
	
	// Private static methods
	private static void showDebugMessage(String aMessage) {
		// This methods copies a part with "givenSize" from "aList[]"
		// so that the copied list is from aList[0] to aList[givenSize-1].
		if (Experiment.DEBUG_MODE) {
			AppView.outputDebugMessage(aMessage);
		}
	} 

	private static Integer[] copyListOfGivenSize(Integer[] aList, int givenSize) {
		if (givenSize <= aList.length) {
			Integer[] copiedList = new Integer[givenSize];
			for (int i = 0; i < givenSize; i++) {
				copiedList[i] = aList[i];
				// The List "copiedList[]" itself is a copy.
				// But, any "Integer" object of the list is not a copy
				// That is both "copiedList[]" and "aList[]" share the same "Integer" objects.
				// However, any sorting will not change any "integer" objects.
				// but changes only the location of each "Integer" objects inside "copiedList[]"
				// The important thing is that any sorting does NEVER change the original "aList[]".
			}
			return copiedList;
		}
		return null;
	}

	public static long durationOfSingleSort(Sort<Integer> aSort, Integer[] aList) {
		// This method measures the running time of the given "aSort" with the list "aList[]".
		Timer.start();
		{
			aSort.sort(aList);
		}
		Timer.stop();
		return Timer.duration();
	}
	
	// Private instance
	private ParameterSetForMeasurement _parameterSetForMeasurement;
	
	// Getters & Setters
	private ParameterSetForMeasurement parameterSetForMeasurement() {
		return this._parameterSetForMeasurement;
	}

	private void setParameterSetForMeasurement(ParameterSetForMeasurement newParameterSet) {
		this._parameterSetForMeasurement = newParameterSet;
	}
	
	// Constructor
	public Experiment(ParameterSetForMeasurement givenParameterSet) {
		this.setParameterSetForMeasurement(givenParameterSet);
	}	
	
	// public method
	public long[] durationOfSort(Sort<Integer> sort, Integer[] list) {	
		long[] durations = new long[this.parameterSetForMeasurement().numberOfIteration()];	
		for (int i = 0, size = this.parameterSetForMeasurement().startingSize(); i < this.parameterSetForMeasurement()
				.numberOfIteration(); i++, size += this.parameterSetForMeasurement().incrementSize()) {
			long sumOfDurations = 0;
			for (int repeated = 0; repeated < this.parameterSetForMeasurement()
					.numberOfRepetitionOfSingleSort(); repeated++) {	
				Integer[] currentList = Experiment.copyListOfGivenSize(list, size);	
				sumOfDurations += Experiment.durationOfSingleSort(sort, currentList);
			}
			durations[i] = sumOfDurations / this.parameterSetForMeasurement().numberOfRepetitionOfSingleSort();
			
			Experiment.showDebugMessage("[Debug.Experiment] " + sort.toString() + " (" + i + ")\n");
		}
		return durations;
	}
}
