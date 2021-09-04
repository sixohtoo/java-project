package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Enemies.Enemy;

/**
 * A treestump that thinks its also something else
 */
public class ConfusingTreeStump extends Shield implements ConfusedRareItem{
    private Item additional;

    /**
     * Constructor for Confusing Treestump class
     * @param x X coordinate of confused treestump in unequipped inventory
     * @param x Y coordinate of confused treestump in unequipped inventory
     * @param level Level of confused treestump (always 0)
     * @param additional Item treestump thinks it is
     */
	public ConfusingTreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, int level, Item additional) {
		super(x, y, level);
        super.setType("treestump");
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
     * Treats the treestump as an anduril and deals damage to an enemy
     * This is only ever called if additional is an anduril
     */
	@Override
	public double getDamage(Enemy enemy) {
		return ((Anduril)additional).getDamage(enemy);
	}

    /**
     * Treats the treestump as an invinciblepotion and makes the character invincible.
     * This is only ever called if additional is an invinciblepotion
     */
    @Override
    public void use(Character character) {
        ((InvinciblePotion)additional).use(character);
    }
}
