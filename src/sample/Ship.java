package sample;

public enum Ship {
    Blue("Resources/playerShip2_blue.png", "Resources/playerLife2_blue.png"),
    Green("Resources/playerShip2_green.png","Resources/playerLife2_green.png"),
    Red("Resources/playerShip2_red.png","Resources/playerLife2_red.png");

    private String urlShip;
    private String urlLife;

    private Ship (String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrlShip(){
        return urlShip;
    }

    public String getUrlLife(){
        return urlLife;
    }
}


