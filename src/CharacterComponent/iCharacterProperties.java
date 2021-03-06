package CharacterComponent;

import javafx.scene.image.ImageView;

public interface iCharacterProperties {
    public String getCharacter();

    public void setCharacter(String character);

    public String getImageSource();

    public void setScore(int score);

    public int getScore();

    public void setLife(int life);

    public int getLife();

    public void addScore(int score);

    public void addLife(int life);

    public int getLine();

    public void setLine(int line);

    public int getCollumn();

    public void setCollumn(int collumn);
}