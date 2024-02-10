package Interfaces;

public interface Observable {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObserver(String message);
}
