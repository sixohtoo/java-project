package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Barracks can only be placed on PathTiles. They provide the character with an additional
 * alliedSoldier whenever the character passes through. They don't do anything when enemies
 * pass through.
 * @author Group FRIDGE
 */
public class BarracksBuilding extends StaticEntity implements Building, BuildingOnMove{
    /**
     * Constructor for BarracksBuilding class
     * @param x SimpleIntegerProperty: column coordinate of Barrack's location on map
     * @param y SimpleIntegerProperty: row coordinate of Barrack's location on map
     */
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("barracks");
    }

    /**
     * Action that gets triggered when something moves onto the barracks.
     * If it is a character, the character gets a new Allied Soldier
     * otherwise nothing happens
     * @param character MovingEntity Entity that moved ontop of barracks
     */
    @Override
    public void updateOnMove(MovingEntity character) {
        if (character instanceof Character && character.getX() == super.getX() && character.getY() == super.getY()) {
            Character c = (Character) character;
            c.addAlliedSoldier(new AlliedSoldier());
        }
    }
    
}
