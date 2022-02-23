package Network;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket = null;
    private ChatDTO packet_chat;
    private ObjectInputStream reader=null;

    public Receiver(Socket socket, ChatDTO packet_chat) {
        this.socket = socket;
        this.packet_chat = packet_chat;
    }

    @Override
    public void run() {
        try {
            // 4. InputStream으로 보내온 메시지를 받는다.
            //InputStream is = socket.getInputStream(); // 핵심
            //BufferedInputStream bis = new BufferedInputStream(is);
            //InputStreamReader reader = new InputStreamReader(bis, "UTF-8");
            //char[] arr = new char[100];
            //BufferedInputStream bis = new BufferedInputStream(is);
            //ObjectInputStream in = new ObjectInputStream(bis);
            while (true) {  // 스트림으로 반복문 제어
                packet_chat = (ChatDTO) reader.readObject();
                System.out.println(packet_chat.getData());
                //System.out.println("reciver decoder 전");
                //ChatDTO.conductDeserializing(packet_chat, in);
                //System.out.println("reciver decoder 후");
            }
        } catch (Exception e) {
            System.out.println("reciver에러");
            System.out.println(e.toString());
        }
    }

}

