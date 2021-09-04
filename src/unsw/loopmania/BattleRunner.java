package unsw.loopmania;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unsw.loopmania.Heroes.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Factories.*;


/**
 * Mediator pattern that runs the battle. BattleRunner retains no information
 * about the world state or fight and is only called when a battle occurs.
 * This class is used to seperate the battle running logic from other classes
 * to keep them simpler and more relevant.
 * @author Group FRIDGE
 */
public class BattleRunner {
    private Character character;
    private ArrayList<Enemy> defeatedEnemies;
    private List<Enemy> enemies;
    private List<AlliedSoldier> allies;
    private List<TowerBuilding> towers;

    /**
     * Empty constructor for BattleRunner class because it doesn't retain
     * any knowledge about the world state.
     */
    public BattleRunner() {
        
    }

    /**
     * Setter to set the character once the fight happens
     * @param c Character: The character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Setter to set the list of enemies involved in the fight
     * @param enemies List<Enemy>: The enemies involved in the fight
     */
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    /**
     * Checks whether a fight will happen given the current position of the character and the enemies.
     * If no fight will happpen, runBattle will be passed an empty list and end immediately.
     * BattleRunner does not distinguish between supporting and attacking enemies. Each tick, checkForFight
     * is called to determine whether a battle takes place
     * @param enemies List<Enemy>: List of all enemy locations
     * @param moveBuildings List<BuildingOnMove>: List of all activate-on-move buildings (to get all towers in range)
     * @return List<Enemy>: List of enemies that died in the fight (Empty if no battle takes place)
     */
    public List<Enemy> checkForFight(List<Enemy> enemies, List<BuildingOnMove> moveBuildings) {
        // List of enemies participating in battle
        List<Enemy> attacking = new ArrayList<Enemy>();
        // Loop through all enemies on map
        for (Enemy enemy : enemies) {
            int battleRadius = enemy.getBattleRadius();
            int supportRadius = enemy.getSupportRadius();
            double distance = Math.sqrt(Math.pow((character.getX()-enemy.getX()), 2) +  Math.pow((character.getY()-enemy.getY()), 2));
            // If enemy is in battle radius, add them to battle
            if (distance <= battleRadius) {
                attacking.add(enemy);
                // If enemy is in support radius, add them to battle
            } else if (distance <= supportRadius) {
                attacking.add(enemy);
            }
        }
        List<TowerBuilding> towers = getInRangeTowers(moveBuildings);
        if (!attacking.isEmpty()) {
            // Reverse list so that enemies in battle radius are attacked first
            Collections.reverse(attacking);
            return runBattle(attacking, character.getAlliedSoldiers(), towers);
        }
            else return attacking;
    }


    /**
     * Gets all towers in range of character that can participate in the battle
     * @param moveBuildings List<BuildingOnMove>: A list of all activate-on-move buildings on the map
     * @return List<TowerBuilding>: A list of all tower buildings participating in the battle
     */
    private List<TowerBuilding> getInRangeTowers(List<BuildingOnMove> moveBuildings) {
        List<TowerBuilding> towers = new ArrayList<TowerBuilding>();
        for (BuildingOnMove building : moveBuildings) {
            if (building.getType().equals("tower")) {
                TowerBuilding tower = (TowerBuilding) building;
                if (tower.isInRange(character)) {
                    towers.add(tower);
                }
            }
        }
        return towers;
    }



    /**
     * run the expected battles in the world, based on current world state
     * @return List of defeated enemies
     */
    public ArrayList<Enemy> runBattle(List<Enemy> enemies, List<AlliedSoldier> allies, List<TowerBuilding> towers) {
        // List of to be defeated enemies
        defeatedEnemies = new ArrayList<Enemy>();
        // Put these in class variables so we don't have to keep calling them in helper functions
        this.enemies = enemies;
        this.allies = allies;
        this.towers = towers;
        while (!character.isDead() && !enemies.isEmpty()) {
            runHeroAttacks();
            runEnemyAttacks();
            checkTrancedEnemies();
        }
        // Kill any tranced enemies
        killConvertedEnemies();
        // Remove strength potion effect
        character.removeBuff();
        return defeatedEnemies;
    }

    /**
     * Checks list of allies and converts any converted enemies (due to staff)
     * back into enemies if needed. When exiting a trance, converted enemies
     * retain the same amount of health that they had before they were converted.
     */
    public void checkTrancedEnemies() {
        for (int i = allies.size() - 1; i >= 0; i--) {
            AlliedSoldier ally = allies.get(i);
            if (ally instanceof ConvertedEnemy) {
                if (((ConvertedEnemy)ally).canExitTrance()) {
                    Enemy original = ((ConvertedEnemy)ally).getEnemy();
                    enemies.add(original);
                    killAlly(ally);
                }
            }
        }
    }

    /**
     * Called after the battle has ended. Any enemies that end the battle
     * tranced/converted by the staff die.
     */
    private void killConvertedEnemies() {
        for (int i = allies.size() - 1; i >= 0; i--) {
            AlliedSoldier ally = allies.get(i);
            if (ally instanceof ConvertedEnemy) {
                killAlly(ally);
            }
        }
    }

    /**
     * Revives the player if they have The One Ring in their inventory. 
     * When the player is revived, the battle continues without stopping.
     * @param c Character: The character
     */
    private void revivecharacter(Character character) {
        character.setHealth(100);
    }

    /**
     * Converts an allied soldier (or converted enemy) that has been
     * attacked by a zombie with critical bite into a zombie, and then 
     * adds it to the enemies list. The new zombie won't attack this round.
     * @param a AlliedSoldier that has been bitten
     */
    public void convertAllyToZombie(AlliedSoldier alliedSoldier) {
        EnemyFactory factory = new EnemyFactory();
        Enemy zombie =  factory.create("zombie");
        enemies.add(zombie);
    }

    /**
     * Deals with the staff converting an enemy into an allied soldier (convertedEnemy).
     * The enemy is saved to retain its health once the trance ends
     * @param enemy Enemy: Enemy that has been tranced
     */
    public void convertEnemyToAlly(Enemy enemy) {
        enemies.remove(enemy);
        HeroFactory factory = new HeroFactory();
        ConvertedEnemy converted = (ConvertedEnemy) factory.create(enemy);
        character.addAlliedSoldier((AlliedSoldier)converted);
        
    }

    /**
     * Deals with enemy attacks. Every enemy attacks one after another.
     * If there are any allied Soldiers alive, they get attacked first 
     * (before the player). Enemies attack in order of oldest to youngest.
     */
    private void runEnemyAttacks() {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            // Enemy attacks allies first
            if (!allies.isEmpty() && !(enemy instanceof Thief)) {
                AlliedSoldier ally = allies.get(0);
                // If enemy is an instance of a zombie or doggie, battle runner
                // needs to passed to use of its functions (i.e convert ally zombie)
                if (enemy instanceof Zombie || enemy instanceof Doggie) {
                    enemy.attack(ally, this);
                } 
                else {
                    enemy.attack(ally);
                }
                if (ally.isDead()) {
                    killAlly(ally);
                }
            } 
            else {
                // If no allies, attack character
                enemy.attack(character, this);
                if (character.isDead() && character.hasRing()) {
                    revivecharacter(character);
                }
            }
        }
    }

    /**
     * Runs the hero attacks. Tower attacks first, then allied soldiers attack
     * and then the character attacks. Heros attack the oldest enemy first.
     */
    private void runHeroAttacks() {
        // Towers attack first
        for (TowerBuilding tower : towers) {
            if (!enemies.isEmpty()) {
                Enemy enemy = enemies.get(0);
                tower.attack(enemy);
                postFight(enemy);
            }
        }
        // Then allies
        for (AlliedSoldier ally : allies) {
            if (!enemies.isEmpty()) {
                Enemy enemy = enemies.get(0);
                ally.attack(enemy);
                postFight(enemy);
            }
        }
        // Finally character attacks
        if (!enemies.isEmpty()) {
            Enemy enemy = enemies.get(0);
            if (!character.isStunned()) {
                character.attack(enemy, this);
                postFight(enemy);
            } else {
                character.setStunned(false);
            }
            
        }
    
    }

    /**
     * Deals with any potential enemy deaths. BattleRunnner saves a list of all
     * enemies that have died and updates it with every death.
     * @param enemy Enemy: enemy that has potentially died
     */
    private void postFight(Enemy enemy){
        if (enemy.isDead()){
            killEnemy(enemy);
            defeatedEnemies.add(enemy);
        }
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
        character.gainGold(enemy.getGold());
        character.gainXP(enemy.getXP());
    }

    /**
     * kill an ally
     * @param enemy enemy to be killed
     */
    private void killAlly(AlliedSoldier ally) {
        allies.remove(ally);
    }
    /**
     * stuns the character
     */
    public void stunCharacter() {
        character.setStunned(true);
    }

    /** 
     * Elan Musk heal ability, heals all enemies
     */
    public void healenemies() {
        for (Enemy enemy: enemies) {
            if (!(enemy instanceof ElanMuske)) {
                enemy.heal();
            }
        }
    }
}