package Network.IO;

import Game.Language;
import Network.DTO.ChatDTO;
import Network.Translator;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket;
    private ChatDTO chatDTO;
    private ObjectInputStream reader;
    private Language lag;
    private Translator translator;
<<<<<<< HEAD
    private ChatDTO inputData;

    public Receiver(Socket socket, Language lag, ChatDTO inputData) throws IOException {
        this.socket = socket;
        this.lag = lag;
        translator = new Translator(lag);
        this.inputData = inputData;
        System.out.println("receiver 생성완료");
    }

=======
    private JTextArea chatTextArea;
>>>>>>> d712d5cfbaf268e4b7b69b2ca2ef3b7e3c230253
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
            while (true) {
                chatDTO = (ChatDTO) reader.readObject();
                if (chatDTO.getLanguage() != lag) {
                    //번역
                    System.out.println(chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                    chatTextArea.setText(chatTextArea.getText() +"\n"+ chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                } else {
                    System.out.println(chatDTO.getName() + ": " + chatDTO.getData());
                    chatTextArea.setText(chatTextArea.getText() +"\n"+ chatDTO.getName() + ": " + chatDTO.getData());
                }

            }
        } catch (Exception e) {
            System.out.println("reciver에러");
            System.out.println(e.toString());
        }
    }
    public void SetChat(JTextArea chaTextArea){
        this.chatTextArea = chaTextArea;
    }
}

