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
    	// �ִ� ũ�Ⱑ �־�����.
        super(givenPointSet.size());
        this.setPointSet(givenPointSet);
        
     // givenPointSet �� point ���� X ��ǥ �Ǵ� Y ��ǥ�� ���ĵ� �ε��� ����Ʈ�� ����
        this.generateReferenceListOrderedByCoordinate();
    }

    protected abstract int coordinateReferencedByIndex(int i);
    // ���Ŀ��� ���� key ���� ��´�.
    // Abstract method �μ�, ���� class ���� �����ؾ� �� method �̴�.
    // ���� class ���� ��� (�� ����) �ϹǷ�, protected method �̴�.
    // ��, index i �� �����ϴ� ���� X ��ǥ �� �Ǵ� Y ��ǥ ���� ��´�.
    // ���� class ReferenceListOrderedByX ������ X ��ǥ�� ���� �򵵷� �����Ѵ�.
    // ���� class ReferenceListOrderedByY ������ Y ��ǥ�� ���� �򵵷� �����Ѵ�.

    // private method
    private void swap(int i, int j) {
    	// ���Ŀ��� �� ������ ��ġ�� �ٲ� �� ����Ѵ�.
        Integer temp = this.elementAt(i);
        this.setElementAt(this.elementAt(j), i);
        this.setElementAt(temp, j);
    }

    private int compareCoordinate(int i, int j) {
    	
        int coordinateReferencedBy_i = this.coordinateReferencedByIndex(i);
        // Index i �� �����ϴ� ���� (X �Ǵ� Y) ��ǥ ���� ��´�.
        int coordinateReferencedBy_j = this.coordinateReferencedByIndex(j);
        // Index j �� �����ϴ� ���� (X �Ǵ� Y) ��ǥ ���� ��´�.

        if (coordinateReferencedBy_i < coordinateReferencedBy_j) {
            return -1;		// Index i �� �����ϴ� ���� ��ǥ ���� �� �۴�.
        } else if (coordinateReferencedBy_i > coordinateReferencedBy_j) {
            return +1;		// Index j �� �����ϴ� ���� ��ǥ ���� �� �۴�.

        } else {
        	// ��ǥ ���� �����ϴ�.
        	// �� ��쿡��, PointSet ������ ���� ���� (��, PointSet �� ���Ե� ����) �� ���Ѵ�.
        	// ���� ���� ���� ����Ų��. �� ���� ���� ���� ���� ���� ���̴�.
            if (this.elementAt(i) < this.elementAt(j)) {
                return -1;
            } else if (this.elementAt(i) > this.elementAt(j)) {
                return +1;
            } else {
                return 0;		// ���� ���� ���� ����, ����� ������ ���� ����.
            }
        }
    }

    private int pivotByMedian(int left, int right) {
    	// Pivot �� ��ġ, �� {���� ����, ���� ������, �� ���} ��
    	// �� ���� �߿��� median �� ������ ��ġ�� ã���ش�.
    	// ������ ������ ������ 2 ���̾ ���������� �۵���

    	
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
        this.swap(left, pivotIndexByMedian);	// pivot ���Ҹ� ������ ���� ���ʿ� ���� ���´�.
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
    	// ����, ���� ����� �� �߿��� ��ǥ�� ���� ū ���� ã�� �� �ڿ� ���� ���´�.
        if (this.size() >= 2) {
            int indexOfMaxCoordinate = 0;
            for (int index = 1; index < this.size(); index++) {
            	// �� ���� ���� ��, x (�Ǵ� y) ��ǥ ���� ������ ��쿡��,
            	// PointSet ������ ������ ������ ���� ū ������ ���ֵȴ�.
                if (this.compareCoordinate(indexOfMaxCoordinate, index) < 0) {
                    indexOfMaxCoordinate = index;
                }
            }
            this.swap(indexOfMaxCoordinate, this.size() - 1);
            
            // Step 2: ���� ������ �� ������ ���� �����ϰ� �����Ѵ�
            this.qSortByCoordinateRecursively(0, this.size() - 2);
        }
    }

    private void generateReferenceListOrderedByCoordinate() {
    	
    	// �� method �� ��ü�� ������ ����, �����ڿ��� ���������� ����ȴ�.
    	// �� method �� �����ϴ� ������, ReferenceList (this) �� empty ���� �̴�.
    	// Step 1:
    	// ������ index �� 0 ���� ���ʷ� this.capacity() ��ŭ ä���.
    	// �����ڿ��� �־��� PointSet �� ũ�⸦ capacity �� �����ߴ�.
    	// (�� method �� ����ϴ� ������ �ڵ带 �� ��)
    	// �׷��Ƿ� this.capacity() �� �־��� PointSet �� ũ���̴�.
        for (int index = 0; index < this.capacity(); index++) {
            this.add(Integer.valueOf(index));
        }
        
        // Step 2: ������ index �� ��ǥ �� ������ �����Ѵ�.
        this.sortbyCoordinate();
        
        // ������ ���� ������, Px �Ǵ� Py �� �������.
    }
}
