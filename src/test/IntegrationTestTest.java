package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileNotFoundException;

import org.json.JSONObject;
import org.junit.Test;

import unsw.loopmania.Items.Item;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Shop.Shop;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Heroes.Character;

public class IntegrationTestTest {

    public static JSONObject parseJSON(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return new JSONObject(content);
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Test
    public void workingTest() throws FileNotFoundException {
        LoopManiaWorld world = IntegrationTestHelper.createWorld("three_by_three_world.json", "worlds", 1);
        for (int i = 0; i < 8; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertTrue(world.checkPlayerWin());
    }

    /**
     * Player walks in a 3x3 circle attacking slugs and then dies on the 38th tick.
     * When player picks up armour, player does not equip it.
     * @throws FileNotFoundException
     */
    @Test
    public void checkLossTest() throws FileNotFoundException {
        LoopManiaWorld world = IntegrationTestHelper.createWorld("three_by_three_world_cant_win.json", "worlds", 150);
        for (int i = 0; i < 20; i++) {
            //System.out.println(i);
            world.tick();
            
            assertFalse(world.checkPlayerLoss());
        }
        world.tick();
        assertTrue(world.checkPlayerLoss());
    }

    // /**
    //  * Same test as checkLossTest except when player picks up armour, player equips it,
    //  * so the player survives for an extra 13 ticks (almost 2 cycles)
    //  * @throws FileNotFoundException
    //  */
    // @Test
    // public void checkLaterLossTest() throws FileNotFoundException {
    //     LoopManiaWorld world = IntegrationTestHelper.createWorld("three_by_three_world_cant_win.json", 4);
    //     for (int i = 0; i < 36; i++) {
    //         List<Item> inventory = world.getItems();
    //         if (!inventory.isEmpty()) {
    //             Item item = world.getItems().get(0);
    //             world.equipItem(item, "armour");
    //         }
    //         world.tick();
            
    //         assertFalse(world.checkPlayerLoss());
    //     }
    //     world.tick();
    //     assertFalse(world.checkPlayerLoss());
    //     for (int i = 0; i < 13; i++) {
    //         world.tick();
    //         assertFalse(world.checkPlayerLoss());
    //     }
    //     assertTrue(((StaticEntity)world.getEquippedItemByCoordinates(0)).getType().equals("sword"));
    //     assertNull(((StaticEntity)world.getEquippedItemByCoordinates(1)));
    //     assertNull(((StaticEntity)world.getEquippedItemByCoordinates(2)));
    //     assertTrue(((StaticEntity)world.getEquippedItemByCoordinates(3)).getType().equals("armour"));
    //     assertNull(((StaticEntity)world.getEquippedItemByCoordinates(4)));
    //     world.tick();
    //     assertTrue(world.checkPlayerLoss());
    // }

    @Test
    public void BuyAndUsePotion() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("one_ring_grind.json", "worlds", 2);
        for (int i = 0; i < 8; i++) {
            world.tick();
        }
        Character c = world.getCharacter();
        Shop s = new Shop(c);
        s.buy("healthpotion");
        world.drinkHealthPotion();
        assertEquals(c.getHealth(), 100);
    }
}
