package Game.Object;

import javax.swing.text.ElementIterator;

public class Bullet {
    //�⺻ �Ѿ� ����
    private static int bulletSpeed = 1;

    //�����ۿ� ����
    private double x;
    private double y;
    private int power;
    private int moveCount;
    private double direction;
    private int lifeTime;

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


    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public boolean tictoc() {
        if(lifeTime == 1) {
            return false;
        }
        lifeTime--;
        return true;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void move() {
        y += bulletSpeed * Math.sin(direction);
        x += bulletSpeed * Math.cos(direction);
        lifeTime--;
    }
}