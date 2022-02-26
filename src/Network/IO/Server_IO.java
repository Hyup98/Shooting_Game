package Network.IO;

import Game.Language;
import Network.DTO.ChatDTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_IO {
    private ChatDTO chatDTO_reciver;
    private ChatDTO chatDTO_sender;

    public Server_IO(int port) {
        chatDTO_sender = new ChatDTO("server", Language.KOR);
        try {
            // 1. 소켓 생성(bind 생략 가능)
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();
            System.out.println("접속 수락");

            System.out.println("receiver 생성");
            Receiver receiver = new Receiver(socket, Language.KOR);

            receiver.start();
            System.out.println("sender생성");
            Sender sender = new Sender(socket, chatDTO_sender);
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}