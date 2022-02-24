package Game;

import Network.IO.Client_IO;
import Network.DTO.ChatDTO;
import Network.DTO.GameDTO;
import Network.DTO.GameRoomDTO;

import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Scanner;
import java.util.Vector;

public class Game extends JFrame{

	//ABOUT UI//
	private final int WIDTH = 1280,
					  HEIGHT = 720;
	private Vector<String> item = new Vector<>();
	Container c;
    //ABOUT GAME//
    Client_IO client;
    PageState pageState;
    Player player;
    String ip;
    int port;
    ChatDTO packet_chat;
    GameDTO packet_game;
    GameRoomDTO packet_gameRoom;
    boolean isRoomSelect;

    public Game() {
        ScreenSetting();
        player = null;
        pageState = PageState.LOGIN;
        port = -1;
        isRoomSelect = false;
        Scanner scan = new Scanner(System.in);
    }

    public void ScreenSetting() {
        setSize(WIDTH, HEIGHT);
        setTitle("DEFAULT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		JComboBox<Language> languageComboBox = new JComboBox<>();
		languageComboBox.setModel(new DefaultComboBoxModel<>(Language.values()));
		languageComboBox.setBounds(60,360,240,40);

    	for(var i = 0; i < 3; i++) {
    		inputTextField[i] = new JTextField();
     		inputTextField[i].setBounds(60, 195 + (i * 55), 240, 40);
    		inputPanel.add(inputTextField[i]);
    	}
		inputTextField[0].setText("IP");
		inputTextField[1].setText("NAME");
		inputTextField[2].setText("PORT");
    	inputPanel.add((languageComboBox));
    	startButton.setBounds(210, 420, 90, 35);

    	startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                //이름, ip, 포트번호 입력받기
                ip = inputTextField[0].getText();
                String name = inputTextField[1].getText();
                Language lag = Language.KOR;
                port = 5801;

                //setting//
                packet_chat = new ChatDTO(name, lag);
                System.out.println("1");
                client = new Client_IO(ip, port, packet_chat);
                player = new Player(name, lag);
                //
				System.out.println("2");
                System.out.println("ip\t: " + ip + "\nname\t: " + name + "\nlag\t: " + lag);
                pageState = PageState.MAIN;
				System.out.println("3");
				Main();
            }
        });

        JTextArea tempArea = new JTextArea(40, 90);
        inputPanel.add(startButton);
        imagePanel.add(tempArea);

        c.add(inputPanel, BorderLayout.WEST);
        c.add(imagePanel, BorderLayout.CENTER);
        setVisible(true);



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
		roomListPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
    	inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER,110,30));

		item.add("test1");
		item.add("test2");
		JList roomList = new JList(item);
		roomList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomList.setPreferredSize(new Dimension(1100,500));
		roomList.addListSelectionListener(new RoomListSelect(roomList));

		JScrollPane roomListScroll = new JScrollPane(roomList);
		roomListScroll.setPreferredSize(new Dimension(300,300));

    	JLabel blank1 = new JLabel();
    	JLabel blank2 = new JLabel();
    	JButton joinRoomButton = new JButton("방 참가");
    	JButton createRoomButton = new JButton("방 생성");
    	
    	joinRoomButton.setPreferredSize(new Dimension(120,60));
    	createRoomButton.setPreferredSize(new Dimension(120,60));
    	
    	joinRoomButton.addActionListener(new RoomConfig(0));
    	createRoomButton.addActionListener(new RoomConfig(1));
    	
    	roomListPanel.add(roomList);
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
    	
    	JPanel playerListPanel = new JPanel();
    	JPanel inputPanel = new JPanel();
    	inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,200,30));
    	
    	JTextArea tempArea= new JTextArea(30,100);
    	JButton readyButton = new JButton("준비완료 / 취소");
    	readyButton.setPreferredSize(new Dimension(120,60));

		playerListPanel.add(tempArea);
    	inputPanel.add(readyButton);
    	
    	c.add(playerListPanel,BorderLayout.CENTER);
    	c.add(inputPanel,BorderLayout.SOUTH);
    	
    	setVisible(true);
        pageState = PageState.INGAME;
    }

    public void InGame() {
    	//게임 종류 후 다시 게임 방으로 이동

        pageState = PageState.GAMEROOM;
    }
    private class RoomListSelect implements ListSelectionListener{
		JList list;
		RoomListSelect(JList list){
			this.list=list;
		}
		public void valueChanged(ListSelectionEvent e){
			if(!e.getValueIsAdjusting()) {
				String roomName = (String) list.getSelectedValue();
				System.out.println(roomName);
			}
		}
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
