package unsw.loopmania.Factories;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Item;

/**
 * Strategy used to create either standard rare items or confusing rare items
 */
public interface RareItemFactoryStrategy {
    public Item create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type);
}
