package test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Heroes.Character;

public class LoopManiaWorldTest {
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
    public void getClosestCampfireTest() throws FileNotFoundException{
        JSONObject json = new JSONObject("{    \"width\": 5,    \"height\": 14,    \"rare_items\": [],    \"goal-condition\": {\"goal\": \"experience\", \"quantity\": 123456 },    \"entities\": [        {\"x\": 0, \"y\": 0, \"type\": \"hero_castle\"}    ],    \"path\": {        \"type\": \"path_tile\",        \"x\": 0, \"y\": 0,        \"path\": [            \"RIGHT\", \"RIGHT\", \"DOWN\", \"DOWN\", \"LEFT\", \"LEFT\", \"UP\", \"UP\"        ]    }}");
        LoopManiaWorld world = new LoopManiaWorld(8, 14, createPath(),json);
        Character c = new Character(new PathPosition(0, createPath()));
        world.setCharacter(c);
        world.loadCard("campfire", 8);
        world.loadCard("campfire", 8);
        world.loadCard("vampirecastle", 8);
        world.convertCardToBuildingByCoordinates(0, 0, 3, 3);
        world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        world.convertCardToBuildingByCoordinates(0, 0, 3, 2);
        Enemy vampire = world.spawnVampire();
        world.moveEntities();
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 1);
    }
}
