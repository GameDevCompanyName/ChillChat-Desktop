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
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;


public class RoomChanger extends Activity {

    TilePane roomCards;
    ScrollPane scrollRooms;
    Map<String, RoomCard> cards;

    public RoomChanger(ActivityManager activityManager) {
        super(activityManager);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        Glow glow = new Glow();
        glow.setLevel(0.7);

        scrollRooms = new ScrollPane();
        scrollRooms.prefWidthProperty().bind(this.widthProperty());
        scrollRooms.maxWidthProperty().bind(this.widthProperty());
        scrollRooms.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        roomCards = new TilePane();
        roomCards.setEffect(glow);
        roomCards.setTileAlignment(Pos.TOP_CENTER);
        roomCards.setHgap(20);
        roomCards.setVgap(20);

        roomCards.maxWidthProperty().bind(scrollRooms.widthProperty());
        scrollRooms.setContent(roomCards);
        this.getChildren().add(scrollRooms);

        cards = new HashMap<>();

    }

    @Override
    public void onCall() {
        Connector.roomChanger = this;
        Connector.requestRoomsInfo();
    }

    @Override
    public void onClose() {
        //Connector.roomChanger = null;
    }

    public void addCards(String[] roomIds) {
        for (String id: roomIds){
            addCard(id);
        }
    }

    private void addCard(String id) {

        System.out.println(id);
        RoomCard card = new RoomCard(id);
        cards.put(id, card);
        roomCards.getChildren().add(card);
    }

    public void cardInfo(String id, String name, String people) {
        if (id.equals("3") || id.equals("2"))
            return;
        System.out.println(id);
        cards.get(id).setInfo(name, people);
    }

}
