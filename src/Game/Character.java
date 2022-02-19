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

    //총알 사용법//
    /*
    1.발사 전 총알은 맵 밖에 좌표로 설정하여 둔다
    2.총알을 발사하면 해당 맵 밖의 총알을 가지고 사용한다.
    3.일정 범위 이상 총알이 날아가면 그 뒤로 총알이 다시 맵 밖의 특정 위치로 이동 -> 사라지게 표현
     */
    private ArrayList<Bullet> bullets;

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

        bullets = new ArrayList<Bullet>(200);
        bulletIndex = 0;
        //맵에 랜덤으로 생성
        x = 10;
        y = 10;
        radian = 0;
    }

    public void reload() {
        bulletCount = initialBulletAmount + bulletItemCount;
    }

    //총 단계별 설정//
    /*
    1단계 : 총알 단일 발사
    2단계 : 2발 발사(샷건)
    3단계 : 3발 발사(샷건)
    4단계 : 4발 발사(샷건)
    5단계 : 투사체 속도 증가 -> 건틀링건 느낌으로
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
        //총알 풀에서 필요한 총알을 끌어다 사용한다.
        /*
        원형큐(우로보로스) 구조로 총알 인덱스를 사용
         */
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
