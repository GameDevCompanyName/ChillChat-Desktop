package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.VisualElements.ActivityManager;
import ChillChat.Client.VisualElements.LogIn;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;

import static ChillChat.Client.Constants.LOGO_SHOW_TIME;

public class Intro extends Activity {

    public Intro(ActivityManager activityManager) {

        super(activityManager);

        File img = new File("resources/images/logo.png");
        ImageView logoImage = new ImageView(new Image(img.toURI().toString()));

        logoImage.fitHeightProperty().bind(this.heightProperty().divide(3));
        logoImage.fitWidthProperty().bind(this.heightProperty().divide(3));
        logoImage.setOpacity(0.0);

        this.getChildren().add(logoImage);

        StackPane.setAlignment(logoImage, Pos.CENTER);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.2), logoImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition stay = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.6), logoImage);
        stay.setFromValue(1);
        stay.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(LOGO_SHOW_TIME * 0.2), logoImage);
        stay.setFromValue(1);
        stay.setToValue(0);

        fadeIn.setOnFinished(e -> stay.play());
        stay.setOnFinished(e -> fadeOut.play());

        fadeOut.setOnFinished(e -> {
            activityManager.goToWithoutRemembering(new LogIn(activityManager));
        });

        fadeIn.play();
    }
}
