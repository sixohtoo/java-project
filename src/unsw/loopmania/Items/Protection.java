package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.*;

/**
 * Abstract class for all protection items
 */
public abstract class Protection extends Item{
    private int level;
    private double price;
    /**
     * Constructor for protection class
     * @param level Level of protection item
     * @param price cost of protection item
     * @param x X coordinate of protection item in inventory
     * @param y Y coordinate of protection item in inventory
     */
    public Protection (int level, double price, SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.level = level;
        this.price = price * Math.pow(1.15, level - 1);
    }

    /**
     * Constructor for protection class
     * @param level Level of protection item
     * @param price Cost of protection item
     */
    public Protection(int level, int price) {
        super();
        this.level = level;
        this.price = price * Math.pow(1.15, level - 1);
    }

    /**
     * Given the damage being inflicted on the character and will return the amount
     * of damage that will be inflicted after the reduction from the protection
     * @param damage the total damage being given
     * @return the damage after the reduction from protection has been applied
     */
    public double protect(double damage) {
        return damage;
    }

    /**
     * Given the damage being inflicted on the character and will return the amount
     * of damage that will be inflicted after the reduction from the protection
     * taking into account the enemy being attacked
     * @param damage the total damage being given
     * @param enemy the enemy being attacked
     * @return the damage after the reduction from protection has been applied
     */
    public double protect(double damage, Enemy enemy) {
        return damage;
    }
    
    /**
     * Gets the item's level
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * gets the item's price
     */
    @Override
    public int getPrice() {
        return (int)price;
    }

    /**
     * Gets the item's sellprice
     */
    @Override
    public int getSellPrice() {
        return (int) (price * 0.4);
    }

    /**
     * Gets the item's replacecost
     */
    @Override
    public int getReplaceCost() {
        return (int) (price * 0.2);
    }
}
