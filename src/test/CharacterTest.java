package test;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Factories.RareItemFactory;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.Protection;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Sword;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.Test;

public class CharacterTest {
    private Character c = new Character();
    
    private BattleRunner b = new BattleRunner();


    @Test
    public void CharacterExistsTest() {
        assertTrue(c.shouldExist().get());
    }

    @Test
    public void CharacterStartStatsTest() {
        Character c = new Character();
        assertEquals(100, c.getHealth());
        assertEquals(0, c.getGold());
        assertEquals(0, c.getXP().get());
    }
    @Test
    public void attackTest(){
        Enemy e = new Slug();
        c.attack(e, b);
        assertEquals(45, e.getHealth());
    }

    @Test
    public void attackSwordTest(){
        Enemy e = new Slug();
        Sword s = new Sword(1);
        Item swordItem = (Item) s;
        c.equip(swordItem, "weapon");
        c.attack(e, b);
        assertEquals(15, e.getHealth());
    }

    @Test
    public void attackStakeTest(){
        Enemy e1 = new Vampire();
        Stake s = new Stake(1);
        Item stakeItem = (Item) s;
        c.equip(stakeItem, "weapon");
        c.attack(e1, b);
        assertEquals(95, e1.getHealth());
        Enemy e2 = new Slug();
        c.attack(e2, b);
        assertEquals(30, e2.getHealth());

    }

    @Test
    public void attackStaffTest(){

        Staff s = new Staff(1);
        Item staffItem = (Item) s;
        LoopManiaWorld.setSeed(40);
        c.equip(staffItem, "weapon");
        Enemy e = new Slug();
        c.attack(e, b);
        assertEquals(32, e.getHealth());
    }

    @Test
    public void attackHelmetTest(){
        Enemy e = new Slug();
        Stake s = new Stake(1);
        Item stakeItem = (Item) s;
        Item helmetItem = (Item) new Helmet(1);
        c.equip(stakeItem, "weapon");
        c.equip(helmetItem, "helmet");
        c.attack(e, b);
        assertEquals(32, e.getHealth());
    }


    @Test
    public void takeDamageShieldTest() {
        Character c = new Character();
        Shield shield = new Shield(1);
        Enemy slug = new Slug();
        LoopManiaWorld world = new LoopManiaWorld(2);
        Item shieldItem = (Item) shield;
        c.equip(shieldItem, "shield");
        slug.attack((Hero)c);
        assertEquals(c.getHealth(), 100);
        c.takeDamage(10);
        assertEquals(c.getHealth(), 90);
    }

    @Test
    public void takeDamageArmourTest() {
        Character c = new Character();
        Armour armour = new Armour(1);
        Enemy slug = new Slug();
        Item armItem = (Item) armour;
        c.equip(armItem, "armour");
        // c.takeDamage(10);
        slug.attack((Hero)c);
        assertEquals(c.getHealth(), 94);
    }

    @Test
    public void takeDamageHelmetTest() {
        Character c = new Character();
        Protection helmet = new Helmet(1);
        Enemy slug = new Slug();
        Item helmetItem = (Item) helmet;
        c.equip(helmetItem, "helmet");
        // c.takeDamage(10);
        slug.attack((Hero)c);
        assertEquals(c.getHealth(), 93);
    }

    @Test
    public void invinciblityTest() {
        Character c = new Character();
        List<String> rareItems = new ArrayList<String>();
        rareItems.add("invinciblepotion");
        c.setRareItems(rareItems);
        c.addUnequippedItem("invinciblepotion", 0);
        assertTrue(c.canTakeDamage());
        c.drinkInvincibilityPotion();
        assertTrue(!c.canTakeDamage());
        c.makeVincible();
        assertTrue(c.canTakeDamage());

    }

    @Test
    public void strengthTest() {
        Character c = new Character();
        c.addUnequippedItem("strengthpotion", 0);
        Slug slug1 = new Slug();
        BattleRunner bR = new BattleRunner();
        c.attack(slug1, bR);
        assertEquals(slug1.getHealth(), 45);
        Slug slug2 = new Slug();
        c.drinkStrengthPotion();
        c.attack(slug2, bR);
        assertEquals(slug2.getHealth(), 30);

    }
/*
    @Test
    public void SellRemovesItemFromInventory() {
        Character c = new Character();
        Item sword = new Sword(1);
        assertEquals(0, c.numEquipmentInInventory());
        c.pickup(sword);
        assertEquals(1, c.numEquipmentInInventory());
        c.sellItem(sword);
        assertEquals(0, c.numEquipmentInInventory());

    }

    @Test
    public void SellGainGold() {
        Character c = new Character();
        Item sword = new Sword(1);
        c.sellItem(sword);
        assertEquals(140, c.getGold());
    }

    @Test
    public void SellLevelGoldValue() {
        Character c = new Character();
        Item sword = new Sword(5);
        c.sellItem(sword);
        assertEquals(224, c.getGold());
    }
    */
}
