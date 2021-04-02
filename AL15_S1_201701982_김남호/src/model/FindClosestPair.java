package model;

public class FindClosestPair {
	// Constant
	private static final int MAX_NUMBER_OF_NEIGHBORS = 7;
	
	// Instance Variable
	private PointSet _pointSet;
	
	// Getter/Setter
    public PointSet pointSet() {
        return _pointSet;
    }

    public void setPointSet(PointSet pointSet) {
        this._pointSet = pointSet;
    }

    // Constructor
    public FindClosestPair() {
        this.setPointSet(null);
    }

    // public method
    public PairOfPoints solveByComparingAllPairs(PointSet pointSetForSolve) {
    	if (pointSetForSolve == null || pointSetForSolve.size() <= 1) {
            return null;
        }
        Point closetPair_point_i = null;
        Point closetPair_point_j = null;
        long minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < pointSetForSolve.size() - 1; i++) {
            Point point_i = pointSetForSolve.elementAt(i);
            for (int j = i + 1; j < pointSetForSolve.size(); j++) {
                Point point_j = pointSetForSolve.elementAt(j);
                long distanceBetween_i_j = point_i.distanceTo(point_j);
                if (distanceBetween_i_j < minDistance) {
                    minDistance = distanceBetween_i_j;
                    closetPair_point_i = point_i;
                    closetPair_point_j = point_j;
                }
            }
        }
        PairOfPoints closestPair = new PairOfPoints(closetPair_point_i, closetPair_point_j);
        return closestPair;
    }

    public PairOfPoints solveByDivideAndConquer(PointSet pointSetForSolve) {
        if (pointSetForSolve == null || pointSetForSolve.size() <= 1) {
            return null;
        }
        this.setPointSet(pointSetForSolve);

        ReferenceList Px = new ReferenceListOrderedByX(this.pointSet());	// Px�� ����
        ReferenceList Py = new ReferenceListOrderedByY(this.pointSet());	// Py�� ����

        PairOfPoints closestPair = this.solveRecursively(Px, Py);
        return closestPair;
    }

    private PairOfPoints solveRecursively(ReferenceList Px, ReferenceList Py) {
        PairOfPoints closestPair;
        // Step 1: Find the closest Pair when Px is small.

        if (Px.size() <= 3) {
            closestPair = this.closestPairDirectlyFromSmallPointSet(Px);
            return closestPair;
        }
        // Step 2: Find the Separation Line
        int separationLine = this.separationLine(Px);
        // Step 3: Construct Qx, Qy, Rx, Ry from Px and Py; // O(n)
        SeparatedPair separatedPairFromPx = this.separateReferenceList(Px, separationLine);
        SeparatedPair separatedPairFromPy = this.separateReferenceList(Py, separationLine);
        ReferenceList Qx = separatedPairFromPx.leftList;
        ReferenceList Rx = separatedPairFromPx.rightList;
        ReferenceList Qy = separatedPairFromPy.leftList;
        ReferenceList Ry = separatedPairFromPy.rightList;

        // Step 4: Find the closest pairs from each left (Qx, Qy) and Right (Rx, Ry), respectively.
        PairOfPoints closestPairFromLeft = this.solveRecursively(Qx, Qy);
        PairOfPoints closestPairFromRight = this.solveRecursively(Rx, Ry);

        // Step 5: Determine the tentative closest Pair at this moment.
        if (closestPairFromLeft == null) {
            closestPair = closestPairFromRight;
        } else if (closestPairFromRight == null) {
            closestPair = closestPairFromLeft;
        } else if (closestPairFromLeft.distance() < closestPairFromRight.distance()) {
            closestPair = closestPairFromLeft;
        } else {
            closestPair = closestPairFromRight;
        }

        // Step 6: Find the closest Pair from Delta Band.
        long delta = closestPair.distance();
        PairOfPoints closestFromDeltaBand = this.closestPairFromDeltaBand(Py, separationLine, delta);

        // Step 7: Determine the final closest pair
        if (closestFromDeltaBand != null) {
            if (closestFromDeltaBand.distance() < closestPair.distance()) {
                closestPair = closestFromDeltaBand;
            }
        }

        return closestPair;
    }

    private PairOfPoints closestPairDirectlyFromSmallPointSet(ReferenceList Px) {
    	// PointSet �� ���� ������ 3 �� ������ ��쿡, 3 ���� ���� ���Ͽ� closestPair �� ã�´�
        if (Px.size() <= 1) {
        	// �� ������ 1 �� �����̸� ��� �Ҵ�, null ��ü�� return
            return null;
        } else {
            Point p0 = this.pointSet().pointReferencedByIndex(Px, 0);
            Point p1 = this.pointSet().pointReferencedByIndex(Px, 1);

            Point closestPair_point_i = p0;
            Point closestPair_point_j = p1;
            long minDistance = p0.distanceTo(p1);

            if (Px.size() == 3) {
                Point p2 = this.pointSet().pointReferencedByIndex(Px, 2);
                if (p0.distanceTo(p2) < minDistance) {
                    closestPair_point_i = p0;
                    closestPair_point_j = p2;
                    minDistance = p0.distanceTo(p2);

                }
                if (p1.distanceTo(p2) < minDistance) {
                    closestPair_point_i = p1;
                    closestPair_point_j = p2;
                }
            }
            PairOfPoints closestPair = new PairOfPoints(closestPair_point_i, closestPair_point_j);
            return closestPair;
        }

    }

    private Integer separationLine(ReferenceList Px) {
    	// Px�κ��� �¿�� ������ ���� Separation Line�� ã�´�.
    	// �ܼ��� Px �� ������ ������ ���� ������
    	// ������ ��� ����, ReferenceList �� Px �� �� ��� ��ġ�� reference ���� �ȴ�.
    	
        return Px.elementAt(Px.size() / 2);
    }

    private class SeparatedPair {
        private ReferenceList leftList;
        private ReferenceList rightList;

        public SeparatedPair() {
        }
    }
    
    private SeparatedPair separateReferenceList(ReferenceList referenceList, int separationLine) {
    	// Separation Line �� �������� ReferenceList �� ������ ������ ����� ������ ����� ������.
    	// ��ǥ ������ ���ĵǾ� �ִ� reference ���� ������ �����ȴ�. (�ٽ� ������ �ʿ䰡 ����)
    	SeparatedPair separatedPair = new SeparatedPair();
        	// �и��� 2 ���� ����Ʈ�� ������ SeparatedPair ��ü�� �����Ѵ�.
        int sizeOfReferenceList = referenceList.size();
        	// �и��� ����Ʈ�� �ִ� ũ���, �и��� ����Ʈ referenceList �� ũ���̴�.

        // ����, ���������� �и��� ReferenceList ��ü�� ���� �����Ѵ�.
        separatedPair.leftList = new ReferenceList(sizeOfReferenceList);
        separatedPair.rightList = new ReferenceList(sizeOfReferenceList);

        int separationX = FindClosestPair.this.pointSet().elementAt(separationLine).x;
        	// separationLine �� ����Ű�� point �� X ��ǥ�� ��´�.

        for (int index = 0; index < sizeOfReferenceList; index++) {
            Integer pointReference = referenceList.elementAt(index);
            	// index �� ����Ű�� pointReference �� ��´�.

            Point pointReferencedByIndex_i = FindClosestPair.this.pointSet().elementAt(pointReference);
            	// pointReference �� �����ϴ� point (��, pointReferencedByIndex_i) �� ��´�.
            
            // separationLine �� ��������, point �� ��, �� ��� ����Ʈ�� ���ϴ��� �Ǵ��Ѵ�.
            if (pointReferencedByIndex_i.x < separationX) {
                separatedPair.leftList.add(pointReference);
            } else if (pointReferencedByIndex_i.x > separationX) {
                separatedPair.rightList.add(pointReference);
            } else { // ���� �и��� (separationLine) ���� �ִ� ���
                if (pointReference < separationLine) {
                    separatedPair.leftList.add(pointReference);
                } else {
                    separatedPair.rightList.add(pointReference);
                }
            }
        }
        return separatedPair;
    }
    
    private PairOfPoints closestPairFromDeltaBand(ReferenceList Py, Integer separationLine, long delta) {
    	
    	// Py�� Separation line �� delta ���� ���� �����Ǵ� Delta Band �ȿ����� closest Pair �� ã�´�
    	// �� �Լ����� deltaBand() �� closestPairFromHereToNeighbors() �� ����Ѵ�.
        PairOfPoints closestPair = null;
        ReferenceList Sy = this.deltaBand(Py, separationLine, delta);
        	// Delta Band �� ���ϴ� ��� ������ Py �κ��� ��´�
        int Sy_size = Sy.size();

        // Sy �� index 0 ���� ���ʷ� ��� index �� ����,
        // �� ���� 7 ���� ���� ���� �ִ� �Ÿ��� ����Ѵ�.
        if (Sy_size > 1) {
            int hereIndex = 0;
            	// ������ �Ǵ� index. �� hereIndex �κ��� �̿��� �ִ� 7 ���� ���� ����Ѵ�.
            closestPair = this.closestPairFromHereToNeighbors(Sy, hereIndex);
            for (hereIndex = 1; hereIndex < Sy_size - 1; hereIndex++) {
                PairOfPoints closestPairToNeighbors = this.closestPairFromHereToNeighbors(Sy, hereIndex);

                if (closestPairToNeighbors.distance() < closestPair.distance()) {
                    closestPair = closestPairToNeighbors;
                }
            }
        }
        return closestPair;
    }
    
    private ReferenceList deltaBand(ReferenceList Py, Integer separationLine, long delta) {
    	
    	// Delta Band �ȿ� ��� �ִ� ������ ����Ʈ�� Py �κ���
    	// separationLine �� delta ���� �̿��Ͽ� ��󳽴�.
    	// Py �� Y ��ǥ�� ���ĵǾ� �����Ƿ�, Sy �� Y ��ǥ�� ���ĵ� ���·� ��� �ȴ�
    	
        ReferenceList Sy = new ReferenceList(Py.size()); 	// �ִ� ũ��� ����

        int separationX = this.pointSet().elementAt(separationLine).x;

        // Delta Band �� �ʺ�� ���� �Ÿ��̾�� �Ѵ�.
        // �˰��򿡼� ����ϰ� �ִ� �Ÿ��� ���� �Ÿ��� ���� ���̴�.
        // ����, ���� delta band �� �ʺ� ���� ��������, �־��� delta �� �������� ����ؾ� �Ѵ�.
        long sqrtOfDelta = (long) Math.sqrt(delta);

        for (int index = 0; index < Py.size(); index++) {
            Integer pointReference = Py.elementAt(index);
            Point point = this.pointSet().elementAt(pointReference);
            if (Math.abs(point.x - separationX) <= sqrtOfDelta) {
                Sy.add(pointReference);
            }
        }
        return Sy;
    }
    
    private PairOfPoints closestPairFromHereToNeighbors(ReferenceList Sy, int hereIndex) {
    	// �־��� hereIndex �� �������� Sy ����Ʈ���� �� �������� ������ �̿��� �ִ�
    	// MAX_NUMBER_OF_NEIGHBORS (=7) ���� ���� �߿���
    	// hereIndex �� ����Ű�� ������ ���� ����� ���� ã�´�.
    	
        Point herePoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex);
        Point closestNeighborPoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex + 1);
        	// hereIndex �ٷ� ���� point �� ��´�.

        long minDistance = herePoint.distanceTo(closestNeighborPoint);
        int lastNeighborIndex = Math.min(hereIndex + MAX_NUMBER_OF_NEIGHBORS, Sy.size() - 1);
        	// hereIndex ���ķ� ���� �ִ� ���� 7 �� ���� ���� �� �ִ�.
        for (int neighborIndex = hereIndex + 2; neighborIndex < lastNeighborIndex; neighborIndex++) {
            Point neighborPoint = this.pointSet().pointReferencedByIndex(Sy, neighborIndex);
            long distanceFromHereToNeighbor = herePoint.distanceTo(neighborPoint);

            if (distanceFromHereToNeighbor < minDistance) {
                minDistance = distanceFromHereToNeighbor;
                closestNeighborPoint = neighborPoint;
            }
        }
        return new PairOfPoints(herePoint, closestNeighborPoint);
    }
}
