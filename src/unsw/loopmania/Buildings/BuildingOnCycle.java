package unsw.loopmania.Buildings;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Enemy;

/**
 * An interface for all buildings that activate at the start of each cycle.
 * @author Group FRIDGE
 */
public interface BuildingOnCycle {
    public Enemy spawnEnemy(PathPosition pathPosition);
    public int generateNumberOfEnemies();
}
