package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Thief;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Factories.BuildingFactory;
import unsw.loopmania.Factories.EnemyFactory;

public class VampireTest {
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
    public void moveVampire() {
        EnemyFactory eF = new EnemyFactory();
        BuildingFactory bF = new BuildingFactory();
        Enemy vampire = eF.create(new PathPosition(0, createPath()), "vampire");
        Building campfire = bF.create(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), "campfire");
        ((Vampire)vampire).move((CampfireBuilding)campfire);
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 1);
    }

    @Test
    public void vampireCritTest() {
        Character character = new Character();
        LoopManiaWorld.setSeed(2);
        EnemyFactory eF = new EnemyFactory();
        Enemy vampire = eF.create( "vampire");
        vampire.attack((Hero)character);
        assertTrue(character.getHealth() < 66);
        vampire.attack((Hero)character);
        assertTrue(character.getHealth() < 30);
    }
}
