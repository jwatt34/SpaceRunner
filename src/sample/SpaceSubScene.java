package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SpaceSubScene extends SubScene {
    private final static String backgroundPath = "Resources/grey_panel.png";
    private final static int width = 500;
    private final static int height = 400;
    private boolean isHidden;

    public SpaceSubScene() {
        super(new AnchorPane(), width, height);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(backgroundPath, width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(backgroundImage));
        setLayoutX(150);
        setLayoutY(1200);
        isHidden = true;
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(.5));
        transition.setNode(this);
        if(isHidden){                                   //Moves subscene up if it is off screen
            transition.setToY(-1150);
            isHidden = false;
        }else{                                          //Moves subscene off screen if it is visible
            transition.setToY(1150);
            isHidden = true;
        }
        transition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane)this.getRoot();
    }

}
