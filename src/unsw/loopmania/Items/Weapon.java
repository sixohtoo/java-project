package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Base class for all weapons
 */
public abstract class Weapon extends Item{
    public int level;
    public double damage;
    public double price;

    /**
     * Constructor for abstract weapon class
     * @param x X coordinate of weapon in inventory
     * @param y Y coordinate of weapon in inventory
     * @param level Level of weapon
     * @param price Price of weapon
     * @param damage Damage of weapon
     */
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int level, double price, Double damage) {
        super(x,y);
        this.level = level;
        this.price = price * Math.pow(1.15, level - 1);
        this.damage = damage * Math.pow(1.1, level - 1);
    }

    /**
     * Constructor for abstract weapon class
     * @param level Level of weapon
     * @param price Price of weapon
     * @param damage Damage of weapon
     */
    public Weapon(int level, Double price, Double damage) {
        super();
        this.level = level;
        this.price = price * Math.pow(1.15, level - 1);
        this.damage = damage * Math.pow(1.1, level - 1);
    }

    /**
     * Gets the weapon's damage
     * @return the damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Gets the weapon's level
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the weapon's price
     * @return the price
     */
    @Override
    public int getPrice() {
        return (int)price;
    }

    /**
     * Gets the weapon's sell price
     * @return the sell price
     */
    @Override
    public int getSellPrice() {
        return (int)(price * 0.4);
    }

    /**
     * Gets the weapon's replace cost
     * @return the replace cost
     */
    @Override
    public int getReplaceCost() {
        return (int)(price * 0.2);
    }
}
