package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private Stage stage;
    private Scene scene;
    private AnchorPane mainScreen;
    private final static int width = 800;
    private final static int height = 600;
    private List<MenuButtons> menuButtons;
    private static int menuButtonX = 195;
    private static int menuButtonY = 480;
    private SpaceSubScene spaceSubScene;
    private List<ShipSelector> shipSelectorList;
    private Ship chosenShip;
    private final static int selectorsX = 70;
    private final static int selectorsY = 300;


    ViewManager(){
        menuButtons = new ArrayList<>();
        mainScreen = new AnchorPane();
        scene = new Scene(mainScreen, width, height);
        stage = new Stage();
        stage.setScene(scene);
        createBackground();
        createStartButton();
        createExitButton();
        createLogo();
        shipMenu();
    }

    public Stage getStage(){
        return stage;
    }

    private void createStartButton(){
        MenuButtons startButton = new MenuButtons("START");
        addMenuButtons(startButton);
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {  //Brings in Ship Selector subscene
            @Override
            public void handle(MouseEvent event) {
                spaceSubScene.moveSubScene();
            }
        });
    }

    private void createExitButton(){
        MenuButtons exitButton = new MenuButtons("EXIT");
        addMenuButtons(exitButton);
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });
    }

    private void shipMenu(){
        spaceSubScene = new SpaceSubScene();
        SelectorLabel label = new SelectorLabel("CHOOSE YOUR SHIP");
        label.setLayoutY(35);
        label.setLayoutX(100);
        spaceSubScene.getPane().getChildren().addAll(shipOptions(),label, startButton(), closeButton());
        mainScreen.getChildren().add(spaceSubScene);
    }

    private HBox shipOptions(){
        HBox hBox = new HBox(20);
        shipSelectorList = new ArrayList<>();
        for (Ship ship: Ship.values()){
            ShipSelector shipSelector = new ShipSelector(ship);
            shipSelectorList.add(shipSelector);
            hBox.getChildren().add(shipSelector);
            shipSelector.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {                      //Prevents selecting multiple ships
                    for(ShipSelector ship : shipSelectorList){
                        ship.setChosen(false);
                    }
                    shipSelector.setChosen(true);
                    chosenShip = shipSelector.getShip();
                }
            });
            shipSelector.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    shipSelector.setEffect(new DropShadow());
                }
            });
            shipSelector.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    shipSelector.setEffect(null);
                }
            });
        }
        hBox.setLayoutX(60);
        hBox.setLayoutY(150);
        return hBox;
    }

    private MenuButtons startButton(){
        MenuButtons start = new MenuButtons("PLAY");
        start.setLayoutX(selectorsX);
        start.setLayoutY(selectorsY);
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            if (chosenShip != null){
                GameView gameView = new GameView();
                gameView.createNewGame(chosenShip,stage);
                } else {                                              //Error message if a ship is not chosen
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText(null);
                alert.setContentText("You're going to need a ship!");
                alert.showAndWait();
            }
            }
        });
        start.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                start.setStyle("-fx-text-fill:LAWNGREEN");
            }
        });
        start.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                start.setStyle("-fx-text-fill:WHITE");
            }
        });
        return start;
    }
    private MenuButtons closeButton(){
        MenuButtons close = new MenuButtons("CLOSE");
        close.setLayoutX(selectorsX + 180);
        close.setLayoutY(selectorsY);
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                  //Removes subscene
                spaceSubScene.moveSubScene();
            }
        });
        close.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setStyle("-fx-text-fill:LAWNGREEN");
            }
        });
        close.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setStyle("-fx-text-fill:WHITE");
            }
        });
        return close;
    }

    private void addMenuButtons(MenuButtons button){
        button.setLayoutX(menuButtonX + menuButtons.size() * 230);
        button.setLayoutY(menuButtonY);
        menuButtons.add(button);
        mainScreen.getChildren().add(button);
    }

    private void createBackground(){
        BackgroundImage backgroundImage = new BackgroundImage(new Image("Resources/blue.png",width,height,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        Background background = new Background(backgroundImage);
        mainScreen.setBackground(background);
    }

    private void createLogo(){
        ImageView logo = new ImageView("Resources/RunnerLogo.png");
        logo.setLayoutX(138);
        logo.setLayoutY(50);
        mainScreen.getChildren().add(logo);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });
    }
}
