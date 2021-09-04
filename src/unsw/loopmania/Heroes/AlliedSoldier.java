package unsw.loopmania.Heroes;

import unsw.loopmania.Enemies.Enemy;

/**
 * AlliedSoldier class. Contains information about allied
 * soldiers that aid the player in battles and act as shields.
 * @author Group FRIDGE
 */
public class AlliedSoldier implements Hero{
    private int health;
    private int attackDamage;

    /**
     * Constructor for AlliedSoldier class.
     * All allied soldiers have 30 health
     * and 20 attack damage
     */
    public AlliedSoldier() {
        health = 30;
        attackDamage = 20;
    }

    /**
     * Getter to get Allied Soldier's health
     * @return int allied soldier's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter to set allied soldiers health.
     * Only used in testing
     * @param health int the allied soldiers health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Attack with this weapon to the given enemy
     * @param enemy that is being attacked
     * @return boolean that is true if the enemy was killed
     */
    public void attack(Enemy enemy) {
        enemy.takeDamage(attackDamage);
    }

    /**
     * Will subtract the given damage from the ally
     * @param attackDamage attack damage given to the ally
     * @return returns true if the ally died
     */
    public void takeDamage(double attackDamage, Enemy enemy) {
        health -= attackDamage;
    }

    /**
     * Checks whether Allied Soldier is dead
     * @param boolean true if dead
     */
    public boolean isDead() {
        if (health <= 0) {
            return true;
        } 
        return false;
    }
}
