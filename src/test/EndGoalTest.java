package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.GoalCalculator.GoalCalculator;
import unsw.loopmania.GoalCalculator.Composite;

public class EndGoalTest {
    

    @ParameterizedTest
    @ValueSource(strings = {"{\"goal\": \"gold\", \"quantity\": 100 }",
                            "{\"goal\": \"experience\", \"quantity\": 100 }",
                            "{\"goal\": \"cycles\", \"quantity\": 100 }"})
    public void SimpleGoalSuccessfulTest(String input) {
        Character c = new Character();
        JSONObject goal = new JSONObject(input);
        GoalCalculator gc = new GoalCalculator(goal, c);
        Composite checker = gc.winCondition(goal);
        for (int i = 0; i < 100; i++) {
            c.gainCycle();
        }
        c.gainXP(100);
        c.gainGold(100);
        assertTrue(checker.getValue());
        assertEquals(100, checker.getMax(goal.getString("goal")));
        assertEquals(0, checker.getMax("elephant"));
    }

    @Test
    public void ComplexGoalSuccessfulTest() {
        JSONObject goal = new JSONObject("{ \"goal\": \"AND\", \"subgoals\":[ { \"goal\": \"cycles\", \"quantity\": 100 },{ \"goal\": \"OR\", \"subgoals\":[ {\"goal\": \"experience\", \"quantity\": 123456 },{\"goal\": \"gold\", \"quantity\": 900000 }]}]}");
        Character c = new Character();
        GoalCalculator gc = new GoalCalculator(goal, c);
        Composite checker = gc.winCondition(goal);
        for (int i = 0; i < 100; i++) {
            c.gainCycle();
        }
        c.gainXP(123456);
        c.gainGold(900000);
        assertTrue(checker.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 25, 602, 9999999, 2799234, 923874924})
    public void SimpleGoalUnsuccessfulTest(int input) {
        JSONObject goal = new JSONObject(String.format("{\"goal\": \"gold\", \"quantity\": %d }", input));
        Character c = new Character();
        GoalCalculator gc = new GoalCalculator(goal, c);
        Composite checker = gc.winCondition(goal);
        c.gainGold(input - 1);
        assertFalse(checker.getValue());
    }
}
