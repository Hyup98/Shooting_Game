package Game.Object;

public class Bullet {
    //기본 총알 설정
    private static int bulletSpeed = 1;

    //아이템에 따라서
    private double x;
    private double y;
    private int power;
    private int moveCount;

    public Bullet() {
        x = -1;
        y = -1;
        power = 1;
    }
    public Bullet(double x, double y, int power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    void move(double radian) {
        //총알 생명주기//
        if (moveCount == 5) {
            x = -1;
            y = -1;
            moveCount = 0;
            return;
        }

        x += Math.sin(radian) * bulletSpeed;
        y -= Math.cos(radian) * bulletSpeed;
        moveCount++;
    }

    public void setPower(int t) {
        power += t;
    }
    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getPower() {
        return power;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}