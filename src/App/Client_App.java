package App;
import Chat.Chat;

public class Client_App {
    final boolean isServer = false;
    public static void main(String[] args) {
        new Client_App().start();
    }
    public void start(){
        Chat chat = new Chat(isServer);
    }
}
