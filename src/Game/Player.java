package Game;

/*
tcp/ip�� ����Ͽ� �����ϴ� ��ü
���� ���� ����ų� ���� ĳ���͸� ���� ��ü
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