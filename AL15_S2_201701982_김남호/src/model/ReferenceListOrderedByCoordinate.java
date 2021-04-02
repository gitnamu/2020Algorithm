package model;

public abstract class ReferenceListOrderedByCoordinate extends ReferenceList{

    private PointSet _pointSet;

    public PointSet pointSet() {
        return _pointSet;
    }

    public void setPointSet(PointSet pointSet) {
        this._pointSet = pointSet;
    }

    public ReferenceListOrderedByCoordinate(PointSet givenPointSet) {
    	// 최대 크기가 주어진다.
        super(givenPointSet.size());
        this.setPointSet(givenPointSet);
        
     // givenPointSet 의 point 들이 X 좌표 또는 Y 좌표로 정렬된 인덱스 리스트를 생성
        this.generateReferenceListOrderedByCoordinate();
    }

    protected abstract int coordinateReferencedByIndex(int i);
    // 정렬에서 비교할 key 값을 얻는다.
    // Abstract method 로서, 하위 class 에서 구현해야 할 method 이다.
    // 하위 class 에서 사용 (즉 구현) 하므로, protected method 이다.
    // 즉, index i 가 참조하는 점의 X 좌표 값 또는 Y 좌표 값을 얻는다.
    // 하위 class ReferenceListOrderedByX 에서는 X 좌표의 값을 얻도록 구현한다.
    // 하위 class ReferenceListOrderedByY 에서는 Y 좌표의 값을 얻도록 구현한다.

    // private method
    private void swap(int i, int j) {
    	// 정렬에서 두 원소의 위치를 바꿀 때 사용한다.
        Integer temp = this.elementAt(i);
        this.setElementAt(this.elementAt(j), i);
        this.setElementAt(temp, j);
    }

    private int compareCoordinate(int i, int j) {
    	
        int coordinateReferencedBy_i = this.coordinateReferencedByIndex(i);
        // Index i 가 참조하는 점의 (X 또는 Y) 좌표 값을 얻는다.
        int coordinateReferencedBy_j = this.coordinateReferencedByIndex(j);
        // Index j 가 참조하는 점의 (X 또는 Y) 좌표 값을 얻는다.

        if (coordinateReferencedBy_i < coordinateReferencedBy_j) {
            return -1;		// Index i 가 참조하는 점의 좌표 값이 더 작다.
        } else if (coordinateReferencedBy_i > coordinateReferencedBy_j) {
            return +1;		// Index j 가 참조하는 점의 좌표 값이 더 작다.

        } else {
        	// 좌표 값이 동일하다.
        	// 이 경우에는, PointSet 에서의 점의 순서 (즉, PointSet 에 삽입된 순서) 를 비교한다.
        	// 참조 값이 점을 가리킨다. 즉 참조 값이 원래 점의 순서 값이다.
            if (this.elementAt(i) < this.elementAt(j)) {
                return -1;
            } else if (this.elementAt(i) > this.elementAt(j)) {
                return +1;
            } else {
                return 0;		// 참조 값이 같은 경우는, 사실은 동일한 점인 경우다.
            }
        }
    }

    private int pivotByMedian(int left, int right) {
    	// Pivot 의 위치, 즉 {가장 왼쪽, 가장 오른쪽, 한 가운데} 의
    	// 세 원소 중에서 median 인 원소의 위치를 찾아준다.
    	// 구간의 원소의 개수가 2 개이어도 정상적으로 작동함

    	
        int mid = (left + right) / 2;
        if (this.compareCoordinate(left, mid) < 0) {
        	// left < mid
            if (this.compareCoordinate(mid, right) < 0) {
                return mid;		// left < mid < right
            } else if (this.compareCoordinate(left, right) < 0) {
                return right;	// left < right < mid
            } else {
                return left;	// right < left < mid
            }
        } else {
        	// mid < left
            if (this.compareCoordinate(right, mid) < 0) {
                return mid;		 // right < mid < left 
            } else if (this.compareCoordinate(right, left) < 0) {
                return right;	 // mid < right < left
            } else {
                return left;	// mid < left < right
            }
        }
    }

    private int partition(int left, int right) {
        int pivotIndexByMedian = this.pivotByMedian(left, right);
        this.swap(left, pivotIndexByMedian);	// pivot 원소를 구간의 가장 왼쪽에 갖다 놓는다.
        int pivotIndex = left;

        int toRight = left;
        int toLeft = right + 1;

        do {
            do {
                toRight++;
            } while (this.compareCoordinate(toRight, pivotIndex) < 0);
            do {
                toLeft--;
            } while (this.compareCoordinate(toLeft, pivotIndex) > 0);
            if (toRight < toLeft) {
                this.swap(toRight, toLeft);
            }
        } while (toRight < toLeft);
        this.swap(toLeft, pivotIndex);
        return toLeft;
    }

    private void qSortByCoordinateRecursively(int left, int right) {
        if (left < right) {
            int mid = this.partition(left, right);
            this.qSortByCoordinateRecursively(left, mid - 1);
            this.qSortByCoordinateRecursively(mid + 1, right);
        }
    }

    private void sortbyCoordinate() {
    	// Step 1:
    	// 먼저, 정렬 대상인 점 중에서 좌표가 가장 큰 점을 찾아 맨 뒤에 갖다 놓는다.
        if (this.size() >= 2) {
            int indexOfMaxCoordinate = 0;
            for (int index = 1; index < this.size(); index++) {
            	// 두 점을 비교할 때, x (또는 y) 좌표 값이 동일할 경우에는,
            	// PointSet 에서의 순서가 나중인 점이 큰 것으로 간주된다.
                if (this.compareCoordinate(indexOfMaxCoordinate, index) < 0) {
                    indexOfMaxCoordinate = index;
                }
            }
            this.swap(indexOfMaxCoordinate, this.size() - 1);
            
            // Step 2: 실제 정렬은 맨 마지막 점을 제외하고 실행한다
            this.qSortByCoordinateRecursively(0, this.size() - 2);
        }
    }

    private void generateReferenceListOrderedByCoordinate() {
    	
    	// 이 method 는 객체가 생성된 직후, 생성자에서 마지막으로 실행된다.
    	// 이 method 가 시작하는 시점에, ReferenceList (this) 는 empty 상태 이다.
    	// Step 1:
    	// 점들의 index 를 0 부터 차례로 this.capacity() 만큼 채운다.
    	// 생성자에서 주어진 PointSet 의 크기를 capacity 로 설정했다.
    	// (이 method 를 사용하는 생성자 코드를 볼 것)
    	// 그러므로 this.capacity() 는 주어진 PointSet 의 크기이다.
        for (int index = 0; index < this.capacity(); index++) {
            this.add(Integer.valueOf(index));
        }
        
        // Step 2: 점들의 index 를 좌표 값 순서로 정렬한다.
        this.sortbyCoordinate();
        
        // 정렬이 끝난 시점에, Px 또는 Py 가 얻어진다.
    }
}
