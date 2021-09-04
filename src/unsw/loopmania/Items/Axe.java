package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Axe is a powerful weapon limited but slow
 */
public class Axe extends Weapon{
    public static final double PRICE = 500.0;
    public static final double DAMAGE = 50.0;
    private boolean inAxeRestingPeriod = false;
    /**
     * Constructor for axe class
     * @param x X coordinate of axe in unequipped inventory
     * @param y Y coordinate of axe in unequipped inventory
     * @param level Level of axe
     */
    public Axe(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level, PRICE, DAMAGE);
        super.setType("axe");
    }

    /**
     * Constructor for axe class
     * @param level Level of axe
     */
    public Axe(int level) {
        super(level, PRICE, DAMAGE);
        super.setType("axe");
    }
    
    /**
     * returns damage weapon deals
     * @return damage applicable for enemy
     */
    @Override
    public double getDamage() {
        // If character is not allowed to attack, return 0 damage
        if (inAxeRestingPeriod) {
            inAxeRestingPeriod = false;
            return 0.0;
        } else {
            inAxeRestingPeriod = true;
            return super.getDamage();
        }
    }
}
