package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Shop.Shop;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Axe;
import unsw.loopmania.Items.DoggieCoin;
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Doggie;

public class ShopIntegrationTest {
    @Test
    public void PickupAndSellAxe() throws FileNotFoundException {
        LoopManiaWorld world = IntegrationTestHelper.createWorld("garden.json", "worlds", 1);
        Character character = world.getCharacter();
        for (int i = 0; i < 8; i++) {
            world.tick();
            assertEquals(100, character.getHealth());
        }
        world.tick();
        assertEquals(90, character.getHealth());
        Shop shop = world.getShop();
        Item axe = character.getUnequippedInventoryItemEntityByCoordinates(0, 0);
        assertEquals(((Axe)axe).getLevel(), 1);
        assertEquals(character.getGold(), 100); // Killed a slug to get the axe
        shop.sell(axe);
        assertEquals(character.getGold(), axe.getSellPrice() + 100);
    }

    @Test
    public void BuyHealthPotionsTest() throws FileNotFoundException {
        LoopManiaWorld world = IntegrationTestHelper.createWorld("one_ring_grind.json", "worlds", 1);
        ArrayList<String> mode = new ArrayList<String>();
        mode.add("beserker");
        world.setMode(mode);
        Character character = world.getCharacter();
        Shop shop = world.getShop();
        for (int i = 0; i < 100; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        int gold = character.getGold();
        System.out.println(gold);
        shop.buy("healthpotion");
        assertEquals(gold - 100, character.getGold());
        shop.buy("healthpotion");
        assertEquals(gold - 100 - 150, character.getGold());
        shop.buy("healthpotion");
        assertEquals(gold - 100 - 150 - 200, character.getGold());
        shop.buy("healthpotion");
        assertEquals(gold - 100 - 150 - 200 - 250, character.getGold());
        shop.buy("healthpotion");
        assertEquals(gold - 100 - 150 - 200 - 250 - 300, character.getGold());
        shop.buy("healthpotion");
        assertEquals(gold - 100 - 150 - 200 - 250 - 300 - 350, character.getGold());
    }

    @Test
    public void BuyStrengthPotionsTest() throws FileNotFoundException {
        LoopManiaWorld world = IntegrationTestHelper.createWorld("one_ring_grind.json", "worlds", 1);
        ArrayList<String> mode = new ArrayList<String>();
        mode.add("beserker");
        world.setMode(mode);
        Character character = world.getCharacter();
        Shop shop = world.getShop();
        for (int i = 0; i < 100; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        int gold = character.getGold();
        shop.buy("strengthpotion");
        assertEquals(gold - 50, character.getGold());
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100, character.getGold());
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100 - 150, character.getGold());
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100 - 150 - 200, character.getGold());
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100 - 150 - 200 - 250, character.getGold());
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100 - 150 - 200 - 250 - 300, character.getGold());
        world.drinkStrengthPotion();
        shop.buy("strengthpotion");
        assertEquals(gold - 50 - 100 - 150 - 200 - 250 - 300 - 350, character.getGold());
    }

    @Test
    public void BuyStuff() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("1.circuit.json", "worlds", 45);
        ArrayList<String> mode = new ArrayList<String>();
        mode.add("beserker");
        mode.add("survival");
        world.setMode(mode);

        Character character = world.getCharacter();
        Shop shop = world.getShop();
        for (int i = 0; i < 100; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        System.out.println(character.getHealth());
        System.out.println(character.getGold());
        shop.buy("healthpotion");
        assertTrue(world.getUnequippedInventoryItemEntityByCoordinates(0, 0) instanceof HealthPotion);
        world.drinkHealthPotion();
        assertNull(world.getUnequippedInventoryItemEntityByCoordinates(0, 0));
        shop.buy("sword");
        Item sword = world.getUnequippedInventoryItemEntityByCoordinates(0, 0);
        assertTrue(sword instanceof Sword);
        assertEquals(character.getHighestLevel(sword), 2);
        int gold = character.getGold();
        shop.sell(sword);
        assertTrue(character.getGold() > gold);
    }

}
