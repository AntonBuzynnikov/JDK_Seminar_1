package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ServerRepositoryTXT implements ServerRepository{
    @Override
    public String downloadFromLog() {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/Server/log.txt"))){
            String line = reader.readLine();

            while (line != null){
                sb.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    @Override
    public void loadToLog(String message) {
        try(FileWriter fileWriter = new FileWriter("./src/main/java/Server/log.txt", true)){
            fileWriter.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
