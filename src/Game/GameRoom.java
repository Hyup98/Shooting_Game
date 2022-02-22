package Game;

import java.util.HashMap;
import java.util.UUID;

//구현 항목//
/*
1.캐릭터 선택
2.게임 준비
3.채팅
 */

//채팅//
/*
1.자신의 버퍼에 입력
2.서버에 보낸다
3.서버에서 같은 방에 있는 사람에게 전달
4.전달받은 메시지에 국적이 다르면 해당 나라의 언어로 변환 함수를 거친다
5.번역된 데이터를 채팅에 띄워준다.
 */

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

    //쓰레드로 게임 준비완료 구현//
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

    //쓰레드로 채팅 구현//
    public void chating() {
        while(true) {

        }
    }

}
