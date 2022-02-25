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
                String msg = scanner.nextLine();
                chatDTO.setData(msg);
                System.out.println(chatDTO.toString());
                writer.writeObject(new ChatDTO(chatDTO.getName(), chatDTO.getLanguage(), chatDTO.getData()));
                writer.flush();
            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
}