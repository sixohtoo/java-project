package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.*;

/**
 * The sheild has a change to completely block an enemy's attack.
 * It also reduces the vampire's crit chance
 */
public class Shield extends Protection {
    public static final int PRICE = 400;
    private int blockChance = 7;
    
    /**
     * Constructor for shield class
     * @param x X coordinate of shield in inventory
     * @param y Y coordinate of shield in inventory
     * @param level Level of shield
     */
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(level, PRICE, x, y);
        blockChance += 3 * level;
        super.setType("shield");
    }

    /**
     * Constructor for shield class
     * @param level Level of shield
     */
    public Shield(int level) {
        super(level, 400);
        blockChance += 3*level;
        super.setType("shield");
    }

    /**
     * Given the damage being inflicted on the character and will return the amount
     * of damage that will be inflicted after the reduction from the protection
     * @param damage the total damage being given
     * @return the damage after the reduction from protection has been applied
     */
    @Override
    public double protect(double damage) {
        int randomInt = LoopManiaWorld.getRandNum();
        if (randomInt < blockChance) {
            return 0;
        }
        return damage;
    }

    /**
     * Gets the shield's block chance
     * @return
     */
    public int getBlockChance() {
        return blockChance;
    }

    /**
     * Sets the shield's block chance
     * @param blockChance
     */
    public void setBlockChance(int blockChance){
        this.blockChance = blockChance;
    }
}
