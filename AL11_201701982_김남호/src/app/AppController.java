package app;

import experiment.ExperimentManagerForThreeSorts;
import experiment.ListOrder;

public class AppController {
	
	// Private instance variables
	private ExperimentManagerForThreeSorts _managerForThreeSorts;

	// Getter & Setter
	private ExperimentManagerForThreeSorts managerForThreeSorts() {
		return this._managerForThreeSorts;
	}
	private void setManagerForThreeSorts(ExperimentManagerForThreeSorts newExperimentManager) {
		this._managerForThreeSorts = newExperimentManager;
	}
	
	// Private methods
	private void showTableTitle(ListOrder anOrder) {
		AppView.outputLine("> " + anOrder.orderName() + "데이터에 대한 측정 : ");
	}

	private void showTableHeadForThreeSorts() {
		AppView.outputLine(String.format("%8S", " ") + String.format("%26s", "<Insertion Sort>")
				+ String.format("%26s", "  <Quick Sort>  ") + String.format("%26s", "  <Heap Sort>  "));
		AppView.outputLine(String.format("%7s", "데이터 크기") + String.format("%26s", "Measure (Estimate)")
				+ String.format("%26s", "Measure (Estimate)") + String.format("%26s", "Measure (Estimate)"));
	}

	private void showTableContentForThreeSorts() {
		for (int iteration = 0; iteration < this.managerForThreeSorts().parameterSetForMeasurement()
				.numberOfIteration(); iteration++) {
			int startingSize = this.managerForThreeSorts().parameterSetForMeasurement().startingSize();
			int incrementSize = this.managerForThreeSorts().parameterSetForMeasurement().incrementSize();
			int sortingSize = startingSize + (incrementSize * iteration);
			AppView.outputLine("[" + String.format("%7d", sortingSize) + "]"
					+ String.format("%15d", this.managerForThreeSorts().measurementForInsertionSortAt(iteration)) + " ("
					+ String.format("%8d", this.managerForThreeSorts().estimationForInsertionSortAt(iteration)) + ")"
					+ String.format("%15d", this.managerForThreeSorts().measurementForQuickSortAt(iteration)) + " ("
					+ String.format("%8d", this.managerForThreeSorts().estimationForQuickSortAt(iteration)) + ")"
					+ String.format("%15d", this.managerForThreeSorts().measurementForHeapSortAt(iteration)) + " ("
					+ String.format("%8d", this.managerForThreeSorts().estimationForHeapSortAt(iteration)) + ")");
		}
	}

	private void showResultTableForThreeSorts(ListOrder anOrder) {
		this.showTableTitle(anOrder);
		this.showTableHeadForThreeSorts();
		this.showTableContentForThreeSorts();
		AppView.outputLine("");
	}

	private void measureAndShow(ListOrder anOrder) {
		this.managerForThreeSorts().performExperiment(anOrder);
		this.showResultTableForThreeSorts(anOrder);
	}

	public void run() {
		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 시작합니다 >>>");
		AppView.outputLine("");
		{
			AppView.outputLine(">> 3 가지 정렬의 성능 비교 : 삽입, 퀵, 힢 <<");
			this.setManagerForThreeSorts(new ExperimentManagerForThreeSorts());
			this.managerForThreeSorts().prepareExperiment(null);

			this.measureAndShow(ListOrder.Random);
			this.measureAndShow(ListOrder.Ascending);
			this.measureAndShow(ListOrder.Descending);
		}

		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 종료합니다 >>>");
	}
}
