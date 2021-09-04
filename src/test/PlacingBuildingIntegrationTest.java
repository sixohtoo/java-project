package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Entities.StaticEntity;

public class PlacingBuildingIntegrationTest {
    @Test
    public void placeBarracks() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("1.circuit.json", "worlds", 5454);
        Character character = world.getCharacter();
        for (int i = 0; i < 10; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertEquals(character.getHealth(), 90);
        Card barracks = world.getCardByCoordinate(0);
        assertTrue(barracks instanceof Card); 
        assertEquals(((StaticEntity)barracks).getType(), "barracks");
        world.convertCardToBuildingByCoordinates(barracks.getX(), barracks.getY(), 9, 7);
        Card emptyCard = world.getCardByCoordinate(0);
        assertNull(emptyCard);
        for (int i = 0; i < 50; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertEquals(character.getAlliedSoldierCount(), 2);
        assertEquals(character.getHealth(), 90);
    }

    @Test
    public void useVillage() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("1.circuit.json", "worlds", 5453);
        Character character = world.getCharacter();
        for (int i = 0; i < 110; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        System.out.println(character.getHealth());
        System.out.println(character.getX());
        System.out.println(character.getY());
        Card village = world.getCardByCoordinate(0);
        assertEquals(((StaticEntity)village).getType(), "village");
        assertEquals(character.getHealth(), 15);
        world.convertCardToBuildingByCoordinates(village.getX(), village.getY(), 2, 10);
        for (int i = 0; i < 2; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertEquals(character.getHealth(), 57);
    }

    @Test
    public void gettingBank() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("one_ring_grind.json", "worlds", 6666);
        Character character = world.getCharacter();
        for (int i = 0; i < 48; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
            assertFalse(world.checkPlayerWin());
        }
        Card nothing = world.getCardByCoordinate(0);
        assertNull(nothing);
        world.tick();
        Card bank = world.getCardByCoordinate(0);
        assertEquals(((StaticEntity)bank).getType(), "bank");
        world.convertCardToBuildingByCoordinates(bank.getX(), bank.getY(), 3, 3);
        assertTrue(character.getGold() < 1500);
        for (int i = 0; i < 5; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertTrue(character.getGold() > 1500);
    }
}
