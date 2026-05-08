package dynamic_beat_21;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class SoundEffect {

    public static void play(String name) {
        new Thread(() -> {
            try {
                File file = new File(Main.class.getResource("/music/" + name).toURI());
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                Player player = new Player(bis);
                player.play(); // 끝까지 재생
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}