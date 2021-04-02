package model;

import list.ArrayList;

public class PointSet extends ArrayList<Point>{
	  public PointSet(int givenCapacity){
	        super(givenCapacity);
	    }

	    public Point pointReferencedByIndex(ReferenceList referenceList, int index){
	    	// 주어진 referenceList 의 index 위치의 값이 참조하는 점을 PointSet 에서 얻는다.
	    	// 자주 사용해야 할 코드이므로, 이렇게 함수로 만들어 사용한다.
	        if(referenceList.orderIsValid(index)){
	        	// index 가 유효해야 한다
	            return this.elementAt(referenceList.elementAt(index));
	        } else {
	            return null;
	        }
	    }
}
