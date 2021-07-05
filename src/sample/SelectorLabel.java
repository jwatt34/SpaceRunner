package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SelectorLabel extends Label {
    private final static String backgroundPath = "Resources/green_button13.png";
    private final static int width = 300;
    private final static int height = 40;

    public SelectorLabel(String text){
        setText(text);
        setFont(Font.font("Verdana",18));
        setPrefSize(width, height);
        setAlignment(Pos.CENTER);
        setBackground();
    }

    private void setBackground(){
        BackgroundImage backgroundImage = new BackgroundImage(new Image(backgroundPath,width,height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }
}
