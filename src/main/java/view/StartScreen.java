package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Every game has start menu window
 * @author Huy Nhat Giang
 *
 */
public class StartScreen {
	
	/**
     * private Object instance (apply Singleton pattern)
     */
    private static StartScreen instance;
    /**
     * call out ButtonConfig instance
     */
    private ButtonConfig buttonConfig = new ButtonConfig();

	/**
	 * private constructor of class(apply Singleton pattern)
	 */
	private StartScreen() {
		super();
	}
	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static StartScreen getInstance(){
        if(instance == null){
            synchronized(StartScreen.class){
                if(instance == null){
                   instance = new StartScreen();
                }
            }
        }
        return instance;
     }

    /**
     * Show all button on start window
     * @param primaryStage current stage of application
     * @throws Exception can't run current stage
     */
    public void ButtonStartScreen(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sokoban start screen");
        
        Pane root = new Pane();
        Button play = new Button("Play");
        Button how = new Button("How to play");
        Button exit = new Button("Exit");

        buttonConfig.SetButtonStartScreenStyle(play, 40, 20, 140, 50);
        buttonConfig.SetButtonStartScreenStyle(how, 40, 90, 140, 50);
        buttonConfig.SetButtonStartScreenStyle(exit, 40, 170, 140, 50);
        
        play.setOnAction(value ->  {
           try {
        	primaryStage.close();
        	NameAskScreen ask = NameAskScreen.getInstance();
        	ask.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
        });
        
        how.setOnAction(value ->  {
        	primaryStage.close();
            try {
				HowtoPlay(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
         });
        
        exit.setOnAction(value ->  {
            System.exit(0);
         });
  
        root.getChildren().add(play);
        root.getChildren().add(how);
        root.getChildren().add(exit);
        Scene scene = new Scene(root, 220, 250);
        scene.getStylesheets().add("src/main/resources/styleCss/WindowStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
	
  /**
   * Show a game instruction window
   * @param primaryStage current stage of application
   * @throws Exception can't run current stage
   */
    public void HowtoPlay(Stage primaryStage) throws Exception{
	  Pane pane = new Pane();
	  TextArea field = new TextArea();
	  field.setMinSize(50, 50);
	  
	  field.setText("1) Move the blocks to the goal tiles using the arrow keys.\r\n"
	  		+ "2) You win when all the blocks are moved to the goal tiles.\r\n"
	  		+ "3) You cannot move more than one block at a time.\r\n"
	  		+ "4) Try to win in as few movements as .");	  
	  field.setStyle("-fx-font-size:15;-fx-font-family: Comic Sans;");  
	  field.setEditable(false);
	  
	  Button back = new Button("Back");
	  ButtonConfig buttonConfig = new ButtonConfig();
	  buttonConfig.SetButtonStartScreenStyle(back, 140, 240, 140, 50);
	  
	  back.setOnAction(value ->  {
          try {
       	primaryStage.close();
       	ButtonStartScreen(primaryStage);
		} catch (Exception e) {
			System.out.print("game close");
		}
       });
	  
	  pane.getChildren().add(field);
	  pane.getChildren().add(back);
      Scene scene = new Scene(pane, 420, 320);
      scene.getStylesheets().add("/src/main/java/view/WindowStyle.css");
      primaryStage.setScene(scene);
      primaryStage.show();
  }
}