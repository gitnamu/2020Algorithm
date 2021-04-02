package model;

public class ReferenceListOrderedByX extends ReferenceListOrderedByCoordinate{
    public ReferenceListOrderedByX(PointSet givenPointSet) {
        super(givenPointSet);
    }

    @Override
    protected int coordinateReferencedByIndex(int i) {
        return pointSet().pointReferencedByIndex(this, i).x;
    }
}
