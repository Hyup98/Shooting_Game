package Bgm;

import javazoom.jl.player.*;

import java.io.FileInputStream;


public class MusicPlayer extends Thread {
    String musicPath;

    public MusicPlayer() {
        musicPath = "src\\Bgm\\test_NCS.mp3";
    }

    public void setMusicPath(String path) {
        musicPath = path;
    }


    public void changeMusic(String path) {
        musicPath = path;
    }

    public void run() {
        try {
            FileInputStream fis = new FileInputStream(musicPath);
            Player playMp3 = new Player(fis);
            playMp3.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stopMusic() {

    }
}
