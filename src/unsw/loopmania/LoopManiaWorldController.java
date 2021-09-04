package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import unsw.loopmania.Items.*;
import unsw.loopmania.Entities.*;
import unsw.loopmania.Shop.*;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Cards.*;
import java.util.EnumMap;


import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Label gameState;

    @FXML
    private Rectangle healthBar;

    @FXML
    private Label goldAmount;

    @FXML
    private Label xpAmount;

    @FXML
    private Label cyclesAmount;

    @FXML
    private Label bossAmount;

    @FXML
    private Label goldGoal;

    @FXML
    private Label xpGoal;

    @FXML
    private Label cyclesGoal;

    @FXML
    private Label bossGoal;

    @FXML
    private Label alliedSoldierAmount;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean buyShopOpen;
    private boolean sellShopOpen;
    private Shop shop;

    private boolean isPaused;
    private LoopManiaWorld world;

    private boolean isAlive;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */

    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    public static final int WEAPONSLOT = 0;
    public static final int HELMETSLOT = 32;
    public static final int SHIELDSLOT = 64;
    public static final int ARMOURSLOT = 96;
    public static final int LAYOUTTOSLOT = 32;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        buyShopOpen = false;
        sellShopOpen = false;
        entityImages = new ArrayList<>(initialEntities);
        currentlyDraggedImage = null;
        currentlyDraggedType = null;
        isAlive = true;
        // shop = new Shop(world.getCharacter());
        shop = world.getShop();

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() throws IOException {
        
        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.getunequippedInventoryWidth(); x++){
            for (int y=0; y<LoopManiaWorld.getunequippedInventoryHeight(); y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // Equip weapon
        Item weapon = world.getEquippedItemByCoordinates(0);
        String type = weapon.getType();
        int level = ((Weapon)weapon).getLevel();
        ImageView viewWeapon = new ImageView(new File(String.format("src/images/%s%d.png", type, level)).toURI().toString());
        equippedItems.add(viewWeapon, 0, 0);

        // Equip helmet (if character starts with any)
        Item helmet = world.getEquippedItemByCoordinates(1);
        if (helmet != null) {
            type = helmet.getType();
            level = ((Protection)helmet).getLevel();
            ImageView viewHelmet = new ImageView(new File(String.format("src/images/%s%d.png", type, level)).toURI().toString());
            equippedItems.add(viewHelmet, 1, 0);
        }

        // Equip shield (if character starts with any)
        Item shield = world.getEquippedItemByCoordinates(2);
        if (shield != null) {
            type = shield.getType();
            level = ((Protection)shield).getLevel();
            ImageView viewShield = new ImageView(new File(String.format("src/images/%s%d.png", type, level)).toURI().toString());
            equippedItems.add(viewShield, 2, 0);
        }

        // Equip armour (if character starts with any)
        Item armour = world.getEquippedItemByCoordinates(3);
        if (armour != null) {
            type = armour.getType();
            level = ((Protection)armour).getLevel();
            ImageView viewArmour = new ImageView(new File(String.format("src/images/%s%d.png", type, level)).toURI().toString());
            equippedItems.add(viewArmour, 3, 0);
        }

        // Load any buildings that start in the game (from the JSON file)
        for (BuildingOnMove b : world.getMoveBuildings()) {
            onLoad((Building)b);
        }

        // Load any buildings that start in the game (from the JSON file)
        for (BuildingOnCycle b : world.getCycleBuildings()) {
            onLoad((Building)b);
        }

        // Load any enemies that start in the game (from the JSON file).
        // This occurs when loading a previously saved game
        for (Enemy e : world.getEnemies()) {
            onLoad(e);
        }

        // Load any items that start in the game (from the JSON file).
        // This occurs when loading a previously saved game
        for (Item i : world.getItems()) {
            onLoad(i);
        }

        // Load any coins that start in the game (from the JSON file).
        // This occurs when loading a previously saved game
        for (Coin c : world.getCoin()) {
            onLoad(c);
        }

        // Initialise bindings for stats and allied soldiers
        goldAmount.textProperty().bind(world.getGold().asString());
        cyclesAmount.textProperty().bind(world.getCycles().asString());
        xpAmount.textProperty().bind(world.getXP().asString());
        bossAmount.textProperty().bind(world.getBossKills().asString());
        alliedSoldierAmount.textProperty().bind(world.getCharacter().getAlliedSoldierProperty().asString());

        // initialises healthbar
        healthBar.toFront();
        healthBar.setFill(Color.RED);
        // binds character health as a percentage to the health bar rectangle
        healthBar.widthProperty().bind(Bindings.divide(world.getHealth(), 100/healthBar.widthProperty().get()));

        // Initialise bindings for goals
        goldGoal.setText(Integer.toString(world.getMaxGoal("gold")));
        xpGoal.setText(Integer.toString(world.getMaxGoal("experience")));
        cyclesGoal.setText(Integer.toString(world.getMaxGoal("cycles")));
        bossGoal.setText(Integer.toString(world.getMaxGoal("bosses")));

        
        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

    }


    /**
     * create and run the timer
     */
    public void startTimer(){        
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            List<Enemy> newEnemies = world.moveEntities();
            List <Coin> newCoins = world.getCoin();
            List <Poop> newPoop = world.getPoop();
            // Load any coins on the map
            for (Coin c : newCoins) {
                onLoad(c);
            }
            // Load any poop on the map
            if (!newPoop.isEmpty()) {
                for (Poop p : newPoop) {
                    onLoad(p);
                }
            }
            // Load any buildings on the map
            for (BuildingOnMove b : world.getMoveBuildings()) {
                onLoad((Building)b);
            }
            // Load any buildings on the map
            for (BuildingOnCycle b : world.getCycleBuildings()) {
                onLoad((Building)b);
            }
            // Load any new enemies for the first time
            for (Enemy newEnemy : newEnemies){
                onLoad(newEnemy);
            }
            List<Enemy> defeatedEnemies = world.fight();
            world.cleanUpFight();
            // Remove any defeated enemies on the map
            for (Enemy e: defeatedEnemies){
                reactToEnemyDefeat(e);
            }
            // Deal with character death
            if (world.isCharacterDead()) {
                isAlive = false;
                pause();
                loadDeathScreen();
            }
            // Deal with player win
            if (world.checkPlayerWin()) {
                pause();
                loadVictoryScreen();
            }
            // Determine whether to open shop
            if (world.onHeroCastle()) {
                try {
                    shopCycles(world.getCycles().get());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
        gameState.setText("Paused");
        gameState.setTextFill(Color.RED);
    }

    /**
     * Quite the entire game
     */
    public void terminate(){
        Platform.exit();
    }

    /**
     * Unpause game if shop menu and sell menu is closed
     */
    public void tryToPlay() {
        if (isPaused && !buyShopOpen && !sellShopOpen && isAlive){
            play();
        }
    }

    /**
     * Unpause the game
     */
    public void play(){
        isPaused = false;
        System.out.println("playing");
        timeline.play();
        gameState.setText("Playing");
        gameState.setTextFill(Color.GREEN);
    }
    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        world.KillEnemy(enemy);
        List<StaticEntity> loot = world.processEnemyLoot(enemy);
        for (StaticEntity drop : loot) {
            if (drop instanceof Card) {
                loadCard((Card)drop);
            }
            else if (drop instanceof Item) {
                loadItem((Item)drop);
            }
        }
    }

    /**
     * Loads an item in the game
     * @param item Item : item to be loaded
     */
    public void loadItem(Item item){
        onLoad(item);
    }

    /**
     * Loads a card in the game
     * @param card
     */
    private void loadCard(Card card) {
        onLoad(card);
    }



    /**
     * load a card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param card Card : card to load
     */
    private void onLoad(Card card) {
        // ImageView view = new ImageView(vampireCastleCardImage);
        String imageName = String.format("src/images/%s_card.png", ((StaticEntity)card).getType());
        ImageView view = new ImageView(new Image((new File(imageName)).toURI().toString()));

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity((Entity)card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item Item : item to be loaded
     */
    private void onLoad(Item item) {
        ImageView view = null;
        if (world.getNonLevelItems().contains(item.getType())) {
            view = new ImageView(new Image((new File(String.format("src/images/%s.png", item.getType()))).toURI().toString()));
        }
        else if (item instanceof RareItem) {
            view = new ImageView(new Image((new File(String.format("src/images/%s.png", item.getType()))).toURI().toString()));
        }
        else if (item.isWeapon()) {
            view = new ImageView(new Image((new File(String.format("src/images/%s%d.png", item.getType(), ((Weapon)item).getLevel()))).toURI().toString()));
        }
        else {
            view = new ImageView(new Image((new File(String.format("src/images/%s%d.png", item.getType(), ((Protection)item).getLevel()))).toURI().toString()));
        }
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
        addEntity((Entity)item, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * @param enemy Enemy : enemy to be loaded
     */
    private void onLoad(Enemy enemy) {
        // ImageView view = new ImageView(basicEnemyImage);
        String imageName = String.format("src/images/%s.png", ((MovingEntity)enemy).getType());
        ImageView view = new ImageView(new Image((new File(imageName)).toURI().toString()));
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building Building : building to be loaded
     */
    private void onLoad(Building building){
        String imageName = String.format("src/images/%s_building.png", ((StaticEntity)building).getType());
        ImageView view = new ImageView(new Image((new File(imageName)).toURI().toString()));
        addEntity((Entity)building, view);
        squares.getChildren().add(view);
    }

    /**
     * load an coin into the GUI
     * @param coin Coin : coin to be loaded
     */
    private void onLoad(Coin coin) {
        ImageView view = new ImageView(new Image((new File("src/images/gold_pile.png")).toURI().toString()));
        addEntity(coin, view);
        squares.getChildren().add(view);
    }

    /**
     * load an poop into the GUI
     * @param poop Poop : poop to be loaded
     */
    private void onLoad(Poop poop) {
        ImageView view = new ImageView(new Image((new File("src/images/poop.png")).toURI().toString()));
        addEntity(poop, view);
        squares.getChildren().add(view);
    }

    /**
     * Sets the gamemodes
     */
    public void setMode(ArrayList<String> mode) {
        world.setMode(mode);
    }


    /**
     * Converts the coordinate of an equipped slot to a string
     * @param slot double : X coordinate of equipped slot
     * @return String : nanme of equipped slot
     */
    private String slotToString(double slot) {
        if (slot == WEAPONSLOT) return "weapon";
        if (slot == HELMETSLOT) return "helmet";
        if (slot == SHIELDSLOT) return "shield";
        if (slot == ARMOURSLOT) return "armour";
        else return null;
    }

    /**
     * Determines whether building can be placed at location
     * @param card : Card being turned into a building
     * @param x : X coordinate of target 
     * @param y
     * @return
     */
    private boolean validPlacement(Card card, int x, int y) {
        return world.isValidPlacement(card, x, y);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    node.toFront();
                    if(node != targetGridPane && db.hasImage()){
                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType){
                            case CARD:
                                // Get card being dropped
                                Card card = world.getCardByCoordinate(nodeX);
                                if (card != null && validPlacement(card, x, y)) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    // Add the relevant building to the map
                                    Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                    onLoad(newBuilding);
                                }
                                else {
                                    // Remove opacity 
                                    for (Node n : targetGridPane.getChildren()) {
                                        if (currentlyDraggedType == draggableType) {
                                            n.setOpacity(1);
                                        }
                                    }
                                    return;
                                }
                                break;
                            case ITEM:
                                // Get item being dragged
                                Item item = world.getUnequippedInventoryItemEntityByCoordinates(nodeX, nodeY);
                                if (item != null && item.isValidPlacement(node.getLayoutX() / LAYOUTTOSLOT)) {
                                    // Get item being unequipped
                                    Item olditem = world.getEquippedItemByCoordinates(x);
                                    // If overwriting an item, add the item back into unequipped inventory
                                    if (olditem != null) {
                                        Item newItem = world.pickupUnequippedItem(olditem, node.getLayoutX() / LAYOUTTOSLOT, nodeX, nodeY);
                                        onLoad(newItem);
                                        olditem.destroy();
                                    }
                                    else {
                                        removeItemByCoordinates(nodeX, nodeY);
                                    }
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    targetGridPane.add(image, x, y, 1, 1);
                                    world.equipItem(item, slotToString(node.getLayoutX()));
                                    System.out.println("Successfully dropped");
                                }
                                else {
                                    // remove opacity
                                    for (Node n : targetGridPane.getChildren()) {
                                        if (currentlyDraggedType == draggableType) {
                                            n.setOpacity(1);
                                        }
                                    }
                                    return;
                                }
                                break;
                            default:
                                break;
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                
                draggedEntity.setVisible(true);
                // draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage()){
                                    n.setOpacity(1);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    private void activateNuke() {
        List<Enemy> killedEnemies = world.useNuke();
        for (int i = killedEnemies.size() - 1; i >= 0; i--) {
            reactToEnemyDefeat(killedEnemies.get(i));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case SPACE:
            if (isPaused && !buyShopOpen && !sellShopOpen && isAlive){
                startTimer();
                play();
            }
            else if (buyShopOpen || sellShopOpen) {
                System.out.println("must close shop");
            }
            else{
                pause();
            }
            break;
        case D:
            world.drinkHealthPotion();
            break;
        case S:
            world.drinkStrengthPotion();
            break;
        case N:
            activateNuke();
            break;
        case I:
            world.drinkInvincibilityPotion();
            break;
        default:
            break;
        }
    }

    /**
     * this method is triggered when click save button in FXML
     * and provides a textbox to name the save file.
     * @throws IOException
     */
    @FXML
    private void save() throws IOException {
        pause();

        Label label = new Label("Enter name of world");
        label.setFont(Font.font("Bauhaus 93", FontWeight.BOLD, 20));
        label.setTextFill(Color.VIOLET); 
        Button save = new Button("Save");
        TextField name = new TextField();
        name.setPromptText("Enter name of world");
        GridPane gridpane = new GridPane();
        gridpane.add(name, 0, 0);
        gridpane.add(save, 0, 1);
        save.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                String n = name.getText();
                if (n != null) {
                    world.saveGame(n);
                    terminate();
                }
            }
        });
        anchorPaneRoot.getChildren().add(gridpane);
        AnchorPane.setTopAnchor(gridpane, anchorPaneRoot.getHeight()/2);
        AnchorPane.setLeftAnchor(gridpane, anchorPaneRoot.getWidth()/4);
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * and brings the user back to the start game menu.
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();

        Scene scene = this.anchorPaneRoot.getScene();
        Stage primaryStage = (Stage) scene.getWindow();

        primaryStage.setTitle("Loop Mania - Start Menu");

        // prevent human player resizing game window (since otherwise would see white space)
        primaryStage.setResizable(false);

        // load the start game menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGameView.fxml"));
        Parent root = loader.load();

        // deploy the menu onto the stage
        scene.setRoot(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void shopCycles(int cycles) throws IOException {
        int sum = 0;
        for (int i = 1; sum <= cycles; i++) {
            sum = sum + i;
            if (sum == cycles) {
                switchToShop();
            }
        }
    }

    public void setBuyShopOpen(boolean shopOpen) {
        this.buyShopOpen = shopOpen;
    }

    public void setSellShopOpen(boolean shopOpen) {
        this.sellShopOpen = shopOpen;
    }

    public void switchToShop() throws IOException  {
        pause();
        buyShopOpen = true;

        ShopBuyController shopBuyController = new ShopBuyController(this, world, shop);
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopBuyView.fxml"));
        shopLoader.setController(shopBuyController);
        
        Scene scene = new Scene(shopLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Shop-Buy");

        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            buyShopOpen = false;
            tryToPlay();
        });
        stage.show();
    }

    public void loadDeathScreen() {
        Label label = new Label("GAME OVER");
        label.setFont(Font.font("Bauhaus 93", FontWeight.BOLD, 40));
        label.setTextFill(Color.RED);
        
        anchorPaneRoot.getChildren().add(label);
        AnchorPane.setTopAnchor(label, anchorPaneRoot.getHeight()/2);
    }

    public void loadVictoryScreen() {
        Label label = new Label("VICTORY!!!");
        label.setFont(Font.font("Bauhaus 93", FontWeight.BOLD, 40));
        label.setTextFill(Color.AQUA);
        
        anchorPaneRoot.getChildren().add(label);
        AnchorPane.setTopAnchor(label, anchorPaneRoot.getHeight()*1/3);
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }
}
