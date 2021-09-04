package unsw.loopmania.Items;

import unsw.loopmania.Enemies.*;
import unsw.loopmania.Heroes.Character;

/**
 * Interface used to convert Rare items into Confused rare items
 */
public interface ConfusedRareItem {
    public double protect(double damage, Enemy e);
    public double getDamage(Enemy enemy);
    public void use(Character character);
    public Item getAdditional();
}
