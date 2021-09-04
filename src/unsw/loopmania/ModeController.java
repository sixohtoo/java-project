package unsw.loopmania;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * controller for the main menu.
 * 
 */
public class ModeController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    private LoopManiaWorldController mainController;

    private String folder;

    private String map;

    private ArrayList<String> mode;

    @FXML
    private VBox rootBox;

    @FXML
    private CheckBox standardBox;

    @FXML
    private CheckBox survivalBox;

    @FXML
    private CheckBox berserkerBox;

    @FXML
    private CheckBox confusingBox;

    @FXML
    private Label errorLabel;

    public ModeController(String folder, String map) {
        this.folder = folder;
        this.map = map;
        mode = new ArrayList<String>();
    }

    @FXML
    private void initialize() {
        // Set the checkbox to add the selected mode to the mode arraylist
        // when selected and remove it when not.
        EventHandler<ActionEvent> standardEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (standardBox.selectedProperty().get())
                    mode.add("standard");
                else
                    mode.remove("standard");
            }
        };
        standardBox.setOnAction(standardEvent);
        EventHandler<ActionEvent> survivalEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (survivalBox.selectedProperty().get())
                    mode.add("survival");
                else
                    mode.remove("survival");
            }
        };
        survivalBox.setOnAction(survivalEvent);
        EventHandler<ActionEvent> berserkerEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (berserkerBox.selectedProperty().get())
                    mode.add("berserker");
                else
                    mode.remove("berserker");
            }
        };
        berserkerBox.setOnAction(berserkerEvent);
        EventHandler<ActionEvent> confusingEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (confusingBox.selectedProperty().get())
                    mode.add("confusing");
                else
                    mode.remove("confusing");
            }
        };
        confusingBox.setOnAction(confusingEvent);
        // Initially set error label to invisible
        errorLabel.setVisible(false);
    }

    private void loadWorld(String map) throws IOException {
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader(folder, map);
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        Scene scene = rootBox.getScene();
        Stage primaryStage = (Stage) scene.getWindow();

        primaryStage.setTitle("Loop Mania");

        this.setGameSwitcher((ArrayList<String> mode) -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
            mainController.setMode(mode);
            mainController.play();
        });

        gameRoot.requestFocus();
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    private void startGame() throws IOException {
        if (mode.isEmpty()) {
            errorLabel.setVisible(true);
        }
        else {
            loadWorld(map);
            gameSwitcher.switchMenu(mode);
        }
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }
}
