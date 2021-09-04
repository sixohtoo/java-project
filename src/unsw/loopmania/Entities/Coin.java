package unsw.loopmania.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Heroes.Character;

/**
 * Pile of gold that spawns randomly on the path each round
 */
public class Coin extends StaticEntity{
    private int amount;
    /**
     * Constructor for Coin class
     * @param x X coordinate of coin on map
     * @param y Y coordinate of coin on map
     */
    public Coin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        amount = LoopManiaWorld.getRandNum()/2+50;
    }

    /**
     * Constructor for coin class
     */
    public Coin() {
        super();
        amount = LoopManiaWorld.getRandNum()/2+50;
    }

    /**
     * Checks if character has stepped on coin. If so activate coin
     * @param character character
     */
    public void updateOnMove(Character character) {
        // If entity is not a character, return
        if (!(character instanceof Character)) {
            return;
        }
        else if (character.getX() == super.getX() && character.getY() == super.getY()) {
            activate(character);
        }
    }

    /**
     * Activates coins
     * @param character character that activated coin
     */
    public void activate(Character character) {
        character.gainGold(amount);
        this.destroy();
    }

    /**
     * Gets the amount of gold stored in coin.
     * Used for loading the game
     * @return The amount of gold stored in coin
     */
    public int getAmount() {
        return amount;
    }
}
