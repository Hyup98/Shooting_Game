package Game;

/*
tcp/ip를 사용하여 연결하는 주체
게임 방을 만들거나 들어가고 캐릭터를 고르는 주체
 */
public class Player {
    private String name;
    private Language lag;
    private Character character;

    public Player(String name, Language lag) {
        this.name = name;
        this.lag = lag;
    }
}