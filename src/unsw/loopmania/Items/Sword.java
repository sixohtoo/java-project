package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.*;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {
    public static final double PRICE = 350.0;
    public static final double DAMAGE = 35.0;
    /**
     * Constructor for sword class
     * @param x X coordinate of sword in inventory
     * @param y Y coordinate of sword in inventory
     * @param level Level of sword
     */
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level, PRICE, DAMAGE);
        super.setType("sword");
    }

    /**
     * Constructor for sword class
     * @param level Level of sword
     */
    public Sword(int level) {
        super(level, PRICE, DAMAGE);
        super.setType("sword");
    }

    /**
     * Gets the sword's damage
     * @param enemy Enemy being attacked
     * @return the damage dealt
     */
    public double getDamage(Enemy enemy) {
        return super.getDamage();
    }
    
}
