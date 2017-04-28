package controller.gameboard;

import controller.map.Cell;
import controller.scoreboard.ScoreBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.Random;

/**
 * Contains the score board and the map. Also controls the game play?
 */
public class GameBoard extends BorderPane{


    /**
     * Displays the number of bombs, the start button, and a clock.
     */
    @FXML
    private ScoreBoard scoreBoard;

    /**
     * Contains all of the cells.
     */
    @FXML
    private TilePane map;



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

    @FXML
    void initialize() {

        //Set up map
        for (int i = 0; i < Options.getCellCount(); i ++){
            Cell c = new Cell();
            Random r = new Random();
//            if (r.nextInt() % 2 == 0) c.getStyleClass().add("flag");
//            if (r.nextInt() % 3 == 0) c.getStyleClass().add("questionable");
            if (r.nextInt() % 17 == 0) c.getStyleClass().add("mine");
            map.getChildren().add(c);
        }

    }


}

