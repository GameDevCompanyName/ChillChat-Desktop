package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static ChillChat.Client.Constants.ACTIVITY_CHANGE_TIME;
import static ChillChat.Client.Constants.DEBUG;

public class Activity extends StackPane {
    
    ActivityManager activityManager;

    public Activity(ActivityManager activityManager){
        if (DEBUG){
            this.setStyle("-fx-border-color: RED");
        }
        this.activityManager = activityManager;
        this.prefHeightProperty().bind(activityManager.heightProperty());
        this.prefWidthProperty().bind(activityManager.widthProperty());
    }

    public void slideToLeft() {
        slide(false);
    }

    public void slideToRight() {
        slide(true);
    }

    private void slide(boolean toRight){
        if (!activityManager.getChildren().contains(this))
            return;

        Timeline flush = new Timeline();

//        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.scaleXProperty(), 1.0)));
//        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.scaleYProperty(), 1.0)));
//        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.scaleXProperty(), 0.8)));
//        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.scaleYProperty(), 0.4)));

        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 1.0)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.opacityProperty(), 0.0)));

        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.translateXProperty(), 0)));

        if (toRight){
            flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.translateXProperty(), + 1500)));
        } else {
            flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.translateXProperty(), - 1500)));
        }
        flush.play();
        flush.setOnFinished(e -> activityManager.getChildren().remove(this));
    }


    public void appearFromLeft() {
        appear(false);
    }

    public void appearFromRight() {
        appear(true);
    }

    private void appear(boolean fromRight){

        if (activityManager.getChildren().contains(this))
            return;

        if (fromRight)
            this.setTranslateX(1500);
        else
            this.setTranslateX(-1500);

        activityManager.getChildren().add(this);

        Timeline appear = new Timeline();

        //appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.scaleXProperty(), 0.3)));
        //appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.scaleYProperty(), 0.15)));
        //appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.scaleXProperty(), 1.0)));
        //appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.scaleYProperty(), 1.0)));

        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 0.0)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME/1.5), new KeyValue(this.opacityProperty(), 1.0)));

        if (fromRight)
            appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.translateXProperty(), 1500)));
        else
            appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.translateXProperty(), -1500)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.translateXProperty(), 0)));

        appear.play();
    }


    public void fadeIn(){
        if (activityManager.getChildren().contains(this))
            return;

        activityManager.getChildren().add(this);

        Timeline fadeIn = new Timeline();

        fadeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 0.0)));
        fadeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME*1.2), new KeyValue(this.opacityProperty(), 1.0)));

        fadeIn.play();

    }

    public void fadeOut(){
        if (!activityManager.getChildren().contains(this))
            return;

        Timeline fadeIn = new Timeline();

        fadeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 1.0)));
        fadeIn.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME*1.2), new KeyValue(this.opacityProperty(), 0.0)));

        fadeIn.play();
        fadeIn.setOnFinished(event -> {
            activityManager.getChildren().remove(this);
        });

    }

}
