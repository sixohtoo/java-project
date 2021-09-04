package unsw.loopmania.GoalCalculator;

import unsw.loopmania.Heroes.Character;

/**
 * Leaf class for all Composite goals.
 */
public class CompositeLeaf implements Composite{
    private int amount;
    private Character character;
    private String type;

    public CompositeLeaf(int amount, Character character, String type) {
        this.amount = amount;
        this.character = character;
        this.type = type;
    }

    @Override
    public boolean getValue() {
        if (type.equals("gold")) {
            return character.getGold() >= amount;
        }
        else if (type.equals("experience")) {
            return character.getXP().get() >= amount;
        }
        else {
            return character.getCycles().get() >= amount;
        }
    }

    @Override
    public int getMax(String type) {
        if (this.type.equals(type)) {
            return amount;
        }
        else {
            return 0;
        }
    }
    
}
