package unsw.loopmania.Cards;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;

/**
 * Cards are located in the player's card inventory and can be converted into buildings.
 * A player can have a maximum of 9 cards.
 * @author Group FRIDGE
 */
public interface Card {
    public boolean canBePlaced(int x, int y, List<Pair<Integer, Integer>> orderedPath);
    public int getX();
    public int getY();
    public void destroy();
    public IntegerProperty x();
    public IntegerProperty y();
}
