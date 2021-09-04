package unsw.loopmania.Cards;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Campfire card just sit in the character's card inventory until placed.
 * Assuming it's placed in a valid location, a CampfireBuilding will spawn.
 * @author Group FRIDGE
 */
public class CampfireCard extends StaticEntity implements Card {

    /**
     * Constructor for CampfireCard class
     * @param x SimpleIntegerProperty: Column Coordinate of Card in card inventoryX
     * @param y SimpleIntegerProperty: Row Coordinate of Card in card inventoryX
     */
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("campfire");
    }
    
    /**
     * Placement rules to determine whether to place CampfireBuilding at location
     * card was dragged to. Campfires can be placed on any non path tile.
     * @param x int: potential destination column coordinate
     * @param y int: potential destination row coordinate
     * @param orderedPath List<Pair<Integer, Integer>>: List containing orderedPath that moving entities travel on
     * @return Boolean depending on whether target location is valid
     */
    @Override
    public boolean canBePlaced(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (tile.getValue0() == x && tile.getValue1() == y) {
                return false;
            }
        }
        return true;
    }
}