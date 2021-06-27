package GraphControllerComponent.InitialScreen;

import GameControllerComponent.iGameControllerProperties;
import GraphControllerComponent.Main.GraphController;
import GraphControllerComponent.Main.iGraphControllerProperties;
import javafx.scene.Group;
import javafx.scene.Scene;

public class InitialScreenController {
    private iGraphControllerProperties screen;

    public InitialScreenController(iGraphControllerProperties screen){
        this.screen = screen;
    }

    public Scene initialScene(){
        InitialScreenDesigner initialScreen = new InitialScreenDesigner();
        Group root = initialScreen.groupScene(screen);

        return new Scene(root, 1200, 650);
    }
}