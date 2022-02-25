package Game;

import java.util.ArrayList;

//전체 맵을 상당히 넓게 잡는다//
/*
현제 화면에 보여줄 영역을 계산
 */
public class InGame {
    private ArrayList<Character> characters;
    private ArrayList<ItemObject> itemObjects;

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
            this.itemObjects.add(i, new ItemObject());
        }
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
    public void run() {
        //아직 구현x
        //서버로부터 받은 각의 입력을 해당 캐릭터에 적적히 업데이트
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).move();
        }

        //아이템을 먹었는지 계산
        for (int i = 0; i < characters.size(); i++) {
            isGetItem(characters.get(i));
        }

        //총에 맞았는지 계산
        /*
        아직 정확한 구상 x
        총알을 각각의 캐릭터가 관리하면 이게 효율적인가? 등등
        발사된 총알과 아닌 총알 구분 등도 마찬가지
         */
    }


    public void isGetItem(Character character) {

    }

}
