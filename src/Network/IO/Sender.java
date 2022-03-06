package Network.IO;

import Network.DTO.ChatDTO;

import java.io.*;
import java.net.Socket;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO chatDTO;
    private ObjectOutputStream writer;
    private String message;
    private boolean isSendMessage;
    private boolean isSendPosition;
    private boolean isPlayingGame;
    private int x,y;
    public Sender(Socket socket, ChatDTO chatDTO) throws IOException {
        this.socket = socket;
        this.chatDTO = chatDTO;
        isSendMessage = false;
        isPlayingGame = false;
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
                if (!isPlayingGame) {
                    if (isSendMessage) {
                        isSendMessage = false;
                        chatDTO.setData(message);
                        System.out.println(chatDTO.toString());
                        //객체를 하나 가지고 그걸 재활용하고 싶은데 그러면 아무리 기존의 개체를 바꿔도 바뀌기 전의 개체가 전달된다 이유 알아낼 것
                        writer.writeObject(new ChatDTO(chatDTO.getName(), chatDTO.getLanguage(), chatDTO.getData()));
                        writer.flush();
                    } else yield();
                }
                else {
                    //System.out.println("isSendPosition");
                    if (isSendPosition) {
                        isSendPosition = false;
                        chatDTO.setPosition(x, y);
                        writer.writeObject(new ChatDTO(chatDTO.retrunX(), chatDTO.returnY()));
                        writer.flush();
                        //outputKeyEvent = br.read();
                        //pw.println();
                    }else yield();
                }
            }
        } catch (Exception e) {
            System.out.println("sender에러");
            System.out.println(e.toString());
        }
    }
    public void SetPlayerPosition(int x,int y){
        this.x = x;
        this.y = y;
        isSendPosition=true;
    }
    public void SetMessage(String message){
        this.message=message;
        isSendMessage=true;
    }

    public void setPlayingGame(boolean isPlayingGame) {
        this.isPlayingGame = isPlayingGame;
    }
}