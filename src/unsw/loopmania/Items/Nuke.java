package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A rare item that can be used to kill every enemy on the screen
 */
public class Nuke extends Item implements RareItem {
    public static final int NOPRICE = 0;
    public static final int NUKESELLPRICE = 1000;
    public static final double NUKEREPLACEPERCENTAGE = 0.4;


    /**
     * Constructor for Nuke class
     * @param x X coordinate of nuke in unequipped inventory
     * @param y Y coordinate of nuke in unequipped inventory
     */
    public Nuke(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("nuke");
    }

    /**
     * Constructor for Nuke class
     */
    public Nuke() {
        super();
        super.setType("nuke");
    }

    /**
     * Rare items can't be bought so nuke has no cost
     */
	@Override
	public int getPrice() {
		return NOPRICE;
	}

    /**
     * Gets the sell price
     */
	@Override
	public int getSellPrice() {
		return NUKESELLPRICE;
	}

    /**
     * GEts the replace cost
     */
	@Override
	public int getReplaceCost() {
		return (int)(NUKESELLPRICE * NUKEREPLACEPERCENTAGE);
	}    
}
