package Game;

import Game.Object.Bullet;
import Game.Object.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InGame extends JPanel implements Runnable{
    private boolean isGameOver;
    private ArrayList<Character> characters;
    private ArrayList<ItemObject> itemObjects;
    private BulletObjectPool bulletObjectPool;
    private ArrayList<Bullet> shootingBullets;
    Character character;
    KeyInput keyInput;

    public InGame(JFrame jFrame){
        character=new Character();
        keyInput=new KeyInput(character);
        jFrame.addKeyListener(keyInput);

        bulletObjectPool = new BulletObjectPool();
        shootingBullets = new ArrayList<>();
    }

    public InGame(ArrayList<Character> input, ArrayList<ItemObject> itemObjects) {
        isGameOver = false;

        //캐릭터 초기화
        this.characters = new ArrayList<Character>(input.size());
        for (int i = 0; i < input.size(); i++) {
            characters.add(i, characters.get(i));
        }

        //아이템 초기화
        this.itemObjects = new ArrayList<ItemObject>(itemObjects.size());
        for (int i = 0; i < itemObjects.size(); i++) {
            this.itemObjects.add(i, itemObjects.get(i));
        }

        //총알 오브젝트 풀
        bulletObjectPool = new BulletObjectPool();
        shootingBullets = new ArrayList<>();
    }

    //서버로 부터 받는 값
    /*
    1. 방향키
    2. 총 발사키 컨트롤
    3. 장전키 쉬프트
     */

    //나의 입력과 서버로부터 오는 값의 시간차이는 어떻게 할까 고민
    //크게 차이가나면 해결해야함
    //추후에 시험작동 해보고 결정
    //인풋 매개변수로 소캣으로부터 여러 유저의 입력정보를 받는다.


    //입력
    //상태업데이트(캐릭터 + 오브젝트)
    //업데이트 바탕으로 게임 업데이트

    public void run() {
        while (!isGameOver) {
            try {
                Thread.sleep(5);

                if(keyInput.getIsShot()){
                    Character.shoot(character, bulletObjectPool, shootingBullets, character.getX(), character.getY(), 10,3);
                }

                for (int i = 0 ; i < shootingBullets.size(); i++) {
                    if(shootingBullets.get(i).getLifeTime() == 0) {
                        bulletObjectPool.returnBullet(shootingBullets.get(i));
                        shootingBullets.remove(i);
                        i--;
                    }
                }
                repaint();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,WIDTH,HEIGHT);
        g.drawOval((int) character.getX(),(int) character.getY(),10,10);

        for (var i = 0; i < shootingBullets.size(); i++){
            g.drawOval((int)shootingBullets.get(i).getX(),(int)shootingBullets.get(i).getY(),10,10);
            shootingBullets.get(i).move();
        }
    }

    public void isGetItem() {
        for(int i = 0; i < characters.size(); i++) {
            for(int j = 0; j < itemObjects.size();j++) {
                if(characters.get(i).getX() == itemObjects.get(j).getX() && characters.get(i).getY() == itemObjects.get(j).getY()) {
                    //해당 유저 능력치 업데이트
                    switch (itemObjects.get(i).getItem()) {
                        case HEALTH:
                            characters.get(i).getItem(Item.HEALTH);
                            break;
                        case BULLET:
                            characters.get(i).getItem(Item.BULLET);
                            break;
                        case SPEED:
                            characters.get(i).getItem(Item.SPEED);
                            break;
                        case POWER:
                            characters.get(i).getItem(Item.POWER);
                            break;
                        case GUN:
                            characters.get(i).getItem(Item.GUN);
                            break;
                        default:
                            break;
                    }
                    itemObjects.remove(j);
                    j--;
                }
            }
        }
    }

    public void isGotShot() {
        for (int i = 0; i < characters.size(); i++) {
            //충돌확인 이후 총알 반납
            for (int j = 0; j < shootingBullets.size(); j++) {
                if (characters.get(i).getX() == shootingBullets.get(j).getX() &&
                        characters.get(i).getY() == shootingBullets.get(j).getY()) {
                    characters.get(i).getdamage(shootingBullets.get(j).getPower());
                    bulletObjectPool.returnBullet(shootingBullets.get(j));
                    shootingBullets.remove(j);
                    j--;
                }
            }
        }
    }

}
