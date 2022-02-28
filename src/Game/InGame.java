package Game;

import Game.Object.Bullet;
import Game.Object.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//게임 로직 흐름//
/*
1. 서버에서 게임아이템과 사람을 각가 다른 좌표에 랜덤하게 생성
2. 각 정보를 게임에 참여한 사람의 클라이언트에 전달하여 그 값으로 각각의 유저가 게임 생성
3. 게임이 총알 풀을 만들어 유저가 총을 쏘는 커맨드가 입력되면 총알 개체를 사용
     3.1)
4. 그 후 실시간으로 발생하는 데이터를 서버가 각각의 유저에게 전달하면 그걸 바탕으로 게임
 */
public class InGame extends JPanel implements Runnable{
    private boolean isGameOver;
    private ArrayList<Character> characters;
    private ArrayList<ItemObject> itemObjects;
    private BulletObjectPool bulletObjectPool;
    private ArrayList<Bullet> shootingBullets;

    Character character;
    KeyInput keyInput;
    /*
    게임 로직
    1. 서버에서 방에 참가한 순서대로 캐릭터 개체를 만들고 개채를 생성 후 배열에 저장하여 각 클라이언트에 반환
     */
    public InGame(JFrame jFrame){
        character=new Character();
        keyInput=new KeyInput(character);
        jFrame.addKeyListener(keyInput);
    }
    public InGame(ArrayList<Character> input, ArrayList<ItemObject> itemObjects) {
        isGameOver = false;
        //캐릭터 생성 및 초기화
        /*
        캐릭터 위치 랜덤하게 생성
        캐릭터 초기값으로 생성
         */
        this.characters = new ArrayList<Character>(input.size());
        for (int i = 0; i < input.size(); i++) {
            characters.add(i, characters.get(i));
        }

        //서버에서 받은 아이탬의 위치정보 저장
        //깊은복사 실시
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

    /*
    게임 로직 순서
    1. 내가 입력을 한다 (방향키, 장전키 발사 키)
    2.
     */

    //입력
    //상태업데이트(캐릭터 + 오브젝트)
    //업데이트 바탕으로 게임 업데이트

    public void run() {
        while (!isGameOver) {
            try {
                Thread.sleep(50);

                //배열 순서는 캐릭터 배열과 1대1 대응으로 구성한다.
                ArrayList<KeyEvent> input = new ArrayList<>();

                Move();
                /*
                여기에 나의 키보드 입력받는 함수 호출 -> 쓰레드 형식으로 계속 입력을 받고 있어야 한다.
                여기는 서버에서 오는 다른 유저의 키 입력을 받는 함수 호출 -> 이것도 마찬가지로 계속 입력을 받는 상태 유지
                */

                for (int i = 0; i < input.size(); i++) {

                }
                //isGetItem();
                //isGotShot();
                //총에 맞았는지 계산
                /*
                아직 정확한 구상 x
                총알을 각각의 캐릭터가 관리하면 이게 효율적인가? 등등
                발사된 총알과 아닌 총알 구분 등도 마찬가지
                */
                repaint();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    //        0   1   2
    //            ㅗ
    //      -1 <- 0 -> 1
    //            ㅜ
    //       -2   -1   0
    public void Move() {
        //System.out.println("Move");
        /*
        if (keyInput.getXMove() + keyInput.getYMove() != 1 && keyInput.getXMove() + keyInput.getYMove() != -1){ // 대각선 움직임

        }
        else{ // 평행 움직임
            //moveVec = new Vector3(xMove, 0, zMove);
        }


         */
        if(keyInput.getXMove() == 1 && keyInput.getYMove() == 1){
            System.out.println("NE");
            character.moveNE();
        }
        else if(keyInput.getXMove() == 1 && keyInput.getYMove() == -1){
            System.out.println("SE");
            character.moveSE();
        }
        else if(keyInput.getXMove() == -1 && keyInput.getYMove() == 1){
            System.out.println("NW");
            character.moveNW();
        }
        else if(keyInput.getXMove() == -1 && keyInput.getYMove() == -1){
            System.out.println("SW");
            character.moveSW();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0,0,WIDTH,HEIGHT);
        g.drawOval((int) character.getX(),(int) character.getY(),10,10);
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
                    bulletObjectPool.retunBullet(shootingBullets.get(j));
                    shootingBullets.remove(j);
                }
            }
        }
    }

}
