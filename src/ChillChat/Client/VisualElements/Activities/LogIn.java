package ChillChat.Client.VisualElements.Activities;

import ChillChat.Client.Connector;
import ChillChat.Client.Utils;
import ChillChat.Client.VisualElements.Utilites.ActivityManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ChillChat.Client.Constants.DEBUG;

public class LogIn extends Activity {

    private VBox box;
    private TextField loginField;
    private PasswordField passwordField;
    private Button acceptButton;

    private Label titleText;
    private Label inputLogin;
    private Label inputPassword;

    private Label loginState;

    public LogIn(ActivityManager activityManager) {
        super(activityManager);

        Connector.logIn = this;

        this.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                tryToLogIn();
            }
        });

        ImageView backgroundImg = new ImageView(Utils.getRandomLogInBackground());
        double scaleCoef = (350 * 500 * 300) / (backgroundImg.getImage().getWidth() * backgroundImg.getImage().getHeight());
        backgroundImg.scaleXProperty().bind(this.widthProperty().multiply(this.heightProperty().divide(500*scaleCoef)));
        backgroundImg.scaleYProperty().bind(this.widthProperty().multiply(this.heightProperty().divide(500*scaleCoef)));
        backgroundImg.setOpacity(0.2);
        this.getChildren().add(backgroundImg);


        Font title = null;
        Font common = null;
        Font input = null;
        Font error = null;

        try {
            common = Font.loadFont(new FileInputStream("resources/commonFont.ttf"), 24);
            title = Font.loadFont(new FileInputStream("resources/nameFont.ttf"), 54);
            input = new Font("Courier New", 24);
            error = Font.loadFont(new FileInputStream("resources/commonFont.ttf"), 28);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.box = new VBox();
        loginField = new TextField();
        passwordField = new PasswordField();
        acceptButton = new Button("CHILLAX");

        Background background = new Background(new BackgroundFill(Color.rgb(35, 35, 45, 0.7), CornerRadii.EMPTY, Insets.EMPTY));
        box.setBackground(background);

        titleText = new Label( "ChillChat");
        inputLogin = new Label("L O G I N");
        inputPassword = new Label("P A S S");
        loginState = new Label("");

        loginField.setStyle("-fx-background-color: transparent; -fx-text-fill: #d1cefa");
        loginField.setFont(input);
        loginField.setScaleX(1.5);
        loginField.setScaleY(1.5);
        loginField.maxWidthProperty().bind(titleText.widthProperty());

        passwordField.setStyle("-fx-background-color: transparent; -fx-text-fill: #d1cefa");
        passwordField.setFont(input);
        passwordField.setScaleX(1.5);
        passwordField.setScaleY(1.5);
        passwordField.maxWidthProperty().bind(titleText.widthProperty());

        titleText.setFont(title);

        inputLogin.setFont(common);
        inputPassword.setFont(common);
        loginState.setFont(error);

        Glow glow = new Glow(1.0);
        loginField.setEffect(glow);
        passwordField.setEffect(glow);

        loginState.setTextFill(Color.RED);
        inputLogin.setStyle("-fx-text-fill: #80a8f0");
        inputPassword.setStyle("-fx-text-fill: #80a8f0");
        titleText.setStyle("-fx-text-fill: rgba(191,190,255,0.95)");

        acceptButton.setOnMouseClicked(e -> tryToLogIn());

        box.getChildren().addAll(titleText, inputLogin, loginField,
                inputPassword, passwordField, loginState);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.maxWidthProperty().bind(titleText.widthProperty().multiply(1.6));
        box.maxHeightProperty().bind(loginState.layoutYProperty().subtract(titleText.layoutYProperty().subtract(15)));
        loginField.setAlignment(Pos.CENTER);
        passwordField.setAlignment(Pos.CENTER);

        StackPane.setAlignment(box, Pos.CENTER);

        if (DEBUG){
            box.setStyle("-fx-border-color: blue");
        }

        this.getChildren().add(box);

        smoothAppear(box, 1.5);

    }

    @Override
    public void onCall() {
        Connector.dropAllTheConnection();
    }

    @Override
    public void onClose() {

    }

    private void smoothAppear(VBox box, double v) {

    }

    public void wrongPass() {
        loginState.setText("Неверный пароль");
    }

    public void disabledSymbols() {
        loginState.setText("Запрещённые символы");
    }

    public void clearErrorField() {
        loginState.setText("");
    }

    public void tooLongInput() {
        loginState.setText("Пароль или логин\nслишком длинный");
    }

    public void wrongInput() {
        loginState.setText("Некорректные данные");
    }

    public void userAlreadyExists() {
        loginState.setText("Юзер с таким логином\nуже подключен");
    }

    public void serverIsUnavalable() {
        loginState.setText("Соединение разорвано.");
    }

    private void tryToLogIn() {

        Connector.createConnectionIfNONE();

        Pattern p = Pattern.compile("[^А-Яа-яA-Za-z0-9_-]");
        Matcher m = p.matcher(loginField.getText());
        if (m.find()){
            return;
        }


        if (passwordField.getText().isEmpty() || loginField.getText().isEmpty()){
            return;
        }

        if (passwordField.getText().length() > 20 || loginField.getText().length() > 20){
            return;
        }

        Connector.sendLogInAttempt(loginField.getText(), passwordField.getText());

    }
    
    

}
