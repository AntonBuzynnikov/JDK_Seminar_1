import Client.Client;
import Server.*;
import Client.*;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new ServerRepositoryTXT());
        server.attach(new ServerGUI(server));
        server.attach(new ClientGUI(server));
        server.attach(new ClientGUI(server));
    }
}
