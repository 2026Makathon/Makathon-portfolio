package dynamic_beat_24;

import java.io.BufferedInputStream;
import javazoom.jl.player.Player;

public class Music extends Thread {

    private Player player;
    private boolean isLoop;
    private String resourcePath;
    private BufferedInputStream bis;
    
    
    private boolean isFinished = false;

    public Music(String name, boolean isLoop) {
        try {
            this.isLoop = isLoop;
            this.resourcePath = "music/" + name;
            bis = new BufferedInputStream(Main.class.getResourceAsStream(resourcePath));
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    public boolean isFinished() {
        return isFinished;
    }

    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }

    public void close() {
        isLoop = false;
        if (player != null)
            player.close();
        this.interrupt();
    }

    @Override
    public void run() {
        try {
            do {
                if (player != null) {
                    player.play(); 
                }
                
                bis = new BufferedInputStream(Main.class.getResourceAsStream(resourcePath));
                player = new Player(bis);
            } while (isLoop);
            
            
            isFinished = true; 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

	public void pause() {
		
		
	}
}