package Network.IO;

import Chat.Language;
import Network.ChatDTO;
import Network.Translator;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket;
    private ChatDTO chatDTO;
    private ObjectInputStream readerChat;
    private BufferedReader readerGame;
    private Language lag;
    private Translator translator;
    private JTextArea chatTextArea;

    public Receiver(Socket socket, Language lag) throws IOException {
        this.socket = socket;
        this.lag = lag;
        translator = new Translator(lag);
        System.out.println("receiver 생성완료");
    }

    protected void finalize() {
        try {
            readerChat.close();
            readerGame.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            readerChat = new ObjectInputStream(socket.getInputStream());
            readerGame = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                chatDTO = (ChatDTO) readerChat.readObject();
                if (chatDTO.getLanguage() != lag) {
                    //번역
                    System.out.println(chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                    chatTextArea.setText(chatTextArea.getText() + "\n" + chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                } else {
                    System.out.println(chatDTO.getName() + ": " + chatDTO.getData());
                    chatTextArea.setText(chatTextArea.getText() + "\n" + chatDTO.getName() + ": " + chatDTO.getData());
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

