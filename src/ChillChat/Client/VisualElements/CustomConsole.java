package ChillChat.Client.VisualElements;

import ChillChat.Client.Network.Connector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static ChillChat.Client.Constants.DEBUG;
import static ChillChat.Client.Constants.TEXT_APPEAR_TIME;

public class CustomConsole {

    private StackPane mainBox;
    private ScrollPane scrollPane;
    private VBox textBox;

    private Message lastMessage;
    private List<Message> selectedMessages;

    private Font commonFont;
    private Font serverFont;

    public CustomConsole(ChillTextPane inputField){

        this.selectedMessages = new ArrayList<>();
        mainBox = new StackPane();
        textBox = new VBox();
        scrollPane = new ScrollPane();

        Message.setParentNode(textBox);

        textBox.setPadding(new Insets(13));
        textBox.setSpacing(5);
        textBox.prefWidthProperty().bind(Connector.messenger.widthProperty());

        mainBox.prefHeightProperty().bind(Connector.messenger.heightProperty().subtract(inputField.heightProperty()));
        mainBox.prefWidthProperty().bind(Connector.messenger.widthProperty());
        mainBox.maxHeightProperty().bind(Connector.messenger.heightProperty().subtract(inputField.heightProperty()));
        mainBox.maxWidthProperty().bind(Connector.messenger.widthProperty());

        if (DEBUG)
            mainBox.setStyle("-fx-border-color: red");
        mainBox.setAlignment(Pos.CENTER);

        scrollPane.setContent(textBox);
        if (DEBUG)
            scrollPane.setStyle("-fx-border-color: green");
        scrollPane.prefWidthProperty().bind(Connector.messenger.widthProperty());
        scrollPane.prefHeightProperty().bind(Connector.messenger.heightProperty().subtract(inputField.heightProperty()));
        scrollPane.maxWidthProperty().bind(Connector.messenger.widthProperty());
        scrollPane.maxHeightProperty().bind(Connector.messenger.heightProperty().subtract(inputField.heightProperty()));

        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;\n" +
                "-fx-background: transparent;");

        mainBox.getChildren().addAll(scrollPane);

    }

    private void slowScrollToBottom() {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), 1.0)));
        animation.play();
    }

    public void userTextAppend(String name, String text, String color){

        if (lastMessage != null)
        {
            if (lastMessage.getSenderName().equals(name)) {
                lastMessage.addText(text);
                slowScrollToBottom();
                return;
            }
        }

        Message message = new Message(name, color);

        lastMessage = message;

        message.addText(text);

        message.build();

        textBox.getChildren().add(message);

        message.setOpacity(0.0);
        animateAppear(message);

        message.setOnMouseClicked(e -> {

            message.playPressedAnimation();
            if (!message.isSelected()){
                message.select();
                selectedMessages.add(message);
            } else {
                message.unSelect();
                selectedMessages.remove(message);
            }

        });

        slowScrollToBottom();

    }

    public static void animateAppear(Node node) {

        GaussianBlur blur = new GaussianBlur();

        node.setEffect(blur);

        Timeline textAppear = new Timeline();
        textAppear.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(blur.radiusProperty(), 25.0)));
        textAppear.getKeyFrames().add(
                new KeyFrame(Duration.seconds(TEXT_APPEAR_TIME), new KeyValue(blur.radiusProperty(), 0.0)));
        textAppear.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(node.opacityProperty(), 0.0)));
        textAppear.getKeyFrames().add(
                new KeyFrame(Duration.seconds(TEXT_APPEAR_TIME), new KeyValue(node.opacityProperty(), 1)));
        textAppear.play();


        /*
        Timeline shutUpAnimation = new Timeline();
        shutUpAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(flow.opacityProperty(), 0)));
        shutUpAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new KeyValue(flow.opacityProperty(), 1)));
        shutUpAnimation.play();
        */

    }

    public Node getBox() {
        return mainBox;
    }

    public void serverMessageAppend(String text) {

        if (lastMessage != null)
        {
            if (lastMessage.getSenderName().equals("[SERVER]")) {
                lastMessage.addText(text);
                slowScrollToBottom();
                return;
            }
        }

        Message message = new Message("[SERVER]", Message.MessageType.SERVER_MESSAGE);
        lastMessage = message;
        message.addText(text);

        message.setOpacity(0.0);
        message.build();

        textBox.getChildren().add(message);
        message.setOnMouseClicked(e -> {
            animateSlideRightDissapear(message);
        });

        animateAppear(message);

        slowScrollToBottom();

    }

    private void animateSlideRightDissapear(Message message) {

        if (message == lastMessage)
            lastMessage = null;

        Timeline slideRight = new Timeline();

        slideRight.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(message.translateXProperty(), message.getTranslateX())));
        slideRight.getKeyFrames().add(
                new KeyFrame(Duration.seconds(TEXT_APPEAR_TIME), new KeyValue(message.translateXProperty(), message.getTranslateX() + 2000)));
        slideRight.setOnFinished(e -> {
            if (textBox.getChildren().contains(message))
                textBox.getChildren().remove(message);
        });
        slideRight.play();

    }

    public void deleteSelectedMessages() {
        if (selectedMessages.isEmpty())
            return;

        for (Message message: selectedMessages) {
            animateSlideRightDissapear(message);
        }

        if (selectedMessages.contains(lastMessage))
            lastMessage = null;

        selectedMessages.clear();
    }

}

