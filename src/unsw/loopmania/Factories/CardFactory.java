package unsw.loopmania.Factories;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Cards.TowerCard;
import unsw.loopmania.Cards.TrapCard;
import unsw.loopmania.Cards.VillageCard;
import unsw.loopmania.Cards.BankCard;
import unsw.loopmania.Cards.BarracksCard;
import unsw.loopmania.Cards.CampfireCard;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Cards.VampireCastleCard;
import unsw.loopmania.Cards.ZombiePitCard;

/**
 * CardFactory is a factory pattern that is used to create Card classes.
 * It exists so that other classes can call create without needing to be aware of 
 * exactly what type of card it is creating.
 * @author Group FRIDGE
 */

public class CardFactory {
    /**
     * Called by other classes to create a building using a type string and coordinates.
     * This function deals with the logic of figuring out which type of object to create.
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @param type String: type of building to create
     * @return Building: Building that is created.
     */
    public Card create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        if (type.equals("campfire")) {
            return createCampfire(x, y);
        }
        else if (type.equals("barracks")) {
            return createBarracks(x, y);
        }
        else if (type.equals("tower")) {
            return createTower(x, y);
        }
        else if (type.equals("trap")) {
            return createTrap(x, y);
        }
        else if (type.equals("vampirecastle")) {
            return createVampireCastle(x, y);
        }
        else if (type.equals("zombiepit")) {
            return createZombiePit(x, y);
        }
        else if (type.equals("village")) {
            return createVillage(x, y);
        }
        else if (type.equals("bank")) {
            return createBank(x,y);
        }
        else {
            return null;
        }
    }
    /**
     * Creates a BankCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return CampfireCard: Building that is created.
     */
    private Card createBank(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BankCard(x, y);
    }

    /**
     * Creates a CampfireCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return CampfireCard: Building that is created.
     */
    public CampfireCard createCampfire(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireCard(x, y);
    }
    
    /**
     * Creates a BarracksCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return BarracksCard: Building that is created.
     */
    public BarracksCard createBarracks(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BarracksCard(x, y);
    }
    
    /**
     * Creates a TowerCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return TowerCard: Building that is created.
     */
    public TowerCard createTower(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TowerCard(x, y);
    }
    
    /**
     * Creates a TrapCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return TrapCard: Building that is created.
     */
    public TrapCard createTrap(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TrapCard(x, y);
    }
    
    /**
     * Creates a VampireCastleCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return VampireCastleCard: Building that is created.
     */
    public VampireCastleCard createVampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleCard(x, y);
    }
    
    /**
     * Creates a VillageCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return VillageCard: Building that is created.
     */
    public VillageCard createVillage(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageCard(x, y);
    }
    
    /**
     * Creates a ZombiePitCard
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return ZombiePitCard: Building that is created.
     */
    public ZombiePitCard createZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new ZombiePitCard(x, y);
    }
}
