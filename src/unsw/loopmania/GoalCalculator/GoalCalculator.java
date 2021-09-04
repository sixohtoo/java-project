package unsw.loopmania.GoalCalculator;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.loopmania.Heroes.Character;

/**
 * Class that determines whether player has won and how much of each goal
 * the player needs to attain
 */
public class GoalCalculator {
    private Character character;
    private Composite checker;

    public GoalCalculator(JSONObject json, Character character) {
        this.character = character;
        this.checker = winCondition(json);
    }

    /**
     * Checks whether win condition has been met
     * @return boolean
     */
    public boolean checkWinCondition() {
        return checker.getValue();
    }

    /**
     * Parses json and discerns winning conditions
     * @param json
     * @return Composite 
     */
    public Composite winCondition(JSONObject json) {
        if (json.has("quantity")) {
            Composite leaf = new CompositeLeaf((int)json.get("quantity"), character, (String)json.get("goal"));
            return leaf;
        }
        else if (json.getString("goal").equals("bosses")) {
            Composite boss = new CompositeBoss(character);
            return boss;
        }
        else {
            JSONArray subgoals = json.getJSONArray("subgoals");
            JSONObject left = (JSONObject)subgoals.get(0);
            JSONObject right = (JSONObject)subgoals.get(1);
            if (((String)json.get("goal")).equals("AND")) {
                Composite and = new CompositeAnd(winCondition(left), winCondition(right));
                return and;
            }
            else {
                Composite or = new CompositeOr(winCondition(left), winCondition(right));
                return or;
            }

        }
    }

    /**
     * Gets the object that can check whether player has won
     * @return
     */
    public Composite getChecker() {
        return checker;
    }

    /**
     * Gets the max of a particular goal needed
     * @param type
     * @return
     */
    public int getMax(String type) {
        return checker.getMax(type);
    }
}
