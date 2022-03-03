package Game;

import Game.Object.Bullet;
import Game.Object.Item;
import Network.IO.Client_IO;
import Network.IO.Server_IO;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InGame extends JPanel implements Runnable{
    private Server_IO server_io;
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

    Client_IO client_io;
    boolean isServer;

    /*
    0번 캐릭터는 항상 클라이언트 캐릭터
    1번 캐릭터는 항상 서버 캐릭터
     */

    /*
    입력이 발생하면
    자신의 인덱스에 해당하는 번호를 보낼 String의 첫번째에 삽입하고 바로 뒤에 입력된 정보를 합쳐서 보낸다
     */
    public InGame(JFrame jFrame, Client_IO client_io, Server_IO server_io, boolean isServer){
        if(isServer) {
            this.server_io = server_io;
            character2 = new Character();
            server_io.SetPosition((int)character1.getX(), (int)character1.getY());
            character1 = new Character(this.server_io.GetPositionX(),this.server_io.GetPositionY(),true);
            this.isServer = isServer;
            characters = new ArrayList<>();

            characters.add(character1);
            characters.add(character2);

            keyInput=new KeyInput(character1);
            jFrame.addKeyListener(keyInput);
        }
        else{
            this.client_io = client_io;
            character1 = new Character();
            this.client_io.SetPosition((int)character1.getX(), (int)character1.getY());
            character2 = new Character(this.client_io.GetPositionX(),this.client_io.GetPositionY(),true);
            this.isServer = isServer;
            characters = new ArrayList<>();

            characters.add(character1);
            characters.add(character2);

            keyInput=new KeyInput(character2);
            jFrame.addKeyListener(keyInput);
        }

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

    public void run() {
        while (!isGameOver) {
            try {
                Thread.sleep(10);

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

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,WIDTH,HEIGHT);


        for(var i = 0; i < characters.size(); i++){
            if(isServer){
                if (i==0) {
                    g.drawImage(gameImage[characters.get(i).getIsRight() ? 1 : 0].getImage(), (int) characters.get(i).getX(), (int) characters.get(i).getY(), this);
                    server_io.SetPosition((int) character1.getX(), (int) character1.getY());
                }
                else{
                    g.drawImage(gameImage[characters.get(i).getIsRight() ? 1: 0].getImage(), server_io.GetPositionX(),server_io.GetPositionY(),this);
                }
                //System.out.println("("+server_io.GetPositionX() + " , " + server_io.GetPositionY()+")");
                //character2 = new Character(this.server_io.GetPositionX(),this.server_io.GetPositionY(),true);
            }
            else{
                if (i==0) {
                    g.drawImage(gameImage[characters.get(i).getIsRight() ? 1 : 0].getImage(), (int) characters.get(i).getX(), (int) characters.get(i).getY(), this);
                    client_io.SetPosition((int) character1.getX(), (int) character1.getY());
                }
                else{
                    g.drawImage(gameImage[characters.get(i).getIsRight() ? 1: 0].getImage(), client_io.GetPositionX(),client_io.GetPositionY(),this);
                }
                //System.out.println("("+client_io.GetPositionX() + " , " + client_io.GetPositionY()+")");
                //character2 = new Character(this.client_io.GetPositionX(),this.client_io.GetPositionY(),true);
            }

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
