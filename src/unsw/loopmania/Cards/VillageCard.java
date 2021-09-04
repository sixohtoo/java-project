package unsw.loopmania.Cards;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Village card just sit in the character's card inventory until placed.
 * Assuming it's placed in a valid location, a VillageBuilding will spawn.
 * @author Group FRIDGE
 */
public class VillageCard extends StaticEntity implements Card {
    /**
    * Constructor for TrapCard class
    * @param x SimpleIntegerProperty: Column Coordinate of Card in card inventoryX
    * @param y SimpleIntegerProperty: Row Coordinate of Card in card inventoryX
    */
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("village");
    }

    /**
     * Placement rules to determine whether to place VillageBuilding at location
     * card was dragged to. Villages must be placed on a PathTile.
     * @param x int: potential destination column coordinate
     * @param y int: potential destination row coordinate
     * @param orderedPath List<Pair<Integer, Integer>>: List containing orderedPath that moving entities travel on
     * @return Boolean depending on whether target location is valid
     */
    @Override
    public boolean canBePlaced(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (x == tile.getValue0() && y == tile.getValue1()) {
                return true;
            }
        }
        return false;
    }   
}
