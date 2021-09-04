package unsw.loopmania.Items;

import unsw.loopmania.LoopManiaWorld;

/**
 * DoggieCoin strategy that reduces the sell price after Elan dies
 */
public class DeflatedCoinStrategy implements CoinStrategy{

    @Override
    public int getSellPrice() {
        return LoopManiaWorld.getRandNum();
    }
    
}
