package ChillChat.Client.Network;

import ChillChat.Client.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import static ChillChat.Client.Constants.DEBUG;

public class ClientMessage {

    public static void read(String input){

        if (input == null || input.equals("null")){  //Мы все мертвы внутри
            if (DEBUG)
                System.out.println("Пришёл NULL чё за хуйня");
            return;
        }

        if (DEBUG)
            System.out.println("read -> " + input);

        JSONObject incomingMessage = (JSONObject) JSONValue.parse(input);

        String methodName = (String) incomingMessage.get("type");
        proceedMessage(methodName, incomingMessage);

    }

    private static void proceedMessage(String methodName, JSONObject incomingMessage) throws NullPointerException {

        switch (methodName){
            case "loginWrongError":
                ClientMethods.loginWrongErrorReceived();
                break;
            case "loginAlreadyError":
                ClientMethods.loginAlreadyErrorReceived();
                break;
            case "loginSuccess":
                ClientMethods.loginSuccessReceived();
                break;
            case "userColor":
                ClientMethods.userColorReceived(
                        incomingMessage.get("login").toString(),
                        incomingMessage.get("color").toString()
                );
                break;
            case "userMessage":
                ClientMethods.userMessageReceived(
                        incomingMessage.get("login").toString(),
                        incomingMessage.get("text").toString(),
                        incomingMessage.get("color").toString()
                );
                break;
            case "serverMessage":
                ClientMethods.serverMessageReceived(
                        incomingMessage.get("text").toString()
                );
                break;
            case "serverUserKicked":
                ClientMethods.serverUserKickedReceived(
                        incomingMessage.get("login").toString(),
                        incomingMessage.get("reason").toString()
                );
                break;
            case "userDisconnect":
                ClientMethods.userDisconnectReceived(
                        incomingMessage.get("reason").toString()
                );
                break;
            case "serverUserLogin":
                ClientMethods.serverUserLoginReceived(
                        incomingMessage.get("login").toString()
                );
                break;
            case "serverUserDisconnect":
                ClientMethods.serverUserDisconnectReceived(
                        incomingMessage.get("login").toString()
                );
                break;
            case "roomIds":
                JSONArray array = (JSONArray) incomingMessage.get("roomIds");
                String[] ids = Utils.jsonArrayToStringArray(array);
                ClientMethods.roomIdsRecieved(
                        ids
                );
                break;
            case "roomInfo":
                ClientMethods.roomInfoRecieved(
                        incomingMessage.get("roomId").toString(),
                        incomingMessage.get("roomName").toString(),
                        incomingMessage.get("roomPeople").toString()
                );
            case "ping":
                ClientMethods.serverPingRequest();
                break;
            case "userChangedRoom":
                ClientMethods.userRoomChanged(
                        incomingMessage.get("roomId").toString(),
                        incomingMessage.get("roomName").toString()
                );
        }

    }

    public static String messageSend(String message){
        JSONObject object = new JSONObject();
        object.put("type", "message");
        object.put("text", message);
        return object.toJSONString();
    }

    public static String versionSend(String version){
        JSONObject object = new JSONObject();
        object.put("type", "version");
        object.put("version", version);
        return object.toJSONString();
    }

    public static String loginAttemptSend(String login, String password){
        JSONObject object = new JSONObject();
        object.put("type", "loginAttempt");
        object.put("login", login);
        object.put("password", password);
        return object.toJSONString();
    }

    public static String disconnectSend(String reason){
        JSONObject object = new JSONObject();
        object.put("type", "disconnect");
        object.put("reason", reason);
        return object.toJSONString();
    }

    public static String userPong() {
        JSONObject object = new JSONObject();
        object.put("type", "pong");
        return object.toJSONString();
    }

    public static String roomChangeRequestSend(String roomId) {
        JSONObject object = new JSONObject();
        object.put("type", "joinRoom");
        object.put("roomId", roomId);
        return object.toJSONString();
    }

    public static String requestRoomsInfoSend() {
        JSONObject object = new JSONObject();
        object.put("type", "requestRoomsInfo");
        return object.toJSONString();
    }

}