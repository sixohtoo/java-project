package unsw.loopmania.Factories;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Items.Anduril;
import unsw.loopmania.Items.ConfusingAnduril;
import unsw.loopmania.Items.ConfusingInvinciblePotion;
import unsw.loopmania.Items.ConfusingNuke;
import unsw.loopmania.Items.ConfusingTheOneRing;
import unsw.loopmania.Items.ConfusingTreeStump;
import unsw.loopmania.Items.TheOneRing;
import unsw.loopmania.Items.Nuke;
import unsw.loopmania.Items.TreeStump;
import unsw.loopmania.Items.InvinciblePotion;
import unsw.loopmania.Items.Item;

/**
 * Creates rare items.
 * Items returned depends on whether confusing mode is selected or not
 */
public class RareItemFactory {
    private List<String> rareItems;
    private RareItemFactoryStrategy createStrategy;

    public RareItemFactory(List<String> rareItems) {
        this.rareItems = rareItems;
        createStrategy = new StandardRareItemFactory(this);
    }

    /**
     * Sets the RareItemFactory into confusing mode
     */
    public void setConfusing() {
        createStrategy = new ConfusingRareItemFactory(this);
    }

    public Item create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        return createStrategy.create(x, y, type);
    }

    public Item createConfusing(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        if (type.equals("theonering")) {
            return createConfusingTheOneRing(x, y);
        }
        else if (type.equals("anduril")) {
            return createConfusingAnduril(x, y);
        }
        else if (type.equals("treestump")) {
            return createConfusingTreeStump(x, y);
        }
        else if (type.equals("nuke")) {
            return createConfusingNuke(x, y);
        }
        else if (type.equals("invinciblepotion")) {
            return createConfusingInvinciblePotion(x, y);
        }
        else {
            return null;
        }
    }
    
    public Item createStandard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        if (type.equals("theonering")) {
            return createTheOneRing(x, y);
        }
        else if (type.equals("anduril")) {
            return createAnduril(x, y);
        }
        else if (type.equals("treestump")) {
            return createTreeStump(x, y);
        }
        else if (type.equals("nuke")) {
            return createNuke(x, y);
        }
        else if (type.equals("invinciblepotion")) {
            return createInvinciblePotion(x, y);
        }
        else {
            return null;
        }
    }

    public Item create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type, String additional) {
        if (type.equals("theonering")) {
            return createLoadedTheOneRing(x, y, additional);
        }
        else if (type.equals("anduril")) {
            return createLoadedAnduril(x, y, additional);
        }
        else if (type.equals("treestump")) {
            return createLoadedTreeStump(x, y, additional);
        }
        else if (type.equals("nuke")) {
            return createLoadedNuke(x, y, additional);
        }
        else if (type.equals("invinciblepotion")) {
            return createLoadedInvinciblePotion(x, y, additional);
        }
        else {
            return null;
        }
    }

    public Item create(String type) {
        if (type.equals("theonering")) {
            return createTheOneRing();
        }
        else if (type.equals("anduril")) {
            return createAnduril();
        }
        else if (type.equals("treestump")) {
            return createTreeStump();
        }
        else if (type.equals("nuke")) {
            return createNuke();
        }
        else if (type.equals("invinciblepotion")) {
            return createInvinciblePotion();
        }
        else {
            return null;
        }
    }

    private Item createTheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TheOneRing(x, y);
    }
    private Item createNuke(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Nuke(x, y);
    }
    private Item createInvinciblePotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new InvinciblePotion(x, y);
    }
    private Item createAnduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Anduril(x, y, 1);
    }
    private Item createTreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TreeStump(x, y, 1);
    }
    private Item createTheOneRing() {
        return new TheOneRing();
    }
    private Item createNuke() {
        return new Nuke();
    }
    private Item createInvinciblePotion() {
        return new InvinciblePotion();
    }
    private Item createAnduril() {
        return new Anduril(1);
    }
    private Item createTreeStump() {
        return new TreeStump(1);
    }
    private Item createConfusingTheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item additional = create(generateRandomRareItem("theonering"));
        return new ConfusingTheOneRing(x, y, additional);
    }
    private Item createConfusingNuke(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item additional = create(generateRandomRareItem("nuke"));
        return new ConfusingNuke(x, y, additional);
    }
    private Item createConfusingInvinciblePotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item additional = create(generateRandomRareItem("invinciblepotion"));
        return new ConfusingInvinciblePotion(x, y, additional);
    }
    private Item createConfusingAnduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item additional = create(generateRandomRareItem("anduril"));
        return new ConfusingAnduril(x, y, 1, additional);
    }
    private Item createConfusingTreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item additional = create(generateRandomRareItem("treestump"));
        return new ConfusingTreeStump(x, y, 1, additional);
    }
    private Item createLoadedTheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y, String additional) {
        Item additionalItem = create(additional);
        return new ConfusingTheOneRing(x, y, additionalItem);
    }
    private Item createLoadedAnduril(SimpleIntegerProperty x, SimpleIntegerProperty y, String additional) {
        Item additionalItem = create(additional);
        return new ConfusingAnduril(x, y, 0, additionalItem);
    }
    private Item createLoadedTreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, String additional) {
        Item additionalItem = create(additional);
        return new ConfusingTreeStump(x, y, 0, additionalItem);
    }
    private Item createLoadedNuke(SimpleIntegerProperty x, SimpleIntegerProperty y, String additional) {
        Item additionalItem = create(additional);
        return new ConfusingNuke(x, y, additionalItem);
    }
    private Item createLoadedInvinciblePotion(SimpleIntegerProperty x, SimpleIntegerProperty y, String additional) {
        Item additionalItem = create(additional);
        return new ConfusingInvinciblePotion(x, y, additionalItem);
    }
    private String generateRandomRareItem(String original) {
        List<String> copy = new ArrayList<String>();
        copy.addAll(rareItems);
        copy.remove(original);
        return copy.get(LoopManiaWorld.getRandNum() % copy.size());
    }
}
