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
    private String message;
    private boolean isSendMessage;
    public Sender(Socket socket, ChatDTO chatDTO) throws IOException {
        this.socket = socket;
        this.chatDTO = chatDTO;
        isSendMessage = false;
        System.out.println("sender생성완료");
    }

    protected void finalize() {
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
            while (true) {
                if (isSendMessage) {
                    isSendMessage=false;
                    chatDTO.setData(message);
                    System.out.println(chatDTO.toString());
                    //객체를 하나 가지고 그걸 재활용하고 싶은데 그러면 아무리 기존의 개체를 바꿔도 바뀌기 전의 개체가 전달된다 이유 알아낼 것
                    writer.writeObject(new ChatDTO(chatDTO.getName(), chatDTO.getLanguage(), chatDTO.getData()));
                    writer.flush();
                }
                else yield();
            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
    public void SetMessage(String message){
        this.message=message;
        isSendMessage=true;
    }
}