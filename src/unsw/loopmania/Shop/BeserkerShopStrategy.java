package unsw.loopmania.Shop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.*;

/**
 * Strategy pattern that runs the shop in berserker game mode.
 * @author Group FRIDGE
 */
public class BeserkerShopStrategy implements ShopStrategy{
    private BooleanProperty available;
    private Character character;

    public BeserkerShopStrategy(Character character) {
        available = new SimpleBooleanProperty(true);
        this.character = character;
    }

    /**
     * Stops player from buying multiple Protection items.
     * Don't need to use purchasedItem.isProtection() because Rare
     * items can never be bough
     * @param purchasedItem the item being purchased
     */
    @Override
    public void buyItem(Item purchasedItem) {
        if (purchasedItem.isProtection()) {
            available.set(false);
        }
    }

    /**
     * Makes Protection items available to purchase again
     */
    @Override
    public void restock() {
        available.set(true);
    }

    /**
     * Checks whether item is available to buy.
     * Takes whether item is protection, price and player's gold into account.
     */
    @Override
    public BooleanBinding getAvailable(Item item) {
        if (item.isProtection()) {
            return available.and(character.getGoldProperty().greaterThanOrEqualTo(item.getPrice()));
        }
        else {
            return Bindings.greaterThanOrEqual(character.getGoldProperty(), item.getPrice());
        }
    }

}
