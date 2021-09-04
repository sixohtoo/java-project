package unsw.loopmania.Enemies;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.Heroes.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Entities.StaticEntity;

/**
 * Slugs are basic common enemies
 */
public class Slug extends Enemy{
    public static final int BATTLERADIUS = 1;
    public static final int SUPPORTRADIUS = 1;
    public static final int DAMAGE = 10;
    public static final int GOLDAMOUNT = 100;
    public static final int HEALTH = 50;
    public static final int XP = 100;
    private String[] cardDrops;

    /**
     * Constructor for slug class
     * @param position position of slug on track
     */
    public Slug (PathPosition position) {
        super(position, BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("slug");
        cardDrops = new String[]{"campfire", "barracks", "tower", "trap", "village", "zombiepit", "bank"};
    }

    /**
     * Constructor for slug class
     */
    public Slug() {
        super(BATTLERADIUS, SUPPORTRADIUS, DAMAGE, GOLDAMOUNT, HEALTH, XP);
        super.setType("slug");
        cardDrops = new String[]{"campfire", "barracks", "tower", "trap", "village", "zombiepit", "bank"};
    }
    /**
     * Generates random loot for player for zombie
     * @param character
     * @param width
     * @param rareItems
     * @return StaticEntity loot
     */
    @Override
    public List<StaticEntity> getLoot(Character character, int width, List<String> rareItems) {
        int num = LoopManiaWorld.getRandNum();
        List<StaticEntity> loot = new ArrayList<StaticEntity>();
        // slug drops item better than current
        if (num < 15) {
            String itemType;
            if (num < 1 && !rareItems.isEmpty()) {
                itemType = rareItems.get(LoopManiaWorld.getRandNum() % rareItems.size());
            }
            else {
                itemType = itemList[LoopManiaWorld.getRandNum() % itemList.length];
            }
            if (character.getNonLevelItems().contains(itemType)) {
                loot.add(character.addUnequippedItem(itemType, 0));
            }
            else if (num < 5) {
                System.out.println("up one level");
                int level = character.getHighestLevel(itemType) + 1;
                if (level > 10) {
                    level = 10;
                }
                loot.add(character.addUnequippedItem(itemType, level));
            }
            else {
                System.out.println("same level");
                int level = character.getHighestLevel(itemType);
                loot.add(character.addUnequippedItem(itemType, level));
            }
        }
        else if (num < 25) {
            String cardType = cardDrops[LoopManiaWorld.getRandNum() % cardDrops.length];
            loot.add(character.loadCard(cardType, width));
        }
        return loot;
    }
}
