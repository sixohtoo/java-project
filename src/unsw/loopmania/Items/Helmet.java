package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The helmet protects the player from damage but also reduces the damage the player can deal
 */
public class Helmet extends Protection {
    public static final int PRICE = 400;
    public static final int BASEDEBUFF = 10;
    private int damageReduction = 3;
    private double debuff;
    /**
     * Constructor for helmet class
     * @param x X coordinate of helmet in the character's inventory
     * @param y Y coordinate of helmet in the character's inventory
     * @param level Level of helmet
     */
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y, int level) {
        super(level, PRICE, x, y);
        debuff = BASEDEBUFF - (level-1);
        damageReduction += (level-1);
        super.setType("helmet");
    }
    
    /**
     * Constructor for helmet class
     * @param level Level of helmet
     */
    public Helmet(int level) {
        super(level, PRICE);
        debuff = BASEDEBUFF - (level-1);
        damageReduction += (level-1);
        super.setType("helmet");
    }

    /**
     * Given the damage being inflicted on the character and will return the amount
     * of damage that will be inflicted after the reduction from the protection
     * @param damage the total damage being given
     * @return the damage after the reduction from protection has been applied
     */
    @Override
    public double protect(double damage) {
        //Will implement the drop by the scalar value
        return damage - damageReduction;
    }
    /**
     * Applies damage debuff to character
     * @param attackDamage
     * @return reduced damage
     */
    public double calcAttackDamage(double attackDamage) {
        if (debuff > 0) {
            return attackDamage * (1 - debuff * 0.01);
        } 
        return attackDamage;
    }
    
}
