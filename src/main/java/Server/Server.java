package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends JFrame {
    private static final int WINDOW_HEIGHT = 360;
    private static final int WINDOW_WIDTH = 540;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 400;
    private boolean isServerStart;
    private JButton btnStart = new JButton("Start");
    private JButton btnStop = new JButton("Stop");
    private JPanel btnPanel = new JPanel(new GridLayout(1,2));
    private JTextArea textArea = new JTextArea();
    public Server(){
        isServerStart = false;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX,WINDOW_POSY);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Chat Server");
        setVisible(true);
        setResizable(false);
        textArea.setEditable(false);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!isServerStart){
                    isServerStart = true;
                    textArea.append("Сервер запущен!\n");
                    for(String str: downloadFromLog()){
                        textArea.append(str +"\n");
                    }
                } else{
                    textArea.append("Сервер уже запущен!\n");
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(isServerStart) {
                    isServerStart = false;
                    textArea.append("Сервер остановлен!\n");
                } else {
                    textArea.append("Сервер уже остановлен!\n");
                }
            }
        });

        btnPanel.add(btnStart);
        btnPanel.add(btnStop);

        add(textArea,BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }
    public boolean auth(String login, String password){
        if(isServerStart){
        textArea.append("Успешная аторизация!" + login + "\n");
        return true;
        }
        return false;
    }

    public void saveInLog(String name, String message){
        String sms = name + ": " + message + "\n";
        try(FileWriter fileWriter = new FileWriter("./src/main/java/Server/log.txt", true)){
            fileWriter.write(sms);
            textArea.append(sms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> downloadFromLog(){
        List<String> log = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/Server/log.txt"))){
            String line = reader.readLine();
            while (line != null){
                log.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return log;
    }

}
