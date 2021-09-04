package unsw.loopmania.GoalCalculator;

import unsw.loopmania.Heroes.Character;

/**
 * Composite class used for determining boss kill progress
 */
public class CompositeBoss implements Composite{
    private static final int MAXBOSSKILLS = 2;
    private Character character;
    public CompositeBoss(Character character) {
        this.character = character;
    }

    /**
     * Gets the number of bosses killed
     */
    @Override
    public boolean getValue() {
        return character.getBossKills().get() == MAXBOSSKILLS;
    }

    /**
     * Gets the mimimum number of boss kills needed to win
     */
    @Override
    public int getMax(String type) {
        return MAXBOSSKILLS;
    }
    
}
