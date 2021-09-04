package unsw.loopmania.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;

/**
 * Poop spawns randomly on the track each round and deals true damage to the character
 */
public class Poop extends StaticEntity{
    private int damage;
    /**
     * Constructor for poop class
     * @param x X coordinate of poop on the map
     * @param y Y coordinate of poop on the map
     */
    public Poop(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        damage = 5;
    }

    /**
     * Constructor for poop class
     */
    public Poop() {
        super();
        damage = 5;
    }
    

    /**
     * Checks if character has stepped on Poop. If so activate Poop
     * @param character
     */
    public void updateOnMove(Character character) {
        if (character.getX() == super.getX() && character.getY() == super.getY()) {
            activate(character);
        }
    }

    /**
     * Activates Poops
     * @param character
     */
    public void activate(Character character) {
        character.takeRawDamage(damage);
        this.destroy();
    }

    /**
     * Gets the damage dealt by the poop
     * @return
     */
    public int getAmount() {
        return damage;
    }
}