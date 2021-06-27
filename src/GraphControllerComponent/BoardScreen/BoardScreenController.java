package GraphControllerComponent.BoardScreen;

import CharacterComponent.Villains;
import GameControllerComponent.GameController;
import GraphControllerComponent.FightScreen.FightScreenController;
import GraphControllerComponent.PlayAgainScreen.PlayAgainScreenController;
import GraphControllerComponent.Main.iGraphControllerProperties;
import exceptions.InvalidKey.InvalidKeyBoard;
import exceptions.InvalidMovement.OutOfBoard;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Random;

public class BoardScreenController extends GameController {
    private ImageView boardCharacters[][] = new ImageView[6][6];
    private ImageView avatar = new ImageView();
    private ProgressBar avatarLife = new ProgressBar();
    private ProgressBar avatarScore = new ProgressBar();
    private Label avatarLifeLabel = new Label();
    private Label avatarScoreLabel = new Label();
    private Label message = new Label();

    private iGraphControllerProperties screen;

    public BoardScreenController(iGraphControllerProperties screen){
        this.screen = screen;
    }

    public Scene boardScreen(){
        screen.getStage().setTitle("Avatar Escape - Fase " + screen.getGame().getBoard().getBoard().getLevel());
        String map = "src/assets/maps/level" + screen.getGame().getBoard().getBoard().getLevel() + "/fase" + screen.getGame().getBoard().getBoard().getLevel() + "." + String.valueOf(new Random().nextInt(15) + 1) + ".csv";
        this.screen.getGame().play(map);

        String screenName = "Fase - " + screen.getGame().getBoard().getBoard().getLevel();
        BoardScreenDesigner boardScreen = new BoardScreenDesigner();
        Group root = boardScreen.groupScene(screen,
                                            boardCharacters, avatar,
                                            avatarLife, avatarLifeLabel,
                                            avatarScore, avatarScoreLabel,
                                            message);

        Scene cena = new Scene(root, 1200, 600);
        cena.addEventHandler(KeyEvent.KEY_PRESSED, movementsEvent);
        return cena;
    }


    EventHandler<KeyEvent> movementsEvent = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            message.setText("");
            try {
                validMovement(event.getCode());
                verifyCell();
            } catch(OutOfBoard ex){
                message.setText(ex.getMessage());
            } catch(InvalidKeyBoard ex){
                message.setText(ex.getMessage());
            }
        }
    };

    public void validMovement(KeyCode key) throws OutOfBoard, InvalidKeyBoard {
        if (key == KeyCode.DOWN) {
            if (avatar.getY() >= 10 && avatar.getY() < 510)
                avatar.setY(avatar.getY() + 100);
            else
                throw new OutOfBoard("Ande no Tabuleiro");
        } else if (key == KeyCode.UP) {
            if (avatar.getY() > 10 && avatar.getY() <= 510)
                avatar.setY(avatar.getY() - 100);
            else
                throw new OutOfBoard("Ande no Tabuleiro");
        } else if (key == KeyCode.LEFT) {
            if (avatar.getX() > 35 && avatar.getX() <= 785)
                avatar.setX(avatar.getX() - 150);
            else
                throw new OutOfBoard("Ande no Tabuleiro");
        } else if (key == KeyCode.RIGHT) {
            if (avatar.getX() >= 35 && avatar.getX() < 785)
                avatar.setX(avatar.getX() + 150);
            else
                throw new OutOfBoard("Ande no Tabuleiro");
        } else
            throw new InvalidKeyBoard("Tecla inválida no Tabuleiro");
    }

    public void verifyCell(){
        int newLine = (int)(avatar.getY()/100);
        int newCollumn = (int)(avatar.getX()/150);
        String text = "";
        double lifeIncrement = 0, scoreIncrement = 0;

        if(screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter() != null){
            if(screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter() instanceof Villains) {
                lifeIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getLife();
                text = String.valueOf(lifeIncrement) +
                        "% de Vida\n" +
                        "Você não pode ficar aí, tem um sentinela do fogo!";

                message.setText(text);
                verifyLimit(avatarLife, lifeIncrement/100);
                screen.getGame().getAvatar().setLife((int)(avatarLife.getProgress()*100));

                avatarLifeLabel.setText("Vida - " + (int)(avatarLife.getProgress()*100) + "%");
                boardCharacters[newLine][newCollumn].opacityProperty().set(1);

                if((int)(avatarLife.getProgress()*100) <= 0){
                    screen.getStage().setScene(new PlayAgainScreenController().playAgainScreen("/assets/messages/lose.png"));
                }
            }
            else {
                if (screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() != "Door" && screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() != "Appa") {
                    if (screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() == "Katara" && screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCellVisited() == false) {
                        lifeIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getLife();
                        text = "+" + String.valueOf(lifeIncrement) +
                                "% de Vida\n" +
                                "Você encontrou a Katara e o Sokka!";
                    } else if (screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() == "Toph" && screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCellVisited() == false) {
                        lifeIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getLife();
                        scoreIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getScore();
                        text = "+" + String.valueOf(lifeIncrement) +
                                "% de Vida e +" + String.valueOf(scoreIncrement) + "% de Estado Avatar\n" +
                                "Você encontrou a Toph!";
                    } else if (screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() == "Zuko" && screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCellVisited() == false) {
                        lifeIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getLife();
                        scoreIncrement = screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getScore();
                        text = "+" + String.valueOf(lifeIncrement) +
                                "% de Vida e +" + String.valueOf(scoreIncrement) + "% de Estado Avatar\n" +
                                "Você encontrou o Zuko!";
                    }

                    message.setText(text);

                    verifyLimit(avatarLife, lifeIncrement/100);
                    verifyLimit(avatarScore, scoreIncrement/100);

                    avatarLifeLabel.setText("Vida - " + (int) (avatarLife.getProgress() * 100) + "%");
                    avatarScoreLabel.setText("Estado Avatar - " + (int) (avatarScore.getProgress() * 100) + "%");

                    screen.getGame().getAvatar().setLife((int) (avatarLife.getProgress() * 100));
                    screen.getGame().getAvatar().setScore((int) (avatarScore.getProgress() * 100));

                    if(boardCharacters[newLine][newCollumn] != null) {
                        boardCharacters[newLine][newCollumn].opacityProperty().set(0);
                        boardCharacters[newLine][newCollumn] = null;
                    }

                    screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].setCellVisited(true);
                }
                else{
                    if(screen.getGame().getBoard().getBoard().getCells()[newLine][newCollumn].getCharacter().getCharacter() == "Door")
                        screen.getStage().setScene(new FightScreenController(screen).fightScreen());
                    else{
                        screen.getGame().getBoard().getBoard().setLevel(screen.getGame().getBoard().getBoard().getLevel() + 1);
                        screen.getStage().setScene(new BoardScreenController(screen).boardScreen());
                    }
                }
            }
        }
    }

    private void verifyLimit(ProgressBar bar, double amount){
        if(bar.getProgress()+amount >= 1.0)
            bar.setProgress(1.0);
        else if(bar.getProgress()+amount <= 0.0)
            bar.setProgress(0.0);
        else
            bar.setProgress(bar.getProgress()+amount);
    }
}