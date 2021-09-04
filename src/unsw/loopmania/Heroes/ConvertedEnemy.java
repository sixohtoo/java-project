package unsw.loopmania.Heroes;

import unsw.loopmania.Enemies.Enemy;

/**
 * Enemies hit by a staff's critical hit are converted to an ConvertedEnemy
 */
public class ConvertedEnemy extends AlliedSoldier{
    private Enemy enemy;
    private int remainingTranceTime;
    /**
     * Constructor for ConvertedEnemy
     * @param enemy enemy that has been whacked
     */
    public ConvertedEnemy(Enemy enemy) {
        super();
        this.enemy = enemy;
    }
    /**
     * Attacks enemy
     */
    @Override
    public void attack(Enemy enemy) {
        super.attack(enemy);
        remainingTranceTime--;
    }
    /**
     * Checks if enemy is able to break free from trance
     * @return
     */
    public boolean canExitTrance() {
        if (remainingTranceTime == 0) {
            return true;
        } 
        return false;
    }

    /**
     * Gets the enemy that was turned into a ConvertedEnemy
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }
}
