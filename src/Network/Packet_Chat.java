package Network;

import Game.Language;

import java.io.*;

public class Packet_Chat implements Serializable {
    String name;
    Language language;
    String data;

    public Packet_Chat(String name, Language lag) {
        this.name = name;
        language = lag;
        data = "";
    }

    public static void conductSerializing(Packet_Chat packet_chat, OutputStream os) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(packet_chat);
            System.out.println("직렬화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void conductDeserializing(Packet_Chat packet_chat, InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            ObjectInputStream in = new ObjectInputStream(bis);
            packet_chat = (Packet_Chat) in.readObject();
            System.out.println(packet_chat.toString());
        } catch (Exception e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String toString() {
        String answer = name + " : " + data + "\n";
        return answer;
    }


}
