package Network.IO;

import Network.DTO.ChatDTO;

import java.net.Socket;

public class Client_IO {
    private ChatDTO chatDTO_sender;

    public Client_IO(String ip, int port, ChatDTO chatDTO,ChatDTO inputData) {
        chatDTO_sender = chatDTO;
        try {
            Socket socket = new Socket(ip, port);

            System.out.println("receiver 생성");
            Receiver receiver = new Receiver(socket, chatDTO.getLanguage(),inputData);
            System.out.println("sender생성");
            Sender sender = new Sender(socket, chatDTO_sender);

            sender.start();
            receiver.start();
        } catch (Exception e) {
            System.out.println("client에러");
            System.out.println(e.toString());
        }
    }
}



