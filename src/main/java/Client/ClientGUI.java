package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Interfaces.Observer;
import Server.Server;

public class ClientGUI extends JFrame implements ClientView, Observer {

    private static final int WINDOW_HEIGHT = 360;
    private static final int WINDOW_WIDTH = 540;
    private static final int WINDOW_POSX = 200;
    private static final int WINDOW_POSY = 200;
    private JTextField ipUser, port, login, password, writeMessage;
    private JTextArea messageLog;
    private JButton btnLogin, btnSend;
    private Client client;

    public ClientGUI(Server server){
        setting(server);
        add(getLoginPanel(),BorderLayout.NORTH);
        add(getMessagesLog(),BorderLayout.CENTER);
        add(getSendMessagePanel(),BorderLayout.SOUTH);
    }
    @Override
    public void showMessage(String message) {
        messageLog.append(message + "\n");
    }

    public void setting(Server server){
        setLocation(WINDOW_POSX,WINDOW_POSY);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Chat Client");
        setVisible(true);
        setResizable(false);
        client = new Client(this,server);
    }
    private Component getLoginPanel(){
        ipUser = new JTextField("127.0.0,1");
        port = new JTextField("8080");
        login = new JTextField("Ivan Ivanov");
        password = new JTextField("********");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connectedToServer();
            }
        });
        JPanel loginPanel = new JPanel(new GridLayout(2,3));
        loginPanel.add(ipUser);
        loginPanel.add(port);
        loginPanel.add(new JPanel());
        loginPanel.add(login);
        loginPanel.add(password);
        loginPanel.add(btnLogin);
        return loginPanel;
    }
    private Component getMessagesLog(){
        messageLog = new JTextArea();
        messageLog.setEditable(false);
        messageLog.setVisible(false);
        return new JScrollPane(messageLog);
    }
    private Component getSendMessagePanel(){
        JPanel sendMessagePanel = new JPanel(new GridLayout(1,2));
        writeMessage = new JTextField();
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendMessage();
            }
        });
        writeMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
        sendMessagePanel.add(writeMessage);
        sendMessagePanel.add(btnSend, BorderLayout.EAST);
        return sendMessagePanel;
    }

    private void sendMessage(){
        client.sendMessage(login.getText() + ": " + writeMessage.getText());
        writeMessage.setText("");
    }
    @Override
    public void connectedToServer(){
        if(client.connectedToServer(login.getText(),password.getText())){
            messageLog.setVisible(true);
            showMessage(client.getName() + " подкючился");
            showMessage(client.getHistory());
        } else {
            messageLog.setVisible(true);
            showMessage("Ошибка подключения");
        }
    }

    @Override
    public void update(String message) {
        showMessage(message);
    }
}
