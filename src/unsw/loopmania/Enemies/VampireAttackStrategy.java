package unsw.loopmania.Enemies;

import unsw.loopmania.Heroes.Hero;

/**
 * Interface to determine attack strategy used by vampire
 */
public interface VampireAttackStrategy {

    
    public void attack(Hero hero, Vampire vampire);
    
}