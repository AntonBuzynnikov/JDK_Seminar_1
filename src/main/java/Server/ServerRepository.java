package Server;

public interface ServerRepository {
    String downloadFromLog();
    void loadToLog(String message);
}
