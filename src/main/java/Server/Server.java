package Server;

import Interfaces.Observable;
import Interfaces.Observer;
import java.util.ArrayList;
import java.util.List;

public class Server implements Observable {
    private List<Observer> observers;
    private ServerRepository serverRepository;
    private boolean serverStatus;
    public Server(ServerRepository serverRepository){
        observers = new ArrayList<>();
        this.serverRepository = serverRepository;
        serverStatus = false;
    }

    public String getHistory(){
        return serverRepository.downloadFromLog();
    }

    public void setServerStatus(boolean status){
        serverStatus = status;
    }
    public boolean getServerStatus(){
        return serverStatus;
    }

    public void sendMessage(String message){
        serverRepository.loadToLog(message);
        notifyObserver(message);
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
