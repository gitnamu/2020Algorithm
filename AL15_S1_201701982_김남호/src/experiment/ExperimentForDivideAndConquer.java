package experiment;

import model.FindClosestPair;
import model.PointSet;

public class ExperimentForDivideAndConquer extends Experiment{
	
	// Constructor
    protected ExperimentForDivideAndConquer(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet) {
        super(givenFindClosestPair, givenParameterSet);
    }

    @Override
    public long durationOfSingleSolve(PointSet pointSet) {
        Timer.start();
        this.findClosetPair().solveByDivideAndConquer(pointSet);
        Timer.stop();
        return Timer.duration();
    }
}
