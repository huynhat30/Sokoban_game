package controller;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * Music and Sound controller
 * 
 * @author Huy Nhat Giang
 *
 */
public class PlaySound implements ActionListener{
	 
	 /**
	 * track path of sound file
	 */
	JFileChooser browser = new JFileChooser();
	 /**
	 * a drop box for choosing music
	 */
	@SuppressWarnings("rawtypes")
	 JComboBox list = new JComboBox();
	 /**
	 * list of music in drop box
	 */
	String[] music = new String[5];
	 /**
	 * file path of sound
	 */
	File file, sound;
	 /**
	 * get audio from file
	 */
	AudioInputStream au;
	 /**
	 * clip
	 */
	Clip clip;
	 /**
	 * Music player window
	 */
	JFrame musicPlayer = new JFrame("Music Player");
     /**
     * Button in music player
     */
    JButton addMusic = new JButton("Add Music"), play = new JButton("Play"), stop = new JButton("Stop");
     
	/**
	 *  sound after each move of character
	 * @param filepath file path of move sound
	 */
	public void playMoveSound(String filepath) {
        Media music;
        File filePath = new File(filepath);
        try {
      	  music =  new Media(filePath.toURI().toString());
		      MediaPlayer audios = new MediaPlayer(music);
		      audios.play();				
        }catch(Exception e){
      	  JOptionPane.showMessageDialog(null,"File Sound Error");
        }
   }

     /**
     * a simple music player
     */
    public void musicPlayer() {   
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV sound", "wav");
        
        addMusic.setBackground(Color.LIGHT_GRAY); addMusic.setForeground(Color.yellow);
        addMusic.setFont(new Font("Arial",20,20));
        addMusic.addActionListener(this);
        
        play.setBackground(Color.LIGHT_GRAY); play.setForeground(Color.green);
        play.setFont(new Font("Arial",20,20));
        play.addActionListener(this);    
        
        stop.setBackground(Color.LIGHT_GRAY); stop.setForeground(Color.red);
        stop.setFont(new Font("Arial",20,20));
        stop.addActionListener(this);    
       
        musicPlayer.setBackground(Color.black);
        musicPlayer.add(list, BorderLayout.PAGE_START);
        musicPlayer.add(addMusic, BorderLayout.LINE_START);
        musicPlayer.add(play, BorderLayout.CENTER);
        musicPlayer.add(stop, BorderLayout.LINE_END);
        browser.setFileFilter(filter);       
        musicPlayer.setSize(400,200);
        musicPlayer.setLocation(1069,38);
        musicPlayer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        musicPlayer.setVisible(true);
     }

	/**
	 *Event in action for buttons in music player
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		int songNum, index=0;
		if(e.getSource() == addMusic) {
			songNum = browser.showOpenDialog(musicPlayer);
			   if (songNum == APPROVE_OPTION) {
				   file = browser.getSelectedFile();
				   music[index] = file.toString();
				   list.addItem("Song "+index);
				   index++;}
		}
		else if(e.getSource() == play) {
			try {
				if(list.getSelectedIndex()==0) {
					sound = new File(music[list.getSelectedIndex()]);
					au = AudioSystem.getAudioInputStream(sound);
					clip = AudioSystem.getClip();
					clip.open(au);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);}
			}catch(Exception ex){
				System.out.print("File error");}
		}
		else if(e.getSource() == stop) {
			clip.stop();
		}
		
	}
     
}
