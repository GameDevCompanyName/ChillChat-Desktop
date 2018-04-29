package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static ChillChat.Client.Constants.ACTIVITY_CHANGE_TIME;
import static ChillChat.Client.Constants.DEBUG;

public abstract class Activity extends StackPane {
    
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

        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 1.0)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME), new KeyValue(this.opacityProperty(), 0.0)));

        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.translateXProperty(), 0)));

        int k = -1;
        if (toRight)
            k = 1;

        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 1.0), new KeyValue(this.translateXProperty(), k * 1500)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.9), new KeyValue(this.translateXProperty(), k * 1470)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.8), new KeyValue(this.translateXProperty(), k * 1410)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.7), new KeyValue(this.translateXProperty(), k * 1300)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.6), new KeyValue(this.translateXProperty(), k * 1120)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.5), new KeyValue(this.translateXProperty(), k * 950)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.4), new KeyValue(this.translateXProperty(), k * 500)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.3), new KeyValue(this.translateXProperty(), k * 200)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.2), new KeyValue(this.translateXProperty(), k * 80)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.1), new KeyValue(this.translateXProperty(), k * 30)));
        flush.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.0), new KeyValue(this.translateXProperty(), k * 0.0)));

        flush.play();
        flush.setOnFinished(e -> {
            activityManager.getChildren().remove(this);
            onClose();
        });

    }


    public void appearFromLeft() {
        appear(false);
    }

    public void appearFromRight() {
        appear(true);
    }

    private void appear(boolean fromRight){

        onCall();

        if (activityManager.getChildren().contains(this))
            return;

        if (fromRight)
            this.setTranslateX(1500);
        else
            this.setTranslateX(-1500);

        activityManager.getChildren().add(this);

        int k = 1;
        if (!fromRight)
            k = -1;

        Timeline appear = new Timeline();

        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(this.opacityProperty(), 0.0)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME/1.5), new KeyValue(this.opacityProperty(), 1.0)));

        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.0), new KeyValue(this.translateXProperty(), k * 1500)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.1), new KeyValue(this.translateXProperty(), k * 1470)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.2), new KeyValue(this.translateXProperty(), k * 1410)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.3), new KeyValue(this.translateXProperty(), k * 1300)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.4), new KeyValue(this.translateXProperty(), k * 1120)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.5), new KeyValue(this.translateXProperty(), k * 950)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.6), new KeyValue(this.translateXProperty(), k * 500)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.7), new KeyValue(this.translateXProperty(), k * 200)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.8), new KeyValue(this.translateXProperty(), k * 80)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 0.9), new KeyValue(this.translateXProperty(), k * 30)));
        appear.getKeyFrames().add(new KeyFrame(Duration.seconds(ACTIVITY_CHANGE_TIME * 1.0), new KeyValue(this.translateXProperty(), k * 0.0)));

        appear.play();
    }


    public void fadeIn(){

        onCall();

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
            onClose();
        });

    }

    abstract public void onCall();

    abstract public void onClose();

}
