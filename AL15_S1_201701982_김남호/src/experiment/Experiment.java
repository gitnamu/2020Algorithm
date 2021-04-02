package experiment;

import model.FindClosestPair;
import model.PointSet;

public abstract class Experiment {
	
	//Instance Variables
    private ParameterSet parameterSet;
    private FindClosestPair findClosetPair;

    //Getters / Setters
    private ParameterSet parameterSet() {
        return parameterSet;
    }

    private void setParameterSet(ParameterSet parameterSet) {
        this.parameterSet = parameterSet;
    }

    protected FindClosestPair findClosetPair() {
        return findClosetPair;
    }

    private void setFindClosetPair(FindClosestPair findClosetPair) {
        this.findClosetPair = findClosetPair;
    }

    protected Experiment(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet) {
        this.setFindClosetPair(givenFindClosestPair);
        this.setParameterSet(givenParameterSet);
    }

    public abstract long durationOfSingleSolve(PointSet pointSet);

    public long averageDurationOfSingleSolves(PointSet pointSet) {
        long sum = 0;
        for (int count = 0; count < this.parameterSet().numberOfRepetitionsOfSameExecution(); count++) {
            sum += this.durationOfSingleSolve(pointSet);
        }
        long average = sum / this.parameterSet().numberOfRepetitionsForAverage();
        return average;
    }

    public long minDurationAmongSingleSolves(PointSet pointSet) {
        long minDuration = this.durationOfSingleSolve(pointSet);
        for (int count = 1; count < this.parameterSet().numberOfRepetitionsOfSameExecution(); count++) {
            long duration = this.durationOfSingleSolve(pointSet);
            if (duration < minDuration) minDuration = duration;
        }
        return minDuration;
    }
}
