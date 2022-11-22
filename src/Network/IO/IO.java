package Network.IO;

import Network.ChatDTO;

import javax.swing.*;
import java.net.Socket;

public class IO {
    private ChatDTO chatDTO_sender;
    Receiver receiver;
    Sender sender;

    public IO(String ip, int port, ChatDTO chatDTO) {
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
}



