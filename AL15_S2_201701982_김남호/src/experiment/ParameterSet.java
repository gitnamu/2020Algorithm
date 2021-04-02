package experiment;

public class ParameterSet {
    private static final int DEFAULT_NUMBER_OF_STEPS = 10;
    private static final int DEFAULT_STARTING_SIZE = 1000;
    private static final int DEFAULT_INCREMENT_SIZE = DEFAULT_NUMBER_OF_STEPS;
    private static final int DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION = 10;
    private static final int DEFAULT_MIN_RECRUSION_SIZE = 150;

    // Instance Variables
    private int _numberOfSteps;
    private int _startingSize;
    private int _incrementSize;
    private int _numberOfRepetitionsOfSameExecution;
    private int _minRecursionSize ;

    // Constructor
    public ParameterSet() {
        this(	DEFAULT_NUMBER_OF_STEPS,
                DEFAULT_STARTING_SIZE,
                DEFAULT_INCREMENT_SIZE,
                DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION,
                DEFAULT_MIN_RECRUSION_SIZE );
    }


    public ParameterSet(int givenNumberOfSteps,
                        int givenStartingSize,
                        int givenIncrementSize,
                        int givenNumberOfRepetitionsOfSameExecution,
                        int givenMinRecursionSize) {
        this._numberOfSteps = givenNumberOfSteps;
        this._startingSize = givenStartingSize;
        this._incrementSize = givenIncrementSize;
        this._numberOfRepetitionsOfSameExecution = givenNumberOfRepetitionsOfSameExecution;
        this._minRecursionSize = givenMinRecursionSize ;
    }

    // Public Getters/Setters
    public int numberOfSteps() {
        return this._numberOfSteps;
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
    
    public int minRecursionSize () {
    	return this._minRecursionSize; 
    }
    public void setMinRecursionSize (int newMinRecursionSize) {
    	this._minRecursionSize = newMinRecursionSize;
    }
}
