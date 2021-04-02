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

        ReferenceList Px = new ReferenceListOrderedByX(this.pointSet());	// Px를 생성
        ReferenceList Py = new ReferenceListOrderedByY(this.pointSet());	// Py를 생성

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
    	// PointSet 의 점의 개수가 3 개 이하인 경우에, 3 점을 직접 비교하여 closestPair 를 찾는다
        if (Px.size() <= 1) {
        	// 점 개수가 1 개 이하이면 계산 불능, null 객체를 return
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
    	// Px로부터 좌우로 점들을 나눌 Separation Line을 찾는다.
    	// 단순히 Px 의 점들의 개수가 같게 나눈다
    	// 실제로 얻는 값은, ReferenceList 인 Px 의 한 가운데 위치의 reference 값이 된다.
    	
        return Px.elementAt(Px.size() / 2);
    }

    private class SeparatedPair {
        private ReferenceList leftList;
        private ReferenceList rightList;

        public SeparatedPair() {
        }
    }
    
    private SeparatedPair separateReferenceList(ReferenceList referenceList, int separationLine) {
    	// Separation Line 을 기준으로 ReferenceList 의 점들을 좌측의 점들과 우측의 점들로 나눈다.
    	// 좌표 값으로 정렬되어 있는 reference 들의 순서는 유지된다. (다시 정렬할 필요가 없다)
    	SeparatedPair separatedPair = new SeparatedPair();
        	// 분리될 2 개의 리스트를 보관할 SeparatedPair 객체를 생성한다.
        int sizeOfReferenceList = referenceList.size();
        	// 분리된 리스트의 최대 크기는, 분리할 리스트 referenceList 의 크기이다.

        // 왼쪽, 오른쪽으로 분리될 ReferenceList 객체를 각각 생성한다.
        separatedPair.leftList = new ReferenceList(sizeOfReferenceList);
        separatedPair.rightList = new ReferenceList(sizeOfReferenceList);

        int separationX = FindClosestPair.this.pointSet().elementAt(separationLine).x;
        	// separationLine 이 가리키는 point 의 X 좌표를 얻는다.

        for (int index = 0; index < sizeOfReferenceList; index++) {
            Integer pointReference = referenceList.elementAt(index);
            	// index 가 가리키는 pointReference 를 얻는다.

            Point pointReferencedByIndex_i = FindClosestPair.this.pointSet().elementAt(pointReference);
            	// pointReference 가 참조하는 point (즉, pointReferencedByIndex_i) 를 얻는다.
            
            // separationLine 을 기준으로, point 가 좌, 우 어느 리스트에 속하는지 판단한다.
            if (pointReferencedByIndex_i.x < separationX) {
                separatedPair.leftList.add(pointReference);
            } else if (pointReferencedByIndex_i.x > separationX) {
                separatedPair.rightList.add(pointReference);
            } else { // 점이 분리선 (separationLine) 위에 있는 경우
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
    	
    	// Py와 Separation line 과 delta 값에 의해 결정되는 Delta Band 안에서의 closest Pair 를 찾는다
    	// 이 함수에서 deltaBand() 와 closestPairFromHereToNeighbors() 를 사용한다.
        PairOfPoints closestPair = null;
        ReferenceList Sy = this.deltaBand(Py, separationLine, delta);
        	// Delta Band 에 속하는 모든 점들을 Py 로부터 얻는다
        int Sy_size = Sy.size();

        // Sy 의 index 0 부터 차례로 모든 index 에 대해,
        // 그 다음 7 개의 점들 중의 최단 거리를 계산한다.
        if (Sy_size > 1) {
            int hereIndex = 0;
            	// 기준이 되는 index. 이 hereIndex 로부터 이웃해 있는 7 개의 점을 고려한다.
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
    	
    	// Delta Band 안에 들어 있는 점들의 리스트를 Py 로부터
    	// separationLine 과 delta 값을 이용하여 골라낸다.
    	// Py 가 Y 좌표로 정렬되어 있으므로, Sy 도 Y 좌표로 정렬된 상태로 얻게 된다
    	
        ReferenceList Sy = new ReferenceList(Py.size()); 	// 최대 크기로 생성

        int separationX = this.pointSet().elementAt(separationLine).x;

        // Delta Band 의 너비는 실제 거리이어야 한다.
        // 알고리즘에서 사용하고 있는 거리는 실제 거리의 제곱 값이다.
        // 따라서, 실제 delta band 의 너비 값을 얻으려면, 주어진 delta 의 제곱근을 계산해야 한다.
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
    	// 주어진 hereIndex 를 기준으로 Sy 리스트에서 그 다음으로 가까이 이웃해 있는
    	// MAX_NUMBER_OF_NEIGHBORS (=7) 개의 점들 중에서
    	// hereIndex 가 가리키는 점에서 가장 가까운 점을 찾는다.
    	
        Point herePoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex);
        Point closestNeighborPoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex + 1);
        	// hereIndex 바로 다음 point 를 얻는다.

        long minDistance = herePoint.distanceTo(closestNeighborPoint);
        int lastNeighborIndex = Math.min(hereIndex + MAX_NUMBER_OF_NEIGHBORS, Sy.size() - 1);
        	// hereIndex 이후로 남아 있는 점이 7 개 보다 적을 수 있다.
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
