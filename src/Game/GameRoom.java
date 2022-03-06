package Game;

import Network.IO.Client_IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameRoom {
    private String uuid;
    private String name;
    private final int maxNumberOfPeople = 5;
    private List clientList;
    private Client_IO master;

    public GameRoom(Client_IO user) {
        master = user;
        this.name = name;
        uuid = UUID.randomUUID().toString();
        clientList = new ArrayList();
    }

    public void endterClient(Client_IO client) {
        clientList.add(client);

    }

    public String getUuid() {
        return uuid;
    }

    public int getUserSize() {
        return clientList.size();
    }

    public String getName() {
        return name;
    }
}
