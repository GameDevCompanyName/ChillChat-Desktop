package ChillChat.Client.VisualElements.Utilites;

import ChillChat.Client.VisualElements.Activities.Activity;
import ChillChat.Client.VisualElements.CustomWindow;
import ChillChat.Client.VisualElements.Utilites.AnimationType;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

import java.util.Stack;

import static ChillChat.Client.Constants.DEBUG;

public class ActivityManager extends StackPane {

    CustomWindow window;
    Stack<Activity> stack;
    Activity lastActive;

    public ActivityManager(CustomWindow window){
        this.setPadding(new Insets(36, 0, 0, 0));
        this.window = window;
        this.stack = new Stack<>();
        if (DEBUG){
            this.setStyle("-fx-border-color: BLUE");
        }
    }

    public void goTo(Activity activity, AnimationType animationType){
        if (lastActive != null){
            stack.push(lastActive);
            if (animationType.equals(AnimationType.SLIDE))
                lastActive.slideToLeft();
        }
        lastActive = activity;
        if (animationType.equals(AnimationType.SLIDE))
            lastActive.appearFromRight();
        if (animationType.equals(AnimationType.FADE))
            lastActive.fadeIn();
    }

    public void goToWithoutRemembering(Activity activity){
        lastActive.slideToLeft();
        lastActive = activity;
        lastActive.appearFromRight();
    }

    public void goBack(){
        if (lastActive != null){
            lastActive.slideToRight();
        }
        if (stack.isEmpty())
            window.fadeOut();
        else {
            lastActive = stack.pop();
            lastActive.appearFromLeft();
        }
    }

}
