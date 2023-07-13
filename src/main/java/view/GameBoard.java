package view;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import controller.Highscore;
import controller.Level;
import controller.MenuItemAction;
import controller.MoveCount;
import controller.GameFlow;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * GameBoard use JavaFx library to create main menu bar(include options), 
 * background frame for the game and all methods for menu options.
 * @author Huy Nhat Giang
 * Break the class: all menu item event action now on a new class
 * Apply singleton
 */

public class GameBoard extends Application{
	
	/**
	 * Title of the game
	 */
	public static final String GAME_NAME = "Best Sokoban Ever";
	/**
	 * current stage of application
	 */
	private Stage primaryStage;
	/**
	 * call out GameFlow instance
	 */
	private GameFlow gameEngine;
    /**
     * call out GridPane instance
     */
    private GridPane gameGrid;
    /**
     * create a new File
     */
    private File saveFile;
    /**
     * create menu bar
     */
    private MenuBar MENU;
    /**
     * call out GameMessage instance
     */
    private GameMessage gm;
    /**
     * call out MenuItemAction instance
     */
    private MenuItemAction mItem;
    
	/**
     * private Object instance (apply Singleton pattern)
     */
    private static GameBoard instance;

	/**
	 * private constructor of class(apply Singleton pattern)
	 */
	private GameBoard() {
		super();
	}
	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static GameBoard getInstance(){
        if(instance == null){
            synchronized(GameBoard.class){
                if(instance == null){
                   instance = new GameBoard();
                }
            }
        }
        return instance;
     }

    /**
     *initialize the game board
     */
    @Override 
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;	
		MENU = new MenuBar();
		mItem= MenuItemAction.getInstance();
		MenuItem menuItemSaveGame = new MenuItem("Save Game");
		menuItemSaveGame.setOnAction(actionEvent -> mItem.saveGame());
		MenuItem menuItemLoadGame = new MenuItem("Load Game");
		menuItemLoadGame.setOnAction(actionEvent -> mItem.loadGame());
		MenuItem menuItemExit = new MenuItem("Exit");
		menuItemExit.setOnAction(actionEvent -> mItem.closeGame());		
		
		Menu menuFile = new Menu("File");        		
		menuFile.getItems().addAll(menuItemSaveGame, menuItemLoadGame,new SeparatorMenuItem()
				, new SeparatorMenuItem(), menuItemExit);
			
		MenuItem menuItemUndo = new MenuItem("Undo");
		menuItemUndo.setOnAction(actionEvent -> mItem.undo(primaryStage));	
		
		Menu music = new Menu("Music");
		MenuItem musicOp = new MenuItem("Music Option");
		musicOp.setOnAction(actionEvent -> mItem.toggleMusic());
		music.getItems().add(musicOp);
		
		Menu score = new Menu("Score");
		MenuItem highSocre = new MenuItem("High Score");
		highSocre.setOnAction(actionEvent -> mItem.HighScoreShow());
		score.getItems().add(highSocre);

		RadioMenuItem radioMenuItemDebug = new RadioMenuItem("Toggle Debug");
		radioMenuItemDebug.setOnAction(actionEvent -> mItem.toggleDebug());
		MenuItem menuItemResetLevel = new MenuItem("Reset Level");
		menuItemResetLevel.setOnAction(actionEvent -> mItem.resetLevel());
		
		Menu menuLevel = new Menu("Level");	
		menuLevel.getItems().addAll(menuItemUndo,radioMenuItemDebug, 
				new SeparatorMenuItem(), menuItemResetLevel);
		
		MenuItem menuItemGame = new MenuItem("About This Game");
		Menu menuAbout = new Menu("About");
		menuAbout.setOnAction(actionEvent -> mItem.showAbout());
		menuAbout.getItems().addAll(menuItemGame);
		
		Timer timer = Timer.getInstance();
		MoveCount mc = MoveCount.getInstance();
		
		MENU.getMenus().addAll(menuFile, menuLevel, music, score, menuAbout);
		gameGrid = new GridPane();
		GridPane root = new GridPane();
		root.add(MENU, 0, 0);
		root.add(mc, 0, 10);
		root.add(timer, 0, 5);
		root.add(gameGrid, 0, 30);	
		primaryStage.setTitle(GAME_NAME);
		primaryStage.setScene(new Scene(root, 605, 700));
		primaryStage.show();
		primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	onClose();}});
		loadDefaultSaveFile(primaryStage);			
	}
    
    /**
     * ADD
     * Action on close
     */
    public void onClose() {
    	String w = "src/main/java/model/highscoreMemory.txt";
    	Highscore hs = Highscore.getInstance();
    	try {
			hs.allIsSpace(w);
			JOptionPane.showMessageDialog(null, "Game are closing!!");
			Thread.sleep(4);
		    System.exit(0);
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}	
    }
    
    /**
     * Load skeleton of game
     * @param primaryStage current Stage
     * @throws IOException 
     */
    void loadDefaultSaveFile(Stage primaryStage) throws IOException { 
    	this.primaryStage = primaryStage;
        String file ="controller/SampleGame.skb";
        InputStream in = getClass().getClassLoader().getResourceAsStream(file);
        System.out.println(in);
        initializeGame(in);
        System.out.println("Good");
        setEventFilter(primaryStage);
        System.out.println("Good");
    }

    /**
     * first game run
     * @param input file path
     * @throws IOException 
     */
    public void initializeGame(InputStream input) throws IOException {
        gameEngine = new GameFlow(input, true);
        reloadGrid();
    }

    /**
     * set what happen with action from keyboard or mouse
     * @param primaryStage current stage
     */
    public void setEventFilter(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
				gameEngine.handleKey(primaryStage, event.getCode());
			} catch (IOException e) {
				System.out.print("Something wrong with your Key");
			}
            reloadGrid();
        });}
    
    /**
     * Load save file of game
     * @throws FileNotFoundException
     */
    public void loadGameFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        saveFile = fileChooser.showOpenDialog(primaryStage);

        if (saveFile != null) {
            if (GameFlow.isDebugActive()) {
                GameFlow.logger.info("Loading save file: " + saveFile.getName());
            }
            try {
				initializeGame(new FileInputStream(saveFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
      }
    
    /**
     * repaint the game
     */
    public void reloadGrid() {
        if (gameEngine.isGameComplete()) {
            gm.showVictoryMessage();
            return;
        }
        Level currentLevel = gameEngine.getCurrentLevel();
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
        gameGrid.getChildren().clear();
        while (levelGridIterator.hasNext()) {
            addObjectToGrid(levelGridIterator.next(), levelGridIterator.getCurrentPosition());
        }gameGrid.autosize();
        primaryStage.sizeToScene();
    }

    /**
     * draw objects on game board
     * @param gameObject game objects
     * @param location location of game objects
     */
    public void addObjectToGrid(GameObject gameObject, Point location) {
        GraphicObject graphicObject = new GraphicObject(gameObject);
        gameGrid.add(graphicObject, location.y, location.x);
    }   
}
