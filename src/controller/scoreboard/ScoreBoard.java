package controller.scoreboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Displays the number of bombs, the start button, and a clock.
 */
public class ScoreBoard extends BorderPane {

    /**
     * Counts how many flags the player has placed (which, if they want to win
     * should be the same as the number of bombs on the map)
     */
    @FXML
    private BombCounter bombCounter;


    /**
     * At the beginning of a game, this displays the word "start", but during a game,
     * to make the tension mount, shows a face.
     */
    @FXML
    private Button middleButton;

    /**
     * In normal mode, this counts up to track how long it takes the player to win.
     */
    @FXML
    private Clock clock;


    public ScoreBoard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "scoreBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void turnItUp() {
        bombCounter.decrement();
        clock.increment();
    }

    public void startClock() {
        clock.start();
    }
}

