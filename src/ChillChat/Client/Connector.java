package ChillChat.Client;

import ChillChat.Client.Network.ClientMessage;
import ChillChat.Client.Network.Resender;
import ChillChat.Client.VisualElements.Activities.Messenger;
import ChillChat.Client.VisualElements.Activities.Settings;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import ChillChat.Client.VisualElements.Activities.LogIn;
import ChillChat.Client.VisualElements.Utilites.AnimationType;
import javafx.application.Application;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

import static ChillChat.Client.Constants.*;

public class Connector {

    public static boolean connected = false;
    public static boolean loggedIn = false;
    public static String roomId = "null";
    public static String roomName = "null";
    public static String name = "null";
    public static String myColor = "null";

    public static Application application;

    static Socket socket;
    static BufferedReader in;
    static PrintWriter out;
    static Resender resender;

    public static ActivityManager manager;
    public static Messenger messenger;
    public static LogIn logIn;
    public static Settings settings;

    private static void send(String msg) {
        if (DEBUG)
            System.out.println("ОТПРАВЛЯЮ --- " + msg);
        if (out != null)
            out.println(msg);
    }

    public static void resetConnection(){
        dropAllTheConnection();
        createConnectionIfNONE();
    }

    public static void createConnectionIfNONE(){
        if (socket == null && resender == null){
            try {
                socket = new Socket(IP, PORT);
                connected = true;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")), true);
                resender = new Resender(in);
                resender.start();
                sendVersion();
            } catch (IOException e) {
                connected = false;
                e.printStackTrace();
            }
        }
    }

    public static void sendVersion(){
        send(ClientMessage.versionSend(DESKTOP_VERSION));
    }

    public static void sendLogInAttempt(String login, String pass){
        name = login;
        send(ClientMessage.loginAttemptSend(login, pass));
    }

    public static void sendMessage(String text){
        send(ClientMessage.messageSend(text));
    }

    public static void sendDisconnect(String reason){
        send(ClientMessage.disconnectSend(reason));
    }

    public static void dropAllTheConnection(){
        sendDisconnect("Завершил сессию.");
        try {
            if (resender != null)
                resender.setStop();
            if (socket != null)
                socket.close();
            if (in != null)
                in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
        in = null;
        out = null;
        resender = null;
    }

    public static void passWrongError() {
    }

    public static void userAlreadyOnline() {
    }

    public static void loginSuccess() {
        Messenger messenger = new Messenger(manager);
        manager.goTo(messenger, AnimationType.SLIDE);
        roomId = "0";
        roomName = "Главная";
    }

    public static void userColorRecieved(String login, String color) {
        if (login.equals(name)){
            myColor = color;
            updateInterfaceColor();
        }

        //TODO
    }

    private static void updateInterfaceColor() {
        messenger.changeInterfaceColor(myColor);
    }

    public static void displayMessage(String login, String message, String color) {
        if (messenger != null)
            messenger.displayMessage(login, message, color);
    }

    public static void displayServerMessage(String message) {
        if (messenger != null)
            messenger.displayServerMessage(message);
    }

    public static void userKickedRecieved(String login, String reason) {
        if (messenger != null)
            messenger.displayUserKicked(login, reason);
    }

    public static void disconnectedByReason(String reason) {
        if (messenger != null)
            messenger.disconnectedByReason(reason);
        dropAllTheConnection();
        manager.goBack();
    }

    public static void userConnectedRecieved(String login) {
        if (messenger != null)
            messenger.displayNewUserConnected(login);
    }

    public static void userDisconnectedRecieved(String login) {
        if (messenger != null)
            messenger.displayUserDisconnected(login);

    }

    public static void sendPong() {
        send(ClientMessage.userPong());
    }

    public static void sendRoomChangeRequest(String roomId) {
        send(ClientMessage.roomChangeRequestSend(roomId));
    }

    public static void cleanMessageHistory() {
        if (messenger != null)
            messenger.cleanMessageHistory();
    }
}
