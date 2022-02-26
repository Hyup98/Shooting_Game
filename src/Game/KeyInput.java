package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Handler;

public class KeyInput extends KeyAdapter {
    Handler handler;

    KeyInput(Handler handler) {
        this.handler = handler;
    }

    //방향키
    //재장전 - 쉬프트
    //쏘기 - 컨트롤
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

    }
}
