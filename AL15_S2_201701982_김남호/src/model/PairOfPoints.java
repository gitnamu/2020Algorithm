package model;

public class PairOfPoints {
	// Instance Variables
	private Point _firstPoint;
	private Point _secondPoint;

	// Constructor
	public PairOfPoints() {
		this(null, null);
	}

	public PairOfPoints(Point firstPoint, Point secondPoint) {
		this.setFirstPoint(firstPoint);
		this.setSecondPoint(secondPoint);
	}
	
	// Getters / Setters
	public Point firstPoint() {
		return _firstPoint;
	}

	public void setFirstPoint(Point firstPoint) {
		this._firstPoint = firstPoint;
	}

	public Point secondPoint() {
		return _secondPoint;
	}

	public void setSecondPoint(Point secondPoint) {
		this._secondPoint = secondPoint;
	}

	public long distance(){
		if(this.firstPoint()==null||this.secondPoint()==null){
			return Integer.MAX_VALUE;
		} else {
			return this.firstPoint().distanceTo(this.secondPoint());
		}
	}
}
