package dynamic_beat_22;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import dynamic_beat_15.Main;
import dynamic_beat_15.Music;

public class Game extends Thread {

	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();

	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("/images/noteRouteLine.png")).getImage();
	private Image judgementLineImage = new ImageIcon(Main.class.getResource("/images/judgementLine.png")).getImage();
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("/images/gameInfo.png")).getImage();

	private Image orangeFlareImage = null;
	private Image judgeImage = null;

	private Image keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	private Image keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();

	private String titleName;
	private String difficulty;
	private String musicTitle;
	private Music gameMusic;

	private boolean isGameEnded = false; // 게임 종료 여부
	private Image resultImage = new ImageIcon(Main.class.getResource("/images/resultBackground2.png")).getImage(); 
																													
	private int countPerfect = 0; 
	private int countGreat = 0;  
	private int countGood = 0;
	private int countMiss = 0;
																																																			// 필요)

	ArrayList<Note> noteList = new ArrayList<Note>();

	// [새로 추가할 변수들]
	private int score = 0; // 현재 점수
	private int combo = 0; // 현재 콤보
	private int maxCombo = 0; // 최대 콤보 기록

	// 콤보 애니메이션을 위한 변수
	private int comboFontSize = 40; // 콤보 텍스트의 초기/기본 폰트 크기

	// 구간 보너스 효과를 위한 변수
	private boolean showBonus = false;
	private int bonusTimer = 0; // 보너스 텍스트를 화면에 띄울 시간

	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
	}

	public void screenDraw(Graphics2D g) {
		
		if (isGameEnded) {
			g.drawImage(resultImage, 0, 0, null);
			
			g.setColor(Color.WHITE); // 텍스트 색상
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			// 제공하신 결과창 UI 디자인 레이아웃에 맞춰 좌표를 세밀하게 조정해 보세요!
			g.drawString(String.valueOf(countPerfect), 400, 350);
			g.drawString(String.valueOf(countGreat), 400, 350);
			g.drawString(String.valueOf(countGood), 400, 500); // Great나 Good 등을 원하는 칸에 배치
			g.drawString(String.valueOf(score), 400, 650);
			
			g.drawString(String.valueOf(countMiss), 500, 500);
			g.drawString(String.valueOf(maxCombo), 1000, 650);
			
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

			// 1. 화면 밖으로 나간 노트를 먼저 찾아 Miss 판정을 내립니다.
			if (note.getY() > 1040 && note.isProceeded()) {
				judgeEvent("Miss");
				score -= 50;
				note.close(); // 여기서 노트를 종료 상태로 바꿉니다.
			}

			// 2. 그 다음, 종료된 노트(!isProceeded)를 리스트에서 지웁니다.
			if (!note.isProceeded()) {
				noteList.remove(i);
				i--;
				continue;
			}

			if (note.getY() > 1040) {
				judgeImage = new ImageIcon(Main.class.getResource("/images/judgeMiss.png")).getImage();
				note.setProceeded(false);
				new ImageEffectTimer("Judge").start(); // 타이머 실행
				combo = 0; // 👈 노트를 놓쳤으므로 콤보 초기화!
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

		// 기존 하단 정보 (점수 출력되도록 수정)
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString(String.format("%07d", score), 840, 1065); // 0000000 형태로 점수 출력

		// ------------------ 새로 추가할 UI 렌더링 부분 ------------------

		// 1. 최대 콤보 (화면 좌측 상단이나 원하시는 곳에 배치)
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("MAX COMBO: " + maxCombo, 1500, 90);

		// 2. 현재 콤보 렌더링 및 애니메이션 처리
		if (combo > 0) {
			// 콤보 폰트 크기가 40보다 크면 서서히 줄어들게 하여 '통통 튀는(Pop)' 효과를 줍니다.
			if (comboFontSize > 40) {
				comboFontSize -= 2;
			}

			g.setColor(Color.ORANGE); // 콤보 색상
			g.setFont(new Font("Elephant", Font.BOLD, comboFontSize));
			// 폰트 크기가 변하므로 중앙 정렬을 위해 X좌표를 약간 보정해 주면 더 좋습니다.
			g.drawString("COMBO " + combo, 850 - (comboFontSize / 2), 200);
		}

		// 3. 구간 보너스 텍스트 효과
		if (showBonus) {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("50 COMBO BONUS! +1000", 650, 300);

			bonusTimer--;
			if (bonusTimer <= 0) {
				showBonus = false; // 타이머가 끝나면 보너스 텍스트 숨김
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
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressD() {
		judge("D");
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressSpace() {
		judge("Space");
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressJ() {
		judge("J");
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseK() {
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	public void pressL() {
		judge("L");
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3", false).start();
	}

	public void releaseL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}

	@Override
	public void run() {
	    dropNotes(); // 노트 생성을 시작하고 끝날 때까지 대기합니다.

	    // 수정: 모든 노트가 화면에서 사라질 때까지 대기
	    while (!noteList.isEmpty()) { 
	        try {
	            Thread.sleep(500);
	            if (this.isInterrupted()) return;
	        } catch (InterruptedException e) {
	            return;
	        }
	    }

	    // 모든 노트가 처리된 후 잠시 여운을 주고 결과창으로 전환
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        return;
	    }

	    isGameEnded = true; // 이제 screenDraw에서 resultImage를 그리게 됩니다!
	}

	public void close() {
		gameMusic.close();
		this.interrupt();
	}

	public void dropNotes() {
		Random rb = new Random();
		ArrayList<Beat> beatList = new ArrayList<Beat>();
		int startTime = 200;
		int gap = 125;
		int totalDuration = 240000;

		if (difficulty.equals("Easy")) {
			gap = 175;
		} else if (difficulty.equals("Hard")) {
			gap = 80;
		}

		String[] keys = { "S", "D", "F", "Space", "J", "K", "L" };

		if (titleName != null) {
			for (int i = 0; i * gap < totalDuration; i++) {
				int currentTime = startTime + (i * gap);

				if (difficulty.equals("Easy")) {
					if (i % 4 == 0) {

						if (rb.nextInt(10) < 7) {
							String randomKey = keys[rb.nextInt(keys.length)];
							beatList.add(new Beat(currentTime, randomKey));
						}
					}
					continue;
				}

				if (i * gap < 40000) {
					if (i % 4 == 0) {
						beatList.add(new Beat(currentTime, keys[i % 7]));
					}
				}

				else if (i * gap < 100000) {
					if (i % 2 == 0) {
						beatList.add(new Beat(currentTime, keys[(i * 3) % 7]));
					}

					if (i % 16 == 0) {
						beatList.add(new Beat(currentTime, "Space"));
					}
				}

				else if (i * gap < 150000) {
					beatList.add(new Beat(currentTime, keys[i % 3]));
					beatList.add(new Beat(currentTime, keys[4 + (i % 3)]));

					if (i % 8 == 0)
						beatList.add(new Beat(currentTime, "Space"));
				}

				else {
					if (i % 4 == 0) {
						beatList.add(new Beat(currentTime, (i % 8 == 0) ? "Space" : keys[i % 7]));
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
	        // 1. 이펙트 처리 (기존 동일)
	        try {
	            orangeFlareImage = new ImageIcon(Main.class.getResource("/images/orangeFlare.png")).getImage();
	            new ImageEffectTimer("Flare").start(); 
	        } catch (Exception e) { }
	        
	        // 2. 판정 처리
	        if (judge.equals("Miss")) {
	            combo = 0; // 콤보 초기화
	            countMiss++; // 결과창용 카운트 증가
	            
	            // [수정 포인트] Miss일 때 보여줄 전용 이미지를 로드합니다.
	            try {
	                // /images/judgeMiss.png 파일이 실제로 리소스 폴더에 있어야 합니다.
	                judgeImage = new ImageIcon(Main.class.getResource("/images/judgeMiss.png")).getImage();
	                new ImageEffectTimer("Judge").start(); // 일정 시간 후 사라지게 함[cite: 3]
	            } catch (Exception e) {
	                judgeImage = null;
	            }
	            
	        } else {
	            // Perfect, Great, Good 등 일반 판정 로직
	            combo++;
	            if (combo > maxCombo) maxCombo = combo;

	            int baseScore = 0;
	            if (judge.equals("Perfect")) { baseScore = 500; countPerfect++; }
	            else if (judge.equals("Great")) { baseScore = 300; countGreat++; }
	            else if (judge.equals("Good")) { baseScore = 200; countGood++; }
	            else if (judge.equals("Early") || judge.equals("Late")) baseScore = 100;

	            score += baseScore + (combo * 10);

	            // 일반 판정 이미지 로드
	            try {
	                String imagePath = "/images/judge" + judge + ".png"; 
	                judgeImage = new ImageIcon(Main.class.getResource(imagePath)).getImage();
	                new ImageEffectTimer("Judge").start(); 
	            } catch (Exception e) {
	                judgeImage = null;
	            }
	        }
	    }
	}
}