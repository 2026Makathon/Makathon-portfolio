package dynamic_beat_11;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

public class Game extends Thread {

	private Image noteBasicImage = new ImageIcon(Main.class.getResource("/images/noteBasic.png")).getImage();

	private Image noteRouteImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();

	private Image judgementLineImage = new ImageIcon(Main.class.getResource("/images/judgementLine.png")).getImage();

	private Image gameInfoImage = new ImageIcon(Main.class.getResource("/images/gameInfo.png")).getImage();

	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("/images/noteRouteLine.png")).getImage();

	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();


	public void screenDraw(Graphics2D g) {
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

		g.drawImage(noteBasicImage, 528, 120, null);
		g.drawImage(noteBasicImage, 632, 580, null);
		g.drawImage(noteBasicImage, 736, 500, null);
		g.drawImage(noteBasicImage, 840, 340, null);
		g.drawImage(noteBasicImage, 940, 340, null);
		g.drawImage(noteBasicImage, 1044, 325, null);
		g.drawImage(noteBasicImage, 1148, 305, null);
		g.drawImage(noteBasicImage, 1252, 970, null);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("ElliniaWhenTheMorningComes", 20, 1065);
		g.drawString("Easy", 1820, 1060);

		g.setColor(Color.DARK_GRAY);
		g.drawString("S", 570, 1000);
		g.drawString("D", 672, 1000);
		g.drawString("F", 776, 1000);
		g.drawString("Space Bar", 870, 1000);
		g.drawString("J", 1084, 1000);
		g.drawString("K", 1188, 1000);
		g.drawString("L", 1295, 1000);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString("0000000", 840, 1065);
	}

	public void pressS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		
	}
	
	public void pressD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	public void pressF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	public void pressSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	public void pressJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	public void pressK() {
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseK() {
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	public void pressL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}
	
	public void releaseL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}
	
	
}
