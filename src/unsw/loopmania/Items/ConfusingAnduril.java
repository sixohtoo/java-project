package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Enemies.*;
import unsw.loopmania.Heroes.Character;

/**
 * An anduril that thinks its also something else
 */
public class ConfusingAnduril extends Anduril implements ConfusedRareItem{
    private Item additional;

    /**
     * Constructor for Confusing Anduril class
     * @param x X coordinate of confused anduril in unequipped inventory
     * @param x Y coordinate of confused anduril in unequipped inventory
     * @param level Level of confused anduril (always 0)
     * @param additional Item anduril thinks it is
     */
	public ConfusingAnduril(SimpleIntegerProperty x, SimpleIntegerProperty y, int level, Item additional) {
		super(x, y, level);
        super.setType("anduril");
        this.additional = additional;
	}

    /**
     * Gets the confused item
     */
    @Override
    public Item getAdditional() {
        return additional;
    }

    /**
     * Treats the anduril as a treestump and reduces damage dealt by enemies.
     * This is only ever called if additional is a treestump
     */
	@Override
	public double protect(double damage, Enemy enemy) {
		return ((TreeStump)additional).protect(damage, enemy);
	}

    /**
     * Treats the anduril as an invinciblepotion and makes the character invincible.
     * This is only ever called if additional is an invinciblepotion
     */
    @Override
    public void use(Character character) {
        ((InvinciblePotion)additional).use(character);
    }
}
