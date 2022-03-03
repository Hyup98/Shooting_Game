package Network.IO;

import Game.PageState;
import Network.DTO.ChatDTO;

import javax.swing.*;
import java.net.Socket;

public class Client_IO {
    private ChatDTO chatDTO_sender;
    Receiver receiver;
    Sender sender;
    PageState state;
    public Client_IO(){

    }
    public Client_IO(String ip, int port, ChatDTO chatDTO) {
        state = PageState.GAMEROOM;
        chatDTO_sender = chatDTO;
        try {
            Socket socket = new Socket(ip, port);

            System.out.println("receiver 생성");
            receiver = new Receiver(socket, chatDTO.getLanguage());
            System.out.println("sender생성");
            sender = new Sender(socket, chatDTO_sender);

            sender.start();
            receiver.start();
        } catch (Exception e) {
            System.out.println("client에러");
            System.out.println(e.toString());
        }
    }

    public void SetChat(JTextArea chaTextArea){
        receiver.SetChat(chaTextArea);
    }

    public void SetMessage(String message){
        sender.SetMessage(message);
    }

    public void gameMode() {
        receiver.setPlayingGame(true);
        sender.setPlayingGame(true);
    }

    public void chatMode() {
        receiver.setPlayingGame(false);
        sender.setPlayingGame(false);
    }

    public int GetPositionX() { return receiver.getPlayerPositionX(); }
    public int GetPositionY() { return receiver.getPlayerPositionY(); }
    public void SetPosition(int x,int y) { sender.SetPlayerPosition(x, y); }
}



