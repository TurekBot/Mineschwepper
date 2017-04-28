package controller.gameboard;

import controller.map.Cell;
import controller.scoreboard.ScoreBoard;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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

    /**
     * Keeps track of which cells have been revealed and which haven't
     */
    private ObservableList<Cell> revealedList = FXCollections.observableArrayList();

    /**
     * A handler that controls what happens when a user clicks a cell. A series of checks are made
     * and the appropriate action is taken
     */
    private EventHandler<ActionEvent> leftClickEvent;


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

    /**
     * Sets up the click handler as well as other important game play
     */
    private void initGamePlay() {

        revealedList.addListener(new ListChangeListener<Cell>() {
            @Override
            public void onChanged(Change<? extends Cell> c) {

                //Simply check if the user has revealed the right number of cells
                if (revealedList.size() == Options.getMustRevealCount()) {
                    System.out.println("You won!");
                    // TODO: 4/28/17  winSequence();
                }
            }
        });


    }

    /**
     * Defines the process that happens when a user clicks a square
     */
    private void initLeftClickHandler() {
        leftClickEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //Get the source of our click
                Button btn = (Button) event.getSource();
                //Get Cell
                Cell cell = (Cell) btn.getParent();
                System.out.println("LEFT");

                //Reveal contents
                reveal(cell);

                //If source contents is a mine
                if (cell.isAMine()) {
                    System.err.println("MINE!");
                    //Gameover
                    // TODO: 4/28/17   gameover();
                }

                //If 0
                if(Objects.equals(cell.getContents(), "0")) {
                    // TODO: 4/28/17  //Explore Emptiness

                }



            }
        };
    }

    /**
     * Reveals a cell by hiding the tapa. Adds the cell to the list of revealed cells; we need
     * to do this so we know when the user has revealed enough cells to win.
     * @param cell the cell being revealed
     */
    private void reveal(Cell cell) {
        //Remove tapa
        cell.getTapa().setVisible(false);

        //Add cell to revealed list
        revealedList.add(cell);
    }

    @FXML
    void initialize() {

        initLeftClickHandler();

        //Set up map
        initCells();

        initGamePlay();



    }

    private void initCells() {

        //Make all the cells
        for (int i = 0; i < Options.getCellCount(); i++){
            Cell c = new Cell();

            //Give each cell the handler it needs to function correctly during gameplay
            c.setOnAction(leftClickEvent);

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

