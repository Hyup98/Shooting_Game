package Network.IO;

import Network.DTO.ChatDTO;


import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO chatDTO;
    private ObjectOutputStream writer;
    private String message;
    private boolean isSendMessage;
    private boolean isSendPosition;
    private boolean isPlayingGame;
    private PrintWriter pw;
    private int outputKeyEvent;
    private BufferedReader br;
    private int x,y;

    public Sender(Socket socket, ChatDTO chatDTO) throws IOException {
        this.socket = socket;
        this.chatDTO = chatDTO;
        isSendMessage = false;
        isPlayingGame = false;
        System.out.println("sender생성완료");
    }


    protected void finalize() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                if (isSendPosition) {
                    isSendPosition = false;
                    chatDTO.setPosition(x, y);
                    System.out.println("보낸 데이터 : ("+x+" : "+y+")");
                    writer.writeObject(new ChatDTO(chatDTO.retrunX(), chatDTO.returnY()));
                    writer.flush();
                } else yield();

            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
    public void SetPlayerPosition(int x,int y){
        this.x = x;
        this.y = y;
        isSendPosition=true;
    }
    public void SetMessage(String message){
        this.message=message;
        isSendMessage=true;
    }

    public void setPlayingGame(boolean isPlayingGame) {
        this.isPlayingGame = isPlayingGame;
    }
}