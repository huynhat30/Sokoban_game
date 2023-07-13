package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.StartScreen;

/**
 * only mission is to run the game
 * 
 * @author Huy Nhat Giang-modified
 * 
 */
public class Main extends Application{
    public static void main(String[] args) {
     launch(args);
    }
	/**
	 *Begin the game at first stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		StartScreen st = StartScreen.getInstance();
		st.ButtonStartScreen(primaryStage);    
	}

}
