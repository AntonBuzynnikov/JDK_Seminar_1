package Client;

import javax.swing.*;
import Server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {
    private ClientView clientView;
    private String name;
    private Server server;
    private boolean connected;


    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public void sendMessage(String message) {
        server.sendMessage(message);
    }

    public boolean connectedToServer(String login, String password) {
        name = login;
        connected = server.getServerStatus();
        return server.getServerStatus();
    }

    public String getName(){
        return name;
    }
    public String getHistory(){
        return server.getHistory();
    }
    public boolean isConnected(){
        return connected;
    }

    public void setConnected(boolean status){
        connected = status;
    }
}



