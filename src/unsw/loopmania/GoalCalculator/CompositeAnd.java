package unsw.loopmania.GoalCalculator;

/**
 * Composite And class for determining composite goal
 */
public class CompositeAnd extends CompositeBinaryOperator{
    public CompositeAnd(Composite left, Composite right) {
        super(left, right);
    }

    /**
     * Takes return the && of the left and the right
     */
    @Override
    public boolean applyOperation(Composite left, Composite right) {
        return left.getValue() && right.getValue();
    }
}
