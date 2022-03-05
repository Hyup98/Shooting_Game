package Network.IO;

import Game.Language;
import Network.DTO.ChatDTO;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server_IO {
    private ChatDTO chatDTO_sender;
    private ArrayList<>
    Receiver receiver;
    Sender sender;
    public Server_IO(int port) {
        chatDTO_sender = new ChatDTO("server", Language.ENG);
        try {
            // 1. 소켓 생성(bind 생략 가능)
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();
            System.out.println("접속 수락");

            System.out.println("receiver 생성");
            receiver = new Receiver(socket, Language.ENG);

            receiver.start();
            System.out.println("sender생성");
            sender = new Sender(socket, chatDTO_sender);
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void SetChat(JTextArea chaTextArea){
        receiver.SetChat(chaTextArea);
    }
    public void SetMessage(String message){
        sender.SetMessage(message);
    }
    public void playGame() {
        receiver.setPlayingGame(true);
        sender.setPlayingGame(true);
    }

    public void exitGame() {
        receiver.setPlayingGame(false);
        sender.setPlayingGame(false);
    }

    public int GetPositionX(){return receiver.getPlayerPositionX();}
    public int GetPositionY(){return receiver.getPlayerPositionY();}
    public void SetPosition(int x,int y){sender.SetPlayerPosition(x, y);}
}