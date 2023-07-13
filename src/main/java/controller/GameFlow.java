package controller;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import view.GameMessage;
import view.GameObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;																																																												

/**
 * Activities flow of game
 * @author Huy Nhat Giang-modified
 * Apply singleton 
 * Rename from StartMeUp to GameFlow
 * Return number of move
 * Add undo() method
 */

public class GameFlow {
    /**
     * call out GameLogger instance
     */
    public static GameLogger logger;
    /**
     * set debug status to not on debug
     */
    private static boolean debug = false;
    /**
     * call out Level instance
     */
    private Level currentLevel;
    /**
     * call out GameMessage instance
     */
    private GameMessage gm;
    /**
     * put in name of the map
     */
    private String mapSetName;
    /**
     * list of level in raw file
     */
    private List<Level> levels;
    /**
     * set status if game is not finished
     */
    private boolean m_GameComplete = false;
    /**
     * initial number of moves
     */
    private int m_MovesCount = 0;
	/**
	 * code when press key on keyboard
	 */
	private KeyCode code;
	/**
	 * put in name of the level
	 */
	private String levelName;

		   
	   /**
	    * ADD
	    * private Object instance (apply Singleton pattern)
	    */
	   private static GameFlow instance;
	   
		/**
		 * private constructor
		 */
		private GameFlow() {}
		/**
		 * ADD
		 * other class can access to Object instance
		 * @return instance of Object
		 */
		public static GameFlow getInstance(){
	       if(instance == null){
	           synchronized(GameFlow.class){
	               if(instance == null){
	                  instance = new GameFlow();
	               }
	           }
	       }
	       return instance;
	    }    
    /**
     * constructor  
     * @param input game file
     * @param production production
     */
    public GameFlow(InputStream input, boolean production) {
        try {
            logger = new GameLogger();
            levels = loadGameFile(input);
            currentLevel = getNextLevel();
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: "+input+"\n"+e.getStackTrace());
        } 
    }

    /**
     * Debug mode is active
     * @return debug mode
     */
    public static boolean isDebugActive() {
        return debug;
    } 

    /**
     * Get map name
     * @return map name
     */
    public String getMapSetName() {
        return mapSetName;
    }

    /**
     * Event active when press key
     * @param primaryStage current stage
     * @param code Object direction
     * @throws FileNotFoundException
     */
    public void handleKey(Stage primaryStage,KeyCode code) throws FileNotFoundException {
        switch (code) {
            case UP:
                move(primaryStage, new Point(-1, 0));
                break;

            case RIGHT:
                move(primaryStage, new Point(0, 1));
                break;

            case DOWN:
                move(primaryStage, new Point(1, 0));
                break;

            case LEFT:
                move(primaryStage, new Point(0, -1));
                break;

            default:
            	JFrame cryWin = new JFrame();
            	JLabel cryLabel = new JLabel("Why are you pressing that?",JLabel.CENTER);
            	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            	cryWin.setSize(new Dimension(320,320));
            	cryWin.setLocation(dim.width/2-cryWin.getSize().width/2, dim.height/2-cryWin.getSize().height/2);
            	cryWin.add(cryLabel);
            	cryWin.setVisible(true);
                logger.severe("Why are you pressing that?");
        }
        GameFlow gl = GameFlow.getInstance();  
        gl.setCode(code);
        if (isDebugActive()) {
            System.out.println("\naction"+code);
        }
    }
    /**
     * Movement of character
     * @param primaryStage current stage
     * @param delta coordinate of character
     */
    public void move(Stage primaryStage, Point delta) {   	
        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.getObjectAt(keeperPosition);
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.getObjectAt(targetObjectPoint);
        if (GameFlow.isDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]+", keeperTarget, targetObjectPoint);
        }
        boolean keeperMoved = false;
        switch (keeperTarget) {
            case WALL:
                break;
            case CRATE:

                GameObject crateTarget = currentLevel.getTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {
                    break;
                }

                currentLevel.moveGameObjectBy(keeperTarget, targetObjectPoint, delta);
                currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
                keeperMoved = true;
                break;

            case FLOOR:
                currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
                keeperMoved = true;
                break;

            default:
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
            }               
        if (keeperMoved) {     	
        	PlaySound ps = new PlaySound();
        	ps.playMoveSound("src/main/resources/Sound/MoveSound.mp3");
        	
        	Highscore hs = Highscore.getInstance();
            keeperPosition.translate((int) delta.getX(), (int) delta.getY());
            System.out.print((int) delta.getX() +", "+(int) delta.getY()+"\n");
            m_MovesCount++;
            String move = Integer.toString(m_MovesCount);            
            hs.writeToMemory("src/main/java/model/highscoreMemory.txt", move); 
            
            if (currentLevel.isComplete()) {
                gm = GameMessage.getInstance();
                gm.levelCompleteMessage();
                m_MovesCount = 0;                  
                String path ="src/main/resources/HighScore/";
                String path1 = "src/main/java/model/";
                hs.readFileScore(primaryStage, currentLevel,  path1+"highscoreMemory.txt",path1+currentLevel.getName()+".txt",
                		path+currentLevel.getName()+"Summary"+".txt");
                              
                if (isDebugActive()) {
                    System.out.println("Level complete!");
                }
                currentLevel = getNextLevel();
              }   
            else {
            	GameFlow st = GameFlow.getInstance();
            	st.setLevelName(currentLevel.getName());          	
            }
        }
    }
    

	/**
	 * Load game construction map of level
	 * @param input file path
	 * @return current load level
	 */
	public List<Level> loadGameFile(InputStream input) {
        List<Level> levels = new ArrayList<>(10);
        int levelIndex = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";

            while (true) {
                String line = reader.readLine();

                // Break the loop if EOF is reached
                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }

                if (line.contains("MapSetName")) {
                    mapSetName = line.replace("MapSetName: ", "");
                    continue;
                }

                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    levelName = line.replace("LevelName: ", "");
                    continue;
                }

                line = line.trim();
                line = line.toUpperCase();
                // If the line contains at least 2 WALLS, add it to the list
                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }

        } catch (IOException e) {
            logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            logger.severe("Cannot open the requested file: " + e);
        }

        return levels;
    }

    /**
     * status if game is complete or not
     * @return status if game is complete or not
     */
    public boolean isGameComplete() {
        return m_GameComplete;
    }

    /**
     * load next level
     * @return next level
     */
    public Level getNextLevel() {
        if (currentLevel == null) {
            return levels.get(0);
        }

        int currentLevelIndex = currentLevel.getIndex();
        if (currentLevelIndex <= levels.size()) {
            return levels.get(currentLevelIndex + 1);
        }

        m_GameComplete = true;
        return null;
    }

    /**
     * get current level
     * @return current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
 
    /**
     * turn on/off debug mode
     */
    public void toggleDebug() {
        debug = !debug;
    }
    
    /**
     * get level name
     * @return level name
     */
    public String getLevelName() {
		return levelName;
	}
	/**
	 * set level name
	 * @param levelName level name
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	/**
	 * set press code
	 * @param code press code
	 */
	public void setCode(KeyCode code) {
    	this.code = code;
    }
    
    /**
     * get press code
     * @return press code
     */
    public KeyCode getCode() {
		return code;   	
    }
    
	/**
	 * undo method (not finish)
	 * @param primaryStage current stage
	 */
	public void toggleUndo(Stage primaryStage) {
		
		GameFlow gl = GameFlow.getInstance();
		KeyCode i = gl.getCode();
		ArrayList<KeyCode> codeArray = new ArrayList<KeyCode>();
		if(i == KeyCode.UP || i == KeyCode.RIGHT || i == KeyCode.DOWN || i == KeyCode.LEFT) {
			codeArray.add(i);
		}
		else {
			JOptionPane.showMessageDialog(null, "you haven't move");
		}
		for(KeyCode a : codeArray) {			
			if(a==KeyCode.UP) {
				System.out.print(a+"\n");
			 }
			else if(a==KeyCode.DOWN) {
				System.out.print(a+"\n");
			 }
			else if(a==KeyCode.RIGHT) {
				System.out.print(a+"\n");
			 }
			else if(a==KeyCode.LEFT) {
				System.out.print(a+"\n");
			 }
			
		}
    }
    

}