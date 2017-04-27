package controller.scoreboard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class BombCounter extends Label {

    private IntegerProperty bombProperty = new SimpleIntegerProperty(0);

    public BombCounter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "bombCounter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // FIXME: 4/27/2017 Maybe needs to go in the initialize method?
        //Bind label to count
        this.textProperty().bind(bombProperty.asString());

    }

    /**
     * Increments the bomb count internally, which will change what the label
     * displays externally.
     */
    public void increment() {
        bombProperty.set(bombProperty.get() + 1);
    }

    /**
     * Decrements the bomb count internally, which will change what the label
     * displays externally.
     */
    public void decrement() {
        bombProperty.set(bombProperty.get() - 1);
    }

//    @FXML
//    protected void doSomething() {
//        System.out.println("The button was clicked!");
//    }
}