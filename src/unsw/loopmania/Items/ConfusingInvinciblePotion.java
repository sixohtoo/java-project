package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Enemy;

/**
 * An invinciblepotion that thinks its also something else
 */
public class ConfusingInvinciblePotion extends InvinciblePotion implements ConfusedRareItem{
    private Item additional;

    /**
     * Constructor for Confusing InvinciblePotion class
     * @param x X coordinate of confused invinciblepotion in unequipped inventory
     * @param x Y coordinate of confused invinciblepotion in unequipped inventory
     * @param additional Item invinciblepotion thinks it is
     */
    public ConfusingInvinciblePotion(SimpleIntegerProperty x, SimpleIntegerProperty y, Item additional) {
        super(x, y);
        super.setType("invinciblepotion");
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
     * Treats the invinciblepotion as a treestump and reduces damage dealt by enemies.
     * This is only ever called if additional is a treestump
     */
	@Override
	public double protect(double damage, Enemy enemy) {
		return ((TreeStump)additional).protect(damage, enemy);
	}

    /**
     * Treats the invinciblepotion as an anduril and deals damage to an enemy.
     * This is only ever called if additional is an anduril
     */
	@Override
	public double getDamage(Enemy enemy) {
		return ((Anduril)additional).getDamage(enemy);
	}

}
