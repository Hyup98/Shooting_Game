package Network.IO;

import Network.DTO.ChatDTO;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
                //객체를 하나 가지고 그걸 재활용하고 싶은데 그러면 아무리 기존의 개체를 바꿔도 바뀌기 전의 개체가 전달된다 이유 알아낼 것
                writer.writeObject(new ChatDTO(chatDTO.getName(), chatDTO.getLanguage(), chatDTO.getData()));
                writer.flush();
            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
}