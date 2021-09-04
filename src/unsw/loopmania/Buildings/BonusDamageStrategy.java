package unsw.loopmania.Buildings;

/**
 * BonusDamageStrategy is a strategy pattern used for calculating external buffs
 * to the character's damage. Currently it is either NormalState or CampfireState
 * depending on whether the character is in range of a CampfireBuilding
 * @author Group FRIDGE
 */
public interface BonusDamageStrategy {
    public double ApplyBonusDamge(double damage);
}
