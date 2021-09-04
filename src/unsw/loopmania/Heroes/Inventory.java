package unsw.loopmania.Heroes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Entities.Entity;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Factories.CardFactory;
import unsw.loopmania.Factories.ItemFactory;
import unsw.loopmania.Factories.RareItemFactory;
import unsw.loopmania.Items.*;

/**
 * Keeps track of the player's inventory.
 * This includes the cards and items.
 */
public class Inventory {
    private List<Item> unequippedInventoryItems;
    private Character character;
    public static final int UNEQUIPPEDINVENTORYWIDTH = 4;
    public static final int UNEQUIPPEDINVENTORYHEIGHT = 4;
    private List<Card> cardEntities;
    private List<String> nonLevelItems;
    private List<String> rareItems;
    private RareItemFactory rF;

    /**
     * Constructor for the inventroy clsas
     * @param character The character who's inventory this is
     */
    public Inventory(Character character) {
        unequippedInventoryItems = new ArrayList<>();
        this.character = character;
        cardEntities = new ArrayList<>();
        nonLevelItems = new ArrayList<String>(Arrays.asList("healthpotion", "strengthpotion", "invinciblepotion", "theonering", "anduril", "treestump", "doggiecoin", "nuke"));
    }

    /**
     * Sets the game in confusing mode
     */
    public void setConfusingMode() {
        rF.setConfusing();
    }

    /**
     * Provides the inventory class with a list of all rare items in game
     * @param rareItems List of rare items
     */
    public void setRareItems(List<String> rareItems) {
        this.rareItems = rareItems;
        this.rF = new RareItemFactory(rareItems);
    }

    /**
     * Generates card and adds to list of cards
     * @param type Type of card to load
     * @param width Maximum number of cards
     * @return Generated Card
     */
    public StaticEntity loadCard(String type, int width) {
        if (cardEntities.size() >= width){
            int gold = LoopManiaWorld.getRandNum() + 1;
            character.gainGold(gold * 3);
            character.gainXP(3);
            removeCard(0);
        }
        CardFactory cf = new CardFactory();
        Card card = cf.create(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0), type);
        cardEntities.add(card);
        return (StaticEntity)card;
    }
    /**
     * Gets Card respective index
     * @param cardNodeX X coordinate of card
     * @param cardNodeY Y coordinate of card
     * @return Card
     */
    public Card getMatchingCard(int cardNodeX, int cardNodeY) {
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                return c;
            }
        }
        return null;
    }
    /**
     * Deletes card
     * @param card card to be deleted
     * @param cardNodeX Position of card in card inventory
     */
    public void destroyCard(Card card, int cardNodeX) {
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);
    }

    /**
     * Gets card via its cordinate
     * @param x Position of card in card inventory
     * @return Card
     */
    public Card getCardByCoordinate(int x) {
        for (Card c : cardEntities) {
            if (c.getX() == x) {
                return c;
            }
        }
        return null;
    }

    /**
     * Checks if TheOneRing is in inventory.
     * If found it is destroyed.
     * @return Boolean on whether one ring is in inventory
     */
    public Boolean hasRing() {
        for (int i = unequippedInventoryItems.size() - 1; i >= 0; i--) {
            Item item = unequippedInventoryItems.get(i);
            if (item.isRing()) {
                item.destroy();
                unequippedInventoryItems.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if Nuke is in inventory
     * If found it is destroyed
     * @return Boolean on whether inventory has a nuke
     */
    public Boolean hasNuke() {
        for (int i = unequippedInventoryItems.size() - 1; i >= 0; i--) {
            Item item = unequippedInventoryItems.get(i);
            if (item.isNuke()) {
                item.destroy();
                unequippedInventoryItems.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes item from inventory
     * @param removeItem Item to be removed
     */
    public void removeUnequippedItem(Item removeItem) {
        unequippedInventoryItems.remove(removeItem);
    }

    /**
     * Remove random UnequippedItem
     */
    public void loseRandomItem() {
        if (unequippedInventoryItems.size() > 0) {
            Item item = unequippedInventoryItems.get(LoopManiaWorld.getRandNum()%unequippedInventoryItems.size());
            unequippedInventoryItems.remove(item);
            ((Entity)item).destroy();
        }
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }


    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * Adds unequipped item to inventory
     * @param type Type of item to be added to inventory
     * @param level Level of item to be added
     * @return Item added
     */
    public StaticEntity addUnequippedItem (String type, int level){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items 
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        // now we insert the new sword, as we know we have at least made a slot available...
        ItemFactory iF = new ItemFactory();
        Item item = null;
        
        if (rareItems != null && rareItems.contains(type)) {
            item = rF.create(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), type);
        }
        else if (nonLevelItems.contains(type)) {
            item = iF.create(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), type);
        }
        else {
            item = iF.create(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), type, level);
        }
        unequippedInventoryItems.add(item);
        if (rareItems != null && !rareItems.contains(type)) {
            character.updateHighest(item);
        }
        return item;
    }

    /**
     * Only used for loading confused rare items in loadGame
     * @param type Type of item confusing item to be added
     * @param additional Item that confusing item thinks it is
     * @return Confusing item added
     */
    public StaticEntity addUnequippedConfusedItem(String type, String additional) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items 
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        Item item = rF.create(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()), type, additional);
        unequippedInventoryItems.add(item);
        return item;
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<UNEQUIPPEDINVENTORYHEIGHT; y++){
            for (int x=0; x<UNEQUIPPEDINVENTORYWIDTH; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }
    /**
     * Removes an item by index
     * @param index Index of item in arraylist
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Item item = unequippedInventoryItems.get(index);
        item.destroy();
        character.gainGold(item.getReplaceCost());
        unequippedInventoryItems.remove(index);
    }
    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */

    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Item item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Item getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Item item: unequippedInventoryItems){
            if ((item.getX() == x) && (item.getY() == y)){
                return item;
            }
        }
        return null;
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Item item){
        // item.destroy();
        unequippedInventoryItems.remove(item);
    }

    //Getters and Setters
    public HealthPotion getHealthPotion() {
        for (int i = unequippedInventoryItems.size() - 1; i >= 0; i--) {
            Item item = unequippedInventoryItems.get(i);
            if (item instanceof HealthPotion) {
                ((Entity)item).destroy();
                unequippedInventoryItems.remove(i);
                return ((HealthPotion)item);
            }
        }
        return null;
    }
    public int getUnequippedInventoryItemsNum() {
        return unequippedInventoryItems.size();
    }
    public int getCardsNum() {
        return cardEntities.size();
    }
    public List<Card> getCards() {
        return cardEntities;
    }
    public static int getunequippedInventoryWidth() {
		return Inventory.UNEQUIPPEDINVENTORYWIDTH;
	}
    public static int getunequippedInventoryHeight() {
		return Inventory.UNEQUIPPEDINVENTORYHEIGHT;
	}
    public List<String> getNonLevelItems() {
        return nonLevelItems;
    }
    public List<Item> getunequippedInventoryItems() {
        return unequippedInventoryItems;
    }

    public StrengthPotion getStrengthPotion() {
        for (int i = unequippedInventoryItems.size() - 1; i >= 0; i--) {
            Item item = unequippedInventoryItems.get(i);
            if (item instanceof StrengthPotion) {
                ((Entity)item).destroy();
                unequippedInventoryItems.remove(i);
                return ((StrengthPotion)item);
            }
        }
        return null;
    }

    /**
     * Checks whether inventory has an invincible potion.
     * If it does, the potion is destroyed
     * @return the found item
     */
    public Item getInvinciblePotion() {
        for (int i = unequippedInventoryItems.size() - 1; i >= 0; i--) {
            Item item = unequippedInventoryItems.get(i);
            if (item.isInvinciblePotion()) {
                item.destroy();
                unequippedInventoryItems.remove(i);
                return item;
            }
        }
        return null;
    }
}
