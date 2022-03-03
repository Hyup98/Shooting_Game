package Network.IO;

import Network.DTO.ChatDTO;

<<<<<<< HEAD
import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
=======
import java.io.*;
>>>>>>> 2fb0f5247a8ae75830c43d5a8f333c913c8d4fd6
import java.net.Socket;

public class Sender extends Thread {
    private Socket socket = null;
    private ChatDTO chatDTO;
    private ObjectOutputStream writer;
    private String message;
    private boolean isSendMessage;
    private boolean isSendPosition;
    private boolean isPlayingGame;
    private PrintWriter pw;
    private int outputKeyEvent;
    private BufferedReader br;
<<<<<<< HEAD
    private KeyAdapter keyAdapter;

    public Sender(Socket socket, ChatDTO chatDTO, KeyAdapter keyAdapter) throws IOException {
        this.socket = socket;
        this.chatDTO = chatDTO;
        isSendMessage = false;
        isPlayingGame = false;
        this.keyAdapter = keyAdapter;
        System.out.println("sender생성완료");
    }


    /*
=======
    private int x,y;
>>>>>>> 2fb0f5247a8ae75830c43d5a8f333c913c8d4fd6
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
            pw = new PrintWriter(socket.getOutputStream(), true);
<<<<<<< HEAD
            //br = new BufferedReader(new InputStreamReader);
=======
            br = new BufferedReader(new InputStreamReader(System.in));
>>>>>>> 2fb0f5247a8ae75830c43d5a8f333c913c8d4fd6
            while (true) {
                if (isSendPosition) {
                    isSendPosition = false;
                    chatDTO.setPosition(x, y);
                    System.out.println("보낸 데이터 : ("+x+" : "+y+")");
                    writer.writeObject(new ChatDTO(chatDTO.retrunX(), chatDTO.returnY()));
                    writer.flush();
                    //outputKeyEvent = br.read();
                    //pw.println();
                } else yield();
                /*
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
                    if (isSendPosition) {
                        isSendPosition = false;
                        chatDTO.setPosition(x, y);
                        System.out.println("보낸 데이터 : ("+x+" : "+y+")");
                        writer.writeObject(new ChatDTO(chatDTO.retrunX(), chatDTO.returnY()));
                        writer.flush();
                        //outputKeyEvent = br.read();
                        //pw.println();
                    }
                }

                 */
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