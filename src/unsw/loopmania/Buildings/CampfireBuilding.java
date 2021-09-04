package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

/**
 * CampfireBuildings double the character's damage when the character is within 2
 * tiles of the campfire. Vampires also run away from campfires. Campfire buffs do
 * not stack.
 * @author Group FRIDGE
 */
public class CampfireBuilding extends StaticEntity implements Building, BuildingOnMove{

    private int radius;

    /**
     * Constructor for CampfireBuilding class
     * @param x SimpleIntegerProperty: Column coordinate of Campfire on the map
     * @param x SimpleIntegerProperty: Row coordinate of Campfire on the map
     */
    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("campfire");
        radius = 2;
    }

    /**
     * When the player moves within the range of the campfire, their damage is doubled.
     * This is done using a BonusDamage strategy. Once the player leaves the range,
     * their damage is returned back to normal.
     * @param character MovingEntity: Entity that potentially walked in/out of campfire's range
     */
    @Override
    public void updateOnMove(MovingEntity character) {
        if (!(character instanceof Character)) {
            return;
        }
        double distance = Math.sqrt(Math.pow((character.getX()-this.getX()), 2) +  Math.pow((character.getY()-this.getY()), 2));
        // If character is in range of campfire
        if (distance < radius) {
            applybuff((Character) character);
        } else {
            removeBuff((Character) character);
        }
    }
    
    /**
     * Does nothing if the character is already in the range of a campfire.
     * Else, apply the BonusDamageStrategy to double the character's damage.
     * @param character Character: The character
     */
    private void applybuff(Character character) {
        if (character.getBonusDamageStrategy() instanceof CampfireState) {
            return;
        } else {
            character.setBonusDamageStrategy(new CampfireState());
        }
    }

    /**
     * Removes BonusDamage buff from character because character has left range of campfire.
     * Other CampfireBuildings may reapply this buff in the same turn
     * @param character Character: The character
     */
    private void removeBuff(Character character) {
        if (character.getBonusDamageStrategy() instanceof NormalState) {
            return;
        } else {
            character.setBonusDamageStrategy(new NormalState());
        }
    }
}
