package Network;

import Game.Language;
import Network.DTO.ChatDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket;
    private ChatDTO chatDTO;
    private ObjectInputStream reader;
    private Language lag;
    private Translator translator;

    public Receiver(Socket socket, Language lag) throws IOException {
        this.socket = socket;
        this.lag = lag;
        translator = new Translator(lag);
        System.out.println("receiver 생성완료");
    }

    protected void finalize() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            while (true) {  // 스트림으로 반복문 제어
                chatDTO = (ChatDTO) reader.readObject();
                System.out.println("받은 데이터 : " + chatDTO.toString());
                if(chatDTO.getLanguage() != lag) {
                    //번역
                    System.out.println(chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()) );
                }
                else {
                    System.out.println(chatDTO.getName() + ": " + chatDTO.getData());
                }
            }
        } catch (Exception e) {
            System.out.println("reciver에러");
            System.out.println(e.toString());
        }
    }

}

