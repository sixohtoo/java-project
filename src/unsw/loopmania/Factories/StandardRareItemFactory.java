package unsw.loopmania.Factories;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Item;

/**
 * Strategy used to create only standard rare items.
 * This is active when confusing mode is not selected
 */
public class StandardRareItemFactory implements RareItemFactoryStrategy{
	private RareItemFactory rF;

	public StandardRareItemFactory(RareItemFactory rF) {
		this.rF = rF;
	}

	/**
	 * Creates standard rare items only
	 */
	@Override
	public Item create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        return rF.createStandard(x, y, type);
    }

	
    
}
