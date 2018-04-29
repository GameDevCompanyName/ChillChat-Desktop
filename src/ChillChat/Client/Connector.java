package ChillChat.Client;

import ChillChat.Client.Network.ClientMessage;
import ChillChat.Client.Network.Handler;
import ChillChat.Client.VisualElements.Activities.Messenger;
import ChillChat.Client.VisualElements.Activities.Settings;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import ChillChat.Client.VisualElements.Activities.LogIn;
import ChillChat.Client.VisualElements.Utilites.AnimationType;
import javafx.application.Application;
import javafx.application.Platform;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static ChillChat.Client.Constants.*;

public class Connector {

    public static boolean connected = false;
    public static boolean loggedIn = false;
    public static String roomId = "null";
    public static String roomName = "null";
    public static String name = "null";
    public static String myColor = "null";

    public static Application application;

    public static Channel channel;
    public static ChannelFuture future;
    public static ChannelFactory factory;
    public static ClientBootstrap bootstrap;

    public static ActivityManager manager;
    public static Messenger messenger;
    public static LogIn logIn;
    public static Settings settings;

    private static void send(String msg) {

        if (DEBUG)
            System.out.println("ОТПРАВЛЯЮ --- " + msg);
        if (connected && channel.isOpen()){
            try {
                channel.write(msg).await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void resetConnection(){
        dropAllTheConnection();
        createConnectionIfNONE();
    }


    public static void createConnectionIfNONE(){

        factory = new NioClientSocketChannelFactory(
                Executors.newFixedThreadPool(1),
                Executors.newFixedThreadPool(4)
        );
        bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(() -> Channels.pipeline(new Handler()));

        if (channel == null && connected == false){
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(IP, PORT));
            try {
                channel = future.await().getChannel();
                connected = true;
                sendVersion();
            } catch (InterruptedException e) {
                if (DEBUG)
                    System.out.println("Не удалось создать подключение");
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
        connected = false;

        if (bootstrap != null)
            Platform.runLater(() -> bootstrap.releaseExternalResources());
        if (factory != null)
            Platform.runLater(() -> bootstrap.releaseExternalResources());
        if (channel != null && channel.isOpen())
            channel.close();
        if (future != null)
            future.cancel();


        future = null;
        channel = null;
        bootstrap = null;
        factory = null;

    }

    public static void passWrongError() {
        if (logIn != null)
            logIn.wrongPass();
    }

    public static void userAlreadyOnline() {
        if (logIn != null)
            logIn.userAlreadyExists();
    }

    public static void loginSuccess() {
        if (logIn != null)
            logIn.clearErrorField();
        loggedIn = true;
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

    public static void updateInterfaceColor() {
        if (messenger != null)
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
        if (roomId == Connector.roomId)
            return;
        send(ClientMessage.roomChangeRequestSend(roomId));
    }

    public static void cleanMessageHistory() {
        if (messenger != null)
            messenger.cleanMessageHistory();
    }

    public static void connectionLost() {

        if (!connected)
           return;

        if (DEBUG)
            System.out.println("Канал закрылся");
        Connector.dropAllTheConnection();
        manager.goToFirst();
    }

    public static void exit() {
        dropAllTheConnection();
        factory.releaseExternalResources();
    }

    public static void exeptionCaught(ExceptionEvent e) {
        if (DEBUG)
            System.out.println("Словил эксепшн");
        e.getCause().printStackTrace();
        manager.goToFirst();
    }

    public static void requestRoomsInfo() {
        send(ClientMessage.requestRoomsInfoSend());
    }

}
