package Game;

public class Game {
    PageState pageState;

    public Game() {

    }

    //게임 로그인 화면//
        /*
        1. 이름 & 포트번호및 ip주소 입력
         */

    //게임 홈 화면//
        /*
        1.게임 방 생성 또는 게임 방 목록을 클릭하여 게임 방에 입장
         */

    //게임 방 화면//
        /*
        1.캐릭터 선택
        2.준비완료
        3.모두 준비완료 되면 방장이 게임 시작 선택 가능
         */

    //게임 화면//

    //게임 방 화면
    public void start() {
        switch (pageState)
        {
            case MAIN:
                break;

            case LOGIN:
                break;

            case INGAME:
                break;

            case GAMEROOM:
                break;

            default:
                break;
        }
    }

    public void LogIn() {

    }

    public void Main() {

    }

    public void GameRoom() {

    }
}
