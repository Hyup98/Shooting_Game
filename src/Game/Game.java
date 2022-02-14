package Game;

public class Game {
    PageState pageState;
    Player player;

    public Game() {
        player = null;
        pageState = PageState.GAMEROOM;
    }

    //              <게임 흐름>               //

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

    /////////////////////////////////////////////////
    public void start() {
        switch (pageState)
        {
            case LOGIN:
                break;

            case MAIN:
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
        String name = " ";
        String ip;
        //이름, ip, 포트번호 입력받기
        player = new Player(name);
    }

    public void Main() {
        /*
        방 목록 띄우기
        방 만들기 or 방 입장
         */

        //방 목록 볼 수 있는 함수 -> 네트워크 필요

        //방 만들기 선택 시 자동적으로 방에 입장

        //방 입장 시 방에 입장함수로 전환

        pageState = PageState.GAMEROOM;
    }

    public void GameRoom() {
        /*
        1.캐릭터 선택
        2.준비완료
        3.모두 준비완료 되면 방장이 게임 시작 선택 가능
         */

        pageState = PageState.INGAME;
    }

    public void InGame() {
        //게임 종류 후 다시 게임 방으로 이동

        pageState = PageState.GAMEROOM;
    }
}
