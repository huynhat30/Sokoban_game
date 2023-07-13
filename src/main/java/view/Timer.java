package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Every games need a timer for challenges
 * @author Huy Nhat Giang
 *
 */
public class Timer extends Pane{
   /**
	* call out Timeline object
	*/
   private Timeline anime;
   
   /**
    * start index of timer
    */
   private int second =0;
   String time = "";
   /**
    * add timer to label
    */
   Label label =new  Label("Timer: 0");
   
   
   /**
    * private Object instance (apply Singleton pattern)
    */
   private static Timer instance;

	
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static Timer getInstance(){
       if(instance == null){
           synchronized(Timer.class){
               if(instance == null){
                  instance = new Timer();
               }
           }
       }
       return instance;
    }
   
   /**
	* Act like a pane to add label timer
	*/
    private Timer() {
	   label.setFont(new Font(20));
	   getChildren().add(label);
	   anime = new  Timeline(new KeyFrame(Duration.seconds(1), e -> timerRun()));
	   anime.setCycleCount(Timeline.INDEFINITE);
	   anime.play();
   }
   
   /**
    * timer active when player start to move character
    */
    public void timerRun() {
	   String path = "src/main/java/model/highscoreMemory.txt";   	 
       String line;
       try {
			BufferedReader reader = new BufferedReader(new FileReader(path)); 
			line = reader.readLine();
			if(line != null) {
				if (second >= 0) {
					   second++;
					   setTime(second);
			           line = reader.readLine();
				   }	
	         }
			else {
				second = 0;}
	         reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   time = second + "";
	   label.setText("Timer: "+time);
   }
   
   /**
    * Getter of time
    * @return time in second
    */
    public int getTime() {
		return second;
	}
    
    /**
     * Setter of time
     * @param second time in second
     */
	public void setTime(int second) {
		this.second = second;
	}
}
