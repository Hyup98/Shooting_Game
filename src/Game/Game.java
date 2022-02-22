package Game;

import Bgm.MusicPlayer;
import Network.Client_IO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class Game extends JFrame{
	//ABOUT UI//
	private final int WIDTH = 1280,
					  HEIGHT = 720;
	//ABOUT GAME//
	Client_IO client;
    PageState pageState;
    Player player;
    String ip;
    int port;
    Container c;
    boolean isRoomSelect;

    public void ScreenSetting() {
    	setSize(WIDTH, HEIGHT);
    	setTitle("DEFAULT");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Game() {
    	ScreenSetting();
        player = null;
        pageState = PageState.LOGIN;
        port = -1;
        isRoomSelect = false;
    }

    /*
    /////////////////<게임 흐름>//////////////////

    //게임 로그인 화면//
    1. 이름 & 포트번호및 ip주소 입력

    //게임 홈 화면//
    1.게임 방 생성 또는 게임 방 목록을 클릭하여 게임 방에 입장

    //게임 방 화면//
    1.캐릭터 선택
    2.준비완료
    3.모두 준비완료 되면 방장이 게임 시작 선택 가능

    //게임 화면//

    //게임 방 화면

    /////////////////////////////////////////////////
     */

    public void start() {
    	while(true) {
			switch (pageState) {
				case LOGIN:
					LogIn();
					break;

				case MAIN:
					Main();
					break;

				case INGAME:
					InGame();
					break;

				case GAMEROOM:
					GameRoom();
					break;

				default:
					JOptionPane.showMessageDialog(null, "ERROR");
					break;
			}
		}
    }

    public void LogIn() {
    	System.out.println("LogIn()");
    	
    	getContentPane().removeAll();
    	c = getContentPane();
    	c.setLayout(new BorderLayout());
    	
    	JPanel inputPanel = new JPanel();
    	JPanel imagePanel = new JPanel();
    	inputPanel.setLayout(null);
    	inputPanel.setPreferredSize(new Dimension(360,720));
    	
    	JTextField [] inputTextField = new JTextField[3];
    	JButton startButton = new JButton("로그인");

    	for(var i = 0; i < 3; i++) {
    		inputTextField[i] = new JTextField();
    		inputTextField[i].setBounds(60, 180 + (i * 60), 240, 40);
    		inputPanel.add(inputTextField[i]);
    	}
    	
    	startButton.setBounds(210, 360, 90, 35);

    	startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//이름, ip, 포트번호 입력받기
				ip = inputTextField[0].getText();
				String name = inputTextField[1].getText();
				Language lag = Language.KOR;
				port = 8000;
				
				System.out.println("ip\t: "+ ip + "\nname\t: " + name + "\nlag\t: " + lag);

				client = new Client_IO(ip, port);
		        player = new Player(name, lag);
		        pageState = PageState.MAIN;
			}
		});
    	
    	JTextArea tempArea= new JTextArea(40,90);
    	inputPanel.add(startButton);
    	imagePanel.add(tempArea);
    	
    	c.add(inputPanel,BorderLayout.WEST);
    	c.add(imagePanel,BorderLayout.CENTER);
		setVisible(true);
    	while(true) {
			if (port != -1) {
				break;
			}
		}
		pageState = PageState.MAIN;
    }

    public void Main() {
        /*
        방 목록 띄우기
        방 만들기 or 방 입장
         */
        //서버에서 방 목록을 가져오는 함수 -> uuid로 방에 입장한다.

        //방 목록 볼 수 있는 함수 -> 네트워크 필요

        //방 만들기 선택 시 자동적으로 방에 입장

        //방 입장 시 방에 입장함수로 전환
    	
    	System.out.println("Main()");
    	
    	getContentPane().removeAll();
    	c = getContentPane();
    	c.setLayout(new BorderLayout());
    	
    	JPanel roomListPanel = new JPanel();
    	JPanel inputPanel = new JPanel();
    	inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER,110,30));
    	
    	JTextArea tempArea= new JTextArea(30,100);
    	JLabel blank1 = new JLabel();
    	JLabel blank2 = new JLabel();
    	JButton joinRoomButton = new JButton("방 참가");
    	JButton createRoomButton = new JButton("방 생성");
    	
    	joinRoomButton.setPreferredSize(new Dimension(120,60));
    	createRoomButton.setPreferredSize(new Dimension(120,60));
    	
    	joinRoomButton.addActionListener(new RoomConfig(0));
    	createRoomButton.addActionListener(new RoomConfig(1));
    	
    	roomListPanel.add(tempArea);
    	inputPanel.add(blank1);
    	inputPanel.add(blank2);
    	inputPanel.add(joinRoomButton);
    	inputPanel.add(createRoomButton);
    	
    	c.add(roomListPanel,BorderLayout.CENTER);
    	c.add(inputPanel,BorderLayout.SOUTH);

    	setVisible(true);
    	while(true) {
    		if(isRoomSelect) {
    			break;
			}
		}
        pageState = PageState.GAMEROOM;
    }

    public void GameRoom() {
        /*
        1.캐릭터 선택
        2.준비완료
        3.모두 준비완료 되면 방장이 게임 시작 선택 가능
         */
    	System.out.println("GameRoom()");
    	getContentPane().removeAll();
    	c = getContentPane();
    	
    	JPanel playeristPanel = new JPanel();
    	JPanel inputPanel = new JPanel();
    	inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,200,30));
    	
    	JTextArea tempArea= new JTextArea(30,100);
    	JButton readyButton = new JButton("준비완료 / 취소");
    	readyButton.setPreferredSize(new Dimension(120,60));
    	
    	playeristPanel.add(tempArea);
    	inputPanel.add(readyButton);
    	
    	c.add(playeristPanel,BorderLayout.CENTER);
    	c.add(inputPanel,BorderLayout.SOUTH);
    	
    	setVisible(true);
        pageState = PageState.INGAME;
    }

    public void InGame() {
    	//게임 종류 후 다시 게임 방으로 이동

        pageState = PageState.GAMEROOM;
    }
    
	private class RoomConfig implements ActionListener{
		int configIndex;
		RoomConfig(int configIndex){
			this.configIndex = configIndex;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(configIndex) {
				case 0:
					System.out.println(configIndex);
					GameRoom();//temp
					break;
				case 1:
					System.out.println(configIndex);
					GameRoom();//temp
					break;
				default:
					JOptionPane.showMessageDialog(null, "ERROR");
					break;
			}
		}
	}
}
