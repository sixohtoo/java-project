package unsw.loopmania.Cards;

import java.util.List;
import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Barracks card just sit in the character's card inventory until placed.
 * Assuming it's placed in a valid location, a BarracksBuilding will spawn.
 * @author Group FRIDGE
 */
public class BarracksCard extends StaticEntity implements Card {
    /**
     * Constructor for BarracksCard class
     * @param x SimpleIntegerProperty: Column Coordinate of Card in card inventoryX
     * @param y SimpleIntegerProperty: Row Coordinate of Card in card inventoryX
     */
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("barracks");
    }

    /**
     * Placement rules to determine whether to place BarracksBuilding at location
     * card was dragged to. Barracks must be placed on a PathTile.
     * @param x int: potential destination column coordinate
     * @param y int: potential destination row coordinate
     * @param orderedPath List<Pair<Integer, Integer>>: List containing orderedPath that moving entities travel on
     * @return Boolean depending on whether target location is valid
     */
    @Override
    public boolean canBePlaced(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (tile.getValue0() == x && tile.getValue1() == y) {
                return true;
            }
        }
        return false;
    }
    
    
}
