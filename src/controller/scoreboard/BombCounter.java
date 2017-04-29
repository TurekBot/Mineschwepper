package controller.scoreboard;

import controller.gameboard.Options;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class BombCounter extends Label {

    private IntegerProperty bombProperty = new SimpleIntegerProperty(Options.getBombCount());

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
     * <p>
     * It's important that this be wrapped in a Platform.runLater so that it doesn't
     * get in a fight with the JavaFX Application Thread. I know enough to know that
     * it won't work without it, but I couldn't quite explain precisely why you
     * can't change something without the JAT's permission.
     */
    public void increment() {
        Platform.runLater(() -> bombProperty.set(bombProperty.get() + 1));
    }

    /**
     * Decrements the bomb count internally, which will change what the label
     * displays externally.
     * <p>
     * It's important that this be wrapped in a Platform.runLater so that it doesn't
     * get in a fight with the JavaFX Application Thread. I know enough to know that
     * it won't work without it, but I couldn't quite explain precisely why you
     * can't change something without the JAT's permission.
     */
    public void decrement() {
        Platform.runLater(() -> bombProperty.set(bombProperty.get() - 1));
    }

    public void reset() {
        bombProperty.set(Options.getBombCount());
    }

//    @FXML
//    protected void doSomething() {
//        System.out.println("The button was clicked!");
//    }
}