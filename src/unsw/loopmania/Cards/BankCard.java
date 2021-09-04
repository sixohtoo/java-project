package unsw.loopmania.Cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

import java.util.List;
import org.javatuples.Pair;

/**
 * Bank Card used to summon bank building
 */
public class BankCard extends StaticEntity implements Card{
    /**
     * Constructor for bank card
     * @param x X coordinate of bank card in inventory
     * @param y Y coordinate of bank card in inventory
     */
    public BankCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        super.setType("bank");
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
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (x == tile.getValue0() && y == tile.getValue1()) {
                return true;
            }
        }
        return false;
    }   
}
