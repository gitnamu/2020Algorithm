package controller;

import experiment.ExperimentManager;
import experiment.ParameterSet;
import view.AppView;

public class AppController {
	
	// Constants
	private static final int STARTING_SIZE = 1000 ;
	private static final int NUMBER_OF_STEPS = 10 ;
	private static final int INCREMENT_SIZE = 1000 ;
	private static final int NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION = 50 ;
	private static final int MIN_RECURSION_SIZE = 300;
	
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
    	// 실험에 사용할 Parameter Set 을 설정한다.
    	this.setParameterSet (
    		new ParameterSet (
    			AppController.NUMBER_OF_STEPS,
    			AppController.STARTING_SIZE,
    			AppController.INCREMENT_SIZE,
    			AppController.NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION,
    			AppController.MIN_RECURSION_SIZE
    			)
    	);
    	// 주어진 ParameterSet 으로 실험을 운영할 ExperiementManager 객체를 생성한다.
    	this.setExperimentManager (
    		new ExperimentManager ( this.parameterSet() ) ) ;
    }

    private void showMeasurement (long[][] measurement, String measurementTitle) {
    	AppView.outputLine ("") ;
    	AppView.outputLine ("<" + measurementTitle + " 측정> (단위: 마이크로 초)") ;
    	AppView.outputLine (
    		String.format ( "%8s %20s %20s %20s %12s",
    			"Size ", 
    			"Compare-All-Pairs", 
    			"Divide-And-Conquer",
    			"Hybrid (MinRS: " + MIN_RECURSION_SIZE + ")",
    			"Saving (%)"
    			)
    		);
    	int size = this.parameterSet().startingSize() ;
    	for ( int step = 0 ; step < this.parameterSet().numberOfSteps() ; step++ ) {
    		long durationByComparingAllPairs = measurement[0][step] ;
    		long durationByDivideAndConquer = measurement[1][step] ;
    		long durationByHybrid = measurement[2][step] ;
    		double savingRatioByHybrid =
    			(double) (durationByDivideAndConquer - durationByHybrid) * 100.0
    			/ (double) durationByDivideAndConquer ;
    	AppView.outputLine (
    		String.format ( "[%6d]%20d %20d %20d %12.0f",
    			size, 
    			durationByComparingAllPairs,
    			durationByDivideAndConquer,
    			durationByHybrid,
    			savingRatioByHybrid
    			)
    	);
    	size += this.parameterSet().incrementSize() ;
    }
}
    
    public void run() {
    	AppView.outputLine ("") ;
    	AppView.outputLine("<<< 최단거리 쌍 찾기 성능 측정을 시작합니다 >>>");
    	
    	if ( this.experimentManager().closestPairAlgorithmsAreCorrect() ) {
    		long[][] measurementOfSingleSolve =
    			this.experimentManager().measureDurationOfSingleSolve();
    		measurementOfSingleSolve =
    			this.experimentManager().measureDurationOfSingleSolve();
    		this.showMeasurement(measurementOfSingleSolve, "한번 실행");
    	
    		long[][] measurementOfAverageOfSingleSolves =
    			this.experimentManager().measureAverageDurationOfSingleSolves();
    		this.showMeasurement(measurementOfAverageOfSingleSolves, "반복 실행의 평균");
    	
    		long[][] measurementOfMinOfSingleSolves =
    			this.experimentManager().measureMinDurationAmongSingleSolves();
    		this.showMeasurement(measurementOfMinOfSingleSolves, "반복 실행 중 최소 시간");
    	}
    	else {
    		AppView.outputLine ("! 현재 구현되어 있는 알고리즘이 올바로 작동하지 않습니다.");
    	}
    	AppView.outputLine ("");
    	AppView.outputLine ("<<< 최단거리 쌍 찾기 성능 측정을 종료합니다 >>>");
    }
}
