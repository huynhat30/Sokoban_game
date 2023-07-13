package controller;

import java.awt.*;
import java.util.Iterator;

import view.GameObject;

/**
 * where game board and game object put in position
 * @author PEER-OLAF Siebers
 */

public class GameGrid implements Iterable<GameObject> {

    /**
     * number of column of level
     */
    final int COLUMNS;
    
    /**
     * number of row of level
     */
    final int ROWS;
    /**
     * position of game objects in game
     */
    private GameObject[][] gameObjects;

    /**
     * Constructor
     * @param columns number of column of level
     * @param rows number of row of level
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * @param sourceLocation
     * @param delta
     * @return
     */
    static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * @return
     */
    public Dimension getDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * @param source
     * @param delta
     * @return
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(translatePoint(source, delta));
    }

    /**
     * @param col
     * @param row
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (GameFlow.isDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return gameObjects[col][row];
    }

    /**
     * receive current position of object
     * @param p position of object
     * @return position of object
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }

        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * remove object at that position
     * @param position position of game object
     * @return if object be removed or not
     */
    public boolean removeGameObjectAt(Point position) {
        return putGameObjectAt(null, position);
    }


    /**
     * put objects in position
     * @param gameObject object in game
     * @param x x-coor of object
     * @param y y-coor of object
     * @return the new location of object
     */
    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * @param gameObject
     * @param p
     * @return
     */
    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * If object not in correct range of game
     * @param x x-coor of object
     * @param y y-coor of object
     * @return
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.getCharSymbol());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new GridIterator();
    }

    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return getGameObjectAt(column++, row);
        }
    }
}