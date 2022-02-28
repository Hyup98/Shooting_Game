package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Handler;

class KeyInput extends KeyAdapter {
    Character character;

    int xMove=0,
        yMove=0;
    //방향키
    //재장전 - 쉬프트
    //쏘기 - 컨트롤
    KeyInput(Character character){
        this.character = character;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                System.out.println("a");
                xMove = -1;
                break;
            case 'd':
                System.out.println("d");
                xMove = 1;
                break;
            case 'w':
                System.out.println("w");
                yMove = 1;
                break;
            case 's':
                System.out.println("s");
                yMove = -1;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                xMove = 0;
                break;
            case 'd':
                xMove = 0;
                break;
            case 'w':
                yMove = 0;
                break;
            case 's':
                yMove = 0;
                break;
        }
    }
    public int getXMove(){
        return xMove;
    }
    public int getYMove(){
        return yMove;
    }
}
