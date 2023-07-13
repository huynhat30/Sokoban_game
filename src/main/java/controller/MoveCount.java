package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Show how many move on screen
 * @author Huy Nhat Giang
 *
 */
public class MoveCount extends Pane{
	/**
	 * call out Timeline object
	 */
	private Timeline anime;
    /**
     * initial number of move
     */
    private int moveNu =0;
    String move = "";
    /**
     * add number of move to label
     */
    Label label =new  Label("Move: 0");
	   
	   /**
	    * private Object instance (apply Singleton pattern)
	    */
	   private static MoveCount instance;

		
		/**
		 * other class can access to Object instance
		 * @return instance of Object
		 */
		public static MoveCount getInstance(){
	       if(instance == null){
	           synchronized(MoveCount.class){
	               if(instance == null){
	                  instance = new MoveCount();
	               }
	           }
	       }
	       return instance;
	    }  
   
    /**
     * show up how many move on screen
     */
	private MoveCount() {
		   label.setFont(new Font(20));
		   getChildren().add(label);
		   anime = new  Timeline(new KeyFrame(Duration.seconds(0.1), e -> countMove()));
		   anime.setCycleCount(Timeline.INDEFINITE);
		   anime.play();
	   }
	   
	 /**
	 * Get number of move base on file
	 */
	public void countMove() {
		   String path = "src/main/java/model/highscoreMemory.txt";   	 
	       String line;
	       try {
			BufferedReader reader = new BufferedReader(new FileReader(path)); 
			line = reader.readLine();			
			if(line != null) {
				int i=Integer.parseInt(line.replaceAll("\\D+", ""));
				if (i >= 0) {
					 setMove(i);
					 moveNu = getMove();
			         line = reader.readLine();
				   }	
	         }
			else {
				moveNu = 0;
			}
	        reader.close();
	        
			} catch (IOException e) {
				e.printStackTrace();
			}
		   move = moveNu + "";
		   label.setText("Move: "+move);
	   }
	   
	/**
	 * Getter of number of move
	 * @return number of move
	 */
	public int getMove() {
			return moveNu;
		}

	/**
	 * Setter of number of move 
	 * @param moveNu number of move
	 */
	public void setMove(int moveNu) {
		this.moveNu = moveNu;
	}
}
