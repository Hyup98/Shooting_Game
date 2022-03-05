package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
        Character character;

        int xMove,
            yMove;
        boolean isShot,
                isReload;
        //방향키
        //재장전 - 쉬프트
        //쏘기 - 컨트롤
        KeyInput(Character character){
            this.character = character;
            xMove=0;
            yMove=0;
            isShot=false;
            isReload=false;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    xMove = -1;
                    break;
                case KeyEvent.VK_D:
                    xMove = 1;
                    break;
                case KeyEvent.VK_W:
                    yMove = -1;
                    break;
                case KeyEvent.VK_S:
                    yMove = 1;
                    break;
                case KeyEvent.VK_SHIFT:
                    isShot = true;
                    break;
                case KeyEvent.VK_CONTROL:
                    isReload = true;
                    break;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                    xMove = 0;
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    yMove = 0;
                    break;
                case KeyEvent.VK_SHIFT:
                    isShot = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    isReload = false;
                    break;
            }
        }
        public int getXMove(){
            return xMove;
        }
        public int getYMove(){
            return yMove;
        }
        public boolean getIsShot(){
            return isShot;
        }
        public boolean getIsReload(){
            return isReload;
        }
    }