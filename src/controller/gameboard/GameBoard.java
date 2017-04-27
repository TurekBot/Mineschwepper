package controller.gameboard;

import controller.scoreboard.ScoreBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Contains the score board and the map. Also controls the game play?
 */
public class GameBoard extends BorderPane{


    /**
     * Displays the number of bombs, the start button, and a clock.
     */
    @FXML
    private ScoreBoard scoreBoard;



    public GameBoard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "gameBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}

