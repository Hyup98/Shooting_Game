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

    //총알 사용법//
    /*
    1.발사 전 총알은 맵 밖에 좌표로 설정하여 둔다
    2.총알을 발사하면 해당 맵 밖의 총알을 가지고 사용한다.
    3.일정 범위 이상 총알이 날아가면 그 뒤로 총알이 다시 맵 밖의 특정 위치로 이동 -> 사라지게 표현
     */



    public Character() {
        powerItemCount = 0;
        speedItemCount = 0;
        healthItemCount = 0;
        bulletItemCount = 0;
        gunItemCount = 0;

        healthPoint = initialHealth;
        bulletCount = initialBulletAmount;
        power = initialPowerValue;
        speed = initialSpeed;
        gun = initialGun;


        //맵에 랜덤으로 생성
        x = 10;
        y = 10;
    }

    public void reload() {
        bulletCount = initialBulletAmount + bulletItemCount;
    }

    public void shoot() {
        if(bulletCount != 0) {
            bulletCount--;
            return;
        }
        reload();
        bulletCount--;
    }

    //동
    public void moveE() {
        x+=speed;
    }

    //서
    public void moveW() {
        x=speed;
    }

    //남
    public void moveS() {
        y+=speed;
    }

    //북
    public  void moveN() {
        y-=speed;
    }

    //북서
    public void moveNW() {
        x-=speed;
        y-=speed;
    }

    //남동
    public void moveSE() {
        x+=speed;
        y+=speed;
    }

    //북동
    public void moveNE() {
        x+=speed;
        y-=speed;
    }

    //남서
    public void moveSW() {
        x-=speed;
        y+=speed;
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

            case Gun:
                if(maxGunItemAmount > gunItemCount) {
                    gun++;
                }
        }
    }

    public void getdamage(int damage) {
        if(damage > healthPoint) {
            healthPoint -= damage;
        }
        healthPoint = 0;
    }




}
