package Server;

import Interfaces.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ServerView, Observer {

    private static final int WINDOW_HEIGHT = 360;
    private static final int WINDOW_WIDTH = 540;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 400;
    private Server server;
    private JTextArea log;
    private JButton btnStartServer, btnStopServer;

    public ServerGUI(Server server){
        this.server = server;
        setting();
    }
    @Override
    public void showMessage(String message) {
        log.append(message + "\n");
    }
    private void setting(){
        setLocation(WINDOW_POSX,WINDOW_POSY);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        add(getLogPanel(),BorderLayout.CENTER);
        add(getBtnPanel(),BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    private Component getBtnPanel(){
        JPanel btnPanel = new JPanel(new GridLayout(1,2));
        btnStartServer = new JButton("Start");
        btnStopServer = new JButton("Stop");
        btnStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!server.getServerStatus()) {
                    server.setServerStatus(true);
                    showMessage("Сервер запущен!");
                    showMessage(server.getHistory());
                } else {
                    showMessage("Сервер уже запущен");
                }
            }
        });
        btnStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(server.getServerStatus()){
                    server.setServerStatus(false);
                    log.setText("");
                    showMessage("Сервер остановлен");
                    server.notifyObserver("Нет связи с сервером");
                } else {
                    showMessage("Сервер уже остановлен");
                }
            }
        });
        btnPanel.add(btnStartServer);
        btnPanel.add(btnStopServer);
        return btnPanel;
    }

    private Component getLogPanel(){
        log = new JTextArea();
        log.setEnabled(false);
        return new JScrollPane(log);
    }

    @Override
    public void update(String message) {
        showMessage(message);
    }
}
