package ChillChat.Client.Network;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

class Resender extends Thread {

    private boolean stoped = false;

    private BufferedReader in;

    Resender(BufferedReader in) {
        setName("Resender");
        this.in = in;
    }

    void setStop() {

        stoped = true;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while (!stoped) {

            try {
                String message = in.readLine();
                System.out.println("прочитал сообщение");
                if (!stoped)
                    Platform.runLater(() -> ClientMessage.read(message));
            } catch (IOException e) {
                System.out.println("Выключение входного потока");
            }


        }

    }

    public String getTime() {
        Date date = new Date();
        return "[" + date + "]";
    }
}