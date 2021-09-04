package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.DoggieCoin;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.StrengthPotion;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Shop.Shop;

public class LoadWorldIntegrationTest {
    @Test
    public void startOPTest() throws FileNotFoundException{
        LoopManiaWorld world = IntegrationTestHelper.createWorld("OP.json", "backup", 2);
        Character character = world.getCharacter();
        Shop shop = world.getShop();
        assertEquals(world.getGold().get(), 366);
        assertEquals(world.getXP().get(), 35903);
        assertEquals(world.getCycles().get(), 15);
        assertEquals(world.getBossKills().get(), 0);
        assertEquals(world.getMaxGoal("gold"), 100000);
        assertEquals(world.getMaxGoal("experience"), 10000);
        assertEquals(world.getMaxGoal("cycles"), 40);
        assertEquals(world.getMaxGoal("bosses"), 2);
        assertFalse(world.checkPlayerLoss());
        assertFalse(world.checkPlayerWin());
        Item equippedWeapon = world.getEquippedItemByCoordinates(0);
        assertNotNull(equippedWeapon);
        assertTrue(equippedWeapon instanceof Staff);
        assertEquals(((Weapon)equippedWeapon).getLevel(), 10);
        Item equippedHelmet = world.getEquippedItemByCoordinates(1);
        assertTrue(equippedHelmet instanceof Helmet);
        assertEquals(((Helmet)equippedHelmet).getLevel(), 9);
        assertEquals(character.getHealth(), 99);
        world.drinkHealthPotion();
        assertEquals(character.getHealth(), 100);
        world.drinkHealthPotion();
        assertEquals(character.getHealth(), 100);
        assertEquals(((Weapon)equippedWeapon).getDamage(), 18 * Math.pow(1.1, 9));
        for (int i = 0; i < 300; i++) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        Item doggiecoin = world.getUnequippedInventoryItemEntityByCoordinates(1, 2);
        assertTrue(doggiecoin instanceof DoggieCoin);
        assertEquals(world.getGold().get(), 24814);
        shop.sell(doggiecoin);
        assertTrue(world.getGold().get() > 24814);
        assertEquals(world.getBossKills().get(), 1);
        Item strengthpotion = world.getUnequippedInventoryItemEntityByCoordinates(0, 2);
        assertTrue(strengthpotion instanceof StrengthPotion);
        world.drinkStrengthPotion();
        assertFalse(strengthpotion.shouldExist().get());
        while (world.getCycles().get() <= 40) {
            world.tick();
            assertFalse(world.checkPlayerLoss());
        }
        assertTrue(world.checkPlayerWin()); // Therefore player has killed ElanMuske
    }
}
