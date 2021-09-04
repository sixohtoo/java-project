package unsw.loopmania.Buildings;

import unsw.loopmania.Entities.MovingEntity;

/**
 * An interface for all buildings that activate when something
 * walks ontop/within range of them
 * @author Group FRIDGE
 */
public interface BuildingOnMove {
    public void updateOnMove(MovingEntity movingEntity);
    public String getType();
}
