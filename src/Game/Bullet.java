package Game;

public class Bullet {
    //�⺻ �Ѿ� ����
    private static int bulletSpeed = 1;

    //�����ۿ� ����
    private int type;
    private double x;
    private double y;
    private double power;
    private int moveCount;

    Bullet() {
        x = -1;
        y = -1;
        power = 1;
    }

    void move(double radian) {
        //�Ѿ� �����ֱ�//
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

    public void powerUp(int t) {
        power += t;
    }

    public double getPower() {
        return power;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}