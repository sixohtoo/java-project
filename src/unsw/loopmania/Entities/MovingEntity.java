package unsw.loopmania.Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    /**
     * object holding position in the path
     */
    public PathPosition position;
    public IntegerProperty health;

    /**
     * Create a moving entity which moves up and down the path in position
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position, int health) {
        super();
        this.position = position;
        this.health = new SimpleIntegerProperty(health);
    }

    public MovingEntity(int health) {
        super();
        this.health = new SimpleIntegerProperty(health);
    }

    public PathPosition getPosition() {
        return position;
    }

    public int getIndexOfPosition() {
        return position.getPositionInPath();
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }
    /**
     * Takes health off entity for damage taken
     * @param attackdamage amount of damage to be taken
     */
    public void takeDamage(double attackDamage) {
        health.set(health.get() - (int)attackDamage);
    }


    /**
     * Checks if moving entity is dead
     * @return boolean for if entity health is <= 0
     */
    public boolean isDead() {
        if (health.get() <= 0) {
            return true;
        }
        return false;
    }

    public PathPosition clonePosition() {
        
        return position.clone();
    }
}
