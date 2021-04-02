package experiment;

import app.AppView;

public abstract class ExperimentManager {
	
	// Only for controlling debugging messages
	private static final boolean DEBUG_MODE = false;

	private static void showDebugMessage(String aMessage) {
		if (ExperimentManager.DEBUG_MODE) {
			AppView.outputDebugMessage(aMessage);
		}
	}
	
	// Constants for the experiment parameters
	protected static final int DEFAULT_NUMBER_OF_ITERATION = 10;
	protected static final int DEFAULT_INCREMENT_SIZE = 1000;
	protected static final int DEFAULT_STARTING_SIZE = DEFAULT_INCREMENT_SIZE;

	protected static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 1;
	
	// private instance variables
	private ExperimentDataSet _dataSet; 
	private Experiment _experiment;
	private ParameterSetForMeasurement _parameterSetForMeasurement; 
	
	// Getters & Setters for instance variables
	public ExperimentDataSet dataSet() {
		return this._dataSet;
	}

	protected void setDataSet(ExperimentDataSet newDataSet) {
		this._dataSet = newDataSet;
	}

	protected Experiment experiment() {
		return _experiment;
	}

	protected void setExperiment(Experiment newExperiment) {
		this._experiment = newExperiment;
	}

	public ParameterSetForMeasurement parameterSetForMeasurement() {
		return this._parameterSetForMeasurement;
	}

	protected void setParameterSetForMeasurement(ParameterSetForMeasurement newParameterSet) {
		this._parameterSetForMeasurement = newParameterSet;
	}
	
	// Private methods
	protected void prepareDataSet() { 
		this.setDataSet(new ExperimentDataSet(this.parameterSetForMeasurement().maxDataSize()));
	}
	
	// Protected methods
	protected void setParameterSetWithDefaults() {
		this.setParameterSetForMeasurement(new ParameterSetForMeasurement(DEFAULT_STARTING_SIZE,
				DEFAULT_NUMBER_OF_ITERATION, DEFAULT_INCREMENT_SIZE, DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT));
	}
	
	// Protected methods
	protected abstract void performMeasuring(ListOrder anOrder); 
	
	// Contructor
	public ExperimentManager() {
		this.setParameterSetWithDefaults(); 
		showDebugMessage("super - ExperimentManager");
	}
	
	// Public methods
	public void prepareExperiment(ParameterSetForMeasurement aParameterSet) {
		// We start an experiment by allocating an object of "Experiment".
		if (aParameterSet != null) {
			this.setParameterSetForMeasurement(aParameterSet);
		}
		this.setExperiment(new Experiment(this.parameterSetForMeasurement()));
		
		// At this time, if we want, we can change the parameters for the experiment
		// using the instance method "setParameter()" of the class "Experiment"
		this.prepareDataSet();
		
		// The following statement is not mandatory for the experiment.
		// It is only for regulating the measured result:
		// The measured result from the beginning of the experiment seems
		// not to reflect the pure performance of the sorting since the result are so irregular
		// This seems from the JAVA characteristics
		this.performMeasuring(ListOrder.Random);
	}

	public abstract void performExperiment(ListOrder anOrder);
}
