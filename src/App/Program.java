package App;

import Game.Game;
import Bgm.MusicPlayer;
public class

Program {
    public static void main(String[] args) {
        Game game = new Game();
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.start();
        game.start();
    }
}


