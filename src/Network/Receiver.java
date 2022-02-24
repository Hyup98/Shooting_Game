package Network;

import Network.DTO.ChatDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket;
    private ChatDTO packet_chat;
    private ObjectInputStream reader;

    public Receiver(Socket socket, ChatDTO packet_chat) throws IOException {
        this.socket = socket;
        System.out.println("a");
        this.packet_chat = packet_chat;
        System.out.println("b");

        System.out.println("c");
    }

    @Override
    public void run() {

        try {
            reader = new ObjectInputStream(socket.getInputStream());
            while (true) {  // 스트림으로 반복문 제어
                packet_chat = (ChatDTO) reader.readObject();
                System.out.println(packet_chat.getData());
            }
        } catch (Exception e) {
            System.out.println("reciver에러");
            System.out.println(e.toString());
        }
    }

}

