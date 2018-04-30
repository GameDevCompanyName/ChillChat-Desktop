package ChillChat.Client.VisualElements.Elements;

import ChillChat.Client.VisualElements.Activities.Messenger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static ChillChat.Client.Constants.DEBUG;

public class ChillTextPane extends StackPane {

    MyTextArea textArea;
    Rectangle backRect;

    public ChillTextPane(Font font, Messenger messenger){

        this.setAlignment(Pos.BOTTOM_LEFT);
        this.prefWidthProperty().bind(messenger.widthProperty());
        this.maxWidthProperty().bind(messenger.widthProperty());

        textArea = new MyTextArea(messenger, this);
        if (DEBUG){
            textArea.setStyle("-fx-border-color: red");
            this.setStyle("-fx-border-color: GREEN");
        }
        textArea.setFont(font);

        backRect = new Rectangle();

        messenger.widthProperty().addListener(e -> {
            resizeField(messenger);
        });
        //backRect.widthProperty().bind(this.widthProperty().subtract(13));

        textArea.heightProperty().addListener(e -> {
            smoothResizeRectangle();
        });

        backRect.setArcHeight(40);
        backRect.setArcWidth(40);
        backRect.setFill(Color.rgb(15, 15, 30));

        this.getChildren().addAll(backRect, textArea);
        //this.getChildren().addAll(textArea);

    }

    private void resizeField(Messenger messenger) {
        Timeline resize = new Timeline();

        resize.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(backRect.widthProperty(), backRect.getWidth())),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(textArea.prefWidthProperty(), textArea.getWidth()))
        );

        resize.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.001), new KeyValue(backRect.widthProperty(), messenger.getWidth() - 25)),
                new KeyFrame(Duration.seconds(0.001), new KeyValue(textArea.prefWidthProperty(), messenger.getWidth() - 20))
        );

        resize.play();
    }

    private void smoothResizeRectangle() {
        Timeline resize = new Timeline();

        resize.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(backRect.heightProperty(), backRect.getHeight())));

        resize.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.2), new KeyValue(backRect.heightProperty(), textArea.getHeight()-2)));

        resize.play();
    }


    public String getText() {
        return textArea.getText();
    }

    public void clear() {
        textArea.setText("");
    }

    public void changeColor(String color){

        final String webColorString;

        System.out.println("ПЫТАЮСЬ УСТАНОВИТЬ ЦВЕТ " + color);

        switch (color){
            case "1":
                webColorString = "#f44336";
                break;
            case "2":
                webColorString = "#3f51b5";
                break;
            case "3":
                webColorString = "#29b6f6";
                break;
            case "4":
                webColorString = "#ff5722";
                break;
            case "5":
                webColorString = "#4caf50";
                break;
            case "6":
                webColorString = "#8bc34a";
                break;
            case "7":
                webColorString = "#ffeb3b";
                break;
            case "8":
                webColorString = "#ec407a";
                break;
            default:
                webColorString = "#546e7a";
                break;
        }

        FadeTransition fadeTransition = new FadeTransition(
                Duration.seconds(1.0),
                textArea
        );
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> {
            textArea.setStyle("-fx-text-fill: " + webColorString + ";");
            FadeTransition fadeInTransition = new FadeTransition(
                    Duration.seconds(1.0),
                    textArea
            );
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        });
        fadeTransition.play();

    }
}
