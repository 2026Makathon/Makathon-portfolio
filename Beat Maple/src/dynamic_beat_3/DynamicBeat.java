package dynamic_beat_3;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DynamicBeat extends JFrame{

	private Image ScreenImage;
	private Graphics ScreenGraphic;
	
	private Image introBackground;
	
	public DynamicBeat(){
		setTitle("Dynamic Beat");
		setSize(Main.SCREEEN_WIDTH, Main.SCREEEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		introBackground = new ImageIcon(Main.class.getResource("/images/introBackground.jpg")).getImage();
		
		Music introMusic = new Music("introMusic.mp3", true);
		introMusic.start();
	}
	
	public void paint(Graphics g) {
		ScreenImage = createImage(Main.SCREEEN_WIDTH, Main.SCREEEN_HEIGHT);
		ScreenGraphic = ScreenImage.getGraphics();
		screenDraw(ScreenGraphic);
		g.drawImage(ScreenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(introBackground, 0, 0, null);
		this.repaint();
	}
	
	
	
}
