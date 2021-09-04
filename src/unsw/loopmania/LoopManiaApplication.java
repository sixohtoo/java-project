package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania - Start Menu");

        // prevent human player resizing game window (since otherwise would see white space)
        primaryStage.setResizable(false);

        // load the start game menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGameView.fxml"));
        Parent root = loader.load();

        // create new scene with the start game menu (so we start with this menu)
        Scene scene = new Scene(root);

        // deploy the menu onto the stage
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
