package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BattleRunner;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Enemies.Enemy;

/**
 * The staff is a weak hitting weapon but has a chance to convert
 * an enemy into an alliedSoldier for a few rounds
 */
public class Staff extends Weapon{
    public static final double PRICE = 700.0;
    public static final double DAMAGE = 18.0;
    public static final int BASETRANCECHANCE = 30;
    private int tranceChance;

    /**
     * Constructor for staff class
     * @param x X coordinate of staff in inventory
     * @param y Y coordinate of staff in inventory
     * @param level Level of staff
     */
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(x, y, level, PRICE, DAMAGE);
        tranceChance = BASETRANCECHANCE + level * 3;
        super.setType("staff");
    }    

    /**
     * Constructor for staff class
     * @param level Level of staff
     */
    public Staff(int level) {
        super(level, PRICE, DAMAGE);
        tranceChance = BASETRANCECHANCE + level * 3;
        super.setType("staff");
    }
    

    /** 
     * Attacks Enemy and potentially cast a spell on them
     * @param enemy The enemy being attacked
     * @param bR the BattleRunner class (to convert the enemy)
     * @return true if enemy tranced, false otherwise
     */
    public boolean castSpell(Enemy enemy, BattleRunner bR) {
        int randNum = LoopManiaWorld.getRandNum();
        if (randNum <  tranceChance) {
            enemy.takeDamage(super.getDamage());
            bR.convertEnemyToAlly(enemy);
            return true;
        }
        return false;
    }
} 
