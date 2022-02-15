package Game;

public class Character {
    //ABOUT ITEM//
    private int magazieItemCount;
    private int powerItemCount;
    private int speedItemCount;
    private int healthItemCount;
    private int bulletItemCount;

    //ABOUT CHARACTER//
    private int healthPoint;
    private int bulletCount;
    private double x;
    private double y;

    public Character() {

    }

    public void reload() {

    }

    public void shoot() {

    }

    //동
    public void moveE() {

    }

    //서
    public void moveW() {

    }

    //남
    public void moveS() {

    }

    //북
    public  void moveN() {

    }

    //북서
    public void moveNW() {

    }

    //남동
    public void moveSE() {

    }

    //북동
    public void moveNE() {

    }

    //남서
    public void moveSW() {

    }

    void getItem(Item item) {
        switch (item) {
            case POWER:
                break;
            case SPEED:
                break;
            case BULLET:
                break;
            case HEALTH:
                break;
            case MAGAZINE:
                break;
        }
    }

    public void getdamage(int damage) {

    }




}
