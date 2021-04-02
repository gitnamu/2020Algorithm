package experiment;

import app.AppView;
import sort.HeapSort;
import sort.InsertionSort;
import sort.QuickSort;
import sort.QuickSortByPivotLeft;

public class ExperimentManagerForThreeSorts extends ExperimentManager {
	
	// Only for controlling debugging messages
	private static final boolean DEBUG_MODE = false;

	private static void showDebugMessage(String aMessage) {
		if (ExperimentManagerForThreeSorts.DEBUG_MODE) {
			AppView.outputDebugMessage(aMessage);
		}
	}
	// Constants
	private static final InsertionSort<Integer> InsertionSort = new InsertionSort<Integer>(true);
	private static final QuickSort<Integer> QuickSort = new QuickSortByPivotLeft<Integer>(true);
	private static final HeapSort<Integer> HeapSort = new HeapSort<Integer>(true);

	// Private instance variables
	private long[] _measurementForInsertionSort;
	private long[] _measurementForQuickSort;
	private long[] _measurementForHeapSort;

	private long[] _estimationForInsertionSort;
	private long[] _estimationForQuickSort;
	private long[] _estimationForHeapSort;

	// Getters & Setters for Measurement results
	private long[] measurementForInsertionSort() {
		return this._measurementForInsertionSort;
	}

	private void setMeasurementForInsertionSort(long[] newMeasurement) {
		this._measurementForInsertionSort = newMeasurement;
	}

	private long[] measurementForQuickSort() {
		return this._measurementForQuickSort;
	}

	private void setMeasurementForQuickSort(long[] newMeasurement) {
		this._measurementForQuickSort = newMeasurement;
	}

	private long[] measurementForHeapSort() {
		return this._measurementForHeapSort;
	}

	private void setMeasurementForHeapSort(long[] newMeasurement) {
		this._measurementForHeapSort = newMeasurement;
	}

	// Getters & Setters for estimation results
	private long[] estimationForInsertionSort() {
		return this._estimationForInsertionSort;
	}

	private void setEstimationForInsertionSort(long[] newEstimation) {
		this._estimationForInsertionSort = newEstimation;
	}

	private long[] estimationForQuickSort() {
		return this._estimationForQuickSort;
	}

	private void setEstimationForQuickSort(long[] newEstimation) {
		this._estimationForQuickSort = newEstimation;
	}

	private long[] estimationForHeapSort() {
		return this._estimationForHeapSort;
	}

	private void setEstimationForHeapSort(long[] newMeasurement) {
		this._estimationForHeapSort = newMeasurement;
	}

	// Private method
	@Override
	protected void performMeasuring(ListOrder anOrder) {
		Integer[] experimentList = this.dataSet().listWithOrder(anOrder);

		this.setMeasurementForInsertionSort(
				this.experiment().durationOfSort(ExperimentManagerForThreeSorts.InsertionSort, experimentList));
		ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of Insertion Sort\n");

		this.setMeasurementForQuickSort(
				this.experiment().durationOfSort(ExperimentManagerForThreeSorts.QuickSort, experimentList));
		ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of Quick Sort\n");

		this.setMeasurementForHeapSort(
				this.experiment().durationOfSort(ExperimentManagerForThreeSorts.HeapSort, experimentList));
		ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of Heap Sort\n");
	}

	private void estimateForRandomList() {
		this.setEstimationForInsertionSort(
				Estimation.estimateByQuadratic(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));
		this.setEstimationForQuickSort(
				Estimation.estimateByNLogN(this.measurementForQuickSort(), this.parameterSetForMeasurement()));
		this.setEstimationForHeapSort(
				Estimation.estimateByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));
	}

	private void estimateForAscendingList() {
		this.setEstimationForInsertionSort(
				Estimation.estimateByLinear(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));
		this.setEstimationForQuickSort(
				Estimation.estimateByQuadratic(this.measurementForQuickSort(), this.parameterSetForMeasurement()));
		this.setEstimationForHeapSort(
				Estimation.estimateByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));
	}

	private void estimateForDescendingList() {
		this.setEstimationForInsertionSort(
				Estimation.estimateByQuadratic(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));
		this.setEstimationForQuickSort(
				Estimation.estimateByQuadratic(this.measurementForQuickSort(), this.parameterSetForMeasurement()));
		this.setEstimationForHeapSort(
				Estimation.estimateByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));
	}

	@Override
	public void performExperiment(ListOrder anOrder) {
		this.performMeasuring(anOrder);
		if (anOrder.equals(ListOrder.Random)) {
			this.estimateForRandomList();
		} else if (anOrder.equals(ListOrder.Ascending)) {
			this.estimateForAscendingList();
		} else {
			this.estimateForDescendingList();
		}
	}

	// Public method
	public long measurementForInsertionSortAt(int anIndex) {
		return this.measurementForInsertionSort()[anIndex];
	}

	public long measurementForQuickSortAt(int anIndex) {
		return this.measurementForQuickSort()[anIndex];
	}

	public long measurementForHeapSortAt(int anIndex) {
		return this.measurementForHeapSort()[anIndex];
	}

	public long estimationForInsertionSortAt(int anIndex) {
		return this.estimationForInsertionSort()[anIndex];
	}

	public long estimationForQuickSortAt(int anIndex) {
		return this.estimationForQuickSort()[anIndex];
	}

	public long estimationForHeapSortAt(int anIndex) {
		return this.estimationForHeapSort()[anIndex];
	}
}
