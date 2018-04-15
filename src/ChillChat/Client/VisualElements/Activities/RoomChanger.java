package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.Connector;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RoomChanger extends Activity {

    public RoomChanger(ActivityManager activityManager) {
        super(activityManager);
        this.setPadding(new Insets(20));

        VBox roomButtons = new VBox();
        roomButtons.setSpacing(20);
        roomButtons.getChildren().add(createRoomButton("0", "Главная"));
        roomButtons.getChildren().add(createRoomButton("1", "беседка"));
        roomButtons.getChildren().add(createRoomButton("2", "АФК"));
        roomButtons.getChildren().add(createRoomButton("---", "-------"));

        this.getChildren().add(roomButtons);
    }

    private Node createRoomButton(String roomId, String roomName) {
        Button button = new Button();
        button.setText(roomName);
        button.setPrefSize(200, 80);
        button.setTextFill(Color.BLUE);
        button.setOnMouseClicked(event -> {
            Connector.sendRoomChangeRequest(roomId);
        });
        return button;
    }


}
