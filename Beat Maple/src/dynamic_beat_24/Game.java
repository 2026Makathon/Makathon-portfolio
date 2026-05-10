package dynamic_beat_24;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game extends Thread {

	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();

	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("images/noteRouteLine.png")).getImage();
	private Image judgementLineImage = new ImageIcon(Main.class.getResource("images/judgementLine.png")).getImage();
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("images/gameInfo.png")).getImage();

	private Image orangeFlareImage = null;
	private Image judgeImage = null;

	private Image keyPadSImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadDImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadFImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadSpace1Image = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadSpace2Image = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadJImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadKImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	private Image keyPadLImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();

	private String titleName;
	private String difficulty;
	private String musicTitle;
	private Music gameMusic;

	private boolean isGameEnded = false;
	private Image resultImage = new ImageIcon(Main.class.getResource("images/resultBackground3.png")).getImage();

	private int countPerfect = 0;
	private int countGreat = 0;
	private int countGood = 0;
	private int countMiss = 0;
	

	ArrayList<Note> noteList = new ArrayList<Note>();

	private int score = 0;
	private int combo = 0;
	private int maxCombo = 0;

	private int comboFontSize = 40;

	private boolean showBonus = false;
	private int bonusTimer = 0;

	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
	}

	public void screenDraw(Graphics2D g) {

		if (isGameEnded) {
			g.drawImage(resultImage, 300, 220, null);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g.drawString(String.valueOf(countPerfect), 500, 500);
			g.drawString(String.valueOf(countGreat), 900, 500);
			g.drawString(String.valueOf(countGood), 500, 635);
			g.drawString(String.valueOf(score), 500, 790);

			g.drawString(String.valueOf(countMiss), 900, 635);
			g.drawString(String.valueOf(maxCombo), 900, 790);

			return;
		}

		g.drawImage(noteRouteSImage, 528, 30, null);
		g.drawImage(noteRouteDImage, 632, 30, null);
		g.drawImage(noteRouteFImage, 736, 30, null);
		g.drawImage(noteRouteSpace1Image, 840, 30, null);
		g.drawImage(noteRouteSpace2Image, 940, 30, null);
		g.drawImage(noteRouteJImage, 1044, 30, null);
		g.drawImage(noteRouteKImage, 1148, 30, null);
		g.drawImage(noteRouteLImage, 1252, 30, null);

		g.drawImage(noteRouteLineImage, 524, 30, null);
		g.drawImage(noteRouteLineImage, 628, 30, null);
		g.drawImage(noteRouteLineImage, 732, 30, null);
		g.drawImage(noteRouteLineImage, 836, 30, null);
		g.drawImage(noteRouteLineImage, 1040, 30, null);
		g.drawImage(noteRouteLineImage, 1144, 30, null);
		g.drawImage(noteRouteLineImage, 1248, 30, null);
		g.drawImage(noteRouteLineImage, 1352, 30, null);

		g.drawImage(gameInfoImage, 0, 1020, null);
		g.drawImage(judgementLineImage, 0, 960, null);

		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);

			if (note.getY() > 1040 && note.isProceeded()) {
				judgeEvent("Miss");
				score -= 50;
				note.close();
			}

			if (!note.isProceeded()) {
				noteList.remove(i);
				i--;
				continue;
			}

			if (note.getY() > 1040) {
				judgeImage = new ImageIcon(Main.class.getResource("images/judgeMiss.png")).getImage();
				note.setProceeded(false);
				new ImageEffectTimer("Judge").start();
				combo = 0;
			}

			note.screenDraw(g);
		}

		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(titleName, 20, 1065);
		g.drawString(difficulty, 1820, 1060);

		g.setColor(Color.BLACK);
		g.drawString("S", 570, 1000);
		g.drawString("D", 672, 1000);
		g.drawString("F", 776, 1000);
		g.drawString("Space Bar", 870, 1000);
		g.drawString("J", 1084, 1000);
		g.drawString("K", 1188, 1000);
		g.drawString("L", 1295, 1000);

		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString(String.format("%07d", score), 840, 1065);

		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("MAX COMBO: " + maxCombo, 1500, 90);

		if (combo > 0) {

			if (comboFontSize > 40) {
				comboFontSize -= 2;
			}

			g.setColor(Color.ORANGE);
			g.setFont(new Font("Elephant", Font.BOLD, comboFontSize));

			g.drawString("COMBO " + combo, 850 - (comboFontSize / 2), 200);
		}

		if (showBonus) {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("50 COMBO BONUS! +1000", 750, 300);

			bonusTimer--;
			if (bonusTimer <= 0) {
				showBonus = false;
			}
		}

		if (orangeFlareImage != null) {
			g.drawImage(orangeFlareImage, 320, 200, null);
		}
		if (judgeImage != null) {
			g.drawImage(judgeImage, 750, 650, null);
		}

		g.drawImage(keyPadSImage, 528, 580, null);
		g.drawImage(keyPadDImage, 632, 580, null);
		g.drawImage(keyPadFImage, 736, 580, null);
		g.drawImage(keyPadSpace1Image, 840, 580, null);
		g.drawImage(keyPadSpace2Image, 940, 580, null);
		g.drawImage(keyPadJImage, 1044, 580, null);
		g.drawImage(keyPadKImage, 1148, 580, null);
		g.drawImage(keyPadLImage, 1252, 580, null);

	}

	public void pressS() {
		judge("S");
		noteRouteSImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressD() {
		judge("D");
		noteRouteDImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRouteFImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressSpace() {
		judge("Space");
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressJ() {
		judge("J");
		noteRouteJImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRouteKImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseK() {
		noteRouteKImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	public void pressL() {
		judge("L");
		noteRouteLImage = new ImageIcon(Main.class.getResource("images/noteRoutePressed.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("images/noteRoute.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("images/keyPadBasic.png")).getImage();
	}

	@Override
	public void run() {
		dropNotes();

		while (!noteList.isEmpty()) {
			try {
				Thread.sleep(500);
				if (this.isInterrupted())
					return;
			} catch (InterruptedException e) {
				return;
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			return;
		}

		isGameEnded = true;
	}

	public void close() {
		gameMusic.close();
		this.interrupt();
	}

	public void dropNotes() {
		Random rb = new Random();
		ArrayList<Beat> beatList = new ArrayList<Beat>();
		int startTime = 2000; 
		int gap = 125;
		int totalDuration = 240000; 

		if (difficulty.equals("Easy"))
			gap = 200;
		else if (difficulty.equals("Hard"))
			gap = 120;

		String[] keys = { "S", "D", "F", "Space", "J", "K", "L" };

		if (titleName != null) {
			for (int i = 0; i * gap < totalDuration; i++) {
				int currentTime = startTime + (i * gap);

				double spawnChance = difficulty.equals("Easy") ? 0.18 : 0.39;

				if (rb.nextDouble() < spawnChance) {

					int randomIdx = rb.nextInt(keys.length);
					beatList.add(new Beat(currentTime, keys[randomIdx]));

					if (difficulty.equals("Hard") && rb.nextDouble() < 0.15) {
						int secondIdx;
						do {
							secondIdx = rb.nextInt(keys.length);
						} while (secondIdx == randomIdx);
						beatList.add(new Beat(currentTime, keys[secondIdx]));
					}
				}
			}
		}

		if (beatList.isEmpty()) {
			beatList.add(new Beat(startTime, "Space"));
		}

		Beat[] beats = beatList.toArray(new Beat[beatList.size()]);
		int i = 0;
		gameMusic.start();

		while (i < beats.length && !isInterrupted()) {
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	public void judge(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (input.equals(note.getNoteType())) {

				judgeEvent(note.judge());
				break;
			}
		}
	}

	class ImageEffectTimer extends Thread {
		private String type;

		public ImageEffectTimer(String type) {
			this.type = type;
		}

		@Override
		public void run() {
			try {

				Thread.sleep(500);

				if (type.equals("Flare")) {
					orangeFlareImage = null;
				} else if (type.equals("Judge")) {
					judgeImage = null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void judgeEvent(String judge) {
	    if (!judge.equals("None")) {
	        
	        try {
	            orangeFlareImage = new ImageIcon(Main.class.getResource("images/orangeFlare.png")).getImage();
	            new ImageEffectTimer("Flare").start();
	        } catch (Exception e) { }

	        if (judge.equals("Miss")) {
	            combo = 0;
	            countMiss++;
	            new Music("drum5.mp3", false).start();
	            judgeImage = new ImageIcon(Main.class.getResource("images/judgeMiss.png")).getImage();
	            new ImageEffectTimer("Judge").start();
	        } 
	        else { 
	            combo++;
	            
	            
	            if (combo > maxCombo) {
	                maxCombo = combo;
	            }

	            
	            comboFontSize = 50;

	           
	            if (combo == 50) {
	                showBonus = true;
	                bonusTimer = 100;
	                score += 1000;
	            }

	           
	            int baseScore = 0;
	            if (judge.equals("Perfect")) {
	                baseScore = 500;
	                countPerfect++;
	                new Music("drum4.mp3", false).start();
	            } else if (judge.equals("Great")) {
	                baseScore = 300;
	                countGreat++;
	                new Music("drum4.mp3", false).start(); // 또는 다른 효과음
	            } else if (judge.equals("Good")) {
	                baseScore = 200;
	                countGood++;
	                new Music("drum4.mp3", false).start();
	            } else if (judge.equals("Early") || judge.equals("Late")) {
	                baseScore = 100;
	            }

	            
	            score += baseScore + (combo * 10);

	            
	            try {
	                String imagePath = "images/judge" + judge + ".png";
	                judgeImage = new ImageIcon(Main.class.getResource(imagePath)).getImage();
	                new ImageEffectTimer("Judge").start();
	            } catch (Exception e) {
	                judgeImage = null;
	            }
	        }
	    }
	}
	
}
