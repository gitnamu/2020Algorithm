package experiment;

import java.util.Random;

import model.FindClosestPair;
import model.PairOfPoints;
import model.Point;
import model.PointSet;

public class ExperimentManager {
	
	// Constants
    private static final int MAX_COORDINATE_VALUE = 10000000;
    // 실험에 사용할 점들의 좌표의 최대값
    // 난수를 사용하여 생성되는 모든 좌표는
    // [0, MAX_COORDINATE_VALUE] 사이의 값을 갖는 것으로 한다.
    
    private static final int NUMBER_OF_KINDS_OF_EXPERIMENTS = 3;
    // 측정 실험의 종류가 3 가지
	// 실험 0: ExperimentForComparingAllPairs
    // 실험 1: ExperimentForDivideAndConquer
    // 실험 2: ExperimentForHybrid

    private static final int SIZE_FOR_CORRECTNESS_TEST = 10000;
    // FindClosestPair 의 알고리즘이 정확한지를,
    // 실제로 실행을 하여 검사할 목적으로 사용하는 점의 집합의 크기

    // Instance Variables
    private ParameterSet _parameterSet;
    private FindClosestPair _findClosestPair;
    private Experiment _experimentForComparingAllPairs;
    private Experiment _experimentForDivideAndConquer;
    private Experiment _experimentForHybrid;

    // Getters / Setters
    private ParameterSet parameterSet() {
        return this._parameterSet;
    }

    private void setParameterSet(ParameterSet parameterSet) {
        this._parameterSet = parameterSet;
    }

    private FindClosestPair findClosestPair() {
        return this._findClosestPair;
    }

    private void setFindClosestPair(FindClosestPair findClosetPair) {
        this._findClosestPair = findClosetPair;
    }

    private Experiment experimentForComparingAllPairs() {
        return this._experimentForComparingAllPairs;
    }

    private void setExperimentForComparingAllPairs(Experiment experimentForComparingAllPairs) {
        this._experimentForComparingAllPairs = experimentForComparingAllPairs;
    }

    private Experiment experimentForDivideAndConquer() {
        return this._experimentForDivideAndConquer;
    }

    private void setExperimentForDivideAndConquer(Experiment experimentForDivideAndConquer) {
        this._experimentForDivideAndConquer = experimentForDivideAndConquer;
    }

    private Experiment experimentForHybrid () {
    	return this._experimentForHybrid;
    }
    private void setExperimentForHybrid (Experiment newExperiment) {
    	this._experimentForHybrid = newExperiment;
    }
    
    // Constructor
    public ExperimentManager(ParameterSet givenParameterSet) {
        this.setParameterSet(givenParameterSet);
        this.setFindClosestPair(new FindClosestPair());
        this.findClosestPair().setMinRecursionSize (this.parameterSet().minRecursionSize() ) ;
        // "solveByHybrid()" 알고리즘에서 사용할 최소 재귀 크기를 이곳에서 알려준다.
        
        this.setExperimentForComparingAllPairs(new ExperimentForComparingAllPairs(this.findClosestPair(), givenParameterSet));
        this.setExperimentForDivideAndConquer(new ExperimentForDivideAndConquer(this.findClosestPair(), givenParameterSet));
        this.setExperimentForHybrid (
        	new ExperimentForHybrid (this.findClosestPair(), givenParameterSet) ) ;
    }

    // Private Method
    private PointSet generatePointSet(int size) {
        PointSet pointSet = new PointSet(size);
        Random random = new Random();
        for (int count = 0; count < size; count++) {
            int x = random.nextInt(MAX_COORDINATE_VALUE);
            int y = random.nextInt(MAX_COORDINATE_VALUE);
            Point point = new Point(x, y);
            pointSet.add(point);
        }
        return pointSet;
    }

    // Public Method
    public long[][] measureDurationOfSingleSolve() {
        long[][] measurement = new long[NUMBER_OF_KINDS_OF_EXPERIMENTS][this.parameterSet().numberOfSteps()];

        int sizeForStep = this.parameterSet().startingSize();
        for (int step = 0; step < this.parameterSet().numberOfSteps(); step++) {
            PointSet pointSet = this.generatePointSet(sizeForStep);
            measurement[0][step] = this.experimentForComparingAllPairs().durationOfSingleSolve(pointSet);
            measurement[1][step] = this.experimentForDivideAndConquer().durationOfSingleSolve(pointSet);
            measurement[2][step] = this.experimentForHybrid().durationOfSingleSolve (pointSet);
            
            sizeForStep += this.parameterSet().incrementSize() ;
        }
        return measurement;
    }

    public long[][] measureAverageDurationOfSingleSolves() {
        long[][] measurement = new long[NUMBER_OF_KINDS_OF_EXPERIMENTS][this.parameterSet().numberOfSteps()];

        int sizeForStep = this.parameterSet().startingSize();
        for (int step = 0; step < this.parameterSet().numberOfSteps(); step++) {
            PointSet pointSet = this.generatePointSet(sizeForStep);
            measurement[0][step] = this.experimentForComparingAllPairs().averageDurationOfSingleSolves(pointSet);

            measurement[1][step] = this.experimentForDivideAndConquer().averageDurationOfSingleSolves(pointSet);
            measurement[2][step] = this.experimentForHybrid().averageDurationOfSingleSolves(pointSet);

            sizeForStep += this.parameterSet().incrementSize();
        }
        return measurement;
    }

    public long[][] measureMinDurationAmongSingleSolves() {
        long[][] measurement = new long[NUMBER_OF_KINDS_OF_EXPERIMENTS][this.parameterSet().numberOfSteps()];

        int sizeForStep = this.parameterSet().startingSize();
        for (int step = 0; step < this.parameterSet().numberOfSteps(); step++) {
            PointSet pointSet = this.generatePointSet(sizeForStep);
            measurement[0][step] = this.experimentForComparingAllPairs().minDurationAmongSingleSolves(pointSet);
            measurement[1][step] = this.experimentForDivideAndConquer().minDurationAmongSingleSolves(pointSet);
            measurement[2][step] = this.experimentForHybrid().minDurationAmongSingleSolves(pointSet);

            sizeForStep += this.parameterSet().incrementSize();
        }
        return measurement;
    }

    public boolean closestPairAlgorithmsAreCorrect() {
        PointSet pointSet = this.generatePointSet(ExperimentManager.SIZE_FOR_CORRECTNESS_TEST);
        PairOfPoints closestPairByComparingAllPairs = this.findClosestPair().solveByComparingAllPairs(pointSet);
        PairOfPoints closestPairByDivideAndConquer = this.findClosestPair().solveByDivideAndConquer(pointSet);
        PairOfPoints closestPairByHybrid = this.findClosestPair().solveByHybrid(pointSet) ;

        return ((closestPairByComparingAllPairs.distance() == closestPairByDivideAndConquer.distance())
        		&&
        		(closestPairByComparingAllPairs.distance() == closestPairByHybrid.distance())
        		);
    }
}
