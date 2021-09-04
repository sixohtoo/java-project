package unsw.loopmania;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.loopmania.Heroes.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Items.*;
import unsw.loopmania.Entities.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Cards.*;

/**
 * SaveGame class saves the game state as a JSON file and creates a file in
 * backups which can then be loaded.
 */
public class SaveGame {
    private LoopManiaWorld world;
    private JSONObject save;

    /**
     * Constructor for SaveGame class
     * @param world LoopManiaWorld : world state to be saved
     */
    public SaveGame(LoopManiaWorld world) {
        this.world = world;
        save = new JSONObject();
    }

    /**
     * Saves the world state into a JSONfile
     * @param name String : name of saved world
     */
    public void SaveWorld(String name) {
        saveCharacter();
        // saveNonSpecifiedEntities();
        saveStaticEntities();
        saveEnemies();
        saveCycleBuildings();
        saveMoveBuildings();
        save.put("seed", world.getSeed());
        save.put("healthPotionsBought", world.getHealthPotionsBought());
        save.put("strengthPotionsBought", world.getStrengthPotionsBought());
        saveGameMode();
        // save.put("gameMode", world.getSelectedGamemode());

        JSONObject json = world.getJSON();
        json.put("saveWorld", save);

        try {
            FileWriter file = new FileWriter(String.format("backup/%s.json", name));
            file.write(json.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all character attributes (including inventory)
     * in the json save
     */
    private void saveCharacter() {
        Character character = world.getCharacter();
        JSONObject characterJSON = new JSONObject();
        characterJSON.put("experience", character.getXP().get());
        characterJSON.put("gold", character.getGold());
        characterJSON.put("cycles", character.getCycles().get());
        characterJSON.put("health", character.getHealth());
        characterJSON.put("bossKills", character.getBossKills().get());
        characterJSON.put("strengthpotionbuff", character.getStrengthPotionBuff());
        characterJSON.put("canTakeDamage", character.canTakeDamage());
        characterJSON.put("position", character.getIndexOfPosition());
        // characterJSON.put("x", character.getX());
        // characterJSON.put("y", character.getY());
        addEquippedWeapon(characterJSON);
        addEquippedArmour(characterJSON);
        addEquippedHelmet(characterJSON);
        addEquippedShield(characterJSON);
        addCharacterStats(characterJSON, character);
        characterJSON.put("aliveSoldiers", character.getAlliedSoldierCount());
        addUnequippedInventory(characterJSON, character.getInventory());
        save.put("character", characterJSON);
    }

    /**
     * Saves all nonspecified entities in the JSON
     */
    // private void saveNonSpecifiedEntities() {
    //     JSONArray nonSpecifiedEntities = new JSONArray();
    //     List<Entity> nonSpecifiedEntitiyList = world.getNonSpecifiedEntities();
    //     if (nonSpecifiedEntitiyList.isEmpty()) {
    //         return;
    //     }
    //     for (Entity entity : nonSpecifiedEntitiyList) {
    //         JSONObject entityJSON = new JSONObject();
    //         entityJSON.put("type", entity.getType());
    //         entityJSON.put("x", entity.getX());
    //         entityJSON.put("y", entity.getY());
    //         nonSpecifiedEntities.put(entityJSON);
    //     }
    //     save.put("nonSpecifiedEntities", nonSpecifiedEntities);
    // }

    private void saveStaticEntities() {
        // Save the poop
        List<Poop> poopList = world.getPoop();
        JSONArray poopArray = new JSONArray();
        if (!poopList.isEmpty()) {
            for (Poop poop : poopList) {
                JSONObject poopJSON = new JSONObject();
                poopJSON.put("x", poop.getX());
                poopJSON.put("y", poop.getY());
                poopArray.put(poopJSON);
            }
        }
        save.put("poop", poopArray);

        // save the coin
        List<Coin> coinList = world.getCoin();
        JSONArray coinArray = new JSONArray();
        if (!coinList.isEmpty()) {
            for (Coin coin : coinList) {
                JSONObject coinJSON = new JSONObject();
                coinJSON.put("x", coin.getX());
                coinJSON.put("y", coin.getY());
                coinArray.put(coinJSON);
            }
        }
        save.put("coin", coinArray);
    }

    /**
     * Saves all currently alive enemies in the JSON
     */
    private void saveEnemies() {
        List<Enemy> list = world.getEnemies();
        JSONArray enemies = new JSONArray();
        for (Enemy enemy : list) {
            JSONObject enemyObject = new JSONObject();
            enemyObject.put("type", enemy.getType());
            enemyObject.put("index", enemy.getIndexOfPosition());
            enemies.put(enemyObject);
        }
        save.put("enemies", enemies);
    }

    /**
     * Saves all CycleBuildings in the JSON
     */
    private void saveCycleBuildings() {
        JSONArray buildings = new JSONArray();
        List<BuildingOnCycle> list = world.getCycleBuildings();
        for (BuildingOnCycle building : list) {
            JSONObject buildingObject = new JSONObject();
            buildingObject.put("type", ((StaticEntity)building).getType());
            buildingObject.put("X", ((StaticEntity)building).getX());
            buildingObject.put("Y", ((StaticEntity)building).getY());
            buildings.put(buildingObject);
        }
        save.put("cycleBuildings", buildings);
    }

    /**
     * Saves all MoveBuildings in the JSON
     */
    private void saveMoveBuildings() {
        JSONArray buildings = new JSONArray();
        List<BuildingOnMove> list = world.getMoveBuildings();
        for (BuildingOnMove building : list) {
            JSONObject buildingObject = new JSONObject();
            buildingObject.put("type", ((StaticEntity)building).getType());
            buildingObject.put("X", ((StaticEntity)building).getX());
            buildingObject.put("Y", ((StaticEntity)building).getY());
            buildings.put(buildingObject);
        }
        save.put("moveBuildings", buildings);
    }

    /**
     * Saves the character's equipped weapon in the json.
     * The character always has an equipped weapon, which could be a
     * confused rare item.
     * @param character Character : character in question
     */
    private void addEquippedWeapon(JSONObject character) {
        JSONObject equippedWeapon = new JSONObject();
        Item item = world.getEquippedItemByCoordinates(0);
        equippedWeapon.put("type", item.getType());
        equippedWeapon.put("level", ((Weapon)item).getLevel());
        equippedWeapon.put("x", item.getX());
        equippedWeapon.put("y", item.getY());
        if (item instanceof ConfusedRareItem) {
            equippedWeapon.put("additional", ((ConfusedRareItem)item).getAdditional().getType());
        }
        character.put("equippedWeapon", equippedWeapon);
    }

    /**
     * Adds character's equipped helmet (if there is one) to the json
     * @param character Character : character in question
     */
    private void addEquippedHelmet(JSONObject character) {
        JSONObject equippedHelmet = new JSONObject();
        Item item = world.getEquippedItemByCoordinates(1);
        if (item != null) {
            equippedHelmet.put("type", item.getType());
            equippedHelmet.put("level", ((Protection)item).getLevel());
            equippedHelmet.put("x", item.getX());
            equippedHelmet.put("y", item.getY());
        }
        character.put("equippedHelmet", equippedHelmet);
    }

    /**
     * Saves the character's equipped shield (if there is one) in the json.
     * The shield could be a confused rare item.
     * @param character Character : character in question
     */
    private void addEquippedShield(JSONObject character) {
        JSONObject equippedShield = new JSONObject();
        Item item = world.getEquippedItemByCoordinates(2);
        if (item != null) {
            equippedShield.put("type", item.getType());
            equippedShield.put("level", ((Protection)item).getLevel());
            equippedShield.put("x", item.getX());
            equippedShield.put("y", item.getY());
            if (item instanceof ConfusedRareItem) {
                equippedShield.put("additional", ((ConfusedRareItem)item).getAdditional().getType());
            }
        }
        character.put("equippedShield", equippedShield);
    }
    
    /**
     * Adds character's equipped helmet (if there is one) to the json
     * @param character Character : character in question
     */
    private void addEquippedArmour(JSONObject character) {
        JSONObject equippedArmour = new JSONObject();
        Item item = world.getEquippedItemByCoordinates(3);
        if (item != null) {
            equippedArmour.put("type", item.getType());
            equippedArmour.put("level", ((Protection)item).getLevel());
            equippedArmour.put("x", item.getX());
            equippedArmour.put("y", item.getY());
        }
        character.put("equippedArmour", equippedArmour);
    }

    /**
     * Adds character's highest level stats to the JSON
     * @param characterJSON JSONObject : Character JSON object
     * @param character Character : The character in question
     */
    private void addCharacterStats(JSONObject characterJSON, Character character) {
        CharacterStats cs = character.getStats();
        JSONObject stats = new JSONObject();
        stats.put("sword", cs.getHighestLevel("sword"));
        stats.put("stake", cs.getHighestLevel("stake"));
        stats.put("staff", cs.getHighestLevel("staff"));
        stats.put("shield", cs.getHighestLevel("shield"));
        stats.put("armour", cs.getHighestLevel("armour"));
        stats.put("helmet", cs.getHighestLevel("helmet"));
        characterJSON.put("stats", stats);
    }

    /**
     * Adds all unequipped items and unused cards into the JSON
     * Some items can be confused rare items which require additional work.
     * The doggie coin has a strategy that needs to be saved.
     * @param character JSONObject : Character JSON object
     * @param inventory Inventory : Character's inventory.
     */
    private void addUnequippedInventory(JSONObject character, Inventory inventory) {
        JSONArray items = new JSONArray();
        for (Item item : inventory.getunequippedInventoryItems()) {
            JSONObject itemObject = new JSONObject();
            itemObject.put("type", item.getType());
            if (item instanceof DoggieCoin) {
                itemObject.put("strategy", ((DoggieCoin)item).getStrategy());
            }
            else if (item instanceof ConfusedRareItem) {
                itemObject.put("additional", ((ConfusedRareItem)item).getAdditional().getType());
            }
            else if (item instanceof Protection) {
                itemObject.put("level", ((Protection)item).getLevel());
            }
            else if (item instanceof Weapon) {
                itemObject.put("level", ((Weapon)item).getLevel());
            }
            items.put(itemObject);
        }

        JSONArray cards = new JSONArray();
        for (Card card : inventory.getCards()) {
            JSONObject cardJSON = new JSONObject();
            cardJSON.put("type", ((StaticEntity)card).getType());
            cards.put(cardJSON);
        }

        character.put("unequippedItems", items);
        character.put("cards", cards);
    }

    private void saveGameMode() {
        JSONArray gamemodes = new JSONArray();
        for (String mode : world.getSelectedGamemode()) {
            gamemodes.put(mode);
        }
        save.put("gameMode", gamemodes);
    }
}
