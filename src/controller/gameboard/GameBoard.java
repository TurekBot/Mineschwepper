package controller.gameboard;

import controller.map.Cell;
import controller.scoreboard.ScoreBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.Collections;

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
     * Visually contains all of the cells.
     */
    @FXML
    private TilePane map;

    /**
     * Keeps track of all of the cells; convenient access to them.
     */
    private CellList cellList = new CellList();



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
        initCells();



    }

    private void initCells() {

        //Make all the cells
        for (int i = 0; i < Options.getCellCount(); i++){
            Cell c = new Cell();
            cellList.add(c);
        }

        //Turn a certain percentage of them into bombs
        for (int i = 0; i < Options.getBombCount(); i++){
            cellList.get(i).setMine();
        }

        //Shuffle them
        Collections.shuffle(cellList);

        //Add the cellList to the map
        map.getChildren().setAll(cellList);

        cellList.plotHints();
    }


}

