package App;

import Bgm.MusicPlayer;
import Game.Game;
import Network.IO.Server_IO;

public class Program_Server {
    public static void main(String[] args) {
        Game game = new Game(true);
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.start();
        game.start();
        //Server_IO server_io = new Server_IO(5801);
    }
}
