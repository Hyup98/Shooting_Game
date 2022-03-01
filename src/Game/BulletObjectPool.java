package Game;

import Game.Object.Bullet;

import java.util.ArrayList;

//설명//
/*
1.생성 인자로는 풀의 최고 크기를 전달
2.총알 요구 시
    2-1) 만일 리스트에 총알이 없으면 부족한 만큼 개체를 생성하여 반환
    2-2) 총알 반환 시 요구하는 총알의 성능을 인자로 받아 해당 요구사항에 맞게 업데이트 후 반환
    2-3) 총알 발사 시 유저의 좌표점에서 바라보는 각도로 사출
3. 총알 반납 시 총알 풀이 넘치면 해당 개체 지우기
4. 반납된 총알의 좌표는 맵의 밖 (-1, -1)로 고정시켜둔다.
 */
public class BulletObjectPool {
    private ArrayList<Bullet> bullets;
    private final int MAXPOOLSIZE = 100;

    public BulletObjectPool() {
        bullets = new ArrayList<>(MAXPOOLSIZE);
    }

    public ArrayList<Bullet> getBullets(int size, int power, double x, double y, int lifeTime) {
        ArrayList<Bullet> answer = new ArrayList<>();
        //부족하면 부족한 만큼 생성 후 반환
        if(bullets.size() < size) {
            for(int i = 0 ; i < bullets.size(); i++) {
                bullets.get(i).setPower(power);
                bullets.get(i).setPoint(x,y);
                bullets.get(i).setLifeTime(lifeTime);
                answer.add(bullets.get(i));
                answer.remove(0);
                i--;
            }

            for (int i = 0; i < size - bullets.size(); i++) {
                answer.add(new Bullet(x,y,power));
            }
            return answer;
        }

        for (int i = 0; i < size; i++) {
            bullets.get(i).setPower(power);
            bullets.get(i).setPoint(x,y);
            answer.add(bullets.get(i));
        }
        for (int i = 0; i < size; i++) {
            answer.remove(0);
        }
        return answer;
    }

    void returnBullet(Bullet bullet) {
        if(bullets.size() == MAXPOOLSIZE) {
            bullet.setPoint(-1, -1);
            return;
        }
        bullet.setPoint(-1,-1);
        bullets.add(bullet);
    }
}
