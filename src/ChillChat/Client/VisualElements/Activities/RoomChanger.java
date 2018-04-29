package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.Connector;
import ChillChat.Client.VisualElements.Elements.RoomCard;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static ChillChat.Client.Constants.MESSAGE_CLICK_ANIMATION_TIME;

public class RoomChanger extends Activity {

    public RoomChanger(ActivityManager activityManager) {
        super(activityManager);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        Glow glow = new Glow();
        glow.setLevel(0.7);

        HBox roomCards = new HBox();
        roomCards.setSpacing(20);
        RoomCard main = new RoomCard("0");
        RoomCard besedka = new RoomCard("1");
        RoomCard huilka = new RoomCard("balalaika");
        roomCards.getChildren().addAll(
                main, besedka, huilka
        );

        main.setInfo("ГЛАВНАЯ", 13843);
        besedka.setInfo("Беседка", 18);

//        VBox roomButtons = new VBox();
//        roomButtons.setAlignment(Pos.CENTER);
//        roomButtons.setEffect(glow);
//        roomButtons.setOpacity(0.97);
//        roomButtons.setSpacing(20);
//        roomButtons.getChildren().add(createRoomButton("0", "Главная"));
//        roomButtons.getChildren().add(createRoomButton("1", "Беседка"));
//        roomButtons.getChildren().add(createRoomButton("2", "АФК"));

        this.getChildren().add(roomCards);
    }

    @Override
    public void onCall() {
        Connector.requestRoomsInfo();
    }

    @Override
    public void onClose() {

    }

    private Node createRoomButton(String roomId, String roomName) {
        Button button = new Button();
        button.setText(roomName);
        button.setPrefSize(200, 80);
        button.setFont(new Font("Century Gothic", 34));
        button.setBackground(Background.EMPTY);

        button.setTextFill(Color.web("#3540db"));
        button.setStyle("-fx-border-color: #3540db");

        button.setOnMouseEntered(event -> {
            button.setTextFill(Color.web("#ce3f81"));
            button.setStyle("-fx-border-color: #ce3f81");
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(Color.web("#3540db"));
            button.setStyle("-fx-border-color: #3540db");
        });

        return button;
    }


}
