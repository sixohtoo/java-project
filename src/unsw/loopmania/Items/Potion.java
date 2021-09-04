package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Heroes.Character;

/**
 * Abstract class for all potions
 */
public abstract class Potion extends Item{
    /**
     * Constructor for abstract potion class
     * @param x X coordiante of potion in unequipped invenetory
     * @param y Y coordiante of potion in unequipped invenetory
     */
    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }
    
    /**
     * Constructor for potion class
     */
    public Potion() {
        super();
    }

    public abstract void reset_cost();
    public abstract void increaseCost(int timesBought);
    public abstract void use(Character character);
}
