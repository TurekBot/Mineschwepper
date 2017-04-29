package controller.scoreboard;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * In normal mode, acts as a stop watch, counting up to see how long it takes the player to win.
 */
public class Clock extends Label {

    private IntegerProperty timeProperty = new SimpleIntegerProperty(0);

    private Timer timer = new Timer();

    /**
     * Just a timer task to increment the timer by one (see increment())
     */
    private TimerTask countUp;

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

        initTimer();

    }

    /**
     * We just make the timer task here because the only place we can provide
     * the delay and period for the timer is when we start it.
     */
    private void initTimer() {
        countUp = new TimerTask() {
            @Override
            public void run() {
                increment();
            }
        };
    }

    /**
     * Increments the clock internally, which will change what the label
     * displays externally.
     * <p>
     * It's important that this be wrapped in a Platform.runLater so that it doesn't
     * get in a fight with the JavaFX Application Thread. I know enough to know that
     * it won't work without it, but I couldn't quite explain precisely why you
     * can't change something without the JAT's permission.
     */
    public void increment() {

        Platform.runLater(() -> timeProperty.set(timeProperty.get() + 1));
    }

    /**
     * Decrements the clock internally, which will change what the label
     * displays externally.
     * <p>
     * It's important that this be wrapped in a Platform.runLater so that it doesn't
     * get in a fight with the JavaFX Application Thread. I know enough to know that
     * it won't work without it, but I couldn't quite explain precisely why you
     * can't change something without the JAT's permission.
     */
    public void decrement() {
        Platform.runLater(() -> timeProperty.set(timeProperty.get() - 1));
    }

    /**
     * Causes the clock to start keeping time like a stopwatch, counting up every 1 second (1000 miliseconds)
     */
    public void start() {
        timer.scheduleAtFixedRate(countUp, 0, 1000);
    }
}