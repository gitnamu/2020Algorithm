package experiment;

public class ParameterSet {
    private static final int DEFAULT_NUMBER_OF_STEPS = 10;
    private static final int DEFAULT_STARTING_SIZE = 1000;
    private static final int DEFAULT_INCREMENT_SIZE = DEFAULT_NUMBER_OF_STEPS;
    private static final int DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION = 10;

    // Instance Variables
    private int _numberOfSteps;
    private int _startingSize;
    private int _incrementSize;
    private int _numberOfRepetitionsOfSameExecution;

    // Constructor
    public ParameterSet() {
        this(	DEFAULT_NUMBER_OF_STEPS,
                DEFAULT_STARTING_SIZE,
                DEFAULT_INCREMENT_SIZE,
                DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION);
    }


    public ParameterSet(int givenNumberOfSteps,
                        int givenStartingSize,
                        int givenIncrementSize,
                        int givenNumberOfRepetitionsOfSameExecution) {
        this._numberOfSteps = givenNumberOfSteps;
        this._startingSize = givenStartingSize;
        this._incrementSize = givenIncrementSize;
        this._numberOfRepetitionsOfSameExecution = givenNumberOfRepetitionsOfSameExecution;
    }

    // Public Getters/Setters
    public int numberOfSteps() {
        return _numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this._numberOfSteps = numberOfSteps;
    }

    public int startingSize() {
        return this._startingSize;
    }

    public void setStartingSize(int startingSize) {
        this._startingSize = startingSize;
    }

    public int incrementSize() {
        return this._incrementSize;
    }

    public void setIncrementSize(int incrementSize) {
        this._incrementSize = incrementSize;
    }

    public int numberOfRepetitionsOfSameExecution() {
        return this._numberOfRepetitionsOfSameExecution;
    }

    public void setNumberOfRepetitionsOfSameExecution(int numberOfRepetitionOfSameExecution) {
        this._numberOfRepetitionsOfSameExecution = numberOfRepetitionOfSameExecution;
    }

    public long numberOfRepetitionsForAverage() {
        return (long)numberOfRepetitionsOfSameExecution();
    }
}
