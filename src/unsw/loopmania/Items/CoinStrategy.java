package unsw.loopmania.Items;

/**
 * Coin strategy to determine whether coin is inflated (Elan is alive)
 * or deflated (Elan is dead) or normal (Elan hasn't spawned)
 */
public interface CoinStrategy {
    public int getSellPrice(); 
}
