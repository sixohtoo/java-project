package unsw.loopmania.GoalCalculator;

/**
 * Abstract class for all Composite binary operators.
 */
public abstract class CompositeBinaryOperator implements Composite{
    private Composite right, left;

    public CompositeBinaryOperator(Composite left, Composite right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the boolean value of the left and right composite nodes
     */
    @Override
    public boolean getValue() {
        return applyOperation(left, right);
    }

    public abstract boolean applyOperation(Composite left, Composite right);

    /**
     * Gets the maximum amount of a goal needed to win the game
     */
    public int getMax(String type) {
        return Integer.max(right.getMax(type), left.getMax(type));
    }
}
