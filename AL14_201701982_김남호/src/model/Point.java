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
			// ���� ��(this)���� �־��� ��(other)������ Euclidean distance�� �������� ��´�.
			long differenceOfX = this.x-other.x;
			long differenceOfY = this.y - other.y;
			return (differenceOfX*differenceOfX+differenceOfY*differenceOfY);
		}
	}
}
