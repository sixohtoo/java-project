package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Buildings.ZombiePitBuilding;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Factories.BuildingFactory;
import unsw.loopmania.Factories.CardFactory;
import unsw.loopmania.Factories.EnemyFactory;
import unsw.loopmania.Factories.HeroFactory;
import unsw.loopmania.Factories.ItemFactory;
import unsw.loopmania.Items.Item;

public class FactoryTest {
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;

    public FactoryTest() {
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        x.set(0);
        y.set(0);
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

    @ParameterizedTest
    @ValueSource(strings = {"sword", "helmet", "armour", "shield", "stake", "staff"})
    public void itemFactoryLevelTest(String input) {
        ItemFactory f = new ItemFactory();
        LoopManiaWorld.setSeed(5);
        int level = (LoopManiaWorld.getRandNum() % 10) + 1;
        Item item1 = f.create(x, y, input, level);
        Item item2 = f.create(x, y, input, level);
        assertNotEquals(item1, item2);
    }

    @Test
    public void itemFactoryRegularTest() {
        ItemFactory f = new ItemFactory();
        Item potion = f.create(x, y, "healthpotion");
        Item potion2 = f.create(x, y, "healthpotion");
        assertNotEquals(potion, potion2);
    }

    @Test
    public void EnemyFactoryTest() {
        EnemyFactory e = new EnemyFactory();
        Enemy slug1 = e.create(new PathPosition(0, createPath()), "slug");
        Enemy slug2 = e.create("slug");
        assertEquals(slug1.getHealth(), 50);
        assertEquals(slug2.getHealth(), 50);
        Enemy vampire1 = e.create(new PathPosition(1, createPath()), "vampire");
        Enemy vampire2 = e.create("vampire");
        assertEquals(vampire1.getHealth(), 150);
        assertEquals(vampire2.getHealth(), 150);
        Enemy zombie1 = e.create(new PathPosition(2, createPath()), "zombie");
        Enemy zombie2 = e.create("zombie");
        assertEquals(zombie1.getHealth(), 100);
        assertEquals(zombie2.getHealth(), 100);
        assertNull(e.create(new PathPosition(2, createPath()), "breakfast"));
        assertNull(e.create("dinner"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"campfire", "barracks", "tower", "trap", "vampirecastle", "zombiepit", "village"})
    public void CardFactoryTest(String input) {
        CardFactory c = new CardFactory();
        Card card = c.create(x, y, input);
        StaticEntity e = (StaticEntity) card;
        String cardClass = e.getType();

        assertEquals(input, cardClass);
    }

    
    BuildingFactory b = new BuildingFactory();
    @Test
    public void createCampfireTest() {
        Building i = b.create(x, y, "campfire");
        assertTrue(i instanceof CampfireBuilding);
    }
    @Test
    public void createbarracksTest() {
        Building i = b.create(x, y, "barracks");
        assertTrue(i instanceof BarracksBuilding);
    }
    @Test
    public void createtowerTest() {
        Building i = b.create(x, y, "tower");
        assertTrue(i instanceof TowerBuilding);
    }
    @Test
    public void createtrapTest() {
        Building i = b.create(x, y, "trap");
        assertTrue(i instanceof TrapBuilding);
    }
    @Test
    public void createvampirecastleTest() {
        Building i = b.create(x, y, "vampirecastle");
        assertTrue(i instanceof VampireCastleBuilding);
    }
    @Test
    public void createzombiepitTest() {
        Building i = b.create(x, y, "zombiepit");
        assertTrue(i instanceof ZombiePitBuilding);
    }
    @Test
    public void createvillageTest() {
        Building i = b.create(x, y, "village");
        assertTrue(i instanceof VillageBuilding);
    }

    @Test
    public void createAlliedSoldierTest() {
        HeroFactory hF = new HeroFactory();
        assertTrue(hF.create() instanceof AlliedSoldier);
    }
}
