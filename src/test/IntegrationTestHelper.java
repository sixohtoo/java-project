package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoadGame;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Heroes.Character;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Entities.Entity;
import unsw.loopmania.Entities.PathTile;
import unsw.loopmania.Entities.StaticEntity;

public class IntegrationTestHelper {

    public static LoopManiaWorld createWorld(String fileName, String folder, int seed) throws FileNotFoundException{
        JSONObject json;
        try {
            json = new JSONObject(new JSONTokener(new FileReader("bin/" + folder +  "/" + fileName)));
        }
        catch (FileNotFoundException e) {
            json = new JSONObject(new JSONTokener(new FileReader(folder + '/' + fileName)));
        }
        // JSONObject goals = json.getJSONObject("goal-condition");
        int width = json.getInt("width");
        int height = json.getInt("height");
        List<Pair<Integer, Integer>> orderedPath = IntegrationTestHelper.loadPathTiles(json.getJSONObject("path"), width, height);

        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath, json, seed);

        JSONArray jsonEntities = json.getJSONArray("entities");

        // load non-path entities later so that they're shown on-top
        for (int i = 0; i < jsonEntities.length(); i++) {
            IntegrationTestHelper.loadEntity(world, jsonEntities.getJSONObject(i), orderedPath);
        }

        if (json.has("saveWorld")) {
            LoadGame load = new LoadGame(world, json);
            load.loadWorld();
        }

        return world;

    }

    /**
     * load an entity into the world
     * @param world backend world object
     * @param json a JSON object to parse (different from the )
     * @param orderedPath list of pairs of x, y cell coordinates representing game path
     */
    private static void loadEntity(LoopManiaWorld world, JSONObject currentJson, List<Pair<Integer, Integer>> orderedPath) {
        String type = currentJson.getString("type");
        int x = currentJson.getInt("x");
        int y = currentJson.getInt("y");
        int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));
        // assert indexInPath != -1;

        Entity entity = null;

        switch (type) {
        case "hero_castle":
            Character character = new Character(new PathPosition(indexInPath, orderedPath));
            world.setCharacter(character);
            entity = character;
            world.placeBuildingAtStart(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y), "heros_castle");
            break;
        case "path_tile":
            throw new RuntimeException("path_tile's aren't valid entities, define the path externally.");
        default:
            world.placeBuildingAtStart(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y), type);

        }
        world.addEntity(entity);
    }

    /**
     * load path tiles
     * @param path json data loaded from file containing path information
     * @param width width in number of cells
     * @param height height in number of cells
     * @return list of x, y cell coordinate pairs representing game path
     */
    private static List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height) {
        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.  No other path types supported at this moment.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")), new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir: path.getJSONArray("path").toList()){
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.size() == 0) {
            throw new IllegalArgumentException(
                "This path just consists of a single tile, it needs to consist of multiple to form a loop.");
        }

        // load the first position into the orderedPath
        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        // add all coordinates of the path into the orderedPath
        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));
            
            if (y >= height || y < 0 || x >= width || x < 0) {
                System.out.println(String.format("(%d, %d)\n Width: %d\nHeight: %d", x, y, width, height));
                throw new IllegalArgumentException("Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }
            
            PathTile.Direction dir = connections.get(i);
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
        }
        // we should connect back to the starting point
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }
        return orderedPath;
    }

    public static void printUnequippedInventory(LoopManiaWorld world) {
        for (Item item : world.getUnequippedInventory()) {
            System.out.println(String.format("%s %d %d", item.getType(), item.getX(), item.getY()));
        }
    }

    public static void printCards(LoopManiaWorld world) {
        for (int i = 0; i < 9; i++) {
            Card card = world.getCardByCoordinate(i);
            if (card == null) break;
            System.out.println(String.format("%s %d", ((StaticEntity)card).getType(), card.getX()));
        }
    } 
}
