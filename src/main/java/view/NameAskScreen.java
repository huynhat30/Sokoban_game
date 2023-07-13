package view;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Everyone has their Identity
 * @author Huy Nhat Giang
 *
 */
public class NameAskScreen extends Application {
    /**
     * name of player
     */
    private String name = "";
    
    /**
     * private Object instance (apply Singleton pattern)
     */
    private static NameAskScreen instance;

	/**
	 * private constructor of class(apply Singleton pattern)
	 */
	private NameAskScreen() {
		super();
	}
	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static NameAskScreen getInstance(){
        if(instance == null){
            synchronized(NameAskScreen.class){
                if(instance == null){
                   instance = new NameAskScreen();
                }
            }
        }
        return instance;
     }
    
	/**
	 * Getter of name
	 * @return player name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter of name
	 * @param name player name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *A window will pop-up and ask Player for their name
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		TextField field = new TextField();
		Button next = new Button("Next");
		Button back = new Button("Back");
		ButtonConfig buttonConfig = new ButtonConfig();
		Label la = new Label("What's your name?");
		la.setFont(new Font("Arial", 20));
		la.setStyle("-fx-text-fill: blue");
		la.setMaxWidth(200);
		la.setLayoutX(20);
		la.setLayoutY(30);
		
	    field.setLayoutX(30);
	    field.setLayoutY(60);
	    	    
	    next.setOnAction(value ->  {
	    	if(field.getText().trim().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "Please enter your name!");
	    		try {
					start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	else {
	    	  setName(field.getText());
	          WallColorChoice cl = new WallColorChoice();
	          try {
				cl.start(primaryStage);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Can't open next window!");
			 }
	    	}
	        });
	     
	    back.setOnAction(value ->  {
	    	  setName(field.getText());
	          StartScreen cl = StartScreen.getInstance();
              try {
				cl.ButtonStartScreen(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        });
	    
		buttonConfig.SetButtonStartScreenStyle(next, 50, 100, 100, 20);
		buttonConfig.SetButtonStartScreenStyle(back, 50, 150, 100, 20);
		pane.getChildren().add(la);
		pane.getChildren().add(field);
		pane.getChildren().add(next);
		pane.getChildren().add(back);
		primaryStage.setTitle("What's your name?");
		primaryStage.setScene(new Scene(pane, 205, 200));
		primaryStage.show();
	}
   
}
