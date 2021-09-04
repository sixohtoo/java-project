package unsw.loopmania.Shop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.Item;

/**
 * Strategy pattern that runs the shop in survival game mode.
 * @author Group FRIDGE
 */

public class SurvivalShopStrategy implements ShopStrategy{
    private BooleanProperty available;
    private Character character;

    /**
     * Constructor for SurvivalShopStrategy
     * @param character Character : the character
     */
    public SurvivalShopStrategy(Character character) {
        available = new SimpleBooleanProperty(true);
        this.character = character;
    }

    /**
     * Stops user from buying 2 potions
     * @param purchasedItem Item : the purchased item
     */
    @Override
    public void buyItem(Item purchasedItem) {
        if (purchasedItem.isPotion()) {
            available.set(false);
        }
    }

    /**
     * Allows user to buy potions again
     */
    @Override
    public void restock() {
        available.set(true);
    }

    /**
     * Checks whether user can buy a particular item.
     * Takes the item type, the item price and the user's gold into account
     * @param item Item : The item being bought
     * @return boolean on whether user can buy item
     */
    @Override
    public BooleanBinding getAvailable(Item item) {
        if (item.isPotion()) {
            return available.and(character.getGoldProperty().greaterThanOrEqualTo(item.getPrice()));
        }
        else {
            return Bindings.greaterThanOrEqual(character.getGoldProperty(), item.getPrice());
        }
    }
}
