package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Stake;


public class StakeTest {
    private BattleRunner b = new BattleRunner();
    @Test
    public void StakeLevelStoredTest() {
        Character c = new Character();
        Item stake = new Stake(3);

        assertEquals(1, c.getHighestLevel(stake));
        c.equip(stake, "weapon");
        assertEquals(3, c.getHighestLevel(stake));
    }

    @Test
    public void StakeDamage() {
        Character c = new Character();
        Item stake = new Stake(1);

        c.equip(stake, "weapon");
        Enemy slug = new Slug();
        assertEquals(slug.getHealth(), 50);
        c.attack(slug, b);
        assertEquals(slug.getHealth(), 30);
        Enemy vampire = new Vampire();
        assertEquals(150, vampire.getHealth());
        c.attack(vampire, b);
        assertEquals(95, vampire.getHealth());
    }

    @Test
    public void compareDamages() {
        Weapon stake = new Stake(1);
        Double damage = stake.getDamage();
        Double initialDamage = damage;
        for (int i = 2; i <= 10; i++) {
            Weapon nextStake= new Stake(i);
            assertEquals(nextStake.getDamage(), initialDamage * Math.pow(1.1, i - 1));
            damage = nextStake.getDamage();
        }
    }
}
