package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * objects of this class represents the position in the path.
 * it holds the current position in the path and a reference to the orderedPath internally.
 * It also holds SimpleIntegerProperties for x and y coordinates, which we have getter methods for,
 *     so we can return them and attach ChangeListers, to decouple the frontend and backend.
 * The SimpleIntegerProperties are updated automatically when we move through the path.
 */
public class PathPosition{

    private int currentPositionInPath;
    private List<Pair<Integer, Integer>> orderedPath;
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;

    /*
     * the integerproperty internal int is the x or y coordinate - since want to use this class to bind with node positions
     * currentPositionInPath is an index of orderedPath
     * usingValue0 is true if using value0 in path, false if using value1
     * orderedPath is the list of path coordinates in pairs
     */
    public PathPosition(int currentPositionInPath, List<Pair<Integer, Integer>> orderedPath){
        this.currentPositionInPath = currentPositionInPath;
        this.orderedPath = orderedPath;
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        // update internal property
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * Gets ordered path from position
     * @return orderedPath (List of Integer Pairs)
     */
    public List<Pair<Integer, Integer>> getOrderedPath() {
        return orderedPath;
    }

    /**
     * Gets next position in path
     * @return Integer Pair representing next position in path
     */
    public Pair<Integer, Integer> getClockwisePosition() {
        return orderedPath.get((getPositionInPath() + 1) % orderedPath.size());
    }

    /**
     * Gets previous position in path
     * @return Integer Pair representing previous position in path
     */
    public Pair<Integer, Integer> getAntiClockwisePosition() {
        // Deal with modulo
        if (getPositionInPath() == 0) {
            return orderedPath.get(orderedPath.size() - 1);
        }
        else {
            return orderedPath.get(getPositionInPath() - 1);
        }
    }

    /**
     * move forward through the path i.e. clockwise
     */
    public void moveDownPath(){
        currentPositionInPath = (currentPositionInPath + 1)%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * move backwards through the path, i.e. anticlockwise
     */
    public void moveUpPath(){
        currentPositionInPath = (currentPositionInPath - 1 + orderedPath.size())%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * change the x and y SimpleIntegerProperties to reflect the current values of
     * the current position in the path, and the ordered path.
     */
    private void resetCoordinatesBasedOnPositionInPath(){
        x.set(orderedPath.get(currentPositionInPath).getValue0());
        y.set(orderedPath.get(currentPositionInPath).getValue1());
    }

    public SimpleIntegerProperty getX(){
        return x;
    }

    public SimpleIntegerProperty getY(){
        return y;
    }

    public int getPositionInPath() {
        return currentPositionInPath;
    }

    public void setPositionIndex(int index) {
        currentPositionInPath = index;
    }

    public PathPosition clone() {
        return new PathPosition(currentPositionInPath, orderedPath);
    }
}
