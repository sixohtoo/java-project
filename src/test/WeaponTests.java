package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
public class WeaponTests {
    private LoopManiaWorld world;
    public WeaponTests() {
        world = new LoopManiaWorld(1);
    }

    @Test
    public void testSword() {
        Sword s = new Sword(1);
        assertEquals(35, s.getDamage());
        assertEquals(350, s.getPrice());
        assertEquals(140, s.getSellPrice());
        assertEquals(1, s.getLevel());
    } 

    @Test
    public void testStake() {
        Stake s = new Stake(1);
        Enemy p = new Slug();
        Enemy v = new Vampire();
        assertEquals(20, s.getDamage(p));
        assertEquals(55, (int)s.getDamage(v));
        assertEquals(350, s.getPrice());
        assertEquals(140, s.getSellPrice());
        assertEquals(1, s.getLevel());

    } 
    @Test
    public void testStaff() {

        System.out.println(LoopManiaWorld.getRandNum());
        System.out.println(LoopManiaWorld.getRandNum());
        System.out.println(LoopManiaWorld.getRandNum());
        System.out.println(LoopManiaWorld.getRandNum());

        Staff s = new Staff(1);
        assertEquals(18, s.getDamage());
        assertEquals(700, s.getPrice());
        assertEquals(280, s.getSellPrice());
        assertEquals(1, s.getLevel());
        System.out.println(LoopManiaWorld.getRandNum());
    } 
}
