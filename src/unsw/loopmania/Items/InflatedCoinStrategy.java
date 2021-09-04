package unsw.loopmania.Items;

import unsw.loopmania.LoopManiaWorld;

/**
 * Dramatically increases the sell price of the DoggieCoin
 * when Elan Muske spawns
 */
public class InflatedCoinStrategy implements CoinStrategy{

    @Override
    public int getSellPrice() {
        return LoopManiaWorld.getRandNum() * 12;
    }
    
}
