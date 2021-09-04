package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The One Ring is a rare item that can revive the player if in the unequipped
 * inventory
 */
public class TheOneRing extends Item implements RareItem{
    public static final int NOPRICE = 0;
    /**
     * Constructor for TheOneRing class
     * @param x X coordiante of TheOneRing in the unequipped inventory
     * @param y Y coordiante of TheOneRing in the unequipped inventory
     */
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        super.setType("theonering");
    }

    /**
     * Constructor for TheOneRing class
     */
    public TheOneRing() {
        super();
        super.setType("theonering");
    }
    /**
     * @return cost of selling item
     */
    @Override
    public int getSellPrice() {
        return 1000;
    }
    /**
     * @return gold gained when item is replaced as oldest in inventory
     */
    @Override
    public int getReplaceCost() {
        return 400;
    }
    /**
     * TheOneRing cannot be bought so it does not have a price
     */
    @Override
    public int getPrice() {
        return NOPRICE;
    }
}
