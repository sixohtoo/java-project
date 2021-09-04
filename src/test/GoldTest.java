package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Heroes.Inventory;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Stake;
import unsw.loopmania.Shop.Shop;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.BattleRunner;


public class GoldTest {

    private Character character = new Character();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
    private List<TowerBuilding> towers = new ArrayList<TowerBuilding>();
    private BattleRunner b = new BattleRunner();

    @Test
    public void GetGoldFromSlugTest() {
        b.setCharacter(character);
        Enemy e = new Slug();
        enemies.add(e);
        assertEquals(0, character.getGold());
        List<Enemy> defeatedEnemies = b.runBattle(enemies, allies, towers);
        assertEquals(1,defeatedEnemies.size());
        assertEquals(100, character.getGold());
    }
    
    @Test
    public void GetGoldFromVampireTest() {
        b.setCharacter(character);
        Enemy vampire = new Vampire();
        character.equip(new Stake(2), "weapon");
        enemies.add(vampire);
        assertEquals(0, character.getGold());
        List<Enemy> defeatedEnemies = b.runBattle(enemies, allies, towers);
        assertEquals(1,defeatedEnemies.size());
        assertEquals(500, character.getGold());
    }

    @Test
    public void GetGoldFromZombieTest() {

        b.setCharacter(character);
        Enemy zombie = new Zombie();
        character.equip(new Sword(2), "weapon");
        enemies.add(zombie);
        assertEquals(0, character.getGold());
        List<Enemy> defeatedEnemies = b.runBattle(enemies, allies, towers);
        assertEquals(1,defeatedEnemies.size());
        assertEquals(250, character.getGold());
    }

    @Test
    public void getGoldFromSellingTest() {
        Item sword = new Sword(10);
        assertEquals(0, character.getGold());
        Shop s = new Shop(character);
        s.sell(sword);
        assertEquals(sword.getSellPrice(), character.getGold());
        Item sword2 = new Sword(2);
        assertTrue(sword.getSellPrice() > sword2.getSellPrice());
    }

    @Test
    public void getGoldFromItemReplaceTest() {
        Item shield = new Shield(3);
        character = new Character();
        Inventory inven = new Inventory(character);
        inven.setRareItems(new ArrayList<String>());
        inven.addUnequippedItem("shield", 3);
        for (int i = 0; i < 15; i++) {
            inven.addUnequippedItem("stake", 7);
        }
        assertEquals(0, character.getGold());
        inven.addUnequippedItem("sword", 1);
        assertEquals(shield.getReplaceCost(), character.getGold());
    }

    @Test
    public void getGoldFromCardReplaceTest() {
        LoopManiaWorld.setSeed(6);
        Inventory inven = new Inventory(character);
        for (int i = 0; i < 8; i++) {
            inven.loadCard("trap", 8);
        }
        assertEquals(0, character.getGold());
        inven.loadCard("campfire", 8);
        assertEquals(36, character.getGold());
        inven.loadCard("vampirecastle", 8);
        assertEquals(267, character.getGold());

    }
}
