package unsw.loopmania.Enemies;

import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.LoopManiaWorld;

/**
 * Attack strategy used by vampire after dealing a critical hit which
 * deals additional damage for the next 3 turns
 */
public class VampireCritical implements VampireAttackStrategy{

    private int critTurns = 3;

    /**
     * Deals damage to Hero
     * @param hero the hero being attacked
     * @param vampire the vampire attacking
     */
    @Override
    public void attack(Hero hero, Vampire vampire) {
        int randomInt = LoopManiaWorld.getRandNum() % 10;
        hero.takeDamage(vampire.getAttackDamage()*2 + randomInt + 1, vampire);
        critTurns--;
        if (critTurns == 0) {
            vampire.setStrategy(new VampireNormal());
        }
    }
}
