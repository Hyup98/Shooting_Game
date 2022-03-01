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
                Thread.sleep(1);

                //배열 순서는 캐릭터 배열과 1대1 대응으로 구성한다.
                ArrayList<KeyEvent> input = new ArrayList<>();

                /*
                여기에 나의 키보드 입력받는 함수 호출 -> 쓰레드 형식으로 계속 입력을 받고 있어야 한다.
                여기는 서버에서 오는 다른 유저의 키 입력을 받는 함수 호출 -> 이것도 마찬가지로 계속 입력을 받는 상태 유지
                */
                if(keyInput.getIsShot()){
                    Character.shoot(character, bulletObjectPool, shootingBullets, character.getX(), character.getY(), 10,3);
                }
                //isGetItem();
                //isGotShot();
                //총에 맞았는지 계산
                /*
                아직 정확한 구상 x
                총알을 각각의 캐릭터가 관리하면 이게 효율적인가? 등등
                발사된 총알과 아닌 총알 구분 등도 마찬가지
                */

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
    //아이탬 확득 계산
    /*
    1.모든 유저를 순회하며 아이템과 같은 좌표에 있는지 계산
    2 아이탬을 획득하면 해당 유저의 아이탬 관련 수치 업데이트
    3 해당 아이이탬은 아이탬 리스트에서 삭제
     */
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
