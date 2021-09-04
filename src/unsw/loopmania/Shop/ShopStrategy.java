package unsw.loopmania.Shop;

import javafx.beans.binding.BooleanBinding;
import unsw.loopmania.Items.Item;

/**
 * Strategy which allows for blocking user from rebuying certain items.
 */
public interface ShopStrategy {
    public void buyItem(Item purchasedItem);
    public void restock();
    public BooleanBinding getAvailable(Item item);
}
