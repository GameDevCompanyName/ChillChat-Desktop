package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.Network.ClientMessage;
import ChillChat.Client.Network.Connector;
import ChillChat.Client.VisualElements.ActivityManager;
import ChillChat.Client.VisualElements.ChillTextPane;
import ChillChat.Client.VisualElements.CustomConsole;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static ChillChat.Client.Constants.DEBUG;
import static ChillChat.Client.Constants.TEXT_DISAPPEAR_TIME;

public class Messenger extends Activity {

    private VBox messengerBox;

    private CustomConsole console;
    private ChillTextPane inputField;
    private GaussianBlur textBlur;


    public Messenger(ActivityManager activityManager) {

        super(activityManager);

        this.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)){
                console.deleteSelectedMessages();
            }
        });

        messengerBox = new VBox();

        Connector.messenger = this;

        if (DEBUG)
            messengerBox.setStyle("-fx-border-color: yellow");

        messengerBox.prefHeightProperty().bind(this.heightProperty());
        messengerBox.prefWidthProperty().bind(this.widthProperty());
        messengerBox.maxHeightProperty().bind(this.heightProperty());
        messengerBox.maxWidthProperty().bind(this.widthProperty());


        Glow textGlow = new Glow();
        textGlow.setLevel(1);
        textBlur = new GaussianBlur();
        textBlur.setRadius(0.0);

//        Font inputFieldFont = null;
//        try {
//            inputFieldFont = Font.loadFont(new FileInputStream(new File("resources/commonFont.ttf")), 16);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        Font inputFieldFont = new Font("Courier New", 14);

        textBlur.setInput(textGlow);

        inputField = new ChillTextPane(inputFieldFont, this);
        inputField.prefWidthProperty().bind(this.widthProperty());

        console = new CustomConsole(inputField);

        messengerBox.getChildren().addAll(console.getBox(), inputField);
        this.getChildren().add(messengerBox);

    }

    public void changeInterfaceColor(String color) {

        inputField.changeColor(color);



    }

    public void flushTextFromField() {

        String text = inputField.getText();

        if (text.isEmpty())
            return;
        if (text.length() > 1000)
            sendMessage(text.substring(0, 1000));
        else
            sendMessage(text);

        animatedEreaseText();

    }

    private void animatedEreaseText() {

        Timeline textDissapear = new Timeline();
        textDissapear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(textBlur.radiusProperty(), 0.0)));
        textDissapear.getKeyFrames().add(new KeyFrame(Duration.seconds(TEXT_DISAPPEAR_TIME), new KeyValue(textBlur.radiusProperty(), 20.0)));
        textDissapear.play();
        textDissapear.setOnFinished(e -> {
            inputField.clear();
            textBlur.setRadius(0);
        });

    }

    public Node getContainer() {
        return messengerBox;
    }

    public void displayMessage(String name, String text, String color) {
        console.userTextAppend(name, text, color);
    }

    private void sendMessage(String text) {
        Connector.sendMessage(text);
    }

    public void displayServerMessage(String text) {
        console.serverMessageAppend(text);
    }

    public void displayUserKicked(String login, String reason) {
        console.serverMessageAppend(login + " кикнут по причине: " + reason);
    }

    public void disconnectedByReason(String reason) {
        console.serverMessageAppend("Вы отключены от сервера.\nПричина: " + reason);
    }

    public void displayNewUserConnected(String login) {
        if (console != null)
        console.serverMessageAppend(login + " подключился.");
    }

    public void displayUserDisconnected(String login) {
        console.serverMessageAppend(login + " отключился.");
    }

}
