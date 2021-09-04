package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;

/**
 * A rare item that makes the character invincible for a single round
 */
public class InvinciblePotion extends Potion implements RareItem{
    public static final int NOCOST = 0;
    public static final int SELLPRICE = 1000;
    public static final double REPLACEPERCENTAGE = 0.4;

	/**
	 * Constructor for InvinciblePotion class
	 * @param x X coordinate of invinciblepotion in the unequipped inventory
	 * @param y Y coordinate of invinciblepotion in the unequipped inventory
	 */
    public InvinciblePotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("invinciblepotion");
    }

	/**
	 * Constructor for InvinciblePotion class
	 */
    public InvinciblePotion() {
        super();
        super.setType("invinciblepotion");
    }

	/**
	 *  Rare items can't be bought so the cost never increases
	 */
	@Override
	public void increaseCost(int timesBought) {
        // Rare items can't be bought.
	}

	/**
	 * Makes the character invincible
	 */
	@Override
	public void use(Character character) {
		character.makeInvincible();
	}

	/**
	 * Rare items can't be bought so InvinciblePotion has no cost
	 */
	@Override
	public int getPrice() {
		return NOCOST;
	}

	/**
	 * Gets the sell price
	 */
	@Override
	public int getSellPrice() {
		return SELLPRICE;
	}

	/**
	 * Gets the replace cost
	 */
	@Override
	public int getReplaceCost() {
		return (int)(SELLPRICE * REPLACEPERCENTAGE);
	}

	/**
	 * Rare items can't be bought so price is never reset
	 */
	@Override
	public void reset_cost() {
		// rare items can't be bought
	}
    
}
