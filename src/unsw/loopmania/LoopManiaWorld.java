package unsw.loopmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Heroes.*;
import unsw.loopmania.Items.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Factories.*;
import unsw.loopmania.GoalCalculator.*;
import unsw.loopmania.Entities.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Shop.*;
import unsw.loopmania.Cards.*;


/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */

public class LoopManiaWorld {
    public final int WEAPONSLOT = 0;
    public final int HELMETSLOT = 1;
    public final int SHIELDSLOT = 2;
    public final int ARMOURSLOT = 3;
    public static int seed;
    private static Random rand;
    private BuildingFactory bF;
    private int width;
    private int height;
    private JSONObject json;
    private List<String> rareItems;
    private Composite winChecker;
    private List<Entity> nonSpecifiedEntities;
    private Character character;
    private Pair<Integer, Integer> heroCastlePosition;
    private List<Enemy> enemies;
    private List<BuildingOnCycle> cycleBuildings;
    private List<BuildingOnMove> moveBuildings;
    private BattleRunner battleRunner;
    private List<Pair<Integer, Integer>> orderedPath;
    private List<Coin> gold;
    private List<Poop> poop;
    private Shop shop;
    private Boolean muskeSpawned = false; 
    private ArrayList<String> selectedGamemode;
    

    public static final int DOGGIESPAWNCYCLE = 20;
    public static final int THIEFSPAWNCYCLE = 4;
    public static final int ELANMUSKESPAWNCYCLE = 40;
    public static final int ELANMUSKESPAWNXP = 10000;

    /**
     * Common methods and stats used by random seed and sest seed constructors
     * @param width int : Width of map
     * @param height int : Height of map
     * @param orderedPath : Path
     * @param json : JSON file used to load the world
     */
    private void commonConstructorMethod(int width, int height, List<Pair<Integer, Integer>> orderedPath, JSONObject json) {
        this.width = width;
        this.height = height;
        bF = new BuildingFactory();
        selectedGamemode = new ArrayList<String>(Arrays.asList("standard"));
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        this.orderedPath = orderedPath;
        moveBuildings = new ArrayList<BuildingOnMove>();
        cycleBuildings = new ArrayList<BuildingOnCycle>();
        battleRunner = new BattleRunner();
        this.json = json;
        rareItems = new ArrayList<String>();
        gold = new ArrayList<Coin>();
        poop = new ArrayList<Poop>();
        spawnCoin();
        if (!json.has("saveWorld")) {
            spawn2slugs();
        }
        getRareItems();
    }

    /**
     * Constructor with random seed
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath, JSONObject goals) {
        seed = (int)System.currentTimeMillis();
        LoopManiaWorld.rand = new Random(seed);
        commonConstructorMethod(width, height, orderedPath, goals);
    }

    /**
     * Constructor with set seed
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath, JSONObject goals, int seed) {
        LoopManiaWorld.seed = seed;
        LoopManiaWorld.rand = new Random(seed);
        commonConstructorMethod(width, height, orderedPath, goals);
    }

    /**
     * Constructor to only set seed (e.g. for unit tests that use LoopManiaWorld.getRandNum())
     */
    public LoopManiaWorld(int seed) {
        rand = new Random(seed);
    }

    /**
     * Places building that occur at start of game
     * @param x
     * @param y
     * @param type
     */
    public void placeBuildingAtStart(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        Building building = bF.create(x, y, type);
        if (building instanceof BuildingOnCycle) {
            cycleBuildings.add((BuildingOnCycle)building);
        }
        else {
            moveBuildings.add((BuildingOnMove)building);
        }
    }

    /**
     * Parse through json to get rare items
     */
    public void getRareItems() {
        JSONArray rareItemList = json.getJSONArray("rare_items");
        for (int i = 0; i < rareItemList.length(); i++) {
            rareItems.add(rareItemList.getString(i));
        }
    }

    /**
     * Spawns 2 slugs at start of game
     */
    public void spawn2slugs() {
        int pos1 = LoopManiaWorld.getRandNum() % orderedPath.size();
        int pos2 = LoopManiaWorld.getRandNum() % orderedPath.size();
        if (pos1 == pos2) pos2 += 1;
        spawnSlug(pos1, orderedPath);
        spawnSlug(pos2, orderedPath);
    }

    /**
     * Spawns coin at start of game
     */
    public void spawnCoin() {

        int pos = LoopManiaWorld.getRandNum() % orderedPath.size();
        PathPosition position = new PathPosition(pos, orderedPath);
        gold.add(new Coin(position.getX(), position.getY()));
    }
    
    /**
     * Checks if character is dead
     * @return
     */
    public boolean isCharacterDead() {
        return character.isDead();
    }
    /**
     * Simulates one tick in game
     */
    public void tick() {
        updateEnemyList();
        moveEntities();
        List<Enemy> deadEnemies = fight();
        for (Enemy e : deadEnemies) {
            e.getLoot(character, width, rareItems);
        }
        cleanUpFight();
    }
    /**
     * Moves character and enemies and check if they activated any buildings
     * @return
     */
    public List<Enemy> moveEntities() {
        List<Enemy> newEnemies = new ArrayList<Enemy>();
        character.moveDownPath();
        checkBuildingActions(character, newEnemies);
        checkGoldActions(character);
        checkPoopActions(character);
        moveEnemies(newEnemies);
        triggerCycleActions(newEnemies);
        updateEnemyList();
        updateBuildingList();
        return newEnemies;
    }
    /**
     * Checks if any enemies died during battle
     */
    public void updateEnemyList() {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            if (!e.shouldExist().get()) {
                enemies.remove(i);
            }
        }
    }

    /**
     * Updates list of buildings after each tick to remove anything that's been destoroyed (e.g. traps)
     */
    public void updateBuildingList() {
        for (int i = moveBuildings.size() - 1; i >= 0; i--) {
            BuildingOnMove b = moveBuildings.get(i);
            if (!((StaticEntity)b).shouldExist().get()) {
                moveBuildings.remove(i);
            }
        }
        for (int i = cycleBuildings.size() - 1; i >= 0; i--) {
            BuildingOnCycle b = cycleBuildings.get(i);
            if (!((StaticEntity)b).shouldExist().get()) {
                cycleBuildings.remove(i);
            }
        }
    }

    
    /**
     * Checks for a fight with character and runs the battle
     * @return list of defeated enemies
     */
    public List<Enemy> fight() {
        List<Enemy> deadEnemies = checkForFight();
        return deadEnemies;
    }
    /**
     * Checks if player has won or nt and updates allied soldier amount
     */
    public void cleanUpFight() {
        if (checkPlayerWin()) {
            System.out.println("GAME HAS BEEN WON");
        }
        else if (checkPlayerLoss()) {
            System.out.println("GAME HAS BEEN LOST");
        }
        updateEnemyList();
        character.updateAlliedSoldierAmount();
    }
    /**
     * Kills an enemy
     * @param enemy - the enemy that died
     */
    public void KillEnemy(Enemy enemy) {
        enemy.shouldExist().set(false);
        enemies.remove(enemy);
    }
    /**
     * Updates cycle count after lap and spawns new enemies
     * @param newEnemies List of enemies that have been created
     */
    public void triggerCycleActions(List<Enemy> newEnemies) {
        if (onHeroCastle()) {
            SpawnEnemiesOnCycle(newEnemies);
            character.gainCycle();
            // if (character.getCycles().get() % 3 == 0) {               
            // }
            spawnTotemOnCycle();
            spawnCoinsOnCycle();
            spawnPoopOnCycle();
            character.makeVincible();
        }
    }


    /**
     * Checks if character is standing on hero castle
     * @return whether character is at the heros castle
     */
    public Boolean onHeroCastle() {
        Pair<Integer, Integer> characterPos = new Pair<Integer, Integer>(character.getX(), character.getY());
        return characterPos.equals(heroCastlePosition);
    }
    /**
     * Checks if character is in range of enemies
     * @return list of defeated enemies
     */
    private List<Enemy> checkForFight() {
        return battleRunner.checkForFight(enemies, moveBuildings);
    }
    /**
     * check if character has won
     * @return true of false if player has won
     */
    public boolean checkPlayerWin() {
        return winChecker.getValue();
    }
    /**
     * check if character has lost
     * @return true of false if player has lost
     */
    public boolean checkPlayerLoss() {
        return character.getHealth() <= 0;
    }
    /**
     * Processes loot drop from enemy
     * This function is only called by the frontend
     * @param deadEnemy
     * @return Loot
     */
    public List<StaticEntity> processEnemyLoot(Enemy deadEnemy) {
        return deadEnemy.getLoot(character, width, rareItems);
    }
    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        nonSpecifiedEntities.add(entity);
    }

    /**
     * Checks whether a pathtile has enemies or the character on it.
     * Enemies can't spawn on a tile that already has a MovingEntity on it
     * @param pathTile Path tile in question
     * @return Boolean on whether tile is empty
     */
    private boolean empty(Pair<Integer, Integer> pathTile) {
        if (!enemies.isEmpty()) {
            for (Enemy e : enemies) {
                if (e.getX() == pathTile.getValue0() && e.getY() == pathTile.getValue1()) {
                    return false;
                }
            }
        }
        if (character.getX() == pathTile.getValue0() && character.getY() == pathTile.getValue1()) {
            return false;
        }
        return true;
    }

    /**
     * Generates a list of path tiles adjacent to the building in question
     * @param building The building
     * @return List of adjacent path tiles
     */
    private List<Pair<Integer, Integer>> getAdjacentPathTiles(StaticEntity building) {
        List<Pair<Integer, Integer>> adjacent = new ArrayList<Pair<Integer, Integer>>();
        for (Pair<Integer, Integer> tile : orderedPath) {
            if (!empty(tile)) continue;

            // If path is above building
            if (tile.getValue0() == building.getX() && tile.getValue1() == building.getY() - 1) {
                adjacent.add(tile);
            }
            // If path is below building
            else if (tile.getValue0() == building.getX() && tile.getValue1() == building.getY() + 1) {
                adjacent.add(tile);
            }
            // If path is left of building
            else if (tile.getValue0() == building.getX() - 1 && tile.getValue1() == building.getY()) {
                adjacent.add(tile);
            }
            // If path is right of building
            else if (tile.getValue0() == building.getX() + 1 && tile.getValue1() == building.getY()) {
                adjacent.add(tile);
            }
        }
        return adjacent;
    }

    /**
     * Spawns enemies each cycle
     * @param newEnemies List of enemies that have been spawned
     */
    public void SpawnEnemiesOnCycle(List<Enemy> newEnemies) {
        // For each building, figure out how many/where to spawn enemies then spawn them
        for (BuildingOnCycle building : cycleBuildings) {
            // adjacent contains every PathTile touching building
            List<Pair<Integer, Integer>> adjacent = getAdjacentPathTiles((StaticEntity)building);
            int numSpawn;
            if (building instanceof VampireCastleBuilding) {
                // vampire castle needs to know what round it is
                numSpawn = Integer.min(((VampireCastleBuilding)building).generateNumberOfEnemies(character.getCycles().get()), adjacent.size());
            }
            else {
                numSpawn = Integer.min(building.generateNumberOfEnemies(), adjacent.size());
            }
            for (int i = 0; i < numSpawn; i++) {
                int tile = LoopManiaWorld.getRandNum() % adjacent.size();
                int positioninPath = getNumInPath(adjacent.get(tile));
                Enemy enemy = building.spawnEnemy(new PathPosition(positioninPath, orderedPath));
                adjacent.remove(tile);
                enemies.add(enemy);
                newEnemies.add(enemy);
            }
        }
        // Spawn 1-4 slugs every cycle
        List<Pair<Integer, Integer>> emptyTiles = getAllEmptyTiles();
        for (int i = 0; i < (LoopManiaWorld.getRandNum() % 4) + 1; i++) {
            int position = LoopManiaWorld.getRandNum() % emptyTiles.size();
            newEnemies.add(spawnSlug(position, emptyTiles));
        }
        if (character.getCycles().get()%THIEFSPAWNCYCLE == 0) {
            int position = LoopManiaWorld.getRandNum() % emptyTiles.size();
            newEnemies.add(spawnBoss(position, emptyTiles, "thief"));
        }
        if (character.getCycles().get() == DOGGIESPAWNCYCLE) {
            int position = LoopManiaWorld.getRandNum() % emptyTiles.size();
            newEnemies.add(spawnBoss(position, emptyTiles, "doggie"));
        }
        if (character.getCycles().get() >= ELANMUSKESPAWNCYCLE && character.getXP().get() >= ELANMUSKESPAWNXP && !muskeSpawned) {
            int position = LoopManiaWorld.getRandNum() % emptyTiles.size();
            newEnemies.add(spawnBoss(position, emptyTiles, "elanmuske"));
            muskeSpawned = true;
        }
    }

    /**
     * Spawns the boss
     * @param index : index in orderedPath
     * @param orderedPath orderedPath
     * @param bossString Boss type
     * @return Spawned boss
     */
    private Enemy spawnBoss(int index, List<Pair<Integer, Integer>> orderedPath, String bossString) {
        EnemyFactory enemy = new EnemyFactory();
        Enemy boss  =  enemy.create(new PathPosition(index, orderedPath), bossString);
        enemies.add(boss);
        return boss;
    }

    /**
     * Spawns a coin on the cycle each round
     */
    public void spawnCoinsOnCycle() {
        List<Pair<Integer, Integer>> emptyTiles = getAllEmptyTiles();
        if (emptyTiles.isEmpty()) return;
        int pos = LoopManiaWorld.getRandNum() % emptyTiles.size();
        PathPosition position = new PathPosition(pos, emptyTiles);
        gold.add(new Coin(position.getX(), position.getY()));
        

    }
    /**
     * Spawns some poop on the track
     */
    public void spawnPoopOnCycle() {
        List<Pair<Integer, Integer>> emptyTiles = getAllEmptyTiles();
        if (emptyTiles.isEmpty()) return;
        int pos = LoopManiaWorld.getRandNum() % emptyTiles.size();
        PathPosition position = new PathPosition(pos, emptyTiles);
        poop.add(new Poop(position.getX(), position.getY()));
        
    }
    private void spawnTotemOnCycle() {
        List<Pair<Integer, Integer>> emptyTiles = getAllEmptyTiles();
        if (emptyTiles.isEmpty()) return;
        int pos = LoopManiaWorld.getRandNum() % emptyTiles.size();
        PathPosition position = new PathPosition(pos, emptyTiles);
        Building newTotem = bF.create(position.getX(), position.getY(), "totem");
        moveBuildings.add((BuildingOnMove)newTotem);
    }

    /**
     * Loads a card in the backend
     */
    public StaticEntity loadCard(String type, int width) {
        return character.loadCard(type, width);
    }

    /**
     * Check if card placement is valid
     * This function is only called by the frontend
     * @param card
     * @param x
     * @param y
     * @return boolean
     */
    public boolean isValidPlacement(Card card, int x, int y) {
        return card.canBePlaced(x, y, orderedPath);
    }

    /**
     * Equips character with item
     * @param item Item to equip
     * @param slot Slot to equip item in (e.g.) some confusing items can go in 2 slots
     */
    public void equipItem(Item item, String slot) {
        character.equip(item, slot);
    }

    public void equipItem(Item item, int slot) {

    }

    /**
     * Gets closest campfire from Vampire
     * @param x
     * @param y
     * @return closest campfire
     */
    private CampfireBuilding getClosestCampfire(int x, int y) {
        CampfireBuilding closest = null;
        double closestDistance = getHeight(); // max distance
        for (BuildingOnMove building : moveBuildings) {
            if (building instanceof CampfireBuilding) {
                double distance = Math.sqrt(Math.pow(((StaticEntity)building).getX() - x, 2) + Math.pow(((StaticEntity)building).getY() - y, 2));
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closest = (CampfireBuilding)building;
                }
            }
        }
        return closest;
    }

    /**
     * Move all enemies. This method is called every tick.
     */
    private void moveEnemies(List<Enemy> newEnemies) { 
        int num_enemies = enemies.size();
        for (int i = 0; i < num_enemies; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy instanceof Vampire) {
                ((Vampire)enemy).move(getClosestCampfire(enemy.getX(), enemy.getY()));
            }
            else {
                enemy.move();
            }
            checkBuildingActions(enemy, newEnemies);
        }
    }

    /**
     * Updates all buildings that update on move
     * @param entity Moving entity that triggered building action
     */
    public void checkBuildingActions(MovingEntity entity, List<Enemy> newEnemies) {
        for (BuildingOnMove building : moveBuildings) {
            if (building instanceof Totem) {
                ((Totem) building).updateOnMove(entity, newEnemies, enemies);
            } else {
                building.updateOnMove(entity);
            }
        }
    }
    /**
     * Updates all gold on path that update on move
     * @param character The character
     */
    public void checkGoldActions(Character character) {
        for (Coin coin : gold) {
            coin.updateOnMove(character);
        }

        for (int i = gold.size() - 1; i >= 0; i--) {
            Coin coin = gold.get(i);
            if (!coin.shouldExist().get()) {
                gold.remove(i);
            }
        }

    }
    /**
     * Updates Poop on path that update on move
     * @param character The character
     */
    public void checkPoopActions(Character character) {
        for (Poop poopObject : poop) {
            poopObject.updateOnMove(character);
        }
        for (int i = poop.size() - 1; i >= 0; i--) {
            Poop poopObject = poop.get(i);
            if (!poopObject.shouldExist().get()) {
                poop.remove(i);
            }
        }   

    }
    /**
     * Adds building to list of buildings
     * @param building 
     */
    public void addBuilding(Building building) {
        if (building instanceof BuildingOnCycle) {
            cycleBuildings.add((BuildingOnCycle)building);
        }
        else if (building instanceof BuildingOnMove) {
            moveBuildings.add((BuildingOnMove)building);
        }
    }

    /**
     * Converts a dragged card into a building on the map and then removes the card afterwards
     * @param cardNodeX int: Column coordinate of card in card inventory
     * @param cardNodeY int: Row coordinate of card in card inventory
     * @param buildingNodeX int: Column coordinate of building on the map
     * @param buildingNodeY int: Row coordinate of building on the map
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = character.getMatchingCard(cardNodeX, cardNodeY);
        // now spawn building
        Building newBuilding = bF.create(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY), ((StaticEntity)card).getType());
        addBuilding(newBuilding);
        // destroy the card
        character.destroyCard(card, cardNodeX);
        return newBuilding;
    }

    /**
     * Spawns a slug  on an empty PathTile
     * @param i int: position in ordered path
     * @param orderedPath2 List<Pair<Integer, Integer>>: the orderedPath
     * @return Slug: the slug that was just spawned
     */
    public Slug spawnSlug(int i, List<Pair<Integer, Integer>> orderedPath) {
        EnemyFactory eF = new EnemyFactory();
        Enemy slug =  eF.create(new PathPosition(i, orderedPath), "slug");
        enemies.add(slug);
        return (Slug)slug;
    }
    

    /**
     * Makes character drink a healthpotion (if possible)
     */
    public void drinkHealthPotion() {
        character.drinkHealthPotion();
    }
    /**
     * Makes character drink a strengthpotion (if possible)
     */
    public void drinkStrengthPotion() {
        character.drinkStrengthPotion();
    }
    /**
     * Makes character drink a invinciblepotion (if possible)
     */
    public void drinkInvincibilityPotion() {
        character.drinkInvincibilityPotion();
    }

    /**
     * Makes the character use a nuke (if possible)
     * @return List of dead enemies
     */
    public List<Enemy> useNuke() {
        if (character.hasNuke()) {
            return enemies;
        }
        // return empty array list if no nuke
        return new ArrayList<Enemy>();
    }

    //Getters and Setters
    //////////////////////////////////
    /**
     * Gets the width of the map
     * @return int: the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the map
     * @return int: the height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Static method that generates a random number so other classes
     * that uses 'random' numbers can generate them using the same Random object
     * @return int: A random number between 0-99
     */
    public static int getRandNum() {
        return LoopManiaWorld.rand.nextInt(100);
    }

    /**
     * Gets the orderedPath that the map uses
     * @return List<Pair<Integer, Integer>>: the orderedPath
     */
    public List<Pair<Integer, Integer>> getOrderedPath() {
        List<Pair<Integer, Integer>> copy = new ArrayList<Pair<Integer, Integer>>();
        copy.addAll(orderedPath);
        return copy;
    }

    /**
     * Gets a list of enemies on the map
     * @return List<Enemy>: the list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Gets the coin on the map
     * @return Coin: the coin on the map
     */
    public List<Coin> getCoin() {
        return gold;
    }
    public List<Poop> getPoop() {
        return poop;
    }

    /**
     * Gets a list of the character's unequipped items
     * @return List<Item>: list of the character's unequipped items
     */
    public List<Item> getItems() {
        return character.getunequippedInventoryItems();
    }

    /**
     * Gets a list of all buildings that update when an entity moves on/within range of it
     * @return List<BuildingOnMove>: a list of buildings
     */
    public List<BuildingOnMove> getMoveBuildings() {
        return moveBuildings;
    }

    /**
     * Gets a list of all buildings that update at the start of every cycle
     * @return List<BuildingOnCycle>: a list of buildings
     */
    public List<BuildingOnCycle> getCycleBuildings() {
        return cycleBuildings;
    }

    /**
     * Gets the maximum amount of a goal 'resource' (gold, experience, cycles)
     * that is needed to win the game
     * @param goal String: The particular resource in question
     * @return int: How much is needed
     */
    public int getMaxGoal(String goal) {
        return winChecker.getMax(goal);
    }

    /**
     * Gets the index of a tile in the orderedPath
     * @param tile Pair<Integer, Integer>: The tile in question
     * @return int: The tile's index
     */
    private int getNumInPath(Pair<Integer, Integer> tile) {
        for (int i = 0; i < orderedPath.size(); i++) {
            if (tile.equals(orderedPath.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets an equipped item by its index in the equipped item inventory
     * @param x int: index of item
     * @return Item: The equipped item
     */
    public Item getEquippedItemByCoordinates(int x) {
        if (x == 0) {
            return character.getWeapon();
        }
        else if (x == 1) {
            return character.getHelmet(); 
        }
        else if (x == 2) {
            return character.getShield();
        }
        else if (x == 3) {
            return character.getArmour();
        }
        else {
            return null;
        }
    }

    /**
     * Gets a list of all items that don't have levels
     * (e.g. HealthPotion and TheOneRing)
     * @return List<String>: a list of all items that don't have levels
     */
    public List<String> getNonLevelItems() {
        return character.getNonLevelItems();
    }

    /**
     * Gets a list of all empty (spawnable) tiles
     * @return List<Pair<Integer, Integer>>: a list of all empty tiles
     */
    private List<Pair<Integer, Integer>> getAllEmptyTiles() {
        List<Pair<Integer, Integer>> tiles = new ArrayList<Pair<Integer, Integer>>();
        for (Pair<Integer, Integer> p : orderedPath) {
            if (empty(p)) tiles.add(p);
        }
        return tiles;
    }

    /**
     * Gets the character
     * @return Character: The character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        ItemFactory iF = new ItemFactory();
        this.character = character;
        character.setRareItems(rareItems);
        heroCastlePosition = new Pair<Integer, Integer>(character.getX(), character.getY());
        GoalCalculator goals = new GoalCalculator(json.getJSONObject("goal-condition"), character);
        winChecker = goals.getChecker();
        battleRunner.setCharacter(character);
        equipItem(iF.create(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), "sword", 1), "weapon");
        shop = new Shop(character);
        character.setRareItems(rareItems);
    }

    public void setMode(ArrayList<String> mode) {
        if (mode.contains("survival") && mode.contains("berserker")) {
            shop.setSurvivalAndBeserker();
            selectedGamemode.add("survival");
            selectedGamemode.add("berserker");
        }
        else if (mode.contains("survival")) {
            shop.setSurvival();
            selectedGamemode.add("survival");
        }
        else if (mode.contains("berserker")) {
            shop.setBeserker();
            selectedGamemode.add("berserker");
        }

        if (mode.contains("confusing") && rareItems.size() >= 2) {
            character.setConfusingMode();
            selectedGamemode.add("confusing");
        }
    }

    /**
     * Gets the width of the unequipped inventory
     * @return int: The width
     */
    public static int getunequippedInventoryWidth() {
        return Inventory.getunequippedInventoryWidth();
    }

    /**
     * Gets the height of the unequipped inventory
     * @return int: The height
     */
    public static int getunequippedInventoryHeight() {
        return Inventory.getunequippedInventoryHeight();
    }

    /**
     * Gets an unequipped item from the character's inventory by its coordinates
     * @param nodeX int: The column coordinate of the item in the character's unequipped item inventory
     * @param nodeY int: The row coordinate of the item in the character's unequipped item inventory
     * @return Item: The item at the coordinates
     */
    public Item getUnequippedInventoryItemEntityByCoordinates(int nodeX, int nodeY) {
        return character.getUnequippedInventoryItemEntityByCoordinates(nodeX, nodeY);
    }

    public List<Item> getUnequippedInventory() {
        return character.getunequippedInventoryItems();
    }

    /**
     * Adds an unequipped item to the character's inventory
     * @param type String: The type of item to add
     * @param i int: The level of the item
     * @return StaticEntity: The item added
     */
    public Item addUnequippedItem(String type, int level) {
        return (Item)character.addUnequippedItem(type, level);
    }

    public Item addUnequippedConfusedItem(String type, String additional) {
        return (Item)character.addUnequippedConfusedItem(type, additional);
    }

    /**
     * Duplicates a recently unequipped item and puts it back in the unequipped inventory.
     * The if statements are necessary for typecasting to get the level of each item,
     * and because not all protection items have 
     * @param olditem The item being overwritten
     * @param slot The slot being equipped in
     * @param x coordinate of now equipped item in unequipped inventory
     * @param y coordinate of now equipped item in unequipped inventory
     * @return Item being overwritten
     */
    public Item pickupUnequippedItem(Item olditem, double slot, int x, int y) {
        character.removeUnequippedInventoryItemByCoordinates(x, y);
        if (olditem instanceof ConfusedRareItem) {
            return addUnequippedConfusedItem(olditem.getType(), ((ConfusedRareItem)olditem).getAdditional().getType());
        }
        else if (slot == WEAPONSLOT) {
            return addUnequippedItem(olditem.getType(), ((Weapon)olditem).getLevel());
        }
        else {
            return addUnequippedItem(olditem.getType(), ((Protection)olditem).getLevel());
        }
    }

    /**
     * Removes an unequipped item to the character's inventory by its coordinates
     * @param nodeX int: The column coordinate of the item in the character's unequipped item inventory
     * @param nodeY int: The row coordinate of the item in the character's unequipped item inventory
     */
    public void removeUnequippedInventoryItemByCoordinates(int nodeX, int nodeY) {
        character.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * Gets a card from the character's card inventory from its index
     * @param x int: index of the card
     * @return Card: The card
     */
    public Card getCardByCoordinate(int nodeX) {
        return character.getCardByCoordinate(nodeX);
    }

    /**
     * Sets the seed that is used to generate random numbers
     * @param seed int: The seed to be used
     */
    public static void setSeed(int seed) {
        rand = new Random(seed);
    }
    
    /**
     * Gets the character's cycles
     * @return int: The character's cycles
     */
    public IntegerProperty getCycles() {
        return character.getCycles();
    }

    /**
     * Gets the character's gold
     * @return int: The character's gold
     */
    public IntegerProperty getGold() {
        return character.getGoldProperty();
    }

    /**
     * Gets the character's experience
     * @return int: The character's experience
     */
    public IntegerProperty getXP() {
        return character.getXP();
    }

    /**
     * Gets the character's goal for boss kills
     * @return int: The character's goal for boss kills
     */
    public IntegerProperty getBossKills() {
        return character.getBossKills();
    }

    /**
     * Gets the shop
     * @return the shop
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * Gets the character's health
     * @return IntegerProperty: The character's health
     */
    public IntegerProperty getHealth() {
        return character.getHealthProperty();
    }

    ////////////////
    // Getters used in SaveGame and LoadGame
    public int getSeed() {
        return seed;
    }

    public List<Entity> getNonSpecifiedEntities() {
        return nonSpecifiedEntities;
    }

    public Pair<Integer, Integer> getHerosCastlePosition() {
        return heroCastlePosition;
    }

    public JSONObject getJSON() {
        return json;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public List<String> getRareItemsList() {
        return rareItems;
    }

    public int getHealthPotionsBought() {
        return shop.getHealthPotionsBought();
    }

    public int getStrengthPotionsBought() {
        return shop.getStrengthPotionsBought();
    }

    public ArrayList<String> getSelectedGamemode() {
        return selectedGamemode;
    }

    public void loadPoop(int x, int y) {
        poop.add(new Poop(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y)));
    }

    public void loadCoin(int x, int y) {
        gold.add(new Coin(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y)));
    }

    public void saveGame(String name) {
        SaveGame save = new SaveGame(this);
        save.SaveWorld(name);
    }

///////////////////
//Functions for testing

/**
 * Integration tests aren't working on Gradle, so this is the only way to spawn a vampire
 * because without using the LoopManiaWorldLoader, it's impossible to load in a 
 */
    public Enemy spawnVampire() {
        EnemyFactory eF = new EnemyFactory();
        Enemy vamp = eF.create(new PathPosition(0, orderedPath), "vampire");
        enemies.add(vamp);
        return vamp;
    }
}