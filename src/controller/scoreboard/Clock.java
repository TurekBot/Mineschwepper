package controller.scoreboard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * In normal mode, acts as a stop watch, counting up to see how long it takes the player to win.
 */
public class Clock extends Label {

    private IntegerProperty timeProperty = new SimpleIntegerProperty(0);

    public Clock() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "clock.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // FIXME: 4/27/2017 Maybe this needs to go in the initialize method?
        //Bind label to time
        this.textProperty().bind(timeProperty.asString());

    }

    // FIXME: 4/27/2017 Maybe, methods are not the way to go here, but maybe they are.
    /**
     * Increments the clock internally, which will change what the label
     * displays externally.
     */
    public void increment() {
        timeProperty.add(1);
    }

    /**
     * Decrements the clock internally, which will change what the label
     * displays externally.
     */
    public void decrement() {
        timeProperty.subtract(1);
    }

//    @FXML
//    protected void doSomething() {
//        System.out.println("The button was clicked!");
//    }
}