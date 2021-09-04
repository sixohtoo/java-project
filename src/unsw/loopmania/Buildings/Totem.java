package unsw.loopmania.Buildings;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;
import unsw.loopmania.Factories.EnemyFactory;

/**
 * Building. When an enemy (not boss) walks through it, turns them into another type of enemy
 */
public class Totem extends StaticEntity implements Building, BuildingOnMove{

    public Totem (SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("totem");
    }

    public void updateOnMove(MovingEntity movingEntity, List<Enemy> newEnemies, List<Enemy> enemies) {
        if (movingEntity.getX() == super.getX() && movingEntity.getY() == super.getY()) {
            activate(movingEntity, newEnemies, enemies);
        }
        
    }
    private void activate(MovingEntity movingEntity, List<Enemy> newEnemies, List<Enemy> enemies) {
        if ((movingEntity instanceof Enemy) && !(movingEntity instanceof Boss)) {
            EnemyFactory factory = new EnemyFactory();
            Enemy enemy = factory.create(movingEntity.clonePosition(), movingEntity.getType());
            newEnemies.add(enemy);
            enemies.add(enemy);
            this.destroy();
        }
    }
    
    @Override
    public void updateOnMove(MovingEntity movingEntity) {    
    }
    
}
