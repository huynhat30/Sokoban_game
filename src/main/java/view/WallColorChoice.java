package view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Everyone has their favorite color
 * @author Giang Nhat Huy
 */
public class WallColorChoice extends Application{

	/**
	 * a window pop-up for choosing color
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane p = new Pane();
		Label la = new Label();
		la.setText("      "+"Choose your Wall Color <3!");
		la.setFont(new Font("Comic Sans", 24));
        p.getChildren().add(la);
    	p.getChildren().add(colorButton(primaryStage,1, "BLACK", 40, 50));
    	p.getChildren().add(colorButton(primaryStage,2, "RED", 200, 50));
    	p.getChildren().add(colorButton(primaryStage,3, "BLUE", 40, 120));
    	p.getChildren().add(colorButton(primaryStage,4, "GREEN", 200, 120));
    	p.getChildren().add(colorButton(primaryStage,5, "YELLOW", 40, 200));
    	p.getChildren().add(colorButton(primaryStage,6, "ORANGE", 200, 200));
    	p.getChildren().add(colorButton(primaryStage,7, "CYAN", 40, 280));
    	p.getChildren().add(colorButton(primaryStage,8, "GRAY", 200, 280));
    	p.getChildren().add(colorBackButton(primaryStage,8, 130, 380));
    	Scene scene = new Scene(p, 380, 450);
    	primaryStage.setTitle("Choose your color");
        primaryStage.setScene(scene);
        primaryStage.show();		
	}
	/**
	 * Set action for color button
	 * @param primaryStage current stage of game
	 * @param c that choosing color
	 * @param i name of color
	 * @param x x-coor of button
	 * @param y y-coor of button
	 * @return that button
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Button colorButton(Stage primaryStage, int c, String i, double x, double y) 
			throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
    	ButtonConfig buttonConfig = new ButtonConfig();
    	Button b = new Button(i);
    	Wall wall = Wall.getInstance();
    	GameBoard gb = GameBoard.getInstance();
    	
	    b.setOnAction(value ->  {
	          try {
	        	primaryStage.close();
				wall.getColor(c);
				gb.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        });    
    	buttonConfig.SetButtonWallColorStyle(b, x, y, 140, 50, i);   	
		return b;
    	
    }
	
	/**
	 * Just a normal back button
	 * @param primaryStage
	 * @param c
	 * @param x
	 * @param y
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Button colorBackButton(Stage primaryStage, int c, double x, double y) 
			throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
    	ButtonConfig buttonConfig = new ButtonConfig();
    	Button b = new Button("Back");   	
	    b.setOnAction(value ->  {
	          try {
	        	primaryStage.close();
				StartScreen start = StartScreen.getInstance();
				start.ButtonStartScreen(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        });    
    	buttonConfig.SetButtonHighScoreStyle(b, x, y, 140, 50);   	
		return b;
    	
    }

}


