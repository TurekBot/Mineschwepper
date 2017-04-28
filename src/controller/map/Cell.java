package controller.map;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Represents one square on the Mineschwepper map.
 */
public class Cell extends StackPane {

    public Button tapa = new Button();

    /**
     * Either a hint to how many mines are around, or, you guessed it, a mine.
     */
    private Label contents = new Label("1");



    public enum Mark {
        BLANK, FLAG, QUESTIONABLE,
    }

    private Mark mark = Mark.BLANK;

    private static final int CELL_SIZE = 10;

    public Cell() {
        initTapa();
        initContents();
        //This will ensure that all cells are styled according to the 'cell' specification in the css


        //Add contents and tapa to cell
        this.getChildren().add(contents);
        this.getChildren().add(tapa);

    }

    private void initContents() {
        Random rand = new Random();
        contents.setText(String.valueOf(rand.nextInt(9)));
        //If the text is a one, the css id will be a one, and that will color it appropriately.
        contents.idProperty().bind(contents.textProperty());

        //let's the CSS know that this is a number hint, and it should be styled as such.
        contents.getStyleClass().add("contents");
    }

    private void initTapa() {
        tapa.getStyleClass().add("cell");

        tapa.setShape(new Rectangle(CELL_SIZE, CELL_SIZE));
        tapa.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        //Handle a click-to-reveal
        tapa.setOnAction(action -> {
                    Button tapa = (Button) action.getSource();
                    tapa.getStyleClass().add("revealed");
                    tapa.setVisible(false);
                }
        );

        //Handle the investigation marking
        tapa.setOnMouseClicked(event -> {
            Button btn = (Button) event.getSource();
            Cell cell = (Cell) btn.getParent();
            if (event.getButton() == MouseButton.SECONDARY) {
                switch (cell.getMark()) {
                    case BLANK:
                        btn.setId("flagged");
                        cell.setMark(Mark.FLAG);
                        break;
                    case FLAG:
                        btn.setId("questionable");
                        cell.setMark(Mark.QUESTIONABLE);
                        break;
                    case QUESTIONABLE:
                        btn.setId("blank");
                        cell.setMark(Mark.BLANK);
                        break;
                }
            }

        });
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}
