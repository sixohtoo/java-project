package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Factories.CardFactory;


public class BuildingPlacementTest {
    CardFactory cF;
    public BuildingPlacementTest() {
        cF = new CardFactory();
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
    public void CampfirePlacementInValidTest() {
        Card campfire = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "campfire");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = campfire.canBePlaced(0, 0, path);
        assertFalse(canPlace);
        canPlace = campfire.canBePlaced(0, 1, path);
        assertFalse(canPlace);
        canPlace = campfire.canBePlaced(2, 2, path);
        assertFalse(canPlace);
    }

    @Test
    public void CampfirePlacementValidTest() {
        Card campfire = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "campfire");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = campfire.canBePlaced(1, 1, path);
        assertTrue(canPlace);
        canPlace = campfire.canBePlaced(3, 1, path);
        assertTrue(canPlace);
        canPlace = campfire.canBePlaced(602, 602, path);
        assertTrue(canPlace);
    }

    @Test
    public void TowerPlacementValidTest() {
        Card tower = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "tower");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = tower.canBePlaced(1, 1, path);
        assertTrue(canPlace);
        canPlace = tower.canBePlaced(2, 3, path);
        assertTrue(canPlace);
        canPlace = tower.canBePlaced(3, 1, path);
        assertTrue(canPlace);
    }

    @Test
    public void TowerPlacementInValidTest() {
        Card tower = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "tower");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = tower.canBePlaced(0, 0, path);
        assertFalse(canPlace);
        canPlace = tower.canBePlaced(3, 3, path);
        assertFalse(canPlace);
        canPlace = tower.canBePlaced(602, 602, path);
        assertFalse(canPlace);
    }
    @Test
    public void VillagePlacementValidTest() {
        Card village = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "village");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = village.canBePlaced(0, 0, path);
        assertTrue(canPlace);
        canPlace = village.canBePlaced(2, 2, path);
        assertTrue(canPlace);
        canPlace = village.canBePlaced(1, 2, path);
        assertTrue(canPlace);
    }

    @Test
    public void VillagePlacementInValidTest() {
        Card village = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "village");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = village.canBePlaced(1, 1, path);
        assertFalse(canPlace);
        canPlace = village.canBePlaced(1, 4, path);
        assertFalse(canPlace);
        canPlace = village.canBePlaced(602, 602, path);
        assertFalse(canPlace);
    }
    @Test
    public void VampireCastlePlacementValidTest() {
        Card vampirecastle = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "vampirecastle");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = vampirecastle.canBePlaced(1, 1, path);
        assertTrue(canPlace);
        canPlace = vampirecastle.canBePlaced(2, 3, path);
        assertTrue(canPlace);
        canPlace = vampirecastle.canBePlaced(3, 1, path);
        assertTrue(canPlace);
    }

    @Test
    public void VampireCastlePlacementInValidTest() {
        Card vampirecastle = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "vampirecastle");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = vampirecastle.canBePlaced(0, 0, path);
        assertFalse(canPlace);
        canPlace = vampirecastle.canBePlaced(2, 2, path);
        assertFalse(canPlace);
        canPlace = vampirecastle.canBePlaced(602, 602, path);
        assertFalse(canPlace);
        canPlace = vampirecastle.canBePlaced(3, 3, path);
        assertFalse(canPlace);
    }

    @Test
    public void ZombiePitPlacementValidTest() {
        Card zombiepit = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "zombiepit");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = zombiepit.canBePlaced(1, 1, path);
        assertTrue(canPlace);
        canPlace = zombiepit.canBePlaced(2, 3, path);
        assertTrue(canPlace);
        canPlace = zombiepit.canBePlaced(3, 1, path);
        assertTrue(canPlace);
    }

    @Test
    public void ZombiePitPlacementInValidTest() {
        Card zombiepit = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "zombiepit");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = zombiepit.canBePlaced(0, 0, path);
        assertFalse(canPlace);
        canPlace = zombiepit.canBePlaced(2, 2, path);
        assertFalse(canPlace);
        canPlace = zombiepit.canBePlaced(602, 602, path);
        assertFalse(canPlace);
        canPlace = zombiepit.canBePlaced(3, 3, path);
        assertFalse(canPlace);
    }
    @Test
    public void BarracksPlacementValidTest() {
        Card barracks = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "barracks");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = barracks.canBePlaced(0, 0, path);
        assertTrue(canPlace);
        canPlace = barracks.canBePlaced(2, 2, path);
        assertTrue(canPlace);
        canPlace = barracks.canBePlaced(1, 2, path);
        assertTrue(canPlace);
    }
    @Test
    public void BankPlacementValidTest() {
        Card bank = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "bank");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = bank.canBePlaced(0, 0, path);
        assertTrue(canPlace);
        canPlace = bank.canBePlaced(2, 2, path);
        assertTrue(canPlace);
        canPlace = bank.canBePlaced(1, 2, path);
        assertTrue(canPlace);
    }

    @Test
    public void BarracksPlacementInValidTest() {
        Card barracks = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "barracks");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = barracks.canBePlaced(1, 1, path);
        assertFalse(canPlace);
        canPlace = barracks.canBePlaced(1, 4, path);
        assertFalse(canPlace);
        canPlace = barracks.canBePlaced(602, 602, path);
        assertFalse(canPlace);
    }
    @Test
    public void TrapPlacementValidTest() {
        Card trap = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "trap");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = trap.canBePlaced(0, 0, path);
        assertTrue(canPlace);
        canPlace = trap.canBePlaced(2, 2, path);
        assertTrue(canPlace);
        canPlace = trap.canBePlaced(1, 2, path);
        assertTrue(canPlace);
    }

    @Test
    public void TrapPlacementInValidTest() {
        Card trap = cF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "trap");
        List<Pair<Integer, Integer>> path = createPath();
        Boolean canPlace = trap.canBePlaced(1, 1, path);
        assertFalse(canPlace);
        canPlace = trap.canBePlaced(1, 4, path);
        assertFalse(canPlace);
        canPlace = trap.canBePlaced(602, 602, path);
        assertFalse(canPlace);
    }



}
