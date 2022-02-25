package Network;

import Network.DTO.ChatDTO;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO chatDTO;
    private ObjectOutputStream writer;
    private Scanner scanner;

    public Sender(Socket socket, ChatDTO chatDTO) throws IOException {
        this.socket = socket;
        this.chatDTO = chatDTO;
        System.out.println("sender생성완료");
    }

    protected void finalize() {
        scanner.close();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            scanner = new Scanner(System.in);
            while (true) {
                System.out.println("스캐너");
                String msg = scanner.nextLine();
                chatDTO.setData(msg);
                System.out.println(chatDTO.getData());
                System.out.println("버퍼입력");
                writer.writeObject(chatDTO);
                writer.flush();
                System.out.println("버퍼 비우기");
            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
}