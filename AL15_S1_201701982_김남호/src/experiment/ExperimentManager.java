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
    
    private static final int NUMBER_OF_KINDS_OF_EXPERIMENTS = 2;
    // 측정 실험의 종류가 2 가지
	// 실험 0: ExperimentForComparingAllPairs
    // 실험 1: ExperimentForDivideAndConquer

    private static final int SIZE_FOR_CORRECTNESS_TEST = 10000;
    // FindClosestPair 의 알고리즘이 정확한지를,
    // 실제로 실행을 하여 검사할 목적으로 사용하는 점의 집합의 크기

    // Instance Variables
    private ParameterSet parameterSet;
    private FindClosestPair findClosetPair;
    private Experiment experimentForComparingAllPairs;
    private Experiment experimentForDivideAndConquer;

    // Getters / Setters
    public ParameterSet parameterSet() {
        return parameterSet;
    }

    public void setParameterSet(ParameterSet parameterSet) {
        this.parameterSet = parameterSet;
    }

    public FindClosestPair findClosetPair() {
        return findClosetPair;
    }

    public void setFindClosetPair(FindClosestPair findClosetPair) {
        this.findClosetPair = findClosetPair;
    }

    public Experiment experimentForComparingAllPairs() {
        return experimentForComparingAllPairs;
    }

    public void setExperimentForComparingAllPairs(Experiment experimentForComparingAllPairs) {
        this.experimentForComparingAllPairs = experimentForComparingAllPairs;
    }

    public Experiment experimentForDivideAndConquer() {
        return experimentForDivideAndConquer;
    }

    public void setExperimentForDivideAndConquer(Experiment experimentForDivideAndConquer) {
        this.experimentForDivideAndConquer = experimentForDivideAndConquer;
    }

    // Constructor
    public ExperimentManager(ParameterSet givenParameterSet) {
        this.setParameterSet(givenParameterSet);
        this.setFindClosetPair(new FindClosestPair());
        this.setExperimentForComparingAllPairs(new ExperimentForComparingAllPairs(this.findClosetPair(), givenParameterSet));
        this.setExperimentForDivideAndConquer(new ExperimentForDivideAndConquer(this.findClosetPair(), givenParameterSet));
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
            
            sizeForStep += this.parameterSet().incrementSize();
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

            sizeForStep += this.parameterSet().incrementSize();
        }
        return measurement;
    }

    public boolean closestPairAlgorithmsAreCorrect() {
        PointSet pointSet = this.generatePointSet(ExperimentManager.SIZE_FOR_CORRECTNESS_TEST);
        PairOfPoints closestPairByComparingAllPairs = this.findClosetPair().solveByComparingAllPairs(pointSet);
        PairOfPoints closestPairByDivideAndConquer = this.findClosetPair().solveByDivideAndConquer(pointSet);

        return closestPairByComparingAllPairs.distance() == closestPairByDivideAndConquer.distance();
    }
}
