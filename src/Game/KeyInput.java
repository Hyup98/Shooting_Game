package Game;

import Game.Object.Bullet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.BitSet;
/*
비트맵 약속
1. 0번은 ws
2. 1번은 a
3. 2번은 s
3. 3번은 d
4. 4번은 컨트롤
5. 5번은 쉬프트
 */
class KeyInput extends KeyAdapter {
    Character character;
    BitSet input;
    boolean isChanged;
    boolean isShot;

    public KeyInput(Character character){
        this.character = character;
        input = new BitSet(6);
        isChanged = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                input.set(0);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_LEFT:
                input.set(1);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_DOWN:
                input.set(2);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_RIGHT:
                input.set(3);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_SHIFT:
                input.set(4);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_CONTROL:
                input.set(5);
                dispatchKeyEvent();
                break;
        }
    }

    public void keyPressed(int e) {
        switch (e) {
            case KeyEvent.VK_UP:
                input.set(0);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_LEFT:
                input.set(1);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_DOWN:
                input.set(2);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_RIGHT:
                input.set(3);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_SHIFT:
                input.set(4);
                dispatchKeyEvent();
                break;

            case KeyEvent.VK_CONTROL:
                input.set(5);
                dispatchKeyEvent();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                input.flip(0);
                break;

            case KeyEvent.VK_LEFT:
                input.flip(1);
                break;

            case KeyEvent.VK_DOWN:
                input.flip(2);
                break;

            case KeyEvent.	VK_RIGHT:
                input.flip(3);
                break;

            case KeyEvent.VK_SHIFT:
                input.flip(4);
                break;

            case KeyEvent.VK_CONTROL:
                isChanged = true;
                input.flip(5);
                break;
        }
    }

    public void keyReleased(int e) {
        switch (e) {
            case KeyEvent.VK_UP:
                input.flip(0);
                break;

            case KeyEvent.VK_LEFT:
                input.flip(1);
                break;

            case KeyEvent.VK_DOWN:
                input.flip(2);
                break;

            case KeyEvent.	VK_RIGHT:
                input.flip(3);
                break;

            case KeyEvent.VK_SHIFT:
                input.flip(4);
                break;

            case KeyEvent.VK_CONTROL:
                isChanged = true;
                input.flip(5);
                break;
        }
    }

    private void dispatchKeyEvent() {
        if (input.get(0) == true && input.get(1) == true) {
            character.moveNW();
        }

        else if (input.get(1) == true && input.get(2) == true) {
            character.moveSW();
        }

        else if (input.get(2) == true && input.get(3) == true) {
            character.moveSE();
        }

        else if (input.get(3) == true && input.get(0) == true) {
            character.moveNE();
        }

        else if (input.get(1) == true) {
            character.moveW();
        }

        else if (input.get(2) == true) {
            character.moveS();
        }

        else if (input.get(3) == true) {
            character.moveE();
        }

        else if(input.get(0)) {
            character.moveN();
        }

        if(input.get(4)) {
            character.reload();
        }

        if(input.get(5)) {
            isShot = true;
        }
    }

    public boolean getIsShot(){
        boolean tem = isChanged;
        isChanged = false;
        return tem;

    }
}
