package view;


/**
 * Which char = game objects
 * @author PEER-OLAF Siebers
 * not modified
 */

public enum GameObject {
	/**
	 *W for wall object
	 */
    WALL('W'),
    /**
	 *" " for floor object
	 */
    FLOOR(' '),
    /**
	 *C for crate object
	 */
    CRATE('C'),
    /**
	 *D for diamond object
	 */
    DIAMOND('D'),
    /**
	 *S for keeper object
	 */
    KEEPER('S'),
    /**
	 *O for goal object
	 */
    CRATE_ON_DIAMOND('O'),
    /**
	 *= for Debug mode object
	 */
    DEBUG_OBJECT('=');

    /**
     * keyboard symbol
     */
    private final char symbol;

    /**
     * Constructor of class
     * @param symbol word character of application
     */
    GameObject(final char symbol) {
        this.symbol = symbol;
    }

    /**
     * for each keyboard symbol = Object word character
     * @param c Object word character
     * @return the game objects
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }

        return WALL;
    }

    /**
     * get a string of words in 1 line
     * @return value of that word
     */
    public String getStringSymbol() {
        return String.valueOf(symbol);
    }

    /**
     * get specific character in application
     * @return that character in symbol
     */
    public char getCharSymbol() {
        return symbol;
    }
}