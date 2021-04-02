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
        AppView.output("?? ������ Ǯ���� 'Y' �Ǵ� 'y' �ƴϸ� �ٸ� �ƹ��ų� ġ�ÿ�");
        char answer = AppView.inputChar();
        return ((answer == 'Y') || (answer == 'y'));
    }

    private int inputNumberOfPoints() {
        do {
            AppView.output("? ���� ������ �Է��Ͻÿ�: ");
            try {
                int numberOfPoints = AppView.inputInt();
                if (numberOfPoints < 2) {
                    AppView.outputLine(("[����] ���� ������ 2 �̻��̾�� �մϴ�."));
                } else {
                    return numberOfPoints;
                }
            } catch (NumberFormatException e) {
                AppView.outputLine("[����] �ùٸ� ���ڰ� �Էµ��� �ʾҽ��ϴ�.");
            }
        } while (true);
    }

    private Point inputPoint() {
    	// �� �Ѱ��� (X,Y)��ǥ�� ���ʷ� �Է¹޴´�.
    	// �Էµ� ��ǥ ���� �� ��ü�� �����Ͽ� return �Ѵ�.
        Point point = new Point();
        AppView.outputLine("");
        AppView.outputLine("! ����(X,Y) ��ǥ�� ���ʷ� �Է��ؾ� �մϴ�:");
        point.x = this.inputCoordinateValue("X");
        point.y = this.inputCoordinateValue("Y");
        return point;
    }

    private int inputCoordinateValue(String coordinateName) {
    	// ��ǥ ������ ���� �� ���� �Է¹޴´�.
    	// coordinateName: �Է� �޾ƾ� �� ��ǥ ���� X���� Y����, �� �̸��� ��Ÿ���� ���ڿ�
        do {
            AppView.output("?"+coordinateName+"��ǥ ���� �Է��Ͻÿ�: ");
            try {
                return AppView.inputInt();
            } catch (NumberFormatException e) {
                AppView.outputLine("[����] �ùٸ� ���ڰ� �Էµ��� �ʾҽ��ϴ�.");
            }
        } while (true);
    }


    private boolean inputPointSet() {
    	// ���� ������ �Է� �޴´�.
    	// ���� ���� ������ �Է¹ް�, �� ���� �� ���� ��ŭ�� ������ ���ʷ� �Է� �޴´�.
        int numberOfPoints = this.inputNumberOfPoints();
        this.setPointSet(new PointSet(numberOfPoints));
        for (int count = 0; count < numberOfPoints; count++) {
            Point point = this.inputPoint();
            if (!this.pointSet().add(point)) {
            	// "add()"���� ���� ���ɼ��� ������, �����ϰ� ó���Ѵ�.
                AppView.output("[����] �Է� ���� ���� ���� ���տ� �߰��ϱ⸦ �����Ͽ����ϴ�.");
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
        AppView.outputLine("! ������ �����Դϴ�.");
        for (int count = 0; count < this.pointSet().size(); count++) {
            this.showPoint(this.pointSet().elementAt(count));
            AppView.outputLine("");
        }
    }

    public void run() {
        AppView.outputLine("<<< �ִܰŸ� �� ã�⸦ �����մϴ�. >>>");
        
        while (this.doesContinueToSolve()) {
        	AppView.outputLine("");
        	AppView.outputLine("[���� Ǯ�̸� �����մϴ�.]");
        	if (!this.inputPointSet()) {
        		 AppView.outputLine("[����] ������ ���������� �Էµ��� �ʽ��ϴ�.");
        	} else {
            this.showPointSet();
            
            PairOfPoints closestPairByDivideAndConquer = this.findClosetPair().solveByConquer(this.pointSet());
            this.showClosestPair(closestPairByDivideAndConquer, "Divide-and-Conquer ������� ã�� �ִܰŸ� ��");

            PairOfPoints closestPairByComparingAllPairs = this.findClosetPair().solveByComparingAllPairs(this.pointSet());
            this.showClosestPair(closestPairByComparingAllPairs, "��� ���� �Ÿ��� ���Ͽ� ã�� �ִ� �Ÿ� ��");
        	}
        }
        AppView.outputLine("");
        AppView.outputLine("<<< �ִܰŸ� �� ã�⸦ �����մϴ�. >>> ");
    }
}
