package Bgm;

import javazoom.jl.player.Player;

import java.io.FileInputStream;


public class test extends Thread {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\UserK\\Desktop\\Project_G\\src\\Bgm\\test_NCS.mp3");
            Player playMp3 = new Player(fis);
            playMp3.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }




}
