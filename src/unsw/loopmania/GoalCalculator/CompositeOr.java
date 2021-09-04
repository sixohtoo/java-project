package unsw.loopmania.GoalCalculator;

public class CompositeOr extends CompositeBinaryOperator{

    public CompositeOr(Composite left, Composite right) {
        super(left, right);
    }

    @Override
    public boolean applyOperation(Composite left, Composite right) {
        return left.getValue() || right.getValue();
    }
    
}
