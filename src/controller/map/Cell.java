package controller.map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.Random;

/**
 * Represents one square on the Mineschwepper map.
 */
public class Cell extends StackPane {

    /**
     * The clickable part. Left-clicking reveals, right-clicking cycle's the mark.
     */
    private Button tapa = new Button();

    /**
     * Either a hint to how many mines are around, or, you guessed it, a mine.
     */
    private Label contents = new Label();

    public Button getTapa() {
        return tapa;
    }

    public Label getContents() {
        return contents;
    }


    public enum Mark {
        BLANK, FLAG, QUESTIONABLE
    }
    //Initially, all should be blank
    private Mark mark = Mark.BLANK;

    //This can be changed, but it won't do anything; really, what changes the size is the CSS
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
        //Style the contents with the same paddinglessness and size
        contents.getStyleClass().add("cell");

        //If the text is a one, the css id will be a one, and that will color it appropriately.
        contents.idProperty().bind(contents.textProperty());

        //let's the CSS know that this is a number hint, and it should be styled as such.
        contents.getStyleClass().add("contents");
    }

    private void initTapa() {
        tapa.getStyleClass().add("cell");
        tapa.setId("blank");

        tapa.setShape(new Rectangle(CELL_SIZE, CELL_SIZE));
        tapa.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        //Handle the investigation marking (right-clicks only)
        tapa.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.SECONDARY) {
            Button btn = (Button) event.getSource();
            Cell cell = (Cell) btn.getParent();
            System.out.println("RIGHT");
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


    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        //I think I'll set it on the tapa because that's the clickable part
        tapa.setOnAction(eventHandler);
    }
    /**
     * Turns cell into a mine.
     */
    public void setMine() {
        //Hopefully, this sould style it too, because the text is bound to the id.
        contents.setText("mine");
    }

    /**
     * The hint tells the player how many of the 8 squares this cell is touching have a mine in them.
     * @param number the number of mines touched by this cell
     */
    public void setHint(String number) {
        contents.setText(number);
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

    /**
     * @return true if this cell contains a mine
     */
    public boolean isAMine() {
        return Objects.equals("mine", contents.getText());
    }
}
