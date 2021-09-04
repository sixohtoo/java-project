package unsw.loopmania.Items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Items are anything that can be stored in the character's unequipped inventory
 */
public abstract class Item extends StaticEntity{
    public static final int WEAPONSLOT = 0;
    public static final int HELMETSLOT = 1;
    public static final int SHIELDSLOT = 2;
    public static final int ARMOURSLOT = 3;
    /**
     * Constructor for abstract item class
     * @param x X coordinate of item in inventory
     * @param y Y coordinate of item in inventory
     */
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Constructor for abstract item class
     */
    public Item() {
        super();
    }
    public abstract int getPrice();
    public abstract int getSellPrice();
    public abstract int getReplaceCost();

    /**
     * Determines whether an item is a weapon
     * @return boolean on whether item is a weapon
     */
    public boolean isWeapon() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof Weapon || additional instanceof Weapon;
        }
        else {
            return this instanceof Weapon;
        }
    }

    /**
     * Determines whether an item is a protection item
     * @return boolean on whether item is a protection item
     */
    public boolean isProtection() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof Protection || additional instanceof Protection;
        }
        else {
            return this instanceof Protection;
        }
    }

    /**
     * Determines whether an item is a shield
     * @return boolean on whether item is a shield
     */
    public boolean isShield() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof Shield || additional instanceof Shield;
        }
        else {
            return this instanceof Shield;
        }
    }

    /**
     * Determines whether an item is a theonering
     * @return boolean on whether item is a theonering
     */
    public boolean isRing() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof TheOneRing || additional instanceof TheOneRing;
        }
        else {
            return this instanceof TheOneRing;
        }
    }

    /**
     * Determines whether an item is a nuke
     * @return boolean on whether item is a nuke
     */
    public boolean isNuke() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof Nuke || additional instanceof Nuke;
        }
        else {
            return this instanceof Nuke;
        }
    }

    /**
     * Determines whether an item is a treestump
     * @return boolean on whether item is a treestump
     */
    public boolean isTreeStump() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof TreeStump || additional instanceof TreeStump;
        }
        else {
            return this instanceof TreeStump;
        }
    }

    /**
     * Determines whether an item is an InvinciblePotion
     * @return boolean on whether item is an InvinciblePotion
     */
    public boolean isInvinciblePotion() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof InvinciblePotion || additional instanceof InvinciblePotion;
        }
        else {
            return this instanceof InvinciblePotion;
        }
    }

    /**
     * Determines whether an item is a potion
     * @return boolean on whether item is a potion
     */
    public boolean isPotion() {
        if (this instanceof ConfusedRareItem) {
            Item additional = ((ConfusedRareItem)this).getAdditional();
            return this instanceof Potion || additional instanceof Potion;
        }
        else {
            return this instanceof Potion;
        }
    }

    /**
     * Determines whether an item can be placed in a particular slot in the frontend
     * @param slot Slot item is being placed in
     * @return boolean depending on whether item is valid for target slot
     */
    public boolean isValidPlacement(double slot) {
        if (slot == WEAPONSLOT && isWeapon()) {
            return true;
        }
        else if (slot == HELMETSLOT && this instanceof Helmet) {
            return true;
        }
        else if (slot == SHIELDSLOT && isShield()) {
            return true;
        }
        else if (slot == ARMOURSLOT && this instanceof Armour) {
            return true;
        }
        return false;
    }
}
