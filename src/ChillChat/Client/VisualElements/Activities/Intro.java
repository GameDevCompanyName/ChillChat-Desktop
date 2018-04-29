package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;

import static ChillChat.Client.Constants.ACTIVITY_CHANGE_TIME;
import static ChillChat.Client.Constants.LOGO_SHOW_TIME;

public class Intro extends Activity {

    public Intro(ActivityManager activityManager) {
        super(activityManager);

        File img = new File("resources/images/logo.png");
        ImageView logoImage = new ImageView(new Image(img.toURI().toString()));

        logoImage.fitHeightProperty().bind(this.heightProperty().divide(3));
        logoImage.fitWidthProperty().bind(this.heightProperty().divide(3));
        logoImage.setOpacity(0.0);

        Text text = new Text();
        text.setText("Добро пожаловать.\nСнова.");
        text.setFont(new Font("Century Gothic Bold", 36));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.LAVENDER);
        text.setOpacity(0.0);

        StackPane intro = new StackPane();
        intro.setAlignment(Pos.CENTER);

        intro.getChildren().addAll(logoImage, text);
        this.getChildren().add(intro);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.3), logoImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition stay = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.1), logoImage);
        stay.setFromValue(1);
        stay.setToValue(1);

        Timeline changeToText = new Timeline();
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(logoImage.opacityProperty(), 1.0)));
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(LOGO_SHOW_TIME*0.22), new KeyValue(logoImage.opacityProperty(), 0.0)));
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(text.opacityProperty(), 0.0)));
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(LOGO_SHOW_TIME*0.22), new KeyValue(text.opacityProperty(), 1.0)));
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), new KeyValue(intro.rotateProperty(), 0)));
        changeToText.getKeyFrames().add(new KeyFrame(Duration.seconds(LOGO_SHOW_TIME*0.35), new KeyValue(intro.rotateProperty(), 360)));

        FadeTransition stayText = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.1), text);
        stayText.setFromValue(1);
        stayText.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.3), text);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeIn.setOnFinished(e -> stay.play());
        stay.setOnFinished(e -> changeToText.play());
        changeToText.setOnFinished(e -> stayText.play());
        stayText.setOnFinished(e -> fadeOut.play());

        fadeOut.setOnFinished(e -> {
            activityManager.goToWithoutRemembering(new LogIn(activityManager));
        });

        fadeIn.play();
    }

    @Override
    public void onCall() {
        //NOTHING
    }

    @Override
    public void onClose() {
        //NOTHING
    }

}
