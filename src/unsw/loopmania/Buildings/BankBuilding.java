package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Building placed on path tiles that provides interest gold to character
 */
public class BankBuilding extends StaticEntity implements Building, BuildingOnMove{
    /**
     * 
     * @param x
     * @param y
     */
    public BankBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("bank");
    }
    /**
     * Checks if character is on Bank. If so heal it
     * @param character
     */
    @Override
    public void updateOnMove(MovingEntity character) {
        if (!(character instanceof Character)) {
            return;
        }
        else if (character.getX() == super.getX() && character.getY() == super.getY()) {
            getInterest((Character) character);
        }
        
    }
    /**
     * Gives interest gold
     * @param c
     */
    public void getInterest(Character character) {
        character.getInterest();
    }
}