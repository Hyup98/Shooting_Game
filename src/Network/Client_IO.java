package Network;

import java.net.Socket;

public class Client_IO {

    public Client_IO(String ip, int port, Packet_Chat packet_chat) {
        try {
            Socket socket = new Socket(ip, port);
            Receiver receiver = new Receiver(socket, packet_chat);
            Sender sender = new Sender(socket, packet_chat);
            sender.start();

        } catch (Exception e) {
            System.out.println("client에러");
            System.out.println(e.toString());
        }
    }

}



