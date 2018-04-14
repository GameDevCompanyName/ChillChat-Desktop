package ChillChat.Client.VisualElements;

import ChillChat.Client.Network.Connector;
import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomWindow extends Application {

    Undecorator undecorator;
    ActivityManager manager;
    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        Thread.currentThread().setName("MAIN");
        Connector.application = this;

        primaryStage.setTitle("ChillChat");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(true);

        StackPane stackPane = new StackPane();
        manager = new ActivityManager(this);
        stackPane.getChildren().add(manager);

        undecorator = new Undecorator(primaryStage, stackPane);
        primaryStage.setMinWidth(250);
        primaryStage.setMinHeight(250);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("skin/undecorator.css");

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ActivityManager getActivityManager(){
        return manager;
    }

    public void fadeOut() {
        undecorator.switchClose();
    }

}
