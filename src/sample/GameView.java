package sample;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class GameView {
    private Stage gameStage;
    private Scene gameScene;
    private AnchorPane gamePane;
    private static double stageWidth = 700;
    private static double stageHeight = 600;
    private int angle;
    private Stage menuStage;
    private ImageView playerShip;
    private ImageView laser;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    boolean isShooting;
    boolean shotOnScreen;
    AnimationTimer gameTimer;
    private final static String backgroundPath1 = "Resources/blue.png";
    private final static String laserPath = "Resources/laserRed16.png";
    private final static String meteorBrownPath = "Resources/meteorBrown_small1.png";
    private final static String meteorGreyPath = "Resources/meteorGrey_small1.png";
    private GridPane gridPane1;
    private GridPane gridPane2;
    private ImageView[] brownMeteor;
    private ImageView[] greyMeteor;
    private final int numberOfMeteors = 4;
    private int lifeCount;
    private ImageView[] life;
    ScoreLabel scoreLabel;
    private double score;
    Random randomPosition;
    private int shipDiameter;
    private int shipHeight;
    private int meteorDiameter;
    private int laserHeight;
    private int laserWidth;
    private ImageView goldStar;
    private final static String goldStarPath = "Resources/star_gold.png";
    private int starWidth;
    private Image explosion1;
    private Image explosion2;
    private Image explosion3;
    private final static String explosionPath1 = "Resources/playerShip3_damage3.png";
    private final static String explosionPath2 = "Resources/playerShip3_damage2.png";
    private final static String explosionPath3 = "Resources/playerShip3_damage1.png";
    private Timeline timeline;
    private double speedIncrease;
    private ImageView yellowBolt;
    private int yellowBoltWidth;
    private final static String yellowBoltPath = "Resources/powerupYellow_bolt.png";
    private int yellowCount;
    private double savedScore;


    public GameView(){
        initializeStage();
        randomPosition = new Random();
    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, stageWidth, stageHeight);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Ship ship, Stage menuStage){
        this.menuStage = menuStage;
        createBackground();
        menuStage.hide();
        addShip(ship);
        createElements(ship);
        createHurtBox(ship);
        gameLoop();
        createKeyListeners();
        gameStage.show();
    }

    public void addShip(Ship chosenShip){            //Positions chosen ship
        playerShip = new ImageView(chosenShip.getUrlShip());
        playerShip.setLayoutX(stageWidth/2 - 60);
        playerShip.setLayoutY(stageHeight-85);
        gamePane.getChildren().add(playerShip);
    }

    private void createHurtBox(Ship chosenShip){     //Creates area where game elements detect collisions
        shipDiameter = (int)(new Image(chosenShip.getUrlShip())).getWidth();
        shipHeight = (int)(new Image(chosenShip.getUrlShip()).getHeight());
        meteorDiameter = (int)(new Image("Resources/meteorGrey_small1.png")).getWidth();
        laserWidth = (int)(new Image(  "Resources/laserRed16.png")).getWidth();
        laserHeight = (int)(new Image("Resources/laserRed16.png")).getHeight();
        starWidth = (int)(new Image(goldStarPath).getWidth());
        yellowBoltWidth = (int)(new Image("Resources/powerupYellow_bolt.png").getWidth());
    }

    private void createElements(Ship chosenShip){
        scoreLabel = new ScoreLabel("SCORE: 0");
        scoreLabel.setLayoutX(550);
        scoreLabel.setLayoutY(20);
        gamePane.getChildren().add(scoreLabel);
        lifeCount = 2;
        life = new ImageView[lifeCount + 1];
        for(int i = 0; i < life.length; i++){
            life[i] = new ImageView(chosenShip.getUrlLife());
            life[i].setLayoutX(550 + i * 45);
            life[i].setLayoutY(70);
            gamePane.getChildren().add(life[i]);
        }
        brownMeteor = new ImageView[numberOfMeteors];
        for(int i = 0; i < brownMeteor.length; i++){
            brownMeteor[i] = new ImageView(meteorBrownPath);
            addMeteors(brownMeteor[i]);
            gamePane.getChildren().add(brownMeteor[i]);
        }
        greyMeteor = new ImageView[numberOfMeteors];
        for(int i = 0; i < greyMeteor.length; i++){
            greyMeteor[i] = new ImageView(meteorGreyPath);
            addMeteors(greyMeteor[i]);
            gamePane.getChildren().add(greyMeteor[i]);
        }
        goldStar = new ImageView(goldStarPath);
        addMeteors(goldStar);
        gamePane.getChildren().add(goldStar);
        yellowBolt = new ImageView(yellowBoltPath);
        addMeteors(yellowBolt);
        if(yellowCount < 1){
            addMeteors(yellowBolt);
        }
        gamePane.getChildren().add(yellowBolt);

    }

    private void addMeteors(ImageView image){        //Starting position of space elements
        if (image == goldStar) {
            image.setLayoutX(randomPosition.nextInt(650));
            image.setLayoutY(-randomPosition.nextInt(2000) - 1000);
        } else if (image == yellowBolt && yellowCount < 1){
            image.setLayoutX(randomPosition.nextInt(650));
            image.setLayoutY(-randomPosition.nextInt(2000) - 3500);
        }

        else {
            image.setLayoutX(randomPosition.nextInt(650));
            image.setLayoutY(-randomPosition.nextInt(1000)-300);
        }
    }

    private void moveMeteors(){
        goldStar.setLayoutY(goldStar.getLayoutY() + 3);
        yellowBolt.setLayoutY(yellowBolt.getLayoutY()+4);
        speedIncrease =  (score/10) - ((score/10) * yellowCount/2);
        if (yellowCount > 0 ){                      //YellowBolt slows meteor speed until score increases by 5
            int scoreIncrease = 5;
            if (score - getSavedScore() == scoreIncrease){
                yellowCount = 0;
            }
        }
        for(int i = 0; i<brownMeteor.length; i++){
            brownMeteor[i].setLayoutY(brownMeteor[i].getLayoutY()+3.5+speedIncrease);
            brownMeteor[i].setRotate(brownMeteor[i].getRotate()+4);
            }

        for(int i = 0; i<greyMeteor.length; i++){
            greyMeteor[i].setLayoutY(greyMeteor[i].getLayoutY()+2.5+speedIncrease);
            greyMeteor[i].setRotate(greyMeteor[i].getRotate()-2);

        }
    }

    private void checkPosition(){                   //Resets position of offscreen elements
        for(int i = 0; i < brownMeteor.length; i++){
            if (brownMeteor[i].getLayoutY() > 1000){
                addMeteors(brownMeteor[i]);
            }
        }
        for(int i = 0; i < greyMeteor.length; i++) {
            if (greyMeteor[i].getLayoutY() > 1000) {
                addMeteors(greyMeteor[i]);
            }
        }
        if (goldStar.getLayoutY() > 1300){
            addMeteors(goldStar);
        }
        if (yellowBolt.getLayoutY() > 2300 && yellowCount < 1){
            addMeteors(yellowBolt);
        }
    }

    public void createKeyListeners(){
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT){
                    isLeftPressed = true;
                }
                else if(event.getCode() == KeyCode.RIGHT){
                    isRightPressed = true;
                }
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.LEFT){
                    isLeftPressed = false;
                }
                else if(event.getCode() == KeyCode.RIGHT){
                    isRightPressed = false;
                }
                else if(event.getCode() == KeyCode.SPACE && !shotOnScreen){
                    isShooting = true;
                    addLaser();

                }
            }
        });
    }

    private void gameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveShip();
                moveBackground();
                shootLaser();
                moveMeteors();
                checkPosition();
                detectCollision();
                destroyMeteor();

            }
        };
        gameTimer.start();
    }

    private void moveShip() {                       //Tilts and moves ship in direction of keys pressed
        if (isLeftPressed && !isRightPressed) {
            if (angle > -25){
                angle -= 5;
            }
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() > -10) {
                playerShip.setLayoutX(playerShip.getLayoutX() - 5);
            }
        }
        if (!isLeftPressed && isRightPressed) {
            if (angle < 25){
                angle += 5;
            }
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() < 600) {
                playerShip.setLayoutX(playerShip.getLayoutX() + 5);
            }
        }
        if (isLeftPressed && isRightPressed) {
            angle = 0;
            playerShip.setRotate(angle);
        }
        if (!isLeftPressed && !isRightPressed) {
            angle = 0;
            playerShip.setRotate(angle);
        }
    }

    private void addLaser(){
        laser = new ImageView(laserPath);
        laser.setLayoutX(playerShip.getLayoutX()+50);
        laser.setLayoutY(stageHeight-100);
        gamePane.getChildren().add(laser);
    }

    public void shootLaser(){
        if(isShooting ){                            //Fires when space key is released
            laser.setLayoutY(laser.getLayoutY()-30);
            if (laser.getLayoutY() > -65){
                shotOnScreen = true;
            } else {
                shotOnScreen = false;

            }
        }
    }

    public void increaseScore(int increase){
        score += increase;
        int displayedScore = (int)score;
        scoreLabel.setText("SCORE: " + displayedScore);
    }

    private void createBackground(){
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        for (int i = 0; i < 12; i++){
            ImageView background1 = new ImageView(backgroundPath1);
            ImageView background2 = new ImageView(backgroundPath1);
            background1.setFitHeight(610);
            background2.setFitHeight(600);
            GridPane.setConstraints(background1, i%3, 1/3);
            GridPane.setConstraints(background2, i%3, 1/3);
            gridPane1.getChildren().add(background1);
            gridPane2.getChildren().add(background2);
        }
        gridPane2.setLayoutY(-600);
        gamePane.getChildren().addAll(gridPane1,gridPane2);
    }
    private void moveBackground(){                  //Moves background down the screen
        gridPane1.setLayoutY(gridPane1.getLayoutY()+1);
        gridPane2.setLayoutY(gridPane2.getLayoutY()+1);
        if(gridPane1.getLayoutY() > 600){
            gridPane1.setLayoutY(-600);
        }
        if(gridPane2.getLayoutY() > 600){
            gridPane2.setLayoutY(-600);
        }
    }

    private void detectCollision(){                 //Detects if ship collides with space elements
        for (int i = 0; i < brownMeteor.length; i++){
            if(calculateDistance(playerShip.getLayoutX()+shipDiameter/2, brownMeteor[i].getLayoutX()+meteorDiameter/2,
                    playerShip.getLayoutY()+shipHeight/2, brownMeteor[i].getLayoutY()+meteorDiameter/2) <=
                    (shipHeight/2) + (meteorDiameter/2)){
                meteorExplode(brownMeteor[i]);
                loseLife();
            }
        }
        for (int i = 0; i < greyMeteor.length; i++){
            if(calculateDistance(playerShip.getLayoutX()+shipDiameter/2, greyMeteor[i].getLayoutX()+meteorDiameter/2,
                    playerShip.getLayoutY()+shipHeight/2, greyMeteor[i].getLayoutY()+meteorDiameter/2) <=
                    (shipDiameter/2) + (meteorDiameter/2)){
                meteorExplode(greyMeteor[i]);
                loseLife();
            }
        }
        if(calculateDistance(playerShip.getLayoutX()+shipDiameter/2, goldStar.getLayoutX()+starWidth/2,
                playerShip.getLayoutY()+shipHeight/2, goldStar.getLayoutY()+starWidth/2) <=
                (shipDiameter/2) + (starWidth/2)){
            addMeteors(goldStar);
            increaseScore(5);
        }
        if(calculateDistance(playerShip.getLayoutX()+shipDiameter/2, yellowBolt.getLayoutX()+yellowBoltWidth/2,
                playerShip.getLayoutY()+shipHeight/2, yellowBolt.getLayoutY()+yellowBoltWidth/2) <=
                (shipDiameter/2) + (yellowBoltWidth/2)){
            setSavedScore(score);
            if(yellowCount == 0){
                addMeteors(yellowBolt);
            }
            yellowCount = 1;
        }

    }

    private void destroyMeteor(){                   //Checks if laser collides with space elements
        for(int i = 0; i < brownMeteor.length; i++){
            if(shotOnScreen){
                if(calculateDistance(laser.getLayoutX()+laserWidth/2, brownMeteor[i].getLayoutX()+meteorDiameter/2,
                        laser.getLayoutY(), brownMeteor[i].getLayoutY()+meteorDiameter/2) <=
                        (laserWidth) + (meteorDiameter/2)){
                    meteorExplode(brownMeteor[i]);
                    increaseScore(1);
                    gamePane.getChildren().remove(laser);
                    shotOnScreen = false;
                    isShooting = false;
                }
            }
        }
        for(int i = 0; i < greyMeteor.length; i++){
            if(shotOnScreen){
                if(calculateDistance(laser.getLayoutX()+laserWidth/2, greyMeteor[i].getLayoutX()+meteorDiameter/2,
                        laser.getLayoutY(), greyMeteor[i].getLayoutY()+meteorDiameter/2) <=
                        (laserWidth) + (meteorDiameter/2)){
                    meteorExplode(greyMeteor[i]);
                    increaseScore(1);
                    gamePane.getChildren().remove(laser);
                    shotOnScreen = false;
                    isShooting = false;
                }
            }
        }
    }

    private void meteorExplode(ImageView meteor){   //Animates explosion effect
        int explosionX = (int)meteor.getLayoutX();
        int explosionY = (int)meteor.getLayoutY();
        ImageView iview = new ImageView();
        explosion1 = new Image(explosionPath1);
        explosion2 = new Image(explosionPath2);
        explosion3 = new Image(explosionPath3);
        iview.setFitHeight(30);
        iview.setFitWidth(35);
        iview.setLayoutX(explosionX);
        iview.setLayoutY(explosionY);
        addMeteors(meteor);
        gamePane.getChildren().add(iview);
        timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(iview.imageProperty(), explosion1)),
                new KeyFrame(Duration.seconds(.1), new KeyValue(iview.imageProperty(), explosion2)),
                new KeyFrame(Duration.seconds(.2), new KeyValue(iview.imageProperty(), explosion3)),
        new KeyFrame(Duration.seconds(.3), new KeyValue(iview.imageProperty(), null)));
        timeline.play();

    }

    private void setSavedScore(double score){
        savedScore = score;
    }
    private double getSavedScore(){
        return savedScore;
    }

    private double calculateDistance(double x1, double x2, double y1, double y2){
        double distance = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
        return distance;
    }

    private void loseLife(){
        gamePane.getChildren().remove(life[lifeCount]);
        lifeCount--;
        if(lifeCount < 0){
            gameTimer.stop();
            gameStage.close();
            menuStage.show();
        }

    }

}
