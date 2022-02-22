package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_IO {

    public Server_IO(int port) {
        try {
            // 1. 소켓 생성(bind 생략 가능)
            ServerSocket server = new ServerSocket(port);

            // 2. 접속 수락
            Socket socket = server.accept();
            System.out.println("접속 수락");
            // 3. 받기 전용 스레드 실행
            Receiver receiver = new Receiver(socket);
            receiver.start();
            // 4. 전송 전용 스레드 실행
            Sender sender = new Sender(socket);
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}