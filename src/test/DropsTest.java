package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Enemies.Thief;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Protection;
import unsw.loopmania.LoopManiaWorld;

public class DropsTest {

    private Slug slug = new Slug();
    private Zombie zombie = new Zombie();
    private Vampire vampire = new Vampire();
    private Thief thief = new Thief();
    private List<String> rare = new ArrayList<String>();

    public DropsTest() {
        rare.add("theonering");
        rare.add("anduril");
        rare.add("treestump");
        rare.add("nuke");
        rare.add("invinciblepotion");
    }
    
    private void checkWeapon(int seed, String weapon, int level, Enemy enemy, Character c) {
        LoopManiaWorld.setSeed(seed);
        StaticEntity item = enemy.getLoot(c, 8, rare).get(0);
        System.out.println(item.getType());
        assertEquals(item.getType(), weapon);
        System.out.println(((Weapon)item).getLevel());
        assertEquals(((Weapon)item).getLevel(), level);
    }

    private void checkProtection(int seed, String protector, int level, Enemy enemy, Character c) {
        LoopManiaWorld.setSeed(seed);
        StaticEntity item = enemy.getLoot(c, 8, rare).get(0);
        System.out.println(item.getType());
        assertEquals(item.getType(), protector);
        assertEquals(((Protection)item).getLevel(), level);
    }

    private void checkOther(int seed, String itemString, Enemy enemy, Character c) {
        LoopManiaWorld.setSeed(seed);
        StaticEntity item = enemy.getLoot(c, 8, rare).get(0);
        System.out.println(item.getType());
        assertEquals(item.getType(), itemString);
    }

    private void checkCard(int seed, String itemString, Enemy enemy, Character c) {
        LoopManiaWorld.setSeed(seed);
        StaticEntity item = enemy.getLoot(c, 20, rare).get(0);
        System.out.println(item.getType());
        assertEquals(item.getType(), itemString);
    }

    @Test
    public void SlugDrop() {
        Character c1 = new Character();
        c1.setRareItems(rare);

        checkWeapon(22, "sword", 2, slug, c1);
        checkWeapon(184, "staff", 2, slug, c1);
        checkWeapon(311, "stake", 2, slug, c1);
        checkWeapon(33, "axe", 2, slug, c1);

        checkProtection(203, "shield", 2, slug, c1);
        checkProtection(26, "helmet", 2, slug, c1);
        checkProtection(307, "armour", 2, slug, c1);
        checkProtection(581, "thornmail", 2, slug, c1); 

        checkOther(6, "healthpotion", slug, c1);
        checkOther(41, "strengthpotion", slug, c1);

        checkOther(61, "theonering", slug, c1);
        checkOther(271, "anduril", slug, c1);
        checkOther(18, "invinciblepotion", slug, c1);
        checkOther(180, "nuke", slug, c1);
        checkOther(743, "treestump", slug, c1);

        Character c2 = new Character();
        c2.setRareItems(rare);

        checkWeapon(10, "sword", 1, slug, c2);
        checkWeapon(878, "staff", 1, slug, c2);
        checkWeapon(45, "stake", 1, slug, c2);
        checkProtection(37, "shield", 1, slug, c2);
        checkProtection(116, "helmet", 1, slug, c2);
        checkProtection(149, "armour", 1, slug, c2); 
        checkProtection(30, "thornmail", 1, slug, c2); 

        checkCard(50, "village", slug, c1);
        checkCard(62, "barracks", slug, c1);
        checkCard(104, "zombiepit", slug, c1);
        checkCard(54, "campfire", slug, c1);
        checkCard(256, "tower", slug, c1);
        checkCard(65, "trap", slug, c1);
        checkCard(69, "bank", slug, c1);

    }


    @Test
    public void ZombieDrop() {
        Character c1 = new Character();
        c1.setRareItems(rare);


        checkWeapon(22, "sword", 2, zombie, c1);
        checkWeapon(2, "staff", 2, zombie, c1);
        checkWeapon(45, "stake", 2, zombie, c1);
        checkWeapon(33, "axe", 2, zombie, c1);

        checkProtection(37, "shield", 2, zombie, c1);
        checkProtection(26, "helmet", 2, zombie, c1);
        checkProtection(149, "armour", 2, zombie, c1);
        checkProtection(120, "thornmail", 2, zombie, c1); 

        checkOther(6, "healthpotion", zombie, c1);
        checkOther(41, "strengthpotion", zombie, c1);

        checkOther(61, "theonering", zombie, c1);
        checkOther(271, "anduril", zombie, c1);
        checkOther(18, "invinciblepotion", zombie, c1);
        checkOther(180, "nuke", zombie, c1);
        checkOther(743, "treestump", zombie, c1);

        Character c2 = new Character();
        c2.setRareItems(rare);

        checkWeapon(10, "sword", 1, zombie, c2);
        checkWeapon(124, "staff", 1, zombie, c2);
        checkWeapon(54, "stake", 1, zombie, c2);
        checkWeapon(93, "axe", 1, zombie, c2);
        checkProtection(418, "shield", 1, zombie, c2);
        checkProtection(14, "helmet", 1, zombie, c2);
        checkProtection(89, "armour", 1, zombie, c2); 
        checkProtection(108, "thornmail", 1, zombie, c2); 

        checkCard(121, "village", zombie, c1);
        checkCard(62, "barracks", zombie, c1);
        checkCard(23, "vampirecastle", zombie, c1);
        checkCard(31, "campfire", zombie, c1);
        checkCard(19, "tower", zombie, c1);
        checkCard(3, "bank", zombie, c1);
        checkCard(27, "trap", zombie, c1);
    }

    @Test
    public void VampireDrop() {
        Character c1 = new Character();
        c1.setRareItems(rare);

        checkWeapon(10, "sword", 2, vampire, c1);
        checkWeapon(2, "staff", 2, vampire, c1);
        checkWeapon(45, "stake", 2, vampire, c1);
        checkWeapon(93, "axe", 2, vampire, c1);

        checkProtection(843, "shield", 2, vampire, c1);
        checkProtection(14, "helmet", 2, vampire, c1);
        checkProtection(89, "armour", 2, vampire, c1);
        checkProtection(30, "thornmail", 2, vampire, c1);

        checkOther(96, "healthpotion", vampire, c1);
        checkOther(38, "strengthpotion", vampire, c1);

        checkOther(61, "theonering", vampire, c1);
        checkOther(271, "anduril", vampire, c1);
        checkOther(18, "invinciblepotion", vampire, c1);
        checkOther(180, "nuke", vampire, c1);
        checkOther(743, "treestump", vampire, c1);

        Character c2 = new Character();
        c2.setRareItems(rare);

        checkWeapon(73, "sword", 1, vampire, c2);
        checkWeapon(177, "staff", 1, vampire, c2);
        checkWeapon(200, "stake", 1, vampire, c2);
        checkWeapon(62, "axe", 1, vampire, c2);

        checkProtection(65, "shield", 1, vampire, c2);
        checkProtection(77, "helmet", 1, vampire, c2);
        checkProtection(571, "armour", 1, vampire, c2); 
        checkProtection(23, "thornmail", 1, vampire, c2);


        checkCard(204, "village", vampire, c1);
        checkCard(213, "barracks", vampire, c1);
        checkCard(74, "vampirecastle", vampire, c1);
        checkCard(55, "zombiepit", vampire, c1);
        checkCard(7, "campfire", vampire, c1);
        checkCard(70, "tower", vampire, c1);
        checkCard(3, "trap", vampire, c1);
        checkCard(46, "bank", vampire, c1);
    }

    @Test
    public void thiefDrop() {
        Character c1 = new Character();
        c1.setRareItems(rare);

        checkWeapon(22, "sword", 2, thief, c1);
        checkWeapon(184, "staff", 2, thief, c1);
        checkWeapon(311, "stake", 2, thief, c1);
        checkWeapon(33, "axe", 2, thief, c1);

        checkProtection(203, "shield", 2, thief, c1);
        checkProtection(26, "helmet", 2, thief, c1);
        checkProtection(307, "armour", 2, thief, c1);
        checkProtection(581, "thornmail", 2, thief, c1); 

        checkOther(76, "healthpotion", thief, c1);
        checkOther(41, "strengthpotion", thief, c1);

        checkOther(61, "theonering", thief, c1);
        checkOther(271, "anduril", thief, c1);
        checkOther(18, "invinciblepotion", thief, c1);
        checkOther(180, "nuke", thief, c1);
        checkOther(743, "treestump", thief, c1);

        Character c2 = new Character();
        c2.setRareItems(rare);

        checkWeapon(326, "sword", 1, thief, c2);
        checkWeapon(2, "staff", 1, thief, c2);
        checkWeapon(45, "stake", 1, thief, c2);
        checkProtection(37, "shield", 1, thief, c2);
        checkProtection(116, "helmet", 1, thief, c2);
        checkProtection(149, "armour", 1, thief, c2); 
        checkProtection(30, "thornmail", 1, thief, c2); 

        checkCard(54, "zombiepit", thief, c1);
        checkCard(6, "vampirecastle", thief, c1);
    }
}


