package Network;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver extends Thread {
    private Socket socket = null;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 4. InputStream으로 보내온 메시지를 받는다.
            InputStream is = socket.getInputStream(); // 핵심
            BufferedInputStream bis = new BufferedInputStream(is);
            InputStreamReader reader = new InputStreamReader(bis, "UTF-8");
            char[] arr = new char[100];
            while (is != null) {  // 스트림으로 반복문 제어
                // 5. 출력
                reader.read(arr);
                // ㅁㅁㅁ는 \0이고 배열의 공백을 의미한다.
                String msg = new String(arr).replace('\0', ' ');
                System.out.println("[상대] " + msg);

                // 긴 문장 후 짧은 문장이 들어올 경우 이전 값과 섞여 들어온다.
                // 초기화
                arr = new char[100];
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}

