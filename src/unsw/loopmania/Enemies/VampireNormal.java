package unsw.loopmania.Enemies;

import java.util.Objects;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.LoopManiaWorld;

/**
 * Attack strategy used by vampire before or 3 turns after a critical bite.
 * Vampire deals normal damage until they critical bite the hero
 */
public class VampireNormal implements VampireAttackStrategy{

    /**
     * Deals damage to Hero
     * @param hero the hero being attacked
     * @param vampire the vampire attacking
     */
    @Override
    public void attack(Hero hero, Vampire vampire) {
        int randomInt = LoopManiaWorld.getRandNum();
        if (hero instanceof Character) {
            Character c = (Character) hero;
            if ((randomInt < 20 && Objects.isNull(c.getShield())) || randomInt < 8) {
                critAttack(hero, vampire);
            } else {
                hero.takeDamage(vampire.getAttackDamage(), vampire);
            }
        } else {
            if (randomInt < 20) {
                critAttack(hero, vampire);
            } else {
                hero.takeDamage(vampire.getAttackDamage(), vampire);
            }
        }
    }

    /**
     * Deals critical damage to Hero
     * @param hero the hero being attacked
     * @param vampire the vampire attacking
     */
    private void critAttack(Hero hero, Vampire vampire) {
        int randomInt = LoopManiaWorld.getRandNum() % 10;
        hero.takeDamage(vampire.getAttackDamage()*2 + randomInt + 1, vampire);
        vampire.setStrategy(new VampireCritical());
    }
}
