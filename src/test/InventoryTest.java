package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.LoopManiaWorld;

public class InventoryTest {
    @Test
    public void PickUpDifferentItemsTest() {
        Character c = new Character();
        c.setRareItems(new ArrayList<String>());
        assertEquals(0, c.getUnequippedInventoryItemsNum());

        c.addUnequippedItem("sword", 1);
        assertEquals(1, c.getUnequippedInventoryItemsNum());

        c.addUnequippedItem("armour", 2);
        c.addUnequippedItem("helmet", 3);
        c.addUnequippedItem("shield", 4);
        c.addUnequippedItem("healthpotion", 0);
        assertEquals(5, c.getUnequippedInventoryItemsNum());
        // Item oneRing = new OneRing(6);
        c.addUnequippedItem("stake", 7);
        c.addUnequippedItem("staff", 8);
        c.addUnequippedItem("sword", 9);
        c.addUnequippedItem("helmet", 9);
        c.addUnequippedItem("healthpotion", 0);
        c.addUnequippedItem("healthpotion", 0);
        c.addUnequippedItem("sword", 1);
        assertEquals(12, c.getUnequippedInventoryItemsNum());
    }

    @Test
    public void PickUpMaxItemsTest() {
        Character c = new Character();
        c.setRareItems(new ArrayList<String>());
        for (int i = 0; i < 16; i++) {
            c.addUnequippedItem("sword", 1);
        }
        assertEquals(16, c.getUnequippedInventoryItemsNum());
        assertEquals(c.getGold(), 0);
        c.addUnequippedItem("sword", 1);
        assertEquals(16, c.getUnequippedInventoryItemsNum());
        assertEquals(c.getGold(), 70);
    }

    @Test
    public void PickUpDifferentCardsTest() {
        Character c = new Character();
        c.setRareItems(new ArrayList<String>());
        LoopManiaWorld.setSeed(5);
        int width = 16;
        assertEquals(0, c.getCardsNum());
        c.loadCard("zombiepit", width);

        assertEquals(1, c.getCardsNum());
        c.loadCard("vampirecastle", width);

        
        c.loadCard("campfire", width);
        assertEquals(3, c.getCardsNum());

        c.loadCard("village", width);
        assertEquals(4, c.getCardsNum());

        c.loadCard("trap", width);
        assertEquals(5, c.getCardsNum());

        c.loadCard("tower", width);
        assertEquals(6, c.getCardsNum());

        c.loadCard("barracks", width);
        assertEquals(7, c.getCardsNum());

        c.loadCard("heros_castle", width);
        assertEquals(8, c.getCardsNum());

    }

    @Test
    public void PickUpMaxCardsTest() {
        LoopManiaWorld.setSeed(5);
        Character c = new Character();
        int width = 8;
        for (int i = 0; i < 8; i++) {
            c.loadCard("trap", width);
        }
        assertEquals(8, c.getCardsNum());
        assertEquals(c.getGold(), 0);
        c.loadCard("trap", width);
        assertEquals(8, c.getCardsNum());
        assertEquals(c.getGold(), 264);
    }
}
