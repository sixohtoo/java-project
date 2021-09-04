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

public class ZombieTest {
    EnemyFactory eF;

    public ZombieTest() {
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
    public void zombieMovementTest() {
        new LoopManiaWorld(602);
        List<Pair<Integer, Integer>> path = createPath();
        Enemy zombie = eF.create(new PathPosition(0, path), "zombie");
        Pair<Integer, Integer> oldPos = new Pair<Integer, Integer>(zombie.getX(), zombie.getY());
        zombie.move();
        Pair<Integer, Integer> currentPos = new Pair<Integer, Integer>(zombie.getX(), zombie.getY());
        assertEquals(currentPos, oldPos);
        zombie.move();
        currentPos = new Pair<Integer, Integer>(zombie.getX(), zombie.getY());
        assertNotEquals(currentPos, oldPos);
    }

    @Test
    public void zombieBiteSoldierTest() {
        new LoopManiaWorld(45);
        HeroFactory hF = new HeroFactory();
        Character c = new Character();
        List<Enemy> enemies = new ArrayList<Enemy>();
        Enemy zombie = eF.create("zombie");
        enemies.add(zombie);
        List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
        allies.add((AlliedSoldier)hF.create());
        allies.add((AlliedSoldier)hF.create());
        allies.add((AlliedSoldier)hF.create());
        BattleRunner b = new BattleRunner();
        b.setCharacter(c);
        b.runBattle(enemies, allies, new ArrayList<TowerBuilding>());
        assertTrue(zombie.getHealth() <= 0);
    }
}
