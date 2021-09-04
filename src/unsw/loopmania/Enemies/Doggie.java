package unsw.loopmania.Enemies;

import java.util.List;

import unsw.loopmania.BattleRunner;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Heroes.Hero;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Entities.StaticEntity;

import java.util.ArrayList;

/**
 * Doggie spawns on round 20 and drops a DoggieCoin
 */
public class Doggie extends Enemy implements Boss{
    public static final int BATTLERADIUS = 1;
    public static final int SUPPORTRADIUS = 1;
    public static final int DAMAGE = 20;
    public static final int GOLDAMOUNT = 0;
    public static final int HEALTH = 500;
    public static final int XP = 700;
    public static final int STUNCHANCE = 20;
    public static final int NOLEVEL = 0;
    private String[] cardDrops;

    /**
     * Constructor for Doggie enemy
     * @param position Doggie's position
     */
    public Doggie(PathPosition position) {
        super(position, BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("doggie");
        cardDrops = new String[]{"campfire", "barracks", "tower", "trap", "village", "vampirecastle", "zombiepit"};
        
    }
    
    /**
     * Constructor for Doggie enemy
     */
    public Doggie() {
        super(BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("doggie");
        cardDrops = new String[]{"campfire", "barracks", "tower", "trap", "village", "vampirecastle", "zombiepit"};
    }

    /**
     * Gets loot dropped by the Doggie.
     * The doggie can drop multiple items and always drops a single DoggieCoin
     */
    @Override
    public List<StaticEntity> getLoot(Character character, int width, List<String> rareItems) {
        character.increaseBossKills();
        int num = LoopManiaWorld.getRandNum();
        List <StaticEntity> loot = new ArrayList<StaticEntity>();
        if (num >= 50) {
            String rareType = rareItems.get(LoopManiaWorld.getRandNum() % rareItems.size());
            loot.add(character.addUnequippedItem(rareType, NOLEVEL));
        }
        String itemType = itemList[LoopManiaWorld.getRandNum() % itemList.length];
        if (character.getNonLevelItems().contains(itemType)) {
            loot.add(character.addUnequippedItem(itemType, NOLEVEL));
        } else {
            int level;
            if (num < 80 && num > 10) {
                level = character.getHighestLevel(itemType) + 2;
                if (level > 10) {
                    level = 10;
                }
            } else {
                level = character.getHighestLevel(itemType) + 1;
                if (level > 10) {
                    level = 10;
                }
            }
            loot.add(character.addUnequippedItem(itemType, level));
        }
        String cardType = cardDrops[LoopManiaWorld.getRandNum() % cardDrops.length];
        loot.add(character.loadCard(cardType, width));
        loot.add(character.addUnequippedItem("doggiecoin", NOLEVEL));
        return loot;
    }

    /**
     * Deals damage to Hero
     */
    @Override
    public void attack (Hero hero, BattleRunner bR) {
        if (hero instanceof Character) {
            int randomInt = LoopManiaWorld.getRandNum();
            if (randomInt < STUNCHANCE) {
                bR.stunCharacter();
            }
        }
        hero.takeDamage(this.getAttackDamage(), this);
    }
}
