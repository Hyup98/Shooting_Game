package Game;

import Game.Object.Bullet;
import Game.Object.Item;

import java.util.ArrayList;

//게임 로직 흐름//
/*
1. 서버에서 게임아이템과 사람을 각가 다른 좌표에 랜덤하게 생성
2. 각 정보를 게임에 참여한 사람의 클라이언트에 전달하여 그 값으로 각각의 유저가 게임 생성
3. 게임이 총알 풀을 만들어 유저가 총을 쏘는 커맨드가 입력되면 총알 개체를 사용
     3.1)
4. 그 후 실시간으로 발생하는 데이터를 서버가 각각의 유저에게 전달하면 그걸 바탕으로 게임
 */
public class InGame {
    private ArrayList<Character> characters;
    private ArrayList<ItemObject> itemObjects;
    private BulletObjectPool bulletObjectPool;
    private ArrayList<Bullet> shootingBullets;

    //0번 인덱스는 항상 자신의 캐릭터가 저장되어있다.
    //매개변수로 캐릭터 생성할 수 있는 초기 값 전달
    public InGame(ArrayList<String> input, ArrayList<ItemObject> itemObjects) {
        //캐릭터 생성 및 초기화
        /*
        캐릭터 위치 랜덤하게 생성
        캐릭터 초기값으로 생성
         */
        this.characters = new ArrayList<Character>(input.size());
        for (int i = 0; i < input.size(); i++) {
            characters.add(i, new Character());
        }

        //서버에서 받은 아이탬의 위치정보 저장
        //깊은복사 실시
        this.itemObjects = new ArrayList<ItemObject>(itemObjects.size());
        for (int i = 0; i < itemObjects.size(); i++) {
            this.itemObjects.add(i, new ItemObject(itemObjects.get(i).getX(), itemObjects.get(i).getY(), itemObjects.get(i).getItem()));
        }

        //총알 오브젝트 풀
        bulletObjectPool = new BulletObjectPool();
        shootingBullets = new ArrayList<>();
    }

    //서버로 부터 받는 값
    /*
    1. 방향키
    2. 총 발사키
    3. 장전키
     */

    //나의 입력과 서버로부터 오는 값의 시간차이는 어떻게 할까 고민
    //크게 차이가나면 해결해야함
    //추후에 시험작동 해보고 결정
    //인풋 매개변수로 소캣으로부터 여러 유저의 입력정보를 받는다.
    public void run() {
        //아직 구현x
        //서버로부터 받은 각의 입력을 해당 캐릭터에 적적히 업데이트
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).move();
        }
        isGetItem();
        isGotShot();
        //총에 맞았는지 계산
        /*
        아직 정확한 구상 x
        총알을 각각의 캐릭터가 관리하면 이게 효율적인가? 등등
        발사된 총알과 아닌 총알 구분 등도 마찬가지
         */
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
