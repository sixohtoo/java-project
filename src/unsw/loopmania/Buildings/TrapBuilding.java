package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

public class TrapBuilding extends StaticEntity implements Building, BuildingOnMove{
    /**
     * 
     * @param x
     * @param y
     */
    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("trap");
    }

    /**
     * Checks if enemy has stepped on trap. If so activate trap
     * @param enemy
     */
    @Override
    public void updateOnMove(MovingEntity enemy) {
        if (!(enemy instanceof Enemy)) {
            return;
        }
        else if (enemy.getX() == super.getX() && enemy.getY() == super.getY()) {
            activate(enemy);
        }
    }

    /**
     * Activates trap
     * @param enemy
     */
    public void activate(MovingEntity enemy) {
        this.destroy();
        enemy.takeDamage(30);
        if (enemy.isDead()) {
            enemy.destroy();
        }
    }
    
}
