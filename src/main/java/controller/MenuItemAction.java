package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import javafx.stage.Stage;
import view.GameBoard;
import view.GameMessage;

/**
 * Every menu items have their event in action
 * 
 * @author Huy Nhat Giang
 * Break from Main(origin) class
 * 
 *
 */
public class MenuItemAction implements ActionListener{	 
	 /**
	 * button to play default game music
	 */
	JButton defaultM = new JButton("Default music");
	 /**
	 * button for optional music choices of player
	 */
	JButton insertM = new JButton("Play your music");
	 /**
	 * button for stop default music
	 */
	JButton stop = new JButton("Stop");	 
	 /**
	 * call out clip
	 */
	Clip clip;
	 /**
	 * instance of class (apply Singleton)
	 */
	private static MenuItemAction instance;
	 
	
	 /**
	 * Call GameBoard Instance
	 */
	 private GameBoard gb = GameBoard.getInstance(); 
	 /**
	 * Call GameMessage object
	 */
	 private GameMessage gm;
	 /**
	 * Call StartMeUp object
	 */
	 private GameFlow gameEngine;
	 
	 /**
	 * private constructor of class(apply Singleton)
	 */
	private MenuItemAction() {}
	/**
	 * exit game on close
	 */
	public void closeGame() {
	        System.exit(0);
	    }
	
	/**
	 * save game file action
	 */
	public void saveGame() {}
	
	/**
	 * load save game file to continue
	 */
	public void loadGame() {
        try {
            gb.loadGameFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	    
    /**
     * reset 1 move of character
     */
    public void undo(Stage primaryStage) { 
    	gameEngine = GameFlow.getInstance();
        gameEngine.toggleUndo(primaryStage);
        gb.reloadGrid();
    }
    
    /**
	 * reset entire current level
	 */    
    public void resetLevel() {
    	
    }
    
    /**
	 * show infor of game
	 */
    public void showAbout() {
    	gm = GameMessage.getInstance();
        String title = "About This Game";
        String message = "Enjoy the Game!\n";
        gm.newDialog(title, message, null);
    }
	
    /**
	 * option for player to play default music or their musics
	 */
    public void toggleMusic() {
    	JFrame frame = new JFrame();
    	defaultM.setBackground(Color.LIGHT_GRAY); defaultM.setForeground(Color.yellow);
    	defaultM.setFont(new Font("Arial",20,20));
    	defaultM.addActionListener(this);
        
    	stop.setBackground(Color.LIGHT_GRAY); stop.setForeground(Color.yellow);
    	stop.setFont(new Font("Arial",20,20));
    	stop.addActionListener(this);
    	
    	insertM.setBackground(Color.LIGHT_GRAY); insertM.setForeground(Color.yellow);
    	insertM.setFont(new Font("Arial",20,20));
    	insertM.addActionListener(this);
    	
    	frame.add(defaultM, BorderLayout.LINE_START);
    	frame.add(stop, BorderLayout.CENTER);
    	frame.add(insertM, BorderLayout.AFTER_LAST_LINE);
    	frame.setSize(350,180);
    	frame.setLocation(1069,38);
    	frame.addWindowListener(new WindowAdapter() {
    		@Override
    		  public void windowClosing(WindowEvent we) {
    			if(!clip.isActive()) {
    				 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				   }
				   else {
					   clip.stop();
		    			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				   }
    			
    			  }
    			});
    	frame.setVisible(true);    	 	
    }
	
    /**
	 *game debug
	 */
    public void toggleDebug() {
    	gameEngine = GameFlow.getInstance();
        gameEngine.toggleDebug();
        gb.reloadGrid();
    }
    
    /**
     * HighScoreShow(): show high score of level
     * @throws IOException can't get instance
     */
    public void HighScoreShow()  {
    	Highscore hs = Highscore.getInstance();
    	try {
			hs.scoreWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}  	
    }
    
    /**
     * create instance for class(apply Singleton)
     * @return instance instance of class
     */
    public static MenuItemAction getInstance(){
        if(instance == null){
            synchronized(MenuItemAction.class){
                if(instance == null){
                   instance = new MenuItemAction();
                }
            }
        }
        return instance;
     }

    
	/**
	 *Event on action from keyboard or mouse
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		File file;
		AudioInputStream au;
		file = new File("src/main/resources/Sound/puzzle_theme.wav");
		if(e.getSource() == defaultM) {		
				try {
					if(file.exists()) {
					   au = AudioSystem.getAudioInputStream(file);
					   clip = AudioSystem.getClip();
					   clip.open(au);
					   if(!clip.isRunning()) {
						   clip.start();
						   clip.loop(Clip.LOOP_CONTINUOUSLY);
					   }
					   else {
						   clip.stop();
					   }					   
					}
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				} 
		}
		else if(e.getSource() == stop) {
			clip.stop();
		}
		else if(e.getSource() == insertM) {
			clip.stop();
			PlaySound ps = new PlaySound();
	    	ps.musicPlayer();
		}
	}
}
