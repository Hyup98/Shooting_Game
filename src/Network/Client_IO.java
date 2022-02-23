package Network;

import java.net.Socket;

public class Client_IO {

    public Client_IO(String ip, int port, ChatDTO packet_chat) {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("3");
            Receiver receiver = new Receiver(socket, packet_chat);
            System.out.println("4");
            Sender sender = new Sender(socket, packet_chat);
            System.out.println("5");
            sender.start();
            System.out.println("6");
            receiver.start();
            System.out.println("7");
        } catch (Exception e) {
            System.out.println("client에러");
            System.out.println(e.toString());
        }
    }

}



