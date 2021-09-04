package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;

/**
 * Strength potions increase the character's damage for the next fight
 */
public class StrengthPotion extends Potion{
    public static final int SELLPRICE = 60;
    public static final int REPLACECOST = 30;
    public static final int BASECOST = 50;
    public static final double BUFFDMG = 15.0;
    public int cost;
    /**
     * Constructor for the HealthPotion class
     * @param x X coordinate of healthpotion in unequipped inventory
     * @param y Y coordinate of healthpotion in unequipped inventory
     */
    public StrengthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        super.setType("strengthpotion");
        cost = BASECOST;
    }

    /**
     * Constructor for the HealthPotion class
     */
    public StrengthPotion() {
        super();
        super.setType("strengthpotion");
    }

    /**
     * Will heal the given character
     * @param character character being healed
     */
    public void use(Character character) {
        character.setBuff(BUFFDMG);
    }
    
    /**
     * Gets the price of the the Strengthpoition
     */
    @Override
    public int getPrice() {
        return cost;
    }

    /**
     * Gets the sell price of a strenghtpotion
     */
    @Override
    public int getSellPrice() {
        return SELLPRICE;
    }

    /**
     * Gets the replaceCost of a strength potion
     */
    @Override
    public int getReplaceCost() {
        return REPLACECOST;
    }

    /**
     *  Increases the cost of the strenghtpotion
     */
    public void increaseCost(int timesBought) {
        cost += (BASECOST * timesBought);
    }

    /**
     * Resets the cost of the strength potion
     */
    @Override
    public void reset_cost() {
        cost = BASECOST;        
    }
}
