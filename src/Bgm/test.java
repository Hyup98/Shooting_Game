package Bgm;
import com.sun.tools.javac.Main;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.Scanner;



public class test extends Thread {
    public static void main(String[] args) {
        try { FileInputStream fis = new FileInputStream("C:\\Users\\UserK\\Desktop\\Project_G\\src\\Bgm\\test_NCS.mp3");
            Player playMp3 = new Player(fis);
            playMp3.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }




}
