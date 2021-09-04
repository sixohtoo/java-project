package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import unsw.loopmania.Heroes.CharacterStats;
import unsw.loopmania.Items.Shield;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.Thornmail;
import unsw.loopmania.Items.Armour;
import unsw.loopmania.Items.Axe;
import unsw.loopmania.Items.Helmet;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Items.Stake;

public class CharacterStatsTest {
    CharacterStats c = new CharacterStats();

    @Test
    public void CharacterStatTest(){
        Sword sword = new Sword(2);
        Stake stake = new Stake(1);
        Staff staff = new Staff(1);
        Helmet helmet = new Helmet(1);
        Shield shield = new Shield(1);
        Armour armour = new Armour(3);
        Thornmail thornmail = new Thornmail(6);
        Axe axe = new Axe(6);
        c.updateHighestLevel(sword);
        assertEquals(2, c.getHighestLevel("sword"));
        c.updateHighestLevel(stake);
        assertEquals(1, c.getHighestLevel("stake"));
        c.updateHighestLevel(staff);
        assertEquals(1, c.getHighestLevel("staff"));
        c.updateHighestLevel(helmet);
        assertEquals(1, c.getHighestLevel("helmet"));
        c.updateHighestLevel(shield);
        assertEquals(1, c.getHighestLevel("shield"));
        c.updateHighestLevel(armour);
        assertEquals(3, c.getHighestLevel("armour"));
        c.updateHighestLevel(thornmail);
        assertEquals(6, c.getHighestLevel("thornmail"));
        c.updateHighestLevel(axe);
        assertEquals(6, c.getHighestLevel("axe"));
    }
}
