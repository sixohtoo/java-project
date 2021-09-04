package unsw.loopmania.Enemies;

import java.util.List;

import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Entities.MovingEntity;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Abstract class that provides all enemies with basic actions
 */
public abstract class Enemy extends MovingEntity {
    private int battleRadius;
    private int supportRadius;
    public String[] itemList;   
    private int attackDamage;
    private int goldAmount;
    private int xpAmount;

    /**
     * Constructor for enemy class
     * @param position Position of enemy on map
     * @param battleRadius support radius of enemy
     * @param supportRadius battle radius of enemy
     * @param attackDamage attack damage of enemy
     * @param goldAmount gold dropped by enemy
     * @param health health of enemy
     * @param xp xp dropped by enemy
     */
    public Enemy (PathPosition position, int battleRadius, int supportRadius, int attackDamage, int goldAmount, int health, int xp) {
        super(position, health);
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.attackDamage  = attackDamage;
        this.goldAmount = goldAmount;
        this.xpAmount = xp;
        itemList = new String[] {"sword", "stake", "staff", "shield", "helmet", "armour", "healthpotion", "strengthpotion", "thornmail", "axe"};
    }
    /**
     * Constructor for enemy class
     * @param battleRadius support radius of enemy
     * @param supportRadius battle radius of enemy
     * @param attackDamage attack damage of enemy
     * @param goldAmount gold dropped by enemy
     * @param health health of enemy
     * @param xp xp dropped by enemy
     */
    public Enemy (int battleRadius, int supportRadius, int attackDamage, int goldAmount, int health, int xp) {
        super(health);
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.attackDamage  = attackDamage;
        this.goldAmount = goldAmount;
        this.xpAmount = xp;
        itemList = new String[] {"sword", "stake", "staff", "shield", "helmet", "armour", "healthpotion", "strengthpotion", "thornmail", "axe"};
    }

    /**
     * Enemy attacks the hero given
     * @param hero hero enemy is attacking
     * @return boolean if the character was killed returns true
     */
    public void attack (Hero hero) {
        hero.takeDamage(attackDamage, this);
    }

    /**
     * Enemy attacks the hero given.
     * BattleRunner is passed in to allow weapon to interact more with battle
     * @param hero Hero enemy is attacking
     * @param bR BattleRunner
     */
    public void attack(Hero hero, BattleRunner bR) {
        hero.takeDamage(attackDamage, this);

    }
    /**
     * move the enemy
     */
    public void move(){
        int directionChoice = LoopManiaWorld.getRandNum() % 2;
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }
    
    public abstract List<StaticEntity> getLoot(Character character, int width, List<String> rareItems);

    // Getters and Setters
    
    public int getHealth() {
        return health.get();
    }
    public int getGold() {
        return goldAmount;
    }

    public int getXP() {
        return xpAmount;
    }

    public int getBattleRadius() {
        return this.battleRadius;
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }
    public int getSupportRadius() {
        return this.supportRadius;
    }

    /**
     * Elan Muske can heal enemies around him
     */
    public void heal() {
        health.set((int)(health.get() * 1.05));
    }
}
