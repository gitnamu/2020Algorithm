package model;

public class ReferenceListOrderedByY extends ReferenceListOrderedByCoordinate{
    public ReferenceListOrderedByY(PointSet givenPointSet) {
        super(givenPointSet);
    }

    @Override
    protected int coordinateReferencedByIndex(int i) {
        return pointSet().pointReferencedByIndex(this, i).y;
    }
}
