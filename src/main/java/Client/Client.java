package Client;

import javax.swing.*;
import Server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Client extends JFrame {
    private static final int WINDOW_HEIGHT = 360;
    private static final int WINDOW_WIDTH = 540;
    private static final int WINDOW_POSX = 200;
    private static final int WINDOW_POSY = 200;
    private JTextField ipUser = new JTextField("127.0.0.1");
    private JTextField port = new JTextField("8080");
    private JTextField login = new JTextField();
    private JTextField password = new JTextField("***");
    private JButton btnLogin = new JButton("login");
    private JPanel panConnected = new JPanel(new GridLayout(2,3));
    private JTextField text = new JTextField();
    private JTextArea messages = new JTextArea();
    private JTextField writeMessage = new JTextField();
    private JButton sendMessage = new JButton("Send");
    private JPanel panMessage = new JPanel(new GridLayout(1,2));

    private static String msg;

    public Client(Server server){
        setLocation(WINDOW_POSX,WINDOW_POSY);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Chat Client");
        setVisible(true);
        setResizable(false);


        panConnected.add(ipUser);
        panConnected.add(port);
        panConnected.add(text).setVisible(false);
        panConnected.add(login);
        panConnected.add(password);
        panConnected.add(btnLogin);

        panMessage.add(writeMessage);
        panMessage.add(sendMessage);
        panMessage.setVisible(false);
        messages.setEditable(false);
        messages.setVisible(false);
        add(panConnected, BorderLayout.NORTH);
        add(messages, BorderLayout.CENTER);
        add(panMessage,BorderLayout.SOUTH);

        JScrollPane jScrollPane = new JScrollPane(messages);
        jScrollPane.setVisible(false);
        add(jScrollPane);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(server.auth(login.getText(),password.getText())){
                    panConnected.setVisible(false);
                    messages.setVisible(true);
                    panMessage.setVisible(true);
                    jScrollPane.setVisible(true);
                    String lastMsg = null;
                    List<String> temp = server.downloadFromLog();
                    for(int i = 0; i < temp.size(); i++){
                        lastMsg = temp.get(i);
                        messages.append(temp.get(i) + "\n");
                    }
                    msg = lastMsg;
                }
            }
        });
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                server.saveInLog(login.getText(), writeMessage.getText());
                msg = login.getText() + ": " + writeMessage.getText();
                messages.append(login.getText() + ": " + writeMessage.getText() + "\n");
                writeMessage.setText("");
            }
        });
        update(server);

    }

    public void update(Server server){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(()-> {
            List<String> log = List.of(messages.getText().split("\n"));
            if(!log.get(log.size()-1).equals(msg) && msg != null && !log.get(1).equals(msg)){
                messages.append(msg + "\n");
            }
        }, 0,1, TimeUnit.SECONDS);

    }


}
