package unsw.loopmania.Factories;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Item;

/**
 * Strategy that creates confusing items whenever RareItemFactory.create() is called.
 * This in only active if confusing mode is selected
 */
public class ConfusingRareItemFactory implements RareItemFactoryStrategy{
    private RareItemFactory rF;

    public ConfusingRareItemFactory(RareItemFactory rF) {
        this.rF = rF;
    }

    /**
     * Creaets confusing versions of all rare items
     */
	@Override
	public Item create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
		return rF.createConfusing(x, y, type);
	}
    
}
