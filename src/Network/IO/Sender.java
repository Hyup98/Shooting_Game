package Network.IO;

import Network.ChatDTO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO chatDTO;
    private ObjectOutputStream writer;
    private String message;
    private boolean isSendMessage;
    private int x,y;
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
                    isSendMessage = false;
                    chatDTO.setData(message);
                    System.out.println(chatDTO.toString());
                    writer.writeObject(new ChatDTO(chatDTO.getName(), chatDTO.getLanguage(), chatDTO.getData()));
                    writer.flush();
                } else Thread.sleep(1);
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