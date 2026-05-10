package dynamic_beat_22;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javazoom.jl.player.Player;
import dynamic_beat_15.Main;

public class Music extends Thread {

	private Player player;
	private boolean isLoop;
	private BufferedInputStream bis;
	private String musicName; // 변수명 충돌 방지

	public Music(String musicName, boolean isLoop) {
		this.musicName = musicName;
		this.isLoop = isLoop;
	}

	public void close() {
		isLoop = false;
		if (player != null) {
			player.close();
		}
		this.interrupt();
	}

	public int getTime() {
		if (player == null) return 0;
		return player.getPosition();
	}

	@Override
	public void run() {
		try {
			do {
				// URI/File을 쓰지 않고 getResourceAsStream을 사용합니다.
				InputStream is = Main.class.getResourceAsStream("/music/" + musicName);
				
				if (is == null) {
					System.out.println("리소스를 찾을 수 없습니다: /music/" + musicName);
					return;
				}

				bis = new BufferedInputStream(is);
				player = new Player(bis);
				player.play();
				
			} while (isLoop);
		} catch (Exception e) {
			// 재생 중단 시 발생하는 예외는 무시하거나 로그를 남깁니다.
		}
	}
}