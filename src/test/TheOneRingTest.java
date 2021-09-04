package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.BuildingOnMove;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Factories.EnemyFactory;
import unsw.loopmania.Factories.ItemFactory;
import unsw.loopmania.Factories.RareItemFactory;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Shop.Shop;
import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.AlliedSoldier;

public class TheOneRingTest {

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
    public void ShopOneRingTest() {
        Character character = new Character();
        Shop shop = new Shop(character);
        List<String> rareItems = new ArrayList<String>();
        rareItems.add("theonering");
        RareItemFactory rF = new RareItemFactory(rareItems);
        Item ring = rF.create("theonering");
        assertEquals(0, character.getGold());
        assertEquals(1000, ring.getSellPrice());
        shop.sell(ring);
        assertEquals(character.getGold(), ring.getSellPrice());
        assertEquals(ring.getReplaceCost(), 0.4 * ring.getSellPrice());
        assertEquals(ring.getPrice(), 0);
    }

    @Test
    public void RevivalTest() {
        LoopManiaWorld world = new LoopManiaWorld(15);
        List<Enemy> enemies = new ArrayList<Enemy>();
        EnemyFactory eF = new EnemyFactory();
        ItemFactory iF = new ItemFactory();
        List<String> rareItems = new ArrayList<String>();
        rareItems.add("theonering");
        enemies.add(eF.create("vampire"));
        enemies.add(eF.create("vampire"));
        Character character = new Character();
        character.equip(iF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "sword", 1), "weapon");
        character.setRareItems(rareItems);
        // Character uses 2 TheOneRings to combat 2 vampires
        character.addUnequippedItem("theonering", 0);
        character.addUnequippedItem("theonering", 0);
        BattleRunner bR = new BattleRunner();
        bR.setCharacter(character);
        List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
        List<TowerBuilding> towers = new ArrayList<TowerBuilding>();
        bR.runBattle(enemies, allies, towers);
        System.out.println(character.getHealth());
        assertEquals(82, character.getHealth());
        assertFalse(character.hasRing());
    }
}
