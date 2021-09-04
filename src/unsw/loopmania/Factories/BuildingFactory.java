package unsw.loopmania.Factories;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.BankBuilding;
import unsw.loopmania.Buildings.BarracksBuilding;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.HerosCastleBuilding;
import unsw.loopmania.Buildings.Totem;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Buildings.ZombiePitBuilding;

/**
 * BuildingFactory is a factory pattern that is used to create Building classes.
 * It exists so that other classes can call create without needing to be aware of 
 * exactly what type of building it is creating.
 * @author Group FRIDGE
 */
public class BuildingFactory {
    /**
     * Called by other classes to create a building using a type string and coordinates.
     * This function deals with the logic of figuring out which type of object to create.
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @param type String: type of building to create
     * @return Building: Building that is created.
     */
    public Building create(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        if (type.equals("campfire")) {
            return createCampfire(x, y);
        }
        else if (type.equals("barracks")) {
            return createBarracks(x, y);
        }
        else if (type.equals("totem")) {
            return createTotem(x, y);
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
        else if (type.equals("heros_castle")) {
            return createHeroCastle(x, y);
        }
        else if (type.equals("bank")) {
            return createBank(x, y);
        }
        else {
            return null;
        }
    }

    private Building createTotem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new Totem(x, y);
    }

    private Building createBank(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BankBuilding(x, y);
    }

    /**
     * Creates a CampfireBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return CampfireBuilding: Building that is created.
     */
    private CampfireBuilding createCampfire(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireBuilding(x, y);
    }
    
    /**
     * Creates a BarracksBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return BarracksBuilding: Building that is created.
     */
    private BarracksBuilding createBarracks(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new BarracksBuilding(x, y);
    }
    
    /**
     * Creates a TowerBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return TowerBuilding: Building that is created.
     */
    private TowerBuilding createTower(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TowerBuilding(x, y);
    }
    
    /**
     * Creates a TrapBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return TrapBuilding: Building that is created.
     */
    private TrapBuilding createTrap(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new TrapBuilding(x, y);
    }
    
    /**
     * Creates a VampireCastleBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return VampireCastleBuilding: Building that is created.
     */
    private VampireCastleBuilding createVampireCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VampireCastleBuilding(x, y);
    }
    
    /**
     * Creates a VillageBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return VillageBuilding: Building that is created.
     */
    private VillageBuilding createVillage(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageBuilding(x, y);
    }
    
    /**
     * Creates a ZombiePitBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return ZombiePitBuilding: Building that is created.
     */
    private ZombiePitBuilding createZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new ZombiePitBuilding(x, y);
    }

    /**
     * Creates a HerosCastleBuilding
     * @param x SimpleIntegerProperty: Column coordinate of building on map
     * @param x SimpleIntegerProperty: Row coordinate of building on map
     * @return HerosCastleBuilding: Building that is created.
     */
    private HerosCastleBuilding createHeroCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new HerosCastleBuilding(x, y);
    }
}
