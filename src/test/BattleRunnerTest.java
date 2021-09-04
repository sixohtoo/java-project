package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Factories.BuildingFactory;

public class BattleRunnerTest {

    private Character character = new Character();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<AlliedSoldier> allies = new ArrayList<AlliedSoldier>();
    private List<TowerBuilding> towers = new ArrayList<TowerBuilding>();
    private BattleRunner b = new BattleRunner();

    @Test
    public void BasicbattleTestVictory(){
        b.setCharacter(character);
        Enemy e = new Slug();
        enemies.add(e);
        List<Enemy> defeatedEnemies = b.runBattle(enemies, allies, towers);
        assertEquals(1,defeatedEnemies.size());
        assert(character.getHealth() < 100);
    }

    @Test
    public void BasicbattleTestDefeat(){
        BuildingFactory bF = new BuildingFactory();
        b.setCharacter(character);

        Enemy e = new Slug();
        Enemy e1 = new Slug();
        Enemy e2 = new Slug();
        Enemy e3 = new Slug();
        Enemy e4 = new Slug();
        enemies.add(e);
        enemies.add(e1);
        enemies.add(e2);
        enemies.add(e3);
        enemies.add(e4);
        towers.add((TowerBuilding)bF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "tower"));
        b.runBattle(enemies, allies, towers);
        assertTrue(character.isDead());
        assertTrue(character.shouldExist().get());

    }

    @Test
    public void BasicbattleTestWithAlly(){
        b.setCharacter(character);
        Enemy e = new Slug();
        Enemy e1 = new Slug();
        AlliedSoldier a = new AlliedSoldier();
        enemies.add(e);
        enemies.add(e1);
        allies.add(a);
        b.runBattle(enemies, allies, towers);
        assert(allies.isEmpty());
        
    }

    @Test
    public void enemyToAllyTest(){
        b.setCharacter(character);

        Enemy e = new Slug();
        assertTrue(enemies.isEmpty());

        enemies.add(e);
        b.setEnemies(enemies);
        b.convertEnemyToAlly(e);
        assertTrue(character.getAlliedSoldierCount() > 0);
        assertTrue(enemies.isEmpty());
    }

    @Test
    public void allyToEnemyTest(){
        b.setCharacter(character);

        AlliedSoldier a = new AlliedSoldier();
        b.setEnemies(enemies);
        b.convertAllyToZombie(a);
        assertTrue(character.getAlliedSoldierCount() == 0);
        assert(enemies.size() > 0);
    }

}
