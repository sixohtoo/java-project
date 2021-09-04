package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.*;

/**
 * Treestump is a rare item that has additional protection against bosses
 */
public class TreeStump extends Shield implements RareItem{
    private static final int SELLPRICE = 1500;
    private static final int REPLACECOST = 375;

    /**
     * Main constructor for Treestump class
     * @param x int Column coordinate in inventory
     * @param y int Row coordinate in inventory
     * @param level int Level of Treestump (always 0)
     */
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level);
        super.setType("treestump");
    }

    /**
     * Main constructor for Treestump class
     * @param level Levl of treestump (always 0)
     */
    public TreeStump(int level) {
        super(level);
        super.setType("treestump");
        super.setBlockChance(50);
    }

    /**
     * gets the sell price
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
        return REPLACECOST;
    }

    /**
     * Figures out how much incoming damage to protect
     * @param damage The incoming damage
     * @param enemy The enemy that dealt the damage
     * @return the amount of damage done to the player after protection
     */
    @Override
    public double protect(double damage, Enemy enemy) {
        int blockChance = 25;

        int randomInt = LoopManiaWorld.getRandNum();
        if (enemy instanceof Boss) {
            blockChance += 50;            
        }
        if (randomInt < blockChance) {
            return 0;
        } else {
            return damage;
        }
    }
}
