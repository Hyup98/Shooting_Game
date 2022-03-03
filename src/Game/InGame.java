package Game;

import Game.Object.Bullet;
import Game.Object.Item;
import Network.IO.Client_IO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InGame extends JPanel implements Runnable{
    //UI
    private ImageIcon gameImage[] = {
                new ImageIcon(new ImageIcon(("Image\\CharacterImage\\character_left.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\CharacterImage\\character_right.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\ItemImage\\gun.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\ItemImage\\power.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\ItemImage\\speed.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\ItemImage\\health.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
                new ImageIcon(new ImageIcon(("Image\\ItemImage\\bullet.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))
    };
    //
    private boolean isGameOver;
    private ArrayList<Character> characters;
    private ArrayList<ItemObject> itemObjects;
    private BulletObjectPool bulletObjectPool;
    private ArrayList<Bullet> shootingBullets;
    Character character1;
    Character character2;
    KeyInput keyInput;

    public InGame(JFrame jFrame, Client_IO client){
        client.gameMode();
        characters = new ArrayList<>();
        character1 = new Character();
        character2 = new Character();

        characters.add(character1);
        characters.add(character2);

        keyInput=new KeyInput(character1);
        jFrame.addKeyListener(keyInput);

        itemObjects = new ArrayList<>();
        bulletObjectPool = new BulletObjectPool();
        shootingBullets = new ArrayList<>();

        itemObjects.add(new ItemObject(100,100,Item.GUN));
        itemObjects.add(new ItemObject(200,200,Item.POWER));
        itemObjects.add(new ItemObject(300,300,Item.SPEED));
        itemObjects.add(new ItemObject(400,400,Item.HEALTH));
        itemObjects.add(new ItemObject(500,500,Item.BULLET));
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

    /*
    원래 로직
    1.게임이 시작된다
    2.서버가 해당 방에 있는 유저를 대상으로 캐릭터 배열, 아이템 배열을 만들어 각각 뿌려준다
    3.각 데이터를 받고 유저들이 공통된 맵을 만들고 게임을 시작한다.
    -----------------------------------------------------------------------------

     */
    public void run() {
        while (!isGameOver) {
            try {
                Thread.sleep(5);

                if(keyInput.getIsShot()){
                    Character.shoot(character1, bulletObjectPool, shootingBullets, character1.getX(), character1.getY(), 10,3);
                }

                for (int i = 0 ; i < shootingBullets.size(); i++) {
                    if(shootingBullets.get(i).getLifeTime() == 0) {
                        bulletObjectPool.returnBullet(shootingBullets.get(i));
                        shootingBullets.remove(i);
                        i--;
                    }
                }

                isGetItem();
                //isGotShot();
                repaint();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /*
    public void OnTriggerEnter(int tempCharacter, int tempObject,boolean index){ //templete 사용
        int characterX = (int)characters.get(tempCharacter).getX();
        int characterY = (int)characters.get(tempCharacter).getY();
        int objectX;
        int objectY;
        if(index){
            objectX = (int)shootingBullets.get(tempCharacter).getX();
            objectY = (int)shootingBullets.get(tempCharacter).getY();
        } else{
            objectX = (int)itemObjects.get(tempCharacter).getX();
            objectY = (int)itemObjects.get(tempCharacter).getY();
        }
        Rectangle r1 = new Rectangle(characterX,characterY,50,50);
        Rectangle r2 = new Rectangle(objectX,objectY,shootingBullets.get(tempObject).getSize(),shootingBullets.get(tempObject).getSize());
        if(r1.intersects(r2)){
            System.out.println("OnTriggerEnter");
        }
    }

     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,WIDTH,HEIGHT);

        for(var i = 0; i < characters.size(); i++){
            g.drawImage(gameImage[characters.get(i).getIsRight() ? 1 : 0].getImage(),(int) characters.get(i).getX(),(int) characters.get(i).getY(),this);
        }

        for(var i = 0; i < itemObjects.size(); i++){
            g.drawImage(gameImage[itemObjects.get(i).getItem().ordinal() + 2].getImage(),(int)itemObjects.get(i).getX(),(int)itemObjects.get(i).getY(),this);
        }

        for (var i = 0; i < shootingBullets.size(); i++){
            g.drawOval((int)shootingBullets.get(i).getX(),(int)shootingBullets.get(i).getY(),10,10);
            shootingBullets.get(i).move();
        }
        //System.out.println(itemObjects.size());
    }

    public void isGetItem() {
        for(int i = 0; i < characters.size(); i++) {
           //System.out.println("(" + characters.get(i).getX() +", " + characters.get(i).getY()+")");
            for(int j = 0; j < itemObjects.size(); j++) {
                if(characters.get(i).getX() == itemObjects.get(j).getX() && characters.get(i).getY() == itemObjects.get(j).getY()
                || characters.get(i).getX() + 5 == itemObjects.get(j).getX() && characters.get(i).getY() + 5 == itemObjects.get(j).getY()
                        || characters.get(i).getX() - 5 == itemObjects.get(j).getX() && characters.get(i).getY() - 5 == itemObjects.get(j).getY()) {
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
