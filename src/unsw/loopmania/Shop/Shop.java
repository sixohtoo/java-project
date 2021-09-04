package unsw.loopmania.Shop;



import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.*;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Factories.ItemFactory;
import unsw.loopmania.Items.*;

/**
 * Shop class. Contains statistics about how many
 */
public class Shop {
    private CharacterStats stats;
    private Character character;
    private ItemFactory iF;
    private ShopStrategy available;
    private int boughtHealthPotions;
    private int boughtStrengthPotions;
    private Inventory inventory;

    /**
     * 
     * @param character
     */
    public Shop(Character character) {
        this.stats = character.getStats();
        this.inventory = character.getInventory();
        this.character = character;
        this.available = new NormalShopStrategy(character);
        this.boughtHealthPotions = 0;
        iF = new ItemFactory();
    }
    /**
     * Gets purchase cost of item
     * @param item
     * @return cost to buy item
     */
    public int getBuyPrice(String item) {
        Item preview = previewItem(item);
        int cost = preview.getPrice();
        reset_price(preview);
        return cost;
    }

    private Item previewItem(String itemType) {
        Item item = null;
        if (itemType.equals("healthpotion")) {
            item = iF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), itemType);
            ((Potion)item).increaseCost(boughtHealthPotions);
        }
        else if (itemType.equals("strengthpotion")) {
            item = iF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), itemType);
            ((Potion)item).increaseCost(boughtStrengthPotions);
        }
        else {
            int level = getItemBuyLevel(itemType);
            item = iF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), itemType, level);
        }
        return item;
    }

    private void reset_price(Item item) {
        if (item.isPotion()) {
            ((Potion)item).reset_cost();
        }
    }

    public int getItemBuyLevel(String item) {
        int level = stats.getHighestLevel(item)+1;
        if (level > 10) {
            return 10;
        }
        return level;
    }

    /**
     * Buys item from shop and adds it to inventory
     * @param item
     * @return item purchased
     */
    public Item buy(String item) {
        // int level = getItemBuyLevel(item);
        // Item purchasedItem = (Item)inventory.addUnequippedItem(item, level);
        int level = stats.getHighestLevel(item);
        Item purchasedItem = (Item)inventory.addUnequippedItem(item, level+1);
        if (purchasedItem instanceof HealthPotion) {
            ((Potion)purchasedItem).increaseCost(boughtHealthPotions);
        }
        else if (purchasedItem instanceof StrengthPotion) {
            ((Potion)purchasedItem).increaseCost(boughtStrengthPotions);
        }
        int price = purchasedItem.getPrice();
        character.loseGold(price);
        available.buyItem(purchasedItem);
        stats.updateHighestLevel(purchasedItem);
        if (item.equals("healthpotion")) {
            boughtHealthPotions++;
        }
        else if (item.equals("strengthpotion")) {
            boughtStrengthPotions++;
        }
        return purchasedItem;
    }

    public int getSellPrice(Item item) {
        int price = item.getSellPrice();
        return price;
    }
    public int getHealthPotionsBought() {
        return boughtHealthPotions;
    }
    public int getStrengthPotionsBought() {
        return boughtStrengthPotions;
    }
    public void setHealthPotionsBought(int amount) {
        boughtHealthPotions = amount;
    }
    public void setStrengthPotionsBought(int amount) {
        boughtStrengthPotions = amount;
    }
    /**
     * Sells item in inventory
     * @param item
     */
    public void sell(Item item) {
        int price = item.getSellPrice();
        item.destroy();
        character.getunequippedInventoryItems().remove(item);
        character.gainGold(price);
    }

    public BooleanBinding canBuy(String item) {
        return available.getAvailable(previewItem(item));
    }

    public void restock() {
        available.restock();
    }

    public void setSurvivalAndBeserker() {
        available = new SurvivalAndBeserkerShopStrategy(character);
    }

    public void setSurvival() {
        available = new SurvivalShopStrategy(character);
    }

    public void setBeserker() {
        available = new BeserkerShopStrategy(character);
    }
}
