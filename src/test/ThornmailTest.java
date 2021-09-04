package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Thornmail;

public class ThornmailTest {
    private JSONObject goals;
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;
    
    public ThornmailTest() {
        goals = new JSONObject();
        goals.put("goal", "gold");
        goals.put("quantity", 1000);
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        x.set(1);
        y.set(1);
    }

    @Test
    public void damageProtectionTest() {
        Thornmail armour = new Thornmail(x, y, 1);
        assertEquals(armour.getType(), "thornmail");
        for (int i = 0; i < 10; i++) {
            double damage = i * 10;
            assertEquals(damage *(1- armour.getDamageReduction()), armour.protect(damage));
        }
    }

    @Test
    public void DifferentLevelTest() {
        Thornmail thornmail1 = new Thornmail(x, y, 1);
        x.set(2);
        Thornmail thornmail2 = new Thornmail(x, y, 10);
        double damage = 100;
        double armour1Damage = thornmail1.protect(damage);
        double armour2Damage = thornmail2.protect(damage);
        assertNotEquals(armour1Damage, armour2Damage);
    }

}
