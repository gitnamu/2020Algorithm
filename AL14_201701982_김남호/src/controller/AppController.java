package controller;

import model.FindClosetPair;
import model.PairOfPoints;
import model.Point;
import model.PointSet;
import view.AppView;

public class AppController {
	// Private instance Variables
	private PointSet pointSet;
    private FindClosetPair findClosetPair;

    // Private Getters / Setters
    private PointSet pointSet() {
        return pointSet;
    }

    private void setPointSet(PointSet pointSet) {
        this.pointSet = pointSet;
    }

    private FindClosetPair findClosetPair() {
        return findClosetPair;
    }

    private void setFindClosetPair(FindClosetPair findClosetPair) {
        this.findClosetPair = findClosetPair;
    }

    // Constructor
    public AppController() {
        this.setPointSet(null);
        this.setFindClosetPair(new FindClosetPair());
    }

    // Private methods
    private boolean doesContinueToSolve() {
        AppView.outputLine("");
        AppView.output("?? 문제를 풀려면 'Y' 또는 'y' 아니면 다른 아무거나 치시오");
        char answer = AppView.inputChar();
        return ((answer == 'Y') || (answer == 'y'));
    }

    private int inputNumberOfPoints() {
        do {
            AppView.output("? 점의 개수를 입력하시오: ");
            try {
                int numberOfPoints = AppView.inputInt();
                if (numberOfPoints < 2) {
                    AppView.outputLine(("[오류] 점의 개수는 2 이상이어야 합니다."));
                } else {
                    return numberOfPoints;
                }
            } catch (NumberFormatException e) {
                AppView.outputLine("[오류] 올바른 숫자가 입력되지 않았습니다.");
            }
        } while (true);
    }

    private Point inputPoint() {
    	// 점 한개의 (X,Y)좌표를 차례로 입력받는다.
    	// 입력된 좌표 값의 점 객체를 생성하여 return 한다.
        Point point = new Point();
        AppView.outputLine("");
        AppView.outputLine("! 점의(X,Y) 좌표를 차례로 입력해야 합니다:");
        point.x = this.inputCoordinateValue("X");
        point.y = this.inputCoordinateValue("Y");
        return point;
    }

    private int inputCoordinateValue(String coordinateName) {
    	// 좌표 값으로 정수 한 개를 입력받는다.
    	// coordinateName: 입력 받아야 할 좌표 값이 X인지 Y인지, 그 이름을 나타내느 문자열
        do {
            AppView.output("?"+coordinateName+"좌표 값을 입력하시오: ");
            try {
                return AppView.inputInt();
            } catch (NumberFormatException e) {
                AppView.outputLine("[오류] 올바른 숫자가 입력되지 않았습니다.");
            }
        } while (true);
    }


    private boolean inputPointSet() {
    	// 점의 집합을 입력 받는다.
    	// 먼저 점의 개수를 입력받고, 그 다음 그 개수 만큼의 점들을 차례로 입력 받는다.
        int numberOfPoints = this.inputNumberOfPoints();
        this.setPointSet(new PointSet(numberOfPoints));
        for (int count = 0; count < numberOfPoints; count++) {
            Point point = this.inputPoint();
            if (!this.pointSet().add(point)) {
            	// "add()"에서 오류 가능성은 없으나, 안전하게 처리한다.
                AppView.output("[오류] 입력 받은 점을 점의 집합에 추가하기를 실패하엿습니다.");
                return false;
            }
        }
        return true;
    }

    private void showPoint(Point point) {
        AppView.output("(" + point.x + ", " + point.y + ") ");
    }

    public void showPairOfPoints(PairOfPoints pair) {
        AppView.output("<");
        this.showPoint(pair.firstPoint());
        AppView.output(",");
        this.showPoint(pair.secondPoint());
        AppView.output(">");

    }

    public void showClosestPair(PairOfPoints closestPair, String explanation) {
        AppView.output("!" + explanation + ":");
        this.showPairOfPoints(closestPair);
        AppView.outputLine(String.format(" Distance=%d", closestPair.distance()));
    }

    private void showPointSet() {
        AppView.outputLine("");
        AppView.outputLine("! 점들의 집합입니다.");
        for (int count = 0; count < this.pointSet().size(); count++) {
            this.showPoint(this.pointSet().elementAt(count));
            AppView.outputLine("");
        }
    }

    public void run() {
        AppView.outputLine("<<< 최단거리 쌍 찾기를 시작합니다. >>>");
        
        while (this.doesContinueToSolve()) {
        	AppView.outputLine("");
        	AppView.outputLine("[문제 풀이를 시작합니다.]");
        	if (!this.inputPointSet()) {
        		 AppView.outputLine("[오류] 점들이 정상적으로 입력되지 않습니다.");
        	} else {
            this.showPointSet();
            
            PairOfPoints closestPairByDivideAndConquer = this.findClosetPair().solveByConquer(this.pointSet());
            this.showClosestPair(closestPairByDivideAndConquer, "Divide-and-Conquer 방식으로 찾은 최단거리 쌍");

            PairOfPoints closestPairByComparingAllPairs = this.findClosetPair().solveByComparingAllPairs(this.pointSet());
            this.showClosestPair(closestPairByComparingAllPairs, "모든 쌍의 거리를 비교하여 찾은 최단 거리 쌍");
        	}
        }
        AppView.outputLine("");
        AppView.outputLine("<<< 최단거리 쌍 찾기를 종료합니다. >>> ");
    }
}
