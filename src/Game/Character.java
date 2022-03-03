package Game;

import Bgm.Audio;
import Bgm.MusicPlayer;
import Game.Object.Bullet;
import Game.Object.Item;

import java.util.ArrayList;

/*
1서버에서 현재 방에 있는 인원만큼->순서대로 캐릭터 초깃값을 준다
 */

public class Character {
    //DEFAULT VALUE//
    private static int initialBulletAmount = 10;
    private static int initialPowerValue = 1;
    private static int initialSpeed = 15;
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
    private Audio hittingSound;
    private Audio reloadSound;
    private Audio getItemSound;

    ///MusicPlayer bgm;

    //총알 사용법//
    /*
    1.발사 전 총알은 맵 밖에 좌표로 설정하여 둔다
    2.총알을 발사하면 해당 맵 밖의 총알을 가지고 사용한다.
    3.일정 범위 이상 총알이 날아가면 그 뒤로 총알이 다시 맵 밖의 특정 위치로 이동 -> 사라지게 표현
     */

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
        //bgm = new MusicPlayer("src\\Bgm\\Gun Fire.mp3");
        //bgm.start();
        hittingSound = new Audio("src\\Bgm\\Gun Fire.wav", false);
        getItemSound = new Audio("src\\Bgm\\Get_Item.wav", false);
        reloadSound = new Audio("src\\Bgm\\Shotgun Reload Old.wav", false);
    }
    //키 입력
    public void update() {

    }

    //재장전 시 바로 총알 완충이 아닌 시간이 흐르면서 하나씩 추가되는 형태로 구현
    public void reload() {
        bulletCount = initialBulletAmount + bulletItemCount;
        reloadSound.start();
    }
    //총 단계별 설정//
    /*
    총알은 현재 캐릭터가 바라보는 방향 기준 30도(+-15)각으로 쏜다.
    1단계 : 총알 단일 발사
    2단계 : 2발 발사(샷건)
    3단계 : 3발 발사(샷건)
    4단계 : 4발 발사(샷건)
    5단계 : 투사체 속도 증가 -> 건틀링건 느낌으로
     */
    public static void shoot(Character character, BulletObjectPool bulletObjectPool, ArrayList<Bullet> shootingBullets, double x, double y, int power, int lifeTime) {
        if(character.bulletCount != 0) {
            character.hittingSound.start();
            switch (character.gunItemCount) {
                case 0:
                    shoot(0, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                case 1:
                    shoot(1, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                case  2:
                    shoot(2, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                case 3:
                    shoot(3, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                case 4:
                    shoot(4, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                case 5:
                    shoot(5, bulletObjectPool, character.radian, shootingBullets, character.x, character.y, power, lifeTime);
                    break;
                default:
                    break;
            }
            character.bulletCount--;
            return;
        }
        character.reload();
    }

    private static void shoot(int type, BulletObjectPool bulletObjectPool, double radian, ArrayList<Bullet> shootingBullets, double x, double y, int power, int lifeTime) {
        ArrayList<Bullet> bullets = new ArrayList<>();
        if(type == 0) {
            bulletObjectPool.getBullets(1, power, x, y, lifeTime, bullets);
            bullets.get(0).setDirection(radian);
        }
        else if(type == 1) {
            bulletObjectPool.getBullets(2, power, x, y, lifeTime, bullets);
            bullets.get(0).setDirection(radian + Math.toRadians(5));
            bullets.get(1).setDirection(radian - Math.toRadians(5));

        }
        else if(type == 2) {
            bulletObjectPool.getBullets(3, power, x, y, lifeTime, bullets);
            bullets.get(0).setDirection(radian);
            bullets.get(1).setDirection(radian + Math.toRadians(5));
            bullets.get(2).setDirection(radian - Math.toRadians(5));

        }
        else if(type == 3) {
            bulletObjectPool.getBullets(4, power, x, y, lifeTime, bullets);
            bullets.get(0).setDirection(radian + Math.toRadians(2));
            bullets.get(1).setDirection(radian + Math.toRadians(7));
            bullets.get(2).setDirection(radian - Math.toRadians(2));
            bullets.get(3).setDirection(radian - Math.toRadians(7));

        }
        else {
            bulletObjectPool.getBullets(5, power, x, y, lifeTime, bullets);
            bullets.get(0).setDirection(radian);
            bullets.get(1).setDirection(radian + Math.toRadians(2));
            bullets.get(2).setDirection(radian + Math.toRadians(7));
            bullets.get(3).setDirection(radian - Math.toRadians(2));
            bullets.get(4).setDirection(radian - Math.toRadians(7));
        }
        for (int i =0; i<bullets.size(); i++) {
            shootingBullets.add(bullets.get(i));
        }
    }

    //동
    public void moveE() {
        x += speed;
        radian = Math.toRadians(0);
    }

    //북동
    public void moveNE() {
        x += speed;
        y -= speed;
        radian = Math.toRadians(315);
    }

    //북
    public void moveN() {
        y -= speed;
        radian = Math.toRadians(270);
    }

    //북서
    public void moveNW() {
        x -= speed;
        y -= speed;
        radian = Math.toRadians(225);
    }

    //서
    public void moveW() {
        x -= speed;
        radian = Math.toRadians(180);
    }

    //남서
    public void moveSW() {
        x -= speed;
        y += speed;
        radian = Math.toRadians(135);
    }

    //남
    public void moveS() {
        y += speed;
        radian = Math.toRadians(90);
    }

    //남동
    public void moveSE() {
        x += speed;
        y += speed;
        radian = Math.toRadians(45);
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
                    gunItemCount++;
                }
                break;
            default:
                break;
        }
        getItemSound.start();
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

    public void setDirection(double direction) {
        radian = Math.toRadians(direction);
    }
    public double getDirection(){
        return radian;
    }
}