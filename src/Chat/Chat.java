package Chat;

import Network.ChatDTO;
import Network.IO.IO;
import Network.IO.Server_IO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Chat extends JFrame{
        private final int WIDTH = 450,
                          HEIGHT = 660;

        private Font chatFont = new Font("SansSerif",Font.ITALIC,15);
        private JTextArea chatTextArea;
        private Container c;

        private Player player;
        private Server_IO server;
        private ChatDTO packet_chat;
        private IO client;
        private String ip;
        private String name;
        private int port;
        private boolean isServer;

        public Chat(boolean isServer) {
            ScreenSetting();
            player = null;
            port = -1;
            Scanner scan = new Scanner(System.in);
            this.isServer = isServer;
        }

        public void ScreenSetting() {
            setSize(WIDTH, HEIGHT);
            setTitle("DEFAULT");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            LogIn();
        }
        public void LogIn() {
            System.out.println("LogIn()");

            getContentPane().removeAll();
            c = getContentPane();
            c.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel();
            JPanel imagePanel = new JPanel();
            inputPanel.setLayout(null);
            inputPanel.setPreferredSize(new Dimension(360, 720));

            JTextField[] inputTextField = new JTextField[3];
            JButton startButton = new JButton("로그인");
            JComboBox<Language> languageComboBox = new JComboBox<>();
            languageComboBox.setModel(new DefaultComboBoxModel<>(Language.values()));
            languageComboBox.setBounds(60, 345, 240, 40);

            for (var i = 0; i < 3; i++) {
                inputTextField[i] = new JTextField();
                inputTextField[i].setBounds(60, 180 + (i * 55), 240, 40);
                inputPanel.add(inputTextField[i]);
            }

            inputTextField[0].setText("127.0.0.1");
            inputTextField[1].setText("NAME");
            inputTextField[2].setText("5801");
            inputPanel.add((languageComboBox));
            startButton.setBounds(210, 420, 90, 35);

            JLabel imageLabel = new JLabel();

            inputPanel.add(startButton);
            imagePanel.add(imageLabel);

            c.add(inputPanel, BorderLayout.WEST);
            c.add(imagePanel, BorderLayout.CENTER);

            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //이름, ip, 포트번호 입력받기
                    ip = inputTextField[0].getText();
                    name = inputTextField[1].getText();
                    port = Integer.parseInt(inputTextField[2].getText());
                    Language lag = Language.KOR;

                    //setting//
                    packet_chat = new ChatDTO(name, lag);

                    if (!isServer) client = new IO(ip, port, packet_chat);
                    else server = new Server_IO(port, name);

                    player = new Player(name, lag);

                    ChattingRoom();

                    System.out.println("ip\t: " + ip + "\nname\t: " + name + "\nlag\t: " + lag);
                }
            });

            setVisible(true);
        }
        public void ChattingRoom() {
            System.out.println("GameRoom()");
            getContentPane().removeAll();
            c = getContentPane();

            JPanel playerPanel = new JPanel();
            JPanel inputPanel = new JPanel();
            playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
            inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,500));

            chatTextArea = new JTextArea(10,10);
            chatTextArea.setEditable(false);
            chatTextArea.setFont(chatFont);

            if (isServer) server.SetChat(chatTextArea);
            else client.SetChat(chatTextArea);

            JScrollPane chatScroll = new JScrollPane(chatTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            chatScroll.setPreferredSize(new Dimension(300,500));

            JTextField chatTextField = new JTextField();
            chatTextField.setPreferredSize(new Dimension(300,50));
            chatTextField.addKeyListener(new ChatEntered(chatTextArea,chatTextField));

            playerPanel.add(chatScroll,BorderLayout.CENTER);
            playerPanel.add(chatTextField,BorderLayout.SOUTH);

            c.add(playerPanel,BorderLayout.CENTER);
            c.add(inputPanel,BorderLayout.EAST);

            setVisible(true);
        }

        private class ChatEntered extends KeyAdapter {
            JTextArea chatTextArea;
            JTextField chatTextField;
            ChatEntered(JTextArea chatTextArea,JTextField chatTextField){
                this.chatTextArea = chatTextArea;
                this.chatTextField = chatTextField;
            }
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    Chatting(chatTextField.getText(),chatTextArea);
                    chatTextField.setText("");
                }
            }
        }

        private void Chatting(String chatText, JTextArea chatArea){ //이곳은 채팅이오
            chatArea.setText(chatArea.getText() + "\n" + name + " : " + chatText);
            if(isServer) server.SetMessage(chatText);
            else client.SetMessage(chatText);
        }
    }

