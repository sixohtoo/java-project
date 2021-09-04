package unsw.loopmania.Items;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * DoggieCoin dropped by Elan Musk.
 * Can be sold for a dynamic amount
 */
public class DoggieCoin extends Item{
    public static final int NOPRICE = 0;
    private CoinStrategy strategy;

    /**
     * Constructor for DoggieCoin class
     * @param x X coordinate of DoggieCoin in unequipped inventory
     * @param y Y coordinate of DoggieCoin in unequipped inventory
     */
    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
        super.setType("doggiecoin");
        strategy = new NormalCoinStrategy();
    }

    /**
     * DoggieCoin can't be bought
     */
    @Override
    public int getPrice() {
        return NOPRICE;
    }

    /**
     * DoggieCoin prices fluctuates constantly
     */
    @Override
    public int getSellPrice() {
        return strategy.getSellPrice();
    }

    /**
     * DoggieCoin replacecost changes with its price
     */
    @Override
    public int getReplaceCost() {
        return (int) (getSellPrice() * 0.4);
    }
    
    /**
     * Makes the DoggieCoin inflated (Elan has just spawned)
     */
    public void setInflatedCoin() {
        this.strategy = new InflatedCoinStrategy();
    }

    /**
     * Makes the DoggieCoin deflated (Elan has just died)
     */
    public void setDeflatedCoin() {
        this.strategy = new DeflatedCoinStrategy();
    }

    /**
     * Gets the strategy currently used by the DoggieCoin.
     * This is used to save the DoggieCoin in SaveGame
     * @return String : The strategy being used
     */
    public String getStrategy() {
        if (strategy instanceof InflatedCoinStrategy) {
            return "inflated";
        }
        else if (strategy instanceof DeflatedCoinStrategy) {
            return "deflated";
        }
        else {
            return "normal";
        }
    }

    public void setStrategy(String strategy) {
        if (strategy.equals("deflated")) {
            setDeflatedCoin();
        }
        else if (strategy.equals("inflated")) {
            setInflatedCoin();
        }
    }
}
