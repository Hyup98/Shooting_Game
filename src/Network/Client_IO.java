package Network;

import java.net.Socket;

public class Client_IO {

    public Client_IO(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            Receiver receiver = new Receiver(socket);
            Sender sender = new Sender(socket);
            sender.start();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}



