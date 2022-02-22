package Game;


import java.util.ArrayList;

public class Character {
    //DEFAULT VALUE//
    private static int initialBulletAmount = 5;
    private static int initialPowerValue = 1;
    private static int initialSpeed = 1;
    private static int initialHealth = 10;
    private static int initialGun = 1;

    private static int maxPowerItemAmount = 5;
    private static int maxSpeedItemCount = 5;
    private static int maxHealthItemCount = 5;
    private static int maxBulletItemAmount = 5;
    private static int maxGunItemAmount = 5;

    //ABOUT ITEM//
    private int powerItemCount;
    private int speedItemCount;
    private int healthItemCount;
    private int bulletItemCount;
    private int gunItemCount;

    //ABOUT CHARACTER//
    private int healthPoint;
    private int bulletCount;
    private int speed;
    private int power;
    private int gun;
    private double x;
    private double y;
    private double radian;
    
    private int bulletIndex;

    //�Ѿ� ����//
    /*
    1.�߻� �� �Ѿ��� �� �ۿ� ��ǥ�� �����Ͽ� �д�
    2.�Ѿ��� �߻��ϸ� �ش� �� ���� �Ѿ��� ������ ����Ѵ�.
    3.���� ���� �̻� �Ѿ��� ���ư��� �� �ڷ� �Ѿ��� �ٽ� �� ���� Ư�� ��ġ�� �̵� -> ������� ǥ��
     */
    private ArrayList<Bullet> bullets;

    public Character() {
        //ABOUT ITEM//
        powerItemCount = 0;
        speedItemCount = 0;
        healthItemCount = 0;
        bulletItemCount = 0;
        gunItemCount = 0;

        //ABOUT STATS//
        healthPoint = initialHealth;
        bulletCount = initialBulletAmount;
        power = initialPowerValue;
        speed = initialSpeed;
        gun = initialGun;
        x = 10;
        y = 10;
        radian = 0;

        //ABOUT BULLTS//
        bullets = new ArrayList<Bullet>(200);
        bulletIndex = 0;

    }

    public void reload() {
        bulletCount = initialBulletAmount + bulletItemCount;
    }

    //�� �ܰ躰 ����//
    /*
    1�ܰ� : �Ѿ� ���� �߻�
    2�ܰ� : 2�� �߻�(����)
    3�ܰ� : 3�� �߻�(����)
    4�ܰ� : 4�� �߻�(����)
    5�ܰ� : ����ü �ӵ� ���� -> ��Ʋ���� ��������
     */
    public void shoot() {
        if(bulletCount != 0) {
            switch (gunItemCount) {
                case 0:
                    shoot(0);
                    break;
                case 1:
                    shoot(1);
                    break;
                case  2:
                    shoot(2);
                    break;
                case 3:
                    shoot(3);
                    break;
                case 4:
                    shoot(4);
                    break;
                case 5:
                    shoot(5);
                    break;
                default:
                    break;
            }
            bulletCount--;
            return;
        }
        reload();
        bulletCount--;
    }

    private void shoot(int type) {
        //�Ѿ� Ǯ���� �ʿ��� �Ѿ��� ����� ����Ѵ�.
        /*
        ����ť(��κ��ν�) ������ �Ѿ� �ε����� ���
         */
    }

    //��
    public void moveE() {
        x += speed;
        radian = Math.toRadians(0);
    }

    //�ϵ�
    public void moveNE() {
        x += speed;
        y -= speed;
        radian = Math.toRadians(45);
    }

    //��
    public void moveN() {
        y -= speed;
        radian = Math.toRadians(90);
    }

    //�ϼ�
    public void moveNW() {
        x -= speed;
        y -= speed;
        radian = Math.toRadians(135);
    }

    //��
    public void moveW() {
        x = speed;
        radian = Math.toRadians(180);
    }

    //����
    public void moveSW() {
        x -= speed;
        y += speed;
        radian = Math.toRadians(225);
    }

    //��
    public void moveS() {
        y += speed;
        radian = Math.toRadians(270);
    }

    //����
    public void moveSE() {
        x += speed;
        y += speed;
        radian = Math.toRadians(315);
    }


    void getItem(Item item) {
        switch (item) {
            case POWER:
                if(maxPowerItemAmount > powerItemCount) {
                    powerItemCount++;
                }
                break;

            case SPEED:
                if(maxSpeedItemCount > speedItemCount) {
                    speedItemCount++;
                }
                break;

            case BULLET:
                if(maxBulletItemAmount > bulletItemCount) {
                    bulletItemCount++;
                }
                break;

            case HEALTH:
                if(maxHealthItemCount > healthItemCount) {
                    healthItemCount++;
                }
                break;

            case GUN:
                if(maxGunItemAmount > gunItemCount) {
                    gun++;
                }
                break;
            default:
                break;
        }
    }

    public void getdamage(int damage) {
        if(damage > healthPoint) {
            healthPoint -= damage;
        }
        healthPoint = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}