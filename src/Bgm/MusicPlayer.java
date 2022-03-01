package Bgm;

import javazoom.jl.player.*;

import java.io.FileInputStream;


public class MusicPlayer extends Thread {
    private String musicPath;
    private Player playMp3;

    public MusicPlayer() {
        musicPath = "src\\Bgm\\Syn Cole - Time [NCS Release].mp3";
    }

    public MusicPlayer(String musicPath) {
        this.musicPath = musicPath;
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
            playMp3 = new Player(fis);
            playMp3.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
