package unsw.loopmania.Items;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Boss;
import unsw.loopmania.Enemies.Enemy;

/**
 * Anduril class. Contains information about rare item anduril
 * most powerful weapon in the game 
 * @author Group FRIDGE
 */
public class Anduril extends Sword implements RareItem{
    private static final double DAMAGE = 50.0;
    private static final int SELLPRICE = 1500;
    private static final int REPLACECOST = 375;
    private static final int BOSSDAMAGEMULTIPLIER = 3;

    /**
     * Constructor for anduril class
     * @param x X coordinate in unequipped inventory
     * @param y Y coordinate in unequipped inventory
     * @param level Level of anduril (always 0)
     */
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level);
        super.setType("anduril");
    }

    /**
     * Constructor for anduril class
     * @param level Level of anduril (always 0)
     */
    public Anduril(int level) {
        super(level);
        super.setType("anduril");
    }

    /**
     * Gets the sell price of an anduril
     */
    @Override 
    public int getSellPrice() {
        return SELLPRICE;
    }

    /**
     * Gets the replace cost of an anduril
     */
    @Override
    public int getReplaceCost() {
        return REPLACECOST;
    }

    /**
     * returns damage weapon deals
     * @param enemy : Enemy being whacked
     * @return damage applicable for enemy
     */
    @Override
    public double getDamage(Enemy enemy) {
        // double damage = super.getDamage();
        if (enemy instanceof Boss){
            return DAMAGE * BOSSDAMAGEMULTIPLIER;
        } else {
            return DAMAGE;
        }
    }
}
