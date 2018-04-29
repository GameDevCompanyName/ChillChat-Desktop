package ChillChat.Client.VisualElements.Elements;

import ChillChat.Client.Connector;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static ChillChat.Client.Constants.DEBUG;
import static ChillChat.Client.Constants.MESSAGE_CLICK_ANIMATION_TIME;

public class RoomCard extends StackPane {

    static String backgroundColor = "#13041B";
    static String defaultColor = "#888888";
    static String passiveColor = "";
    static String activeColor = "#FFFFFF";
    static Font nameFont = new Font("Century Gothic", 26);
    static Font peopleFont = new Font("Century Gothic", 20);
    //static Font descriptionFont = new Font("Century Gothic", 12);

    private int H = 240;
    private int W = 160;

    private String name;
    private int people;
    private String roomId;

    private Rectangle background;
    private Label nameLabel;
    private Label peopleLabel;

    public RoomCard(String roomId){

        if (DEBUG)
            this.setStyle("-fx-border-color: #550000");

        this.roomId = roomId;
        this.setOnMouseClicked(event -> {
            playBounceAnimation(this);
            Connector.sendRoomChangeRequest(roomId);
            Connector.manager.goBack();
        });

        this.setWidth(W);
        this.setHeight(H);
        this.setPrefSize(W, H);
        this.setMaxHeight(H);

        background = new Rectangle(W, H);
        background.setFill(Color.web(backgroundColor, 0.95));

        nameLabel = new Label("ИМЯ");
        nameLabel.setFont(nameFont);
        nameLabel.setTextFill(Color.web(defaultColor));

        peopleLabel = new Label("0/0");
        peopleLabel.setFont(peopleFont);
        peopleLabel.setTextFill(Color.web(defaultColor));

        VBox info = new VBox();
        info.setPadding(new Insets(30, 0, 0, 0));
        info.setAlignment(Pos.TOP_CENTER);
        info.setSpacing(10);
        info.setMaxSize(W, H);
        info.setPrefSize(W, H);
        if (DEBUG)
            info.setStyle("-fx-border-color: #005500");

        info.getChildren().addAll(nameLabel, peopleLabel);

        this.getChildren().addAll(background, info);
        this.setOpacity(0.4);

    }

    public void setInfo(String name, int people){
        this.setOpacity(1.0);
        this.name = name;
        this.people = people;

        nameLabel.setText(name);
        peopleLabel.setText(Integer.toString(people) + "/∞");

        nameLabel.setTextFill(Color.web(activeColor));
        peopleLabel.setTextFill(Color.web(activeColor));
    }

    private void playBounceAnimation(Node node) {
        Timeline clickedAnimation = new Timeline();

        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(node.scaleXProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME/4), new KeyValue(node.scaleXProperty(), 0.965)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME), new KeyValue(node.scaleXProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(node.scaleYProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME/4), new KeyValue(node.scaleYProperty(), 0.965)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME), new KeyValue(node.scaleYProperty(), 1.0)));

        clickedAnimation.playFromStart();
    }


}
