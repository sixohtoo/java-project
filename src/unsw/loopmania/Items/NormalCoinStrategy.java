package unsw.loopmania.Items;

import unsw.loopmania.LoopManiaWorld;

/**
 * Determines the sell price of a DoggieCoin before Elan spawns
 */
public class NormalCoinStrategy implements CoinStrategy{

    @Override
    public int getSellPrice() {
        return LoopManiaWorld.getRandNum() * 5;
    }

}
