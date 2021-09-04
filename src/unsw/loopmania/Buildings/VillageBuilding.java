package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

public class VillageBuilding extends StaticEntity implements Building, BuildingOnMove{
    /**
     * 
     * @param x
     * @param y
     */
    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("village");
    }

    /**
     * Checks if character is on village. If so heal it
     * @param character
     */
    @Override
    public void updateOnMove(MovingEntity character) {
        if (!(character instanceof Character)) {
            return;
        }
        else if (character.getX() == super.getX() && character.getY() == super.getY()) {
            heal((Character) character);
        }
        
    }
    /**
     * Heals character
     * @param c
     */
    public void heal(Character character) {
        character.restoreHealth((100- character.getHealth())/2);
    }
    
}
