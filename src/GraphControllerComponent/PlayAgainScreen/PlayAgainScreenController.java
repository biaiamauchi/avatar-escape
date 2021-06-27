package GraphControllerComponent.PlayAgainScreen;

import GraphControllerComponent.Main.iGraphControllerProperties;
import javafx.scene.Group;
import javafx.scene.Scene;

public class PlayAgainScreenController {
    private iGraphControllerProperties screen;

    public PlayAgainScreenController(iGraphControllerProperties screen){
        this.screen = screen;
    }

    public Scene playAgainScreen(String messageSource){
        PlayAgainScreenDesigner playAgainScreen = new PlayAgainScreenDesigner();
        Group root = playAgainScreen.groupScene(screen, messageSource);

        return new Scene(root, 1200, 650);
    }
}
