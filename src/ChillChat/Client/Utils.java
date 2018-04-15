package ChillChat.Client;

import javafx.scene.image.Image;

import java.io.*;
import java.util.Random;

public class Utils {

    public static Image getRandomLogInBackground(){

        File folder = new File("resources/images/LogInBackgrounds");

        int numberOfFiles = folder.listFiles().length;

        int randomIndex = 1 + new Random().nextInt(numberOfFiles);

        return new Image(getFileByNumber(randomIndex, folder).toURI().toString());

    }

    private static File getFileByNumber(int randomIndex, File folder) {

        int count = 1;

        File resultFile = null;

        for (File file: folder.listFiles()) {
            resultFile = file;
            if (count == randomIndex)
                return resultFile;
            count++;
        }

        return resultFile;

    }

    public static String loadIP() {
        String IP = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("config/settings.txt")));
            IP = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IP;
    }

    public static int loadPORT() {
        String PORT = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("config/settings.txt")));
            reader.readLine();
            PORT = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(PORT);
    }

}
