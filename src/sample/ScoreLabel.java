package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class ScoreLabel extends Label {
    ScoreLabel(String text){
        setText(text);
        setPrefHeight(50);
        setPrefWidth(127);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("Resources/green_button13.png",127,50,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER);
        setFont(Font.font("Verdana", 15));

    }

}
