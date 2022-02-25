package Network.DTO;

import Game.Language;

import java.io.*;

public class ChatDTO implements Serializable {
    String name;
    Language language;
    String data;

    public ChatDTO(String name, Language lag) {
        this.name = name;
        language = lag;
        data = "초기화 전";
    }

    public ChatDTO() {
        name = null;
        language = null;
        data = null;
    }

    public static void conductSerializing(ChatDTO packet_chat, OutputStream os) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(packet_chat);
            System.out.println("직렬화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void conductDeserializing(ChatDTO packet_chat, ObjectInputStream in) {
        try {
            //BufferedInputStream bis = new BufferedInputStream(is);
            //ObjectInputStream in = new ObjectInputStream(bis);
            packet_chat = (ChatDTO)in.readObject();
            System.out.println(packet_chat.toString());
            System.out.println("몰라레후");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public Language getLanguage() {
        return language;
    }

    public String toString() {
        String answer ="[" + name + " : " + data + "]";
        return answer;
    }


}
