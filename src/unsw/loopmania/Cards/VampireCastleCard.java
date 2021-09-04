package unsw.loopmania.Cards;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * VampireCastle card just sits in the character's card inventory until placed.
 * Assuming it's placed in a valid location, a VampireCastleBuilding will spawn.
 * @author Group FRIDGE
 */
public class VampireCastleCard extends StaticEntity implements Card {

    /**
    * Constructor for VampireCastleCard class
    * @param x SimpleIntegerProperty: Column Coordinate of Card in card inventoryX
    * @param y SimpleIntegerProperty: Row Coordinate of Card in card inventoryX
    */
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("vampirecastle");
    }
    
    public VampireCastleCard() {
        super();
        super.setType("vampirecastle");
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
     * Placement rules to determine whether to place VampireCastleBuildinf at location
     * card was dragged to. VampireCastles must be placed next to a PathTile.
     * @param x int: potential destination column coordinate
     * @param y int: potential destination row coordinate
     * @param orderedPath List<Pair<Integer, Integer>>: List containing orderedPath that moving entities travel on
     * @return Boolean depending on whether target location is valid
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
