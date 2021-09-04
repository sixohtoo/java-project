package test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Heroes.Hero;

public class ProtectionTests {

    //Armour Tests
    @Test
    public void damageProtectionTest() {
        Armour armour = new Armour(1);
        assertEquals(armour.getType(), "armour");
        for (int i = 0; i < 10; i++) {
            double damage = i * 10;
            assertEquals(damage * (1 - armour.getDamageReduction()), armour.protect(damage));
        }
    }

    @Test
    public void DifferentLevelTest() {
        Armour armour1 = new Armour(1);
        Armour armour2 = new Armour(10);
        double damage = 100;
        double armour1Damage = armour1.protect(damage);
        double armour2Damage = armour2.protect(damage);
        assertNotEquals(armour1Damage, armour2Damage);
    }

    //ShieldTests
    @Test
    public void ShieldTest() {
        
        LoopManiaWorld.setSeed(22);
        Shield s1 = new Shield(1);
        assertEquals(s1.protect(10), 0);
        assertEquals(s1.protect(10), 10);
        assertEquals(s1.protect(10), 10);
        assertEquals(s1.protect(10), 10);
    }
    
    @Test
    public void ShieldLevelTest() {
        LoopManiaWorld.setSeed(22);
        Shield s2 = new Shield(2);
        assertEquals(s2.protect(10), 0);
        assertEquals(s2.protect(10), 10);
        assertEquals(s2.protect(10), 0);
        assertEquals(s2.protect(10), 10);
    }

    @Test
    public void VampireShieldTest() {
        
        LoopManiaWorld.setSeed(6);
        Character c = new Character();
        Vampire v1 = new Vampire();
        Hero h = (Hero) c;
        v1.attack(h);
        assertEquals(57, c.getHealth());

        Shield s1 = new Shield(1);
        LoopManiaWorld.setSeed(6);
        Vampire v2 = new Vampire();
        c.equip(s1, "shield");
        v2.attack(h);
        assertEquals(39, c.getHealth());
    }

    //HelmetTests
    @Test
    public void helmetProtectTest(){
        Helmet h = new Helmet(1);
        assertEquals(7, h.protect(10));
        assertEquals(90, h.calcAttackDamage(100));

    }

    @Test
    public void helmetProtectLevelTest() {

        Helmet h = new Helmet(5);
        assertEquals(13, h.protect(20));
        assertEquals(94, h.calcAttackDamage(100));
    }
}
