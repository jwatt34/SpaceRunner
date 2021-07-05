package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;


public class MenuButtons extends Button {
    private final static String buttonFreeStyle = "Resources/red_button01.png";
    private final static String buttonPressedStyle = "Resources/red_button02.png";
    private final static int width = 180;
    private final static int height = 50;

    public MenuButtons (String text){
        setText(text);
        setStyle("-fx-text-fill:WHITE");
        setFont(Font.font("Verdana", 20));
        setPrefSize(width, height);
        buttonFreeStyle();
        setButtonListener();
    }

    public void buttonFreeStyle(){          //Button appearance when not pressed
        BackgroundImage backgroundImage = new BackgroundImage(new Image(buttonFreeStyle, width,height,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setPrefHeight(50);
        setLayoutY(getLayoutY()-4);
        setLayoutX(getLayoutX()-4);
    }

    public void buttonPressedStyle(){       //Button appearance when pressed
        BackgroundImage backgroundImage = new BackgroundImage(new Image(buttonPressedStyle,width,height,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
        setPrefHeight(46);
        setLayoutY(getLayoutY()+4);
        setLayoutX(getLayoutX()+4);
    }

    public void setButtonListener(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)){
                    buttonPressedStyle();
                }
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)){
                    buttonFreeStyle();
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
