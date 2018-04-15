package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.Connector;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static ChillChat.Client.Constants.MESSAGE_CLICK_ANIMATION_TIME;

public class RoomChanger extends Activity {

    public RoomChanger(ActivityManager activityManager) {
        super(activityManager);
        this.setPadding(new Insets(20));

        VBox roomButtons = new VBox();
        roomButtons.setSpacing(20);
        roomButtons.getChildren().add(createRoomButton("0", "Главная"));
        roomButtons.getChildren().add(createRoomButton("1", "беседка"));
        roomButtons.getChildren().add(createRoomButton("2", "АФК"));

        this.getChildren().add(roomButtons);
    }

    private Node createRoomButton(String roomId, String roomName) {
        Button button = new Button();
        button.setText(roomName);
        button.setPrefSize(200, 80);
        button.setFont(new Font("Courier New Bold", 26));
        button.setBackground(Background.EMPTY);

        button.setTextFill(Color.BLUE);
        button.setStyle("-fx-border-color: BLUE");

        button.setOnMouseEntered(event -> {
            button.setTextFill(Color.MEDIUMVIOLETRED);
            button.setStyle("-fx-border-color: MEDIUMVIOLETRED");
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(Color.BLUE);
            button.setStyle("-fx-border-color: BLUE");
        });
        button.setOnMouseClicked(event -> {
            playBounceAnimation(button);
            Connector.sendRoomChangeRequest(roomId);
        });
        return button;
    }

    private void playBounceAnimation(Button button) {
        Timeline clickedAnimation = new Timeline();

        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(button.scaleXProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME/4), new KeyValue(button.scaleXProperty(), 0.965)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME), new KeyValue(button.scaleXProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(button.scaleYProperty(), 1.0)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME/4), new KeyValue(button.scaleYProperty(), 0.965)));
        clickedAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(MESSAGE_CLICK_ANIMATION_TIME), new KeyValue(button.scaleYProperty(), 1.0)));

        clickedAnimation.playFromStart();
    }


}
