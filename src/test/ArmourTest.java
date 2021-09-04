
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Items.Armour;

public class ArmourTest {
    private JSONObject goals;
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;
    
    public ArmourTest() {
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
        Armour armour = new Armour(x, y, 1);
        assertEquals(armour.getType(), "armour");
        for (int i = 0; i < 10; i++) {
            double damage = i * 10;
            assertEquals(damage *(1- armour.getDamageReduction()), armour.protect(damage));
        }
    }

    @Test
    public void DifferentLevelTest() {
        Armour armour1 = new Armour(x, y, 1);
        x.set(2);
        Armour armour2 = new Armour(x, y, 10);
        double damage = 100;
        double armour1Damage = armour1.protect(damage);
        double armour2Damage = armour2.protect(damage);
        assertNotEquals(armour1Damage, armour2Damage);
    }
}
