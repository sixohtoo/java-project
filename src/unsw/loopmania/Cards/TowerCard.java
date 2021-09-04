package unsw.loopmania.Cards;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Tower card just sit in the character's card inventory until placed.
 * Assuming it's placed in a valid location, a TowerBuilding will spawn.
 * @author Group FRIDGE
 */
public class TowerCard extends StaticEntity implements Card {
    /**
     * Constructor for TowerCard
     * @param x X coordinate for TowerCard in inventory
     * @param y Y coordinate for TowerCard in inventory
     */
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("tower");
    }
    
    /**
     * Constructor for TowerCard
     */
    public TowerCard() {
        super();
        super.setType("tower");
    }

    /**
     * Checks if card placement coordinates are next to another coordinate
     * @param oldX card Placement x coordinate
     * @param oldY card Placement y coordinate
     * @param newX new tile x coordinate
     * @param newY new tile y coordinate
     * @return true or false whether old coordinates are next to new ones
     */
    private boolean nextTo(int oldX, int oldY, int newX, int newY) {
        if (oldX == newX + 1 && oldY == newY) {
            return true;
        }
        if (oldX == newX - 1 && oldY == newY) {
            return true;
        }
        if (oldX == newX && oldY == newY + 1) {
            return true;
        }
        if (oldX == newX && oldY == newY - 1) {
            return true;
        }
        return false;
    }


    /**
     * checks whether card can be placed on path
     * @param x
     * @param y
     * @param orderedPath
     * @return boolean on whether card can be placed
     */
    @Override
    public boolean canBePlaced(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        if (orderedPath.contains(new Pair<Integer, Integer>(x,y))) {
            return false;
        }
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (nextTo(x, y, tile.getValue0(), tile.getValue1())) {
                return true;
            }
        }
        return false;
    }
}