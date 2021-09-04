package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.ConfusedRareItem;
import unsw.loopmania.Items.Item;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.RareItem;
import unsw.loopmania.Factories.RareItemFactory;

public class ConfusingRareItemTest {
    RareItemFactory rF;
    SimpleIntegerProperty x;
    SimpleIntegerProperty y;
    public ConfusingRareItemTest() {
        rF = new RareItemFactory(getRareItemList());
        rF.setConfusing();
        x = new SimpleIntegerProperty(0);
        y = new SimpleIntegerProperty(0);
    }

    private List<String> getRareItemList() {
        List<String> rareItems = new ArrayList<String>();
        rareItems.add("theonering");
        rareItems.add("anduril");
        rareItems.add("treestump");
        rareItems.add("nuke");
        rareItems.add("invinciblepotion");
        return rareItems;
    }
    @ParameterizedTest
    @ValueSource(strings = {"theonering", "anduril", "treestump", "nuke", "invinciblepotion"})
    public void AdditionalTest(String input) {
        LoopManiaWorld world = new LoopManiaWorld(5);
        Item item = rF.create(x, y, input);
        assertTrue(item instanceof ConfusedRareItem);
        Item additional = ((ConfusedRareItem)item).getAdditional();
        assertTrue(additional instanceof RareItem);
        assertFalse(item.getType().equals(additional.getType()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invinciblepotion", "treestump", "nuke", "anduril"})
    public void ConfusingRareItemGetDamage(String input) {
        LoopManiaWorld world = new LoopManiaWorld(602);
        Item item = rF.create(x, y, input);
        assertEquals(((ConfusedRareItem)item).getDamage(null), 50);
    }

    @ParameterizedTest
    @ValueSource(strings = {"nuke", "invinciblepotion", "treestump"})
    public void ConfusingRareItemGetProtection(String input) {
        LoopManiaWorld world = new LoopManiaWorld(4);
        Item item = rF.create(x, y, input);
        assertEquals(((ConfusedRareItem)item).protect(100, null), 100);
    }
}
