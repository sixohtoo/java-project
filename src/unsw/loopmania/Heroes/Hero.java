package unsw.loopmania.Heroes;

import unsw.loopmania.Enemies.Enemy;

/**
 * Interface used to refer to all entities that attack for the character.
 * This includes the character, AlliedSoldier and ConvertedEnemy
 */
public interface Hero {
    public void takeDamage(double attackDamage, Enemy enemy);
    public boolean isDead();
    public void setHealth(int health);
}
