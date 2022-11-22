package Network.IO;

import Chat.Language;
import Network.ChatDTO;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_IO {
    private ChatDTO chatDTO_reciver;
    private ChatDTO chatDTO_sender;
    Receiver receiver;
    Sender sender;
    public Server_IO(int port, String name) {
        chatDTO_sender = new ChatDTO(name, Language.ENG);
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
    public void SetMessage(String message){sender.SetMessage(message); }

}