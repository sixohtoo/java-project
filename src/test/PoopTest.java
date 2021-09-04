package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Entities.Poop;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.AlliedSoldier;
import unsw.loopmania.Heroes.Hero;

public class PoopTest {

    @Test
    public void poopTest() {
        Poop poop = new Poop();
        assertEquals(poop.getAmount(), 5);

    }

    
}
