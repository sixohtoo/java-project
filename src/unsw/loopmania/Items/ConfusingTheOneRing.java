package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Enemies.Enemy;

/**
 * A one ring that thinks its also something else
 */
public class ConfusingTheOneRing extends TheOneRing implements ConfusedRareItem{
    private Item additional;
    
    /**
     * Constructor for Confusing Anduril class
     * @param x X coordinate of confused theonering in unequipped inventory
     * @param x Y coordinate of confused theonering in unequipped inventory
     * @param additional Item theonering thinks it is
     */
    public ConfusingTheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y, Item additional) {
        super(x, y);
        super.setType("theonering");
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
     * Treats the theonering as a treestump and reduces damage dealt by enemies.
     * This is only ever called if additional is a treestump
     */
	@Override
	public double protect(double damage, Enemy enemy) {
		return ((TreeStump)additional).protect(damage, enemy);
	}

    /**
     * Treats the theonering as an anduril and deals damage to an enemy
     * This is only ever called if additional is an anduril
     */
	@Override
	public double getDamage(Enemy enemy) {
		return ((Anduril)additional).getDamage(enemy);
	}

    /**
     * Treats the theonering as an invinciblepotion and makes the character invincible.
     * This is only ever called if additional is an invinciblepotion
     */
    @Override
    public void use(Character character) {
        ((InvinciblePotion)additional).use(character);
    }
}
