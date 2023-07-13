package view;

import javafx.scene.control.Button;

/**
 * Default buttons are boring, make them more stylish
 * @author Huy Nhat Giang
 */
public class ButtonConfig {
	 /**
	  * Style for start screen button
	 * @param bt that button
	 * @param x x-coor for button
	 * @param y y-coor for button
	 * @param w width of button
	 * @param h height of button
	 */
	public void SetButtonStartScreenStyle (Button bt, double x, double y, double w, double h) {
		  bt.setLayoutX(x);
		  bt.setLayoutY(y);
		  bt.setMinSize(w, h);
		  bt.setStyle("-fx-font-size:15; -fx-font-family: \"Times New Roman\";"
		  		+ " -fx-background-color: green;-fx-text-fill: lightyellow;");
		  
	  }
	 
	 /**
	  * Style for High score window button
	 * @param bt that button
	 * @param x x-coor for button
	 * @param y y-coor for button
	 * @param w width of button
	 * @param h height of button
	 */
	public void SetButtonHighScoreStyle (Button bt, double x, double y, double w, double h) {
		  bt.setLayoutX(x);
		  bt.setLayoutY(y);
		  bt.setMinSize(w, h);
		  bt.setStyle("-fx-font-size:15; -fx-font-family: \"Times New Roman\";"
		  		+ " -fx-background-color: green;-fx-text-fill: lightyellow;");
		  
	  }
	 
	 /**
	  * Style for wall color choosing screen button
	 * @param bt that button
	 * @param x x-coor for button
	 * @param y y-coor for button
	 * @param w width of button
	 * @param h height of button
	 * @param c color name of button
	 */
	public void SetButtonWallColorStyle (Button bt, double x, double y, double w, double h, String c) {
		  bt.setLayoutX(x);
		  bt.setLayoutY(y);
		  bt.setMinSize(w, h);
		  bt.setStyle("-fx-font-size:15; -fx-font-family: \"Times New Roman\";"
		  		+ " -fx-background-color: "+c+";-fx-text-fill: lightyellow;");
		  
	  }
}
