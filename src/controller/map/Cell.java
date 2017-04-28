package controller.map;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Created by School on 4/27/2017.
 */
public class Cell extends Button {

    private static final int CELL_SIZE = 10;
    public Cell() {
        //This will ensure that all cells are styled according to the 'cell' specification in the css
        this.getStyleClass().add("cell");

        this.setShape(new Rectangle(CELL_SIZE, CELL_SIZE));
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


        this.setOnAction(action -> {
            this.getStyleClass().add("revealed");
            this.setDisable(true);
                }
        );

    }
}
