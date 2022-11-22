package App;

import Chat.Chat;

public class Server_App {
    final boolean isServer = true;
    public static void main(String[] args) {
        new Server_App().start();
    }
    public void start(){
        Chat chat = new Chat(isServer);
    }
}
