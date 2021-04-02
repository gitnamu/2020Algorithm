package controller;

import experiment.ExperimentManager;
import experiment.ParameterSet;
import model.FindClosestPair;
import model.PairOfPoints;
import model.Point;
import model.PointSet;
import view.AppView;

public class AppController {
	
	// Constants
	private static final int STARTING_SIZE = 1000 ;
	private static final int NUMBER_OF_STEPS = 10 ;
	private static final int INCREMENT_SIZE = 1000 ;
	private static final int NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION = 50 ;
	
	// Private instance Variables
	private ExperimentManager _experimentManager;
	private ParameterSet _parameterSet;

    // Private Getters / Setters
    public ExperimentManager experimentManager() {
        return this._experimentManager;
    }

    public void setExperimentManager(ExperimentManager experimentManager) {
        this._experimentManager = experimentManager;
    }

    public ParameterSet parameterSet() {
        return _parameterSet;
    }

    public void setParameterSet(ParameterSet parameterSet) {
        this._parameterSet = parameterSet;
    }

    // Constructor
    public AppController() {
    	// ���迡 ����� Parameter Set �� �����Ѵ�.
    	this.setParameterSet (
    		new ParameterSet (
    			AppController.NUMBER_OF_STEPS,
    			AppController.STARTING_SIZE,
    			AppController.INCREMENT_SIZE,
    			AppController.NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION
    			)
    	);
    	// �־��� ParameterSet ���� ������ ��� ExperiementManager ��ü�� �����Ѵ�.
    	this.setExperimentManager (
    		new ExperimentManager ( this.parameterSet() ) ) ;
    }

    // Private methods
    private void showMeasurement (long[][] measurement, String measurementTitle) {
    	AppView.outputLine ("") ;
    	AppView.outputLine ("<" + measurementTitle + " ����> (����: ����ũ�� ��)") ;
    	AppView.outputLine (
    		String.format ( "%8s %20s %20s",
    			"Size ", "Compare-All-Pairs", "Divide-And-Conquer" )
    		);
    	int size = this.parameterSet().startingSize() ;
    	for ( int step = 0 ; step < this.parameterSet().numberOfSteps() ; step++ ) {
    		long durationByComparingAllPairs = measurement[0][step] ;
    		long durationByDivideAndConquer = measurement[1][step] ;
    	AppView.outputLine (
    		String.format ( "[%6d]%20d %20d",
    			size, durationByComparingAllPairs, durationByDivideAndConquer )
    	);
    	size += this.parameterSet().incrementSize() ;
    }
}
    
    public void run() {
    	AppView.outputLine ("") ;
    	AppView.outputLine("<<< �ִܰŸ� �� ã�� ���� ������ �����մϴ� >>>");
    	
    	if ( this.experimentManager().closestPairAlgorithmsAreCorrect() ) {
    		long[][] measurementOfSingleSolve =
    			this.experimentManager().measureDurationOfSingleSolve();
    		measurementOfSingleSolve =
    			this.experimentManager().measureDurationOfSingleSolve();
    		this.showMeasurement(measurementOfSingleSolve, "�ѹ� ����");
    	
    		long[][] measurementOfAverageOfSingleSolves =
    			this.experimentManager().measureAverageDurationOfSingleSolves();
    		this.showMeasurement(measurementOfAverageOfSingleSolves, "�ݺ� ������ ���");
    	
    		long[][] measurementOfMinOfSingleSolves =
    			this.experimentManager().measureMinDurationAmongSingleSolves();
    		this.showMeasurement(measurementOfMinOfSingleSolves, "�ݺ� ���� �� �ּ� �ð�");
    	}
    	else {
    		AppView.outputLine ("! ���� �����Ǿ� �ִ� �˰����� �ùٷ� �۵����� �ʽ��ϴ�.");
    	}
    	AppView.outputLine ("");
    	AppView.outputLine ("<<< �ִܰŸ� �� ã�� ���� ������ �����մϴ� >>>");
    }
}
