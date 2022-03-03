package Network.IO;

import Game.Language;
import Network.DTO.ChatDTO;
import Network.Translator;

import javax.swing.*;
import java.awt.event.KeyEvent;
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
    private boolean isPlayingGame;
    private int inputKeyEvent;

    private int x,y;
    public Receiver(Socket socket, Language lag) throws IOException {
        this.socket = socket;
        this.lag = lag;
        translator = new Translator(lag);
        isPlayingGame = false;
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
                //채팅 중일때
                if(!this.isPlayingGame) {
                    chatDTO = (ChatDTO) readerChat.readObject(); //isPlayingGame이 true 되도 여기서 시간이 걸리는 것 같음
                    if(!this.isPlayingGame) {                    //윗문장 수행하는동안 플레이어는 InGame임
                        if (chatDTO.getLanguage() != lag) {      //InGame에서 lag을 받으려고해서 reciver에러 나옴
                            //번역
                            System.out.println(chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                            chatTextArea.setText(chatTextArea.getText() + "\n" + chatDTO.getName() + ": " + translator.translate(chatDTO.getData(), chatDTO.getLanguage()));
                        } else {
                            System.out.println(chatDTO.getName() + ": " + chatDTO.getData());
                            chatTextArea.setText(chatTextArea.getText() + "\n" + chatDTO.getName() + ": " + chatDTO.getData());
                        }
                    }
                }
                else {
                    //inputKeyEvent = readerGame.read();
                    //System.out.println(inputKeyEvent);
                    chatDTO = (ChatDTO) readerChat.readObject();
                    //System.out.println("받은 데이터 : ("+chatDTO.retrunX()+" : "+chatDTO.returnY()+")");
                    this.x = chatDTO.retrunX();
                    this.y = chatDTO.returnY();
                }

            }
        } catch (Exception e) {
            System.out.println("reciver에러");
            System.out.println(e.toString());
        }
    }

    public int getPlayerPositionX(){
        return x;
    }
    public int getPlayerPositionY(){
        return y;
    }
    public void SetChat(JTextArea chaTextArea){
        this.chatTextArea = chaTextArea;
    }
    public void setPlayingGame(boolean isPlayingGame) {
        this.isPlayingGame = isPlayingGame;
    }
}

