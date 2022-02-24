package Network;

import Network.DTO.ChatDTO;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO packet_chat;
    private ObjectOutputStream writer;

    public Sender(Socket socket, ChatDTO packet_chat) throws IOException {
        this.socket = socket;
        this.packet_chat = packet_chat;
        writer = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            // 입력값을 받는 Scanner
            Scanner scan = new Scanner(System.in);
            // OutputStream으로 보내온 메시지를 전송한다.
            System.out.println("sender while 전");

            while (true) {
                String msg = scan.nextLine();
                packet_chat.sendData(msg);
                System.out.println("sender 패킷 생성 전");
                //ChatDTO.conductSerializing(packet_chat, os);
                //System.out.println("sender 패킷 생성 후");
                writer.writeObject(packet_chat);
                writer.flush();
                System.out.println("sender 버퍼 비운 후");
            }

        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }

}