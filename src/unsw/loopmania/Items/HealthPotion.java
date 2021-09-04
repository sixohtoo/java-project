package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;

/**
 * HealthPotion heals the character by a percentage of its health when used
 */
public class HealthPotion extends Potion{
    public static final int SELLPRICE = 40;
    public static final int REPLACECOST = 20;
    public static final int BASECOST = 100;
    public static final int INCREMENTCOST = 50;
    private int cost;
    /**
     * The class of health potion
     * @param x X coordinate of HealthPotion in unequipped inventory
     * @param y Y coordinate of HealthPotion in unequipped inventory
     */
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        super.setType("healthpotion");
        cost = BASECOST;
    }

    /**
     * Constructor for health potion class
     */
    public HealthPotion() {
        super();
        super.setType("healthpotion");
    }

    /**
     * Will heal the given character
     * @param character
     */
    public void use(Character character) {
        character.restoreHealth(40);
    }

    /**
     * Cost of healthpotion increases each time
     */
    @Override
    public int getPrice() {
        return cost;
    }

    /**
     * HealthPotions always sell for the same amount
     * (40% of original cost)
     */
    @Override
    public int getSellPrice() {
        return SELLPRICE;
    }

    /**
     * HealthPotions have a constant replacecost
     */
    @Override
    public int getReplaceCost() {
        return REPLACECOST;
    }

    /**
     * Each HealthPotion bought costs INCREMENTCOST more
     */
    public void increaseCost(int timesBought) {
        cost += (INCREMENTCOST * timesBought);
    }

    /**
     * Sets the price of healthpotions back to its original value
     */
    @Override
    public void reset_cost() {
        cost = BASECOST;
    }

	

}
