package unsw.loopmania;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartGameController {

    @FXML
    private Pane rootPane;

    private Stage primaryStage;

    private Scene scene;

    @FXML
    private void initialize() {

        rootPane.setBackground(new Background(
            new BackgroundImage(
                    new Image((new File(String.format("src/images/backgroundImage.jpg"))).toURI().toString()),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                    new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
            )));
    }
    
    @FXML
    private void handleNewGame() throws IOException {
        loadMapSelector("worlds");

    }

    @FXML
    private void handleLoadGame() throws IOException {
        loadMapSelector("backup");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private void loadMapSelector(String folder) throws IOException {
        scene = rootPane.getScene();
        primaryStage = (Stage) scene.getWindow();

        // set title on top of window bar
        primaryStage.setTitle("Loop Mania - Select Map");

        // prevent human player resizing game window (since otherwise would see white space)
        primaryStage.setResizable(false);

        // load the map selector menu
        MapSelectorController mapSelectController = new MapSelectorController(folder);
        FXMLLoader mapSelectLoader = new FXMLLoader(getClass().getResource("MapSelectorView.fxml"));
        mapSelectLoader.setController(mapSelectController);
        Parent mapSelectRoot = mapSelectLoader.load();

        // deploy the menu onto the stage
        scene.setRoot(mapSelectRoot);
        mapSelectRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
