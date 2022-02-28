package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;
import java.util.logging.Handler;

/*
비트맵 약속
1. 0번은 w
2. 1번은 a
3. 2번은 s
3. 3번은 d
4. 4번은 컨트롤
5. 5번은 쉬프트
 */
class KeyInput extends KeyAdapter {
    Character character;
    BitSet input;

    KeyInput(Character character){
        this.character = character;
        input = new BitSet(4);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                System.out.println("w");
                input.set(0);
                dispatchKeyEvent();
                break;
            case 'a':
                System.out.println("a");
                input.set(1);
                dispatchKeyEvent();
                break;
            case 's':
                System.out.println("s");
                input.set(2);
                dispatchKeyEvent();
                break;
            case 'd':
                System.out.println("d");
                input.set(3);
                dispatchKeyEvent();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                input.flip(0);
                break;
            case 'a':
                input.flip(1);
                break;
            case 's':
                input.flip(2);
                break;
            case 'd':
                input.flip(3);
                break;
        }
    }

    private void dispatchKeyEvent() {
        if(input.get(0)) {
            character.moveN();
        }
        if(input.get(1)) {
            character.moveW();
        }
        if(input.get(2)) {
            character.moveS();
        }
        if(input.get(3)) {
            character.moveE();
        }
    }
}
