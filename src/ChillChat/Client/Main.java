package ChillChat.Client;

import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import ChillChat.Client.VisualElements.CustomWindow;
import ChillChat.Client.VisualElements.Activities.Intro;
import ChillChat.Client.VisualElements.Utilites.AnimationType;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Connector.initConnector();

        CustomWindow window = new CustomWindow();
        window.start(primaryStage);
        ActivityManager manager = window.getActivityManager();
        Connector.manager = manager;

        Intro intro = new Intro(manager);
        manager.goTo(intro, AnimationType.FADE);

        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)){
                manager.goBack();
            }
        });

    }

}
