package unsw.loopmania.Shop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.Item;

public class NormalShopStrategy implements ShopStrategy{
    private Character character;
    private BooleanProperty available;

    public NormalShopStrategy(Character character) {
        this.character = character;
        available = new SimpleBooleanProperty(true);
        
    }

    /**
     * Items can be bought regularly
     */
    @Override
    public void buyItem(Item purchasedItem) {
        // available doesn't change in normal shop    
    }

    @Override
    public void restock() {
        available.set(true);
    }
    
    /**
     * Checks whether character has enough gold to purchase item
     */
    @Override
    public BooleanBinding getAvailable(Item item) {
        return Bindings.greaterThanOrEqual(character.getGoldProperty(), item.getPrice());
    }
    
}
