package Game;

import Game.Object.Bullet;
import Network.IO.Client_IO;
import Network.IO.Server_IO;

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
    String isServer;
    private Server_IO server_io;
    Client_IO client_io;
    String outputData;

    public KeyInput(Character character){
        this.character = character;
        input = new BitSet(6);
        isChanged = false;
    }

    public KeyInput(Character character, Client_IO client_io, Server_IO server_io, String isServer ){
        this.character = character;
        input = new BitSet(6);
        isChanged = false;
        this.isServer = isServer;
        if(isServer == "0") {
            this.server_io = server_io;
            client_io = null;
        }
        else {
            this.client_io = client_io;
            server_io = null;
        }
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

    private void dispatchKeyEvent() {
        if (input.get(0) == true && input.get(1) == true) {
            character.moveNW();
            outputData = isServer;
            outputData += "A";
        }

        else if (input.get(1) == true && input.get(2) == true) {
            character.moveSW();
            outputData = isServer;
            outputData += "B";
        }

        else if (input.get(2) == true && input.get(3) == true) {
            character.moveSE();
            outputData = isServer;
            outputData += "D";
        }

        else if (input.get(3) == true && input.get(0) == true) {
            character.moveNE();
            outputData = isServer;
            outputData += "E";
        }

        else if (input.get(1) == true) {
            character.moveW();
            outputData = isServer;
            outputData += "F";

        }

        else if (input.get(2) == true) {
            character.moveS();
            outputData = isServer;
            outputData += "G";
        }

        else if (input.get(3) == true) {
            character.moveE();
            outputData = isServer;
            outputData += "H";
        }

        else if(input.get(0)) {
            character.moveN();
            outputData = isServer;
            outputData += "I";
        }

        if(input.get(4)) {
            character.reload();
            outputData = isServer;
            outputData += "J";
        }

        if(input.get(5)) {
            isShot = true;
            outputData = isServer;
            outputData += "K";
        }
        //여기서 위 정보를 우리 sender 버퍼에 입력한다.
    }

    public boolean getIsShot(){
        boolean tem = isChanged;
        isChanged = false;
        return tem;

    }
}
