package Network;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
    private Socket socket = null;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 입력값을 받는 Scanner
            Scanner scan = new Scanner(System.in);
            // OutputStream으로 보내온 메시지를 전송한다.
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");

            while(os != null) {
                String msg = scan.nextLine();
                writer.write(msg);
                writer.flush();

            }

            scan.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}