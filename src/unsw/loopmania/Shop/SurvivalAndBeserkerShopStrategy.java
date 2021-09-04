package unsw.loopmania.Shop;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.*;

/**
 * Strategy pattern that runs the shop in survival and berserker game modes.
 * @author Group FRIDGE
 */
public class SurvivalAndBeserkerShopStrategy implements ShopStrategy{
    private BooleanProperty protAvailable;
    private BooleanProperty healthAvailable;
    private Character character;

    public SurvivalAndBeserkerShopStrategy(Character character) {
        protAvailable = new SimpleBooleanProperty(true);
        healthAvailable = new SimpleBooleanProperty(true);
        this.character = character;
    }

    @Override
    public void buyItem(Item purchasedItem) {
        if (purchasedItem.isProtection()) {
            protAvailable.set(false);
        }
        else if (purchasedItem.isPotion()) {
            healthAvailable.set(false);
        }
    }

    @Override
    public void restock() {
        protAvailable.set(true);
        healthAvailable.set(true);
    }

    @Override
    public BooleanBinding getAvailable(Item item) {
        if (item.isProtection()) {
            return protAvailable.and(character.getGoldProperty().greaterThanOrEqualTo(item.getPrice()));
        }
        else if (item.isPotion()) {
            return healthAvailable.and(character.getGoldProperty().greaterThanOrEqualTo(item.getPrice()));
        }
        else {
            return Bindings.greaterThanOrEqual(character.getGoldProperty(), item.getPrice());
        }
    }
}
