package model;

public class Point {
	// public instance variables
	public int x;
	public int y;

	public Point() {
		this(0,0);
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public long distanceTo(Point other){
		if (other == null) {
			return Integer.MAX_VALUE;
		} else {
			// 현재 점(this)에서 주어진 점(other)까지의 Euclidean distance의 제곱값을 얻는다.
			long differenceOfX = this.x-other.x;
			long differenceOfY = this.y - other.y;
			return (differenceOfX*differenceOfX+differenceOfY*differenceOfY);
		}
	}
}
