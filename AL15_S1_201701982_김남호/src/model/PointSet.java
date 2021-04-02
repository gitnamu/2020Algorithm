package model;

import list.ArrayList;

public class PointSet extends ArrayList<Point>{
	  public PointSet(int givenCapacity){
	        super(givenCapacity);
	    }

	    public Point pointReferencedByIndex(ReferenceList referenceList, int index){
	    	// �־��� referenceList �� index ��ġ�� ���� �����ϴ� ���� PointSet ���� ��´�.
	    	// ���� ����ؾ� �� �ڵ��̹Ƿ�, �̷��� �Լ��� ����� ����Ѵ�.
	        if(referenceList.orderIsValid(index)){
	        	// index �� ��ȿ�ؾ� �Ѵ�
	            return this.elementAt(referenceList.elementAt(index));
	        } else {
	            return null;
	        }
	    }
}
