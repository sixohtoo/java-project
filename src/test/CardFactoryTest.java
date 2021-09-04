package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Cards.TrapCard;
import unsw.loopmania.Cards.BankCard;
import unsw.loopmania.Cards.BarracksCard;
import unsw.loopmania.Cards.CampfireCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.TowerCard;
import unsw.loopmania.Cards.VampireCastleCard;
import unsw.loopmania.Cards.ZombiePitCard;
import unsw.loopmania.Factories.CardFactory;
import unsw.loopmania.Cards.VillageCard;


public class CardFactoryTest {
    //"Campfire", "Barracks", "Tower", "Trap", "VampireCastle", "ZombiePit", "Village"}
    SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    SimpleIntegerProperty y = new SimpleIntegerProperty(0);
    CardFactory c = new CardFactory();

    @Test
    public void createCampfireTest() {
        Card i = c.create(x, y, "campfire");
        assertTrue(i instanceof CampfireCard);
    }
    @Test
    public void createBarracksTest() {
        Card i = c.create(x, y, "barracks");
        assertTrue(i instanceof BarracksCard);
    }
    @Test
    public void createTowerTest() {
        Card i = c.create(x, y, "tower");
        assertTrue(i instanceof TowerCard);
    }
    @Test
    public void createTrapTest() {
        Card i = c.create(x, y, "trap");
        assertTrue(i instanceof TrapCard);
    }
    @Test
    public void createVampireCastleTest() {
        Card i = c.create(x, y, "vampirecastle");
        assertTrue(i instanceof VampireCastleCard);
    }
    @Test
    public void createZombiePitTest() {
        Card i = c.create(x, y, "zombiepit");
        assertTrue(i instanceof ZombiePitCard);
    }
    @Test
    public void createVillageTest() {
        Card i = c.create(x, y, "village");
        assertTrue(i instanceof VillageCard);
    }
    @Test
    public void createBankTest() {
        Card i = c.create(x, y, "bank");
        assertTrue(i instanceof BankCard);
    }
}
