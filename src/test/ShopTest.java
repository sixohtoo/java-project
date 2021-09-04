
package test;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.Sword;
import unsw.loopmania.Items.Weapon;
import unsw.loopmania.Items.HealthPotion;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Shop.Shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class ShopTest {
    @Test
    public void buyWeaponTest() {
        Character c = new Character();
        c.setRareItems(new ArrayList<String>());
        Shop shop = new Shop(c);
        c.gainGold(1000);
        Item weapon = shop.buy("sword");
        Item sword = new Sword(2);
        assertEquals(((Weapon)weapon).getLevel(), ((Weapon)sword).getLevel());
        assertEquals(((Weapon)weapon).getType(), ((Weapon)sword).getType());;
        assertEquals(c.getGold(), 1000 - weapon.getPrice());        
    }

    @Test
    public void buyPotionTest() {
        Character c = new Character();
        c.setRareItems(new ArrayList<String>());
        Shop shop = new Shop(c);
        c.gainGold(1000);
        Item boughItem = shop.buy("healthpotion");
        Item potion = new HealthPotion();
        assertEquals(((HealthPotion)potion).getType(), ((HealthPotion)boughItem).getType());
        assertEquals(c.getGold(), 900);        
    }

    @Test
    public void checkBuyPriceTest() {
        Character c = new Character();
        Shop shop = new Shop(c);
        assertEquals(shop.getBuyPrice("sword"), 402);
        assertEquals(shop.getBuyPrice("staff"), 804);
        assertEquals(shop.getBuyPrice("stake"), 402);
        assertEquals(shop.getBuyPrice("helmet"), 459);
        assertEquals(shop.getBuyPrice("armour"), 459);
        assertEquals(shop.getBuyPrice("shield"), 459);
        assertEquals(shop.getBuyPrice("healthpotion"), 100);
    }
}