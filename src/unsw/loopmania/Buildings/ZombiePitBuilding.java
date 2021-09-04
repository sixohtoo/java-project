package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Factories.EnemyFactory;

public class ZombiePitBuilding extends StaticEntity implements Building, BuildingOnCycle{
    /**
     * 
     * @param x
     * @param y
     */
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
		super.setType("zombiepit");
    }

    /**
     * Spawns enemy on pathPosition
     * @param pathPosition
     * @return Spawned enemy
     */
	public Enemy spawnEnemy(PathPosition pathPosition) {
        EnemyFactory ef = new EnemyFactory();
		return ef.create(pathPosition, "zombie");
	}

    /**
     * Generates a random number of enemies between 1-3
     * @return int number of random enemies
     */
	public int generateNumberOfEnemies() {
        int num = LoopManiaWorld.getRandNum();
        int spawn1 = 30;
        int spawn2 = spawn1 + 60;
        if (num <= spawn1) {
            return 1;
        }
        else if (num <= spawn2) {
            return 2;
        }
        else {
            return 3;
        }
    }
    
}
