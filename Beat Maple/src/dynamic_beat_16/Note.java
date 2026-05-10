package dynamic_beat_16;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

import dynamic_beat_15.Main; // 패키지 경로에 맞춰 수정 필요

public class Note extends Thread {
	
	// 노트의 기본 정보 상수
	public static final int NOTE_WIDTH = 100;
	public static final int SPACE_WIDTH = 200;
	public static final int NOTE_HEIGHT = 30;

	// 판정 범위 상수 (픽셀 단위)
	public static final int PERFECT_RANGE = 20;
	public static final int GREAT_RANGE = 40;
	public static final int GOOD_RANGE = 60;
	public static final int EARLY_LATE_RANGE = 80;

	// 판정선 Y 좌표
	public static final int JUDGEMENT_LINE_Y = 960; 

	private Image noteBasicImage = new ImageIcon(Main.class.getResource("/images/noteBasic.png")).getImage();
	private int x, y; // 초기 좌표는 생성자에서 계산
	private String noteType;
	private boolean proceeded = true;
	
	// --- Getter & Setter ---
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

	// --- 생성자 ---
	public Note(String noteType) {
		// 노트가 판정선에 정확히 도달하는 시간을 계산하여 초기 Y값 설정
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
	
	// --- 메서드 ---
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y, SPACE_WIDTH, NOTE_HEIGHT, null);
		} else {
			g.drawImage(noteBasicImage, x, y, NOTE_WIDTH, NOTE_HEIGHT, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		// 판정선을 완전히 지나치면 Miss 처리 (Game 클래스에서도 처리하지만 여기서 안전하게 종료)
		if(y > 1040) {
			close();
		}
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
					// 판정되었거나 화면을 벗어나면 스레드 종료
					break; 
				}
			}
		} catch(Exception e) {
			System.err.println("Note 스레드 에러: " + e.getMessage());
		}
	}
	
	// 판정 로직
	public String judge() {
		// 현재 노트의 중심점 Y 좌표를 계산
		int noteCenterY = y + NOTE_HEIGHT / 2;
		
		// 판정선과의 거리를 계산 (절대값)
		int distance = Math.abs(noteCenterY - JUDGEMENT_LINE_Y);

		if (distance <= PERFECT_RANGE) {
			setProceeded(false);
			return "Perfect";
		} else if (distance <= GREAT_RANGE) {
			setProceeded(false);
			return "Great";
		} else if (distance <= GOOD_RANGE) {
			setProceeded(false);
			return "Good";
		} else if (distance <= EARLY_LATE_RANGE) {
			setProceeded(false);
			return (noteCenterY < JUDGEMENT_LINE_Y) ? "Early" : "Late";
		}

		return "None";
	}
}