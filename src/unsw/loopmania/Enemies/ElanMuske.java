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
 * Elan Muske is a boss that spawns on round 40 or later (depending on player's XP level)
 * It messes with the price of the DoggieCoin
 */
public class ElanMuske extends Enemy implements Boss{
    public static final int BATTLERADIUS = 1;
    public static final int SUPPORTRADIUS = 1;
    public static final int DAMAGE = 29;
    public static final int GOLDAMOUNT = 800;
    public static final int HEALTH = 600;
    public static final int XP = 1700;
    private String[] cardDrops;
    private boolean canMove = true;
    /**
     * Constructor for ElanMuske class
     * @param position where ElanMuske spawns
     */
    public ElanMuske(PathPosition position) {
        super(position, BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("elanmuske");
        cardDrops = new String[]{"campfire", "barracks", "tower", "bank", "trap", "village", "vampirecastle", "zombiepit"};
        
    }

    /**
     * Constructor for ElanMuske class
     */
    public ElanMuske() {
        super(BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("elanmuske");
        cardDrops = new String[]{"campfire", "barracks", "tower", "trap", "village", "vampirecastle", "zombiepit"};
        
    }

    /**
     * Gets the loot dropped by ElanMuske
     */
    @Override
    public List<StaticEntity> getLoot(Character character, int width, List<String> rareItems) {
        character.increaseBossKills();
        int num = LoopManiaWorld.getRandNum();
        List <StaticEntity> loot = new ArrayList<StaticEntity>();
        if (num >= 50) {
            String rareType = rareItems.get(LoopManiaWorld.getRandNum() % rareItems.size());
            loot.add(character.addUnequippedItem(rareType, 0));
        }
        String itemType = itemList[LoopManiaWorld.getRandNum() % itemList.length];
        if (character.getNonLevelItems().contains(itemType)) {
            loot.add(character.addUnequippedItem(itemType, 0));
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
        if (num < 60) {
            String cardType = cardDrops[LoopManiaWorld.getRandNum() % cardDrops.length];
            loot.add(character.loadCard(cardType, width));
        }
        return loot;
    }

    /**
     * Elan muske attacks the character and heals enemies around him
     */
    @Override
    public void attack (Hero hero, BattleRunner bR) {
        hero.takeDamage(this.getAttackDamage(), this);
        bR.healenemies();
    }

    /**
     * Elan muske moves at half speed
     */
    @Override
    public void move() {
        if (canMove) {
            super.moveDownPath();
            canMove = false;
        }
        else {
            canMove = true;
        }
    }
}
