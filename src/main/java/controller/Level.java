package controller;

import static controller.GameGrid.translatePoint;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import view.GameObject;

/**
 * All information of a level
 * @author PEER-OLAF Siebers
 *
 */

public final class Level implements Iterable<GameObject> {

    /**
     * name of level
     */
    private final String name;
    /**
     * call out object of Grid
     */
    private final GameGrid objectsGrid;
    /**
     * call out object of Grid
     */
    private final GameGrid diamondsGrid;
    /**
     * level Index
     */
    private final int index;
    /**
     * initial number of diamonds
     */
    private int numberOfDiamonds = 0;
    /**
     * initial location of keeper
     */
    private Point keeperPosition = new Point(0, 0);
    
    
    /**
     * Constructor of class
     * @param levelName name of level
     * @param levelIndex level index
     * @param raw_level how level look like in file
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        if (GameFlow.isDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);
        }

        name = levelName;
        index = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);
        diamondsGrid = new GameGrid(rows, columns);

        for (int row = 0; row < raw_level.size(); row++) {

            // Loop over the string one char at a time because it should be the fastest way:
            // http://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
            for (int col = 0; col < raw_level.get(row).length(); col++) {
                // The game object is null when the we're adding a floor or a diamond
                GameObject curTile = GameObject.fromChar(raw_level.get(row).charAt(col));

                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    keeperPosition = new Point(row, col);
                }

                objectsGrid.putGameObjectAt(curTile, row, col);
                curTile = null;
            }
        }
    }

    /**
     * check if level is finished
     * @return complete goal
     */
    public boolean isComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                if (objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }

        return cratedDiamondsCount == numberOfDiamonds;
    }
    
    

	/**
	 * Getter of level name
	 * @return level name
	 */
	public String getName() {
        return name;
    }

    /**
     * Getter of level index
     * @return level index
     */
    int getIndex() {
        return index;
    }

    /**
     * getter of keeper location
     * @return keeper location
     */
    Point getKeeperPosition() {
        return keeperPosition;
    }

    /**
     * getter of object location in file
     * @return object location in file
     */
    GameObject getTargetObject(Point source, Point delta) {
        return objectsGrid.getTargetFromSource(source, delta);
    }
 
    /**
     * getter of object location in real time game
     * @return object location in real time game
     */
    GameObject getObjectAt(Point p) {
        return objectsGrid.getGameObjectAt(p);
    }

    /**
     * move object location 
     * @param object game object
     * @param source location from game
     * @param delta delta
     */
    void moveGameObjectBy(GameObject object, Point source, Point delta) {
        moveGameObjectTo(object, source, translatePoint(source, delta));
    }

    /**
     * move object to that location
     * @param object
     * @param source
     * @param destination 
     */
    public void moveGameObjectTo(GameObject object, Point source, Point destination) {
        objectsGrid.putGameObjectAt(getObjectAt(destination), source);
        objectsGrid.putGameObjectAt(object, destination);
    }

    @Override
    public String toString() {
        return objectsGrid.toString();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    /**
     * @author PEER-OLAF Siebers
     *
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        /**
         *
         */
        @Override
        public boolean hasNext() {
            return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);
        }

        /**
         *
         */
        @Override
        public GameObject next() {
            if (column >= objectsGrid.COLUMNS) {
                column = 0;
                row++;
            }

            GameObject object = objectsGrid.getGameObjectAt(column, row);
            GameObject diamond = diamondsGrid.getGameObjectAt(column, row);
            GameObject retObj = object;
            column++;

            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else {
                    retObj = object;
                }
            }

            return retObj;
        }

        /**
         * get current position of object
         * @return current position
         */
        public Point getCurrentPosition() {
            return new Point(column, row);
        }
    }
}