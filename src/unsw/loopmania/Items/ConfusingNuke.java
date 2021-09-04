package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Heroes.Character;

/**
     * Constructor for Confusing Nuke class
     * @param x X coordinate of confused nuke in unequipped inventory
     * @param x Y coordinate of confused nuke in unequipped inventory
     * @param additional Item nuke thinks it is
     */
public class ConfusingNuke extends Nuke implements ConfusedRareItem{
    private Item additional;
	public ConfusingNuke(SimpleIntegerProperty x, SimpleIntegerProperty y, Item additional) {
		super(x, y);
		super.setType("nuke");
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
     * Treats the nuke as a treestump and reduces damage dealt by enemies.
     * This is only ever called if additional is a treestump
     */
	@Override
	public double protect(double damage, Enemy enemy) {
		return ((TreeStump)additional).protect(damage, enemy);
	}

	/**
     * Treats the nuke as an anduril and deals damage to an enemy
     * This is only ever called if additional is an anduril
     */
	@Override
	public double getDamage(Enemy enemy) {
		return ((Anduril)additional).getDamage(enemy);
	}
    
	/**
     * Treats the nuke as an invinciblepotion and makes the character invincible.
     * This is only ever called if additional is an invinciblepotion
     */
    @Override
    public void use(Character character) {
        ((InvinciblePotion)additional).use(character);
    }
}
