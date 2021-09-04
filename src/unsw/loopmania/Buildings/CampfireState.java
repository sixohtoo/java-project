package unsw.loopmania.Buildings;

/**
 * BonusDamageStrategy that doubles the character's damage. This 
 * strategy is active when the player is within range of a campfire.
 * @author Group FRIDGE
 */
public class CampfireState implements BonusDamageStrategy{
    /**
     * Empty constructor for CampfireState
     */
    public CampfireState() {
        
    }

    /**
     * Doubles the character's damage
     * @param damage double: Incoming damage
     * @return double: Outgoing damage (damage * 2)
     */
    @Override
    public double ApplyBonusDamge(double damage) {    
        return damage*2;
    }
    
}
