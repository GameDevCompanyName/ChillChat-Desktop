package ChillChat.Client.Network;

import ChillChat.Client.Connector;
import javafx.application.Platform;

public class ClientMethods {

    public static void clientVersionRequestReceived(){
        //TODO
    }

    public static void loginWrongErrorReceived(){
        Connector.passWrongError();
    }

    public static void loginAlreadyErrorReceived(){
        Connector.userAlreadyOnline();
    }

    public static void loginSuccessReceived(){
        Connector.loginSuccess();
    }

    public static void userRoomChanged(String roomId, String roomName){
        if (Connector.roomId.equals(roomId))
            return;
        Connector.roomId = roomId;
        Connector.roomName = roomName;
        Connector.cleanMessageHistory();
        Connector.displayServerMessage("Вы присоединились к комнате \"" + roomName + "\"");
    }

    public static void userRegistrationSuccessReceived(){
        //TODO
    }

    public static void userColorReceived(String login, String color){
        Connector.userColorRecieved(login, color);
    }

    public static void userMessageReceived(String login, String message, String color){
        Connector.displayMessage(login, message, color);
    }

    public static void userActionReceived(String login, String action){
        //TODO
    }

    public static void serverMessageReceived(String message){
        Connector.displayServerMessage(message);
    }

    public static void serverEventReceived(String event){
        //TODO
    }

    public static void serverUserKickedReceived(String login, String reason){
        Connector.userKickedRecieved(login, reason);
    }

    public static void userDisconnectReceived(String reason){
        Connector.disconnectedByReason(reason);
    }

    public static void serverUserLoginReceived(String login){
        Connector.userConnectedRecieved(login);
    }

    public static void serverUserDisconnectReceived(String login){
        Connector.userDisconnectedRecieved(login);
    }

    public static void serverPingRequest() {
        Connector.sendPong();
    }

    public static void roomIdsRecieved(String[] roomIds) {
        Connector.roomIdsRecieved(roomIds);
    }

    public static void roomInfoRecieved(String id, String name, String people) {
        Platform.runLater(() -> {
            Connector.roomInfoRecieved(id, name, people);
        });
    }

}
