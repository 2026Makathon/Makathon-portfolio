package dynamic_beat_23;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;



public class Note extends Thread {
	
	
	public static final int NOTE_WIDTH = 100;
	public static final int SPACE_WIDTH = 200;
	public static final int NOTE_HEIGHT = 30;

	
	public static final int PERFECT_RANGE = 20;
	public static final int GREAT_RANGE = 40;
	public static final int GOOD_RANGE = 60;
	public static final int EARLY_LATE_RANGE = 80;

	
	public static final int JUDGEMENT_LINE_Y = 960; 

	private Image noteBasicImage = new ImageIcon(Main.class.getResource("images/noteBasic.png")).getImage();
	private int x, y; 
	private String noteType;
	private boolean proceeded = true;
	
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void setProceeded(boolean proceeded) {
		this.proceeded = proceeded;
	}

	public int getY() {
		return y;
	}

	
	public Note(String noteType) {
		
		this.y = JUDGEMENT_LINE_Y - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
		
		if(noteType.equals("S")) {
			x = 528;
		}
		else if(noteType.equals("D")) {
			x = 632;
		}
		else if(noteType.equals("F")) {
			x = 736;
		}
		else if(noteType.equals("Space")) {
			x = 840;
		}
		else if(noteType.equals("J")) {
			x = 1044;
		}
		else if(noteType.equals("K")) {
			x = 1148;
		}
		else if(noteType.equals("L")) {
			x = 1252;
		}
		this.noteType = noteType;
	}
	
	
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y, SPACE_WIDTH, NOTE_HEIGHT, null);
		} else {
			g.drawImage(noteBasicImage, x, y, NOTE_WIDTH, NOTE_HEIGHT, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		

	}
	
	public void close() {
		proceeded = false;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				} else {
					
					break; 
				}
			}
		} catch(Exception e) {
			System.err.println("Note 스레드 에러: " + e.getMessage());
		}
	}
	
	
	public String judge() {
	    
	    
	    if (y >= 950 && y <= 970) {
	        System.out.println("Perfect");
	        close();
	        return "Perfect";
	    } 
	    else if (y >= 930 && y <= 990) {
	        System.out.println("Great");
	        close();
	        return "Great";
	    } 
	    else if (y >= 900 && y <= 1020) {
	        System.out.println("Good");
	        close();
	        return "Good";
	    } 
	    else if (y >= 850 && y <= 1050) {
	        System.out.println("Early/Late");
	        close();
	        return "Early"; 
	    }
	    
	    return "None";
	}
}