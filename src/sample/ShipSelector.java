package sample;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipSelector extends VBox {
    private ImageView circleImage;
    private ImageView shipImage;
    private String circleImageEmpty = "Resources/grey_circle.png";
    private String circleImageSelected = "Resources/green_circle.png";
    private Ship ship;
    private boolean isChosen;

    ShipSelector (Ship ship){
        circleImage = new ImageView(circleImageEmpty);
        shipImage = new ImageView(ship.getUrlShip());
        this.ship = ship;
        isChosen = false;
        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().addAll(circleImage,shipImage);
    }

    public Ship getShip() {
        return ship;
    }

    public void setChosen(boolean isChosen){
        this.isChosen = isChosen;
        String imageToSet = this.isChosen  ? circleImageSelected: circleImageEmpty; //Green circle is used on selected image
        circleImage.setImage(new Image(imageToSet));

    }

}
