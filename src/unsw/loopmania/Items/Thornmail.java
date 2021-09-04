package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.*;

/**
 * Thornmail armour has weaker protection against enemies than
 * regular armour but also deals some of the attack back to the enmy
 */
public class Thornmail extends Armour {
    public static final int PRICE = 350;
    private static final double DMGREDUCTION = 0.3;
    private static final double DMGTHORNS = 0.1;
    private double damageReduction;
    private double thorns;

    /**
     * Main constructor for Thronmail class
     * @param x int Column coordinate in inventory
     * @param y int Row coordinate in inventory
     * @param level int Level of thornmail
     */
    public Thornmail(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level);
        thorns = DMGTHORNS + 0.01*(level - 1);
        damageReduction = DMGREDUCTION + 0.03*(level-1);
        super.setType("thornmail");
    }
    
    /**
     * Constructor for Thornmail class used in testing
     * @param level int Level of thornmail
     */
    public Thornmail(int level) {
        super(level);
        thorns = DMGTHORNS + 0.01*(level - 1);
        damageReduction = DMGREDUCTION + 0.03*(level-1);
        super.setType("thornmail");
    }

    /**
     * FIgures out how much damage armour prevents
     * and how much damage to deal to the enemy
     * @param damage double Incoming damage
     * @return double outgoing damage (after armour reduces it)
     */
    public double protect(double damage, Enemy enemy) {
        enemy.takeDamage(damage * thorns);
        return damage * (1 - damageReduction);
    }
    
}