package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Entities.Coin;
import unsw.loopmania.Heroes.Character;

public class CoinTest {

    @Test
    public void checkCoinAmount() {
        for (int i = 0; i < 100; i++) {
            LoopManiaWorld.setSeed(i);
            Coin c = new Coin();
            assertTrue(c.getAmount() >= 50 && c.getAmount() <= 100);
        }
    }

    @Test
    public void checkCharacterGainGold() {
        LoopManiaWorld.setSeed(1);
        Coin c = new Coin();
        Character character = new Character();
        assertTrue(c.shouldExist().get());
        assertEquals(0, character.getGold());
        c.activate(character);
        assertEquals(c.getAmount(), character.getGold());
        assertTrue(!c.shouldExist().get());
    }


}
