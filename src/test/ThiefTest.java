package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Factories.EnemyFactory;
import unsw.loopmania.Factories.HeroFactory;
import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.Heroes.Inventory;

public class ThiefTest {
    EnemyFactory eF;

    public ThiefTest() {
        eF = new EnemyFactory();
    }
    private List<Pair<Integer, Integer>> createPath() {
        List<Pair<Integer, Integer>> l = new ArrayList<Pair<Integer, Integer>>();
        l.add(new Pair<Integer, Integer>(0, 0));
        l.add(new Pair<Integer, Integer>(0, 1));
        l.add(new Pair<Integer, Integer>(0, 2));
        l.add(new Pair<Integer, Integer>(1, 2));
        l.add(new Pair<Integer, Integer>(2, 2));
        l.add(new Pair<Integer, Integer>(2, 1));
        l.add(new Pair<Integer, Integer>(2, 0));
        l.add(new Pair<Integer, Integer>(1, 0));
        return l;
    }

    @Test
    public void thiefMovementTest() {
        Enemy thief = eF.create(new PathPosition(1, createPath()), "thief");
        assertEquals(thief.getX(), 0);
        assertEquals(thief.getY(), 1);
        thief.move();
        assertEquals(thief.getX(), 0);
        assertEquals(thief.getY(), 0);
    }

    @Test
    public void thiefStealItem() {
        Enemy thief = eF.create( "thief");
        Character character = new Character();
        character.addUnequippedItem("healthpotion", 0);
        character.addUnequippedItem("healthpotion", 0);
        character.addUnequippedItem("healthpotion", 0);
        assertEquals(character.getUnequippedInventoryItemsNum(), 3);
        LoopManiaWorld.setSeed(0);
        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(thief);
        List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
        BattleRunner b = new BattleRunner();
        b.setCharacter(character);
        b.runBattle(enemies, allies, new ArrayList<TowerBuilding>());
        assertEquals(character.getHealth(), 100);
        assertEquals(character.getUnequippedInventoryItemsNum(), 0);
    }

    @Test
    public void thiefStealItemWithAllies() {
        Enemy thief = eF.create( "thief");
        Character character = new Character();
        character.addUnequippedItem("healthpotion", 0);
        character.addUnequippedItem("healthpotion", 0);
        character.addUnequippedItem("healthpotion", 0);
        HeroFactory hF = new HeroFactory();
        List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
        allies.add((AlliedSoldier)hF.create());
        assertEquals(character.getUnequippedInventoryItemsNum(), 3);
        LoopManiaWorld.setSeed(0);
        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(thief);
        BattleRunner b = new BattleRunner();
        b.setCharacter(character);
        b.runBattle(enemies, allies, new ArrayList<TowerBuilding>());
        assertEquals(character.getHealth(), 100);
        assertEquals(character.getUnequippedInventoryItemsNum(), 1);
    }

    @Test
    public void thiefChanceRemoveItem() {
        LoopManiaWorld.setSeed(7);
        Character character = new Character();
        Enemy thief = eF.create( "thief");
        character.addUnequippedItem("healthpotion", 0);
        assertEquals(character.getUnequippedInventoryItemsNum(), 1);
        LoopManiaWorld.setSeed(0);
        thief.attack((Hero) character);
        assertEquals(character.getUnequippedInventoryItemsNum(), 1);
        LoopManiaWorld.setSeed(2);
        thief.attack((Hero) character);
        assertEquals(character.getUnequippedInventoryItemsNum(), 0);


    }

    @Test
    public void thiefDoesNothingToAllies() {
        LoopManiaWorld.setSeed(7);
        Enemy thief = eF.create( "thief");
        HeroFactory hF = new HeroFactory();
        Hero alliedSoldier = hF.create();
        thief.attack(alliedSoldier);
        assertEquals(((AlliedSoldier)alliedSoldier).getHealth(), 30);


    }
}
