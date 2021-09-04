package unsw.loopmania.Buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Entities.StaticEntity;

public class TowerBuilding extends StaticEntity implements Building{
    /**
     * 
     * @param x
     * @param y
     */
    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("tower");
    }
    /**
     * Checks if tower is in range of character
     * @param character
     * @return
     */
    public boolean isInRange(Character character) {
        double distance = Math.sqrt(Math.pow((character.getX()-this.getX()), 2) +  Math.pow((character.getY()-this.getY()), 2));
        if (distance < 3) {
            return true;
        }
        return false;
    }
    /**
     * Attacks enemy
     * @param e
     */
    public void attack(Enemy enemy) {
        int maxHealth = enemy.getHealth();
        enemy.takeDamage(maxHealth * 0.08);
    }
    
}
