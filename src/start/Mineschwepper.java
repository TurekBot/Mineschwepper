package start;


import controller.gameboard.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

        try {
            primaryStage.getIcons().add(new Image(
                    getClass().getClassLoader().getResourceAsStream("resources/Naval-Mine-Filled-100.png")
            ));
        } catch (NullPointerException npe ) {
            System.err.println("For some freaking reason I can't find the mine icon");
            npe.printStackTrace();
        }

        primaryStage.setTitle("Mineschwepper");
        primaryStage.setOnCloseRequest(onClose -> System.exit(0));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
