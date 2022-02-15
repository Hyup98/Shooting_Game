package Game;
import java.util.HashMap;
import java.util.UUID;

public class GameRoom {
    private String uuid;
    private String name;
    private final int maxNumberOfPeople = 5;
    private HashMap<Player,Boolean> players;
    Player master;

    public GameRoom(Player player, String name) {
        players = new HashMap<>();
        master = player;
        this.name = name;
        uuid = UUID.randomUUID().toString();
        players.put(player, false);
    }

    public boolean enterRoom(Player player) {
        if(players.size() >= maxNumberOfPeople) {
            return false;
        }

        players.put(player, false);
        return true;
    }

    public boolean exitRoom(Player player) {
        if(player == master) {
            //방 파괴
            return true;
        }

        players.remove(player);
        return true;
    }

    public void pushRedyButton(Player player) {
        if(player == master) {
            //자신 제외 모든 인원이 준비완료가 되어 있으면 준비가능
            //준비시 바로 게임 시작
        }

        else {
            if (players.get(player) == true) {
                players.put(player, false);
                return;
            }
            players.put(player, true);
        }
    }


}
