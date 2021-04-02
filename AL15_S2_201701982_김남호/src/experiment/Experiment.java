package experiment;

import model.FindClosestPair;
import model.PointSet;

public abstract class Experiment {
	
	//Instance Variables
    private ParameterSet _parameterSet;
    private FindClosestPair _findClosestPair;

    //Getters / Setters
    private ParameterSet parameterSet() {
        return _parameterSet;
    }

    private void setParameterSet(ParameterSet parameterSet) {
        this._parameterSet = parameterSet;
    }

    protected FindClosestPair findClosestPair() {
        return _findClosestPair;
    }

    private void setFindClosestPair(FindClosestPair findClosetPair) {
        this._findClosestPair = findClosetPair;
    }

    protected Experiment(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet) {
        this.setFindClosestPair(givenFindClosestPair);
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
