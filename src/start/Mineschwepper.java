package start;


import controller.gameboard.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starts the game.
 */
public class Mineschwepper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Start the game
        GameBoard gameBoard = new GameBoard();

        primaryStage.setScene(new Scene(gameBoard));

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
