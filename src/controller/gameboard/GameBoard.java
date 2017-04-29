package controller.gameboard;

import controller.map.Cell;
import controller.scoreboard.ScoreBoard;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * Contains the score board and the map. Also controls the game play?
 */
public class GameBoard extends BorderPane {


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
    //we pass the cellList "this" so it can define our explore emptiness method later
    private CellList cellList = new CellList(this);

    /**
     * Keeps track of which cells have been revealed and which haven't
     */
    private ObservableList<Cell> revealedList = FXCollections.observableArrayList();

    /**
     * A handler that controls what happens when a user clicks a cell. A series of checks are made
     * and the appropriate action is taken
     */
    private EventHandler<ActionEvent> leftClickEvent;

    /**
     * I'm using this in attempts to not duplicate code that need not be duplicated.
     * <p>
     * I'll set this in the CellList class and then call it when necessary.
     */
    private static Callback<Cell, Void> exploreEmptinessCallback = null;

    /**
     * Merely starts the clock.
     */
    private InvalidationListener startClock;

    /**
     * Cycles the tapa through 3 possible marks and decrements or increments the flag/mine count accordingly.
     */
    private EventHandler<MouseEvent> rightClickEvent;


    /**
     * Called to define our exploreEmptiness routine from the context of inside of CellList.
     * This will let the GameBoard class reuse some of the components in the CellList class.
     *
     * @param callback defines what to do when you click a cell with a 0
     */
    void defineExploreEmptiness(Callback<Cell, Void> callback) {
        exploreEmptinessCallback = callback;
    }


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
        //Make the listener first
        startClock = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                scoreBoard.startClock();
                revealedList.removeListener(this);
            }
        };

        //Then add it.
        //This will help us know when the user has started
        revealedList.addListener(startClock);

        //This will help us determine if the user has won.
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

                //Only reveal cells that are unmarked
                if (Objects.equals(btn.getId(), "blank")) {
                    //Reveal contents
                    reveal(cell);

                    //If source contents is a mine
                    if (cell.isAMine()) {
                        //Gameover
                        // TODO: 4/28/17   gameover();
                    }

                    //If 0
                    if (Objects.equals(cell.getContents().getText(), "0")) {
                        exploreEmptiness(cell);
                    }
                }


            }
        };
    }

    private void initRightClickHandler() {
        rightClickEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {

                    //Change the mark and the counter if necessary
                    Button btn = (Button) event.getSource();
                    Cell cell = (Cell) btn.getParent();
                    System.out.println("RIGHT");
                    switch (cell.getMark()) {
                        case BLANK:
                            btn.setId("flagged");
                            cell.setMark(Cell.Mark.FLAG);
                            scoreBoard.decrementBombCounter();
                            break;
                        case FLAG:
                            btn.setId("questionable");
                            cell.setMark(Cell.Mark.QUESTIONABLE);
                            break;
                        case QUESTIONABLE:
                            btn.setId("blank");
                            cell.setMark(Cell.Mark.BLANK);
                            scoreBoard.incrementBombCounter();
                            break;
                    }
                }
            }
        };
    }

    private void exploreEmptiness(Cell cell) {
        //Call the callback: pray
        exploreEmptinessCallback.call(cell);
    }


    /**
     * Reveals a cell by hiding the tapa. Adds the cell to the list of revealed cells; we need
     * to do this so we know when the user has revealed enough cells to win.
     *
     * @param cell the cell being revealed
     */
    void reveal(Cell cell) {
        //Remove tapa
        Button tapa = cell.getTapa();

        //For fun, log what it contains
        System.out.println(cell.getContents().getText());

        if (tapa.isVisible()) {
            if (cell.getMark() == Cell.Mark.BLANK) {
                tapa.setVisible(false);

                //Add cell to revealed list
                revealedList.add(cell);
            }
        }
        //Otherwise, don't add it: it's already invisible and on the list
    }

    @FXML
    void initialize() {

        initLeftClickHandler();
        initRightClickHandler();

        //Set up map
        initCells();

        initGamePlay();


    }

    private void initCells() {

        //Make all the cells
        for (int i = 0; i < Options.getCellCount(); i++) {
            Cell c = new Cell();

            //Give each cell the handlers it needs to function correctly during game play
            c.setClickEvents(leftClickEvent, rightClickEvent);

            cellList.add(c);
        }

        //Turn a certain percentage of them into bombs
        for (int i = 0; i < Options.getBombCount(); i++) {
            cellList.get(i).setMine();
        }

        //Shuffle them
        Collections.shuffle(cellList);

        //Add the cellList to the map
        map.getChildren().setAll(cellList);

        cellList.plotHints();
    }


}

