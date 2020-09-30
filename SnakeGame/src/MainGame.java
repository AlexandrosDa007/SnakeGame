import java.awt.Color;
import java.net.URL;

public class MainGame {
	
    public static Color c; //Background color
	
    public static void main(String args[]) {
    	
            boolean sound = false,grid = false; //Flags for sound on/off and grid line showing on/off
            //Game speed
            final int speed;
            if(args[2] != null || args[2] != "")
            	speed = Integer.parseInt(args[2]);
            else
            	speed = 100;
            
            if(args[0].equals("true"))
                sound = true;
            if(args[1].equals("true"))
                grid = true;
        
        GameContainer gc = new GameContainer(sound,grid,c);
	
        /*
         * Thread running the game and checking if its paused if its not then move snake
         */
		Thread th = new Thread(new Runnable(){
			public void run(){
				while(true){
					try {
						if(gc.isRunning){
							gc.getResetButton().setEnabled(false);
							gc.requestFocus();
							if(!gc.paused)
								gc.moveSnake();
							}
						
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		th.start();
		
		
		//If there is a sound then loop it forever
		if(sound){
		Thread music = new Thread(new Runnable(){
			public void run(){
				while(true){
					URL musicURL = getClass().getResource("bgMusic.wav");
					Sound.PlayMusic(musicURL);
					
				}
			}
		});
		music.start();
        }
		
		
	}
}
