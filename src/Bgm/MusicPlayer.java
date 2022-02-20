package Bgm;

import javazoom.jl.player.Player;

import java.io.FileInputStream;


public class MusicPlayer extends Thread {
    String musicPath;

    public MusicPlayer() {
        musicPath = "C:\\Users\\UserK\\Desktop\\Project_G\\src\\Bgm\\test_NCS.mp3";
    }

    public void setMusicPath(String path) {
        musicPath = path;
    }


    public void changeMusic(String path) {
        musicPath = path;
    }

    public void play() {
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
