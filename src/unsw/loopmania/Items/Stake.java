package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Vampire;

/**
 * The stake is a fairly weak weapon that deals a lot of extra damage to vampires
 */
public class Stake extends Weapon{
    public static final double PRICE = 350.0;
    public static final double DAMAGE = 20.0;
    public static final int BASECRIT = 50;
    private double critAttack;
    /**
     * Constructor for Stake class
     * @param x X coordinate of stake in inventory
     * @param y Y coordinate of stake in inventory
     * @param level Level of stake
     */
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level, PRICE, DAMAGE);
        critAttack = BASECRIT * Math.pow(1.1, level);
        super.setType("stake");
    }

    /**
     * Constructor for Stake class
     * @param level Level of stake
     */
    public Stake(int level) {
        super(level, PRICE, DAMAGE);
        critAttack = BASECRIT * Math.pow(1.1, level);
        super.setType("stake");
    }

    /**
     * returns damage weapon deals
     * @param enemy The enemy being attacked
     * @return damage applicable for enemy
     */
    public double getDamage(Enemy enemy) {
        if (enemy instanceof Vampire){
            return critAttack;
        }
        return super.getDamage();
    }
}
