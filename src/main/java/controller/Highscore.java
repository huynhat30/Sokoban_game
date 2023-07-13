package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javafx.stage.Stage;

import view.NameAskScreen;
import view.Timer;

/**
 * Get player score and compare them.
 * @author Huy Nhat Giang
 *
 */
public class Highscore implements Comparator<Highscore> {
   
    /**
	 * score user get
	 */
   private int highscore;
   /**
    * player name
    */
   private String name;
   /**
    * current date
    */
   private String date;
   /**
    * total of moves
    */
	private int totalMove;
   /**
    * total of time to finish
    */
	private int time;
   
   /**
    * private Object instance (apply Singleton pattern)
    */
   private static Highscore instance;
   
	/**
	 * private constructor(apply singleton)
	 */
	private Highscore() {}
	/**
	 * other class can access to Object instance
	 * @return instance of Object
	 */
	public static Highscore getInstance(){
       if(instance == null){
           synchronized(Highscore.class){
               if(instance == null){
                  instance = new Highscore();
               }
           }
       }
       return instance;
    }
   
   
   /**
    * class constructor
    * @param highScore  score user get
    * @param date  current date
   */
       private Highscore(String name, int highScore, int time, String date) {
	   this.highscore = highScore;
	   this.time = time;
	   this.setName(name);
	   this.date = date;
   }

		
	/**
	 * Read and Write to Save score of user into File and Display on the screen
	 * @param currentLevel the current Level player play
	 * @param r file path for read
	 * @param w1 file path for write
	 * @param w2 file path for write 
	 * @param w3 file path for write
	 *
	 */
	public void readFileScore(Stage primaryStage, Level currentLevel, String w1, String w2, String w3) {
		BufferedReader reader; // call out reader
        Date date = new Date();  //call out date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        NameAskScreen name = NameAskScreen.getInstance();
        Timer timer = Timer.getInstance();
		try {
			reader = new BufferedReader(new FileReader(w1));
            String line = reader.readLine();
            while(line != null){ 
            	int i=Integer.parseInt(line.replaceAll("\\D+", "")); //get the score in file
            	setTotalMove(i); //save into Memory the score
                reader.close();
                break;}
            
            allIsSpace(w1);			
			Writer wr1 = new FileWriter(w2, true);
			wr1.append(name.getName() + ", " + getTotalMove()+", "+ timer.getTime()+", "+ formatter.format(date)+"\n");
			wr1.close();
            reader = new BufferedReader(new FileReader(w2));       
            ArrayList<Highscore> scoreArray = new ArrayList<Highscore>(); // call out an ArrayList to store each score
            line = reader.readLine();
            
            while (line != null){
                String[] scoreDetail = line.split(", ");               
                String playerName = scoreDetail[0];
                int currentScore = Integer.valueOf(scoreDetail[1]);
                int totalTime = Integer.valueOf(scoreDetail[2]);
                String currentDate = scoreDetail[3];                                     
                scoreArray.add(new Highscore(playerName, currentScore, totalTime, currentDate));         
                line = reader.readLine();               
            }
            reader.close();
            Collections.sort(scoreArray, new Highscore()); // sorting score in descending order
            Writer wr2 = new FileWriter(w3);
            for (Highscore score : scoreArray) {    // for each score in List
            	wr2 = new FileWriter(w3, true);
    			wr2.append("Name: "+score.getName() +" || Total moves: "+ score.getHighscore() 
    			                                    +" || Time: "+ score.getTime()+" || Date: "+ score.getDate()+"\n");
    			wr2.close();
            }  
            
            ArrayList<String> scoreSumArray = new ArrayList<String>(); // call out an ArrayList to store all character in 1 line
            reader = new BufferedReader(new FileReader(w3)); 
            line = reader.readLine();
            while(line != null) {            	 
            	scoreSumArray.add(line);
            	line = reader.readLine();
            }
            reader.close();
            
            JFrame frame = new JFrame("Your Score at Level" + currentLevel.getName()); // create a window to show all score 1 level
            JTextArea textArea = new JTextArea(); 
            JScrollPane sp = new JScrollPane(textArea);
            for(String a : scoreSumArray){ 
            	   textArea.append(a + "\n");
            	}
            textArea.setBounds(0, 0, 172, 339);
            textArea.setEditable(false);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(new Dimension(420,320));
            frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
            frame.getContentPane().add(sp);
            frame.setVisible(true);
            
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();} 
		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * Show high score window when click in High score in menu bar
	 * @param primaryStage
	 * @param name
	 * @param arrayScore
	 * @throws IOException 
	 * @throws Exception
	 */
	public void scoreWindow() throws IOException{
		GameFlow st = GameFlow.getInstance();
    	JFrame frame = new JFrame("Your Score at Level" + st.getLevelName()); // create a window to show all score 1 level
        JTextArea textArea = new JTextArea(); 
        JScrollPane sp = new JScrollPane(textArea);
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/HighScore/"+st.getLevelName()+"Summary.txt"));
        String line = reader.readLine();
        ArrayList<String> score= new ArrayList<String>();
        while(line!=null) {
        	score.add(line);
        	line = reader.readLine();
        }
        for(String a : score) {
        	textArea.append(a + "\n");
        }
        reader.close();
        textArea.setBounds(0, 0, 172, 339);
        textArea.setEditable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(new Dimension(420,320));
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.getContentPane().add(sp);
        frame.setVisible(true);
	} 
	
	/**
	 * replace all char in file with " " if necessary
	 * @param w file path
	 * @throws IOException
	 */
	public void allIsSpace (String w) throws IOException {
		Writer wr2 = new FileWriter(w);
        wr2.write("");
        wr2.close();
	}
	
	/**
	 * write number of moves into file
	 * @param w file path
	 * @param move number of moves
	 */
	public void writeToMemory (String w, String move) {
		try {
			Writer wr = new FileWriter(w);
			wr.write("Move: " + move);
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter of score
	 * @return score of player
	 */
	public int getHighscore() {
		return highscore;
	}
	
	/**
	 * Setter of score
	 * @param highscore score of player
	 */
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	
	/**
	 * getter of date
	 * @return current date and time
	 */ 
	public String getDate() {
		return date;
	}
	
	/**
	 * setter of date
	 * @param date current date and time
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * getter of total moves
	 * @return total move of user
	 */
	public int getTotalMove() {
		return totalMove;
	}

	/**
	 * setter of total moves
	 * @param totalMove total move of user
	 */
	public void setTotalMove(int totalMove) {
		this.totalMove = totalMove;
	}

	/**
	 * getter of player name
	 * @return name of player
	 */
	public String getName() {
		return name;
	}	
	/**
	 * setter of player name
	 * @param name name of player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter of time to finish
	 * @return time to finish
	 */
	public int getTime() {
		return time;
	}
	/**
	 * setter of time to finish
	 * @param time time to finish
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 *compare score from each time player play
	 */
	@Override
	public int compare(Highscore s1, Highscore s2) {	
		return s1.highscore - s2.highscore;
	}
}
