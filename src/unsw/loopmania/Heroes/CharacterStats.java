package unsw.loopmania.Heroes;

import unsw.loopmania.Items.*;

/**
 * Class that keeps track of the current highest level for each item
 */
public class CharacterStats {
    private int sword;
    private int stake;
    private int staff;
    private int shield;
    private int helmet;
    private int armour;
    private int thornmail;
    private int axe;

    /**
     * Constructor for CharacterStats
     */
    public CharacterStats() {
        sword = 1;
        stake = 1;
        staff = 1;
        shield = 1;
        helmet = 1;
        armour = 1;
        thornmail = 1;
        axe = 1;
    }
    /**
     * Gets highest level of respective item
     * @param type
     * @return int highest level
     */
    public int getHighestLevel(String type) {
        if (type.equals("sword")) {
            return sword;
        }
        else if (type.equals("stake")) {
            return stake;
        }
        else if (type.equals("staff")) {
            return staff;
        }
        else if (type.equals("shield")) {
            return shield;
        }
        else if (type.equals("helmet")) {
            return helmet;
        }
        else if (type.equals("armour")) {
            return armour;
        }
        else if (type.equals("thornmail")) {
            return thornmail;
        }
        else if (type.equals("axe")) {
            return axe;
        }
        else {
            return 1;
        }
    }
    /**
     * updates respective item with new highest level
     * @param item
     */
    public void updateHighestLevel(Item item) {
        String type = item.getType();
        if (type.equals("sword") &&  sword <= ((Weapon)item).getLevel()) {
            sword = ((Weapon)item).getLevel();
        }
        else if (type.equals("stake") && stake <= ((Weapon)item).getLevel()) {
            stake = ((Weapon)item).getLevel();
        }
        else if (type.equals("axe") && axe <= ((Weapon)item).getLevel()) {
            axe = ((Weapon)item).getLevel();
        }
        else if (type.equals("staff") && staff <= ((Weapon)item).getLevel()) {
            staff = ((Weapon)item).getLevel();
        }
        else if (type.equals("shield") && shield <= ((Protection)item).getLevel()) {
            shield = ((Protection)item).getLevel();
        }
        else if (type.equals("helmet") && helmet <= ((Protection)item).getLevel()) {
            helmet = ((Protection)item).getLevel();
        }
        else if (type.equals("armour") && armour <= ((Protection)item).getLevel()) {
            armour = ((Protection)item).getLevel();
        }
        else if (type.equals("thornmail") && armour <= ((Protection)item).getLevel()) {
            thornmail = ((Protection)item).getLevel();
        }
    }

    /**
     * Sets the character's highest level of particular items
     * @param type The item in question
     * @param level The new highest level
     */
    public void setStats(String type, int level) {
        if (type.equals("sword")) {
            sword = level;
        }
        else if (type.equals("stake")) {
            stake = level;
        }
        else if (type.equals("staff")) {
            staff = level;
        }
        else if (type.equals("shield")) {
            shield = level;
        }
        else if (type.equals("helmet")) {
            helmet = level;
        }
        else if (type.equals("armour")) {
            armour = level;
        }
        else if (type.equals("thornmail")) {
            thornmail = level;
        }
        else if (type.equals("axe")) {
            axe = level;
        }
    }
}
