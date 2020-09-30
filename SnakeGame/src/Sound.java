
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sound {


	//Playing the sound effect once
		static void PlaySound(URL Sound){
			try{
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(Sound));
				clip.start();
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error loading sound effect");
			}
		}
	
	
	//Playing the background music
		static void PlayMusic(URL Sound){
			try{
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(Sound)); 
				clip.start(); 
				//Thread is used for looping the game music
				Thread.sleep(clip.getMicrosecondLength()/1000);
				
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error loading background music");
			}
		}
		
	
}
