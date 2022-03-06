package Network.IO;

import Game.GameRoom;

import java.util.*;

public class GameRoomManager {

    private Map<String, GameRoom> gameRooms;
    private static GameRoomManager gameRoomManager = null;

    private GameRoomManager() {
        gameRooms = new HashMap<>();
    }

    public static GameRoomManager getInstance() {
        if (gameRoomManager == null) {
            gameRoomManager = new GameRoomManager();
            return gameRoomManager;
        } else {
            return gameRoomManager;
        }
    }

    public Map<String, String> getRoomData() {
        String tem; //= Integer.toString()
        Iterator<String> keys = gameRooms.keySet().iterator();
        Map<String, String> answer = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            tem = Integer.toString(gameRooms.get(key).getUserSize());
            tem += gameRooms.get(key).getName();
            answer.put(key, tem);
        }
        return answer;
    }

    public boolean enterTheRoom(String UUID, Client_IO user) {
        GameRoom tem = gameRooms.get(UUID);
        if (tem.getUserSize() < 5) {
            tem.endterClient(user);
            return true;
        } else {
            return false;
        }
    }

    public void creatRoom(Client_IO user) {
        gameRooms.put(UUID.randomUUID().toString(), new GameRoom(user));
    }

}
