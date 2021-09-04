package unsw.loopmania;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import unsw.loopmania.Items.*;
import unsw.loopmania.Shop.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShopSellController {

    @FXML
    private GridPane imageGrid;

    @FXML
    private AnchorPane overPane;

    private LoopManiaWorld world;

    private LoopManiaWorldController worldController;

    private Shop shop;

    public ShopSellController(LoopManiaWorld world, LoopManiaWorldController worldController, Shop shop) {
        this.world = world;
        this.worldController = worldController;
        this.shop = shop;
    }

    @FXML
    public void initialize() {
        addItems();
        addDoneButton();
    }

    public void addDoneButton() {
        Button done = new Button("Done");
        done.setFont(Font.font ("Bauhaus 93", FontWeight.BOLD, 25));
        done.setTextFill(Color.GREEN);
        done.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                doneButtonAction(done);
            }
        });

        overPane.getChildren().add(done);
        AnchorPane.setTopAnchor(done, 640.0);
        AnchorPane.setLeftAnchor(done, 180.0);
    }

    public void doneButtonAction(Button done) {
        worldController.setSellShopOpen(false);
        worldController.tryToPlay();
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();
    }

    public void addItems() {
        int i = 0;
        for (Item item: world.getItems()) {
            int itemLevel = 0;
            if (item.isWeapon()) {
                itemLevel = ((Weapon)item).getLevel();
            }
            else if (item.isProtection()) {
                itemLevel = ((Protection)item).getLevel();
            }
            String itemName = item.getType();
            String itemString;

            if (world.getNonLevelItems().contains(itemName)) {
                itemString = itemName;
            }
            else {
                itemString = itemName + itemLevel;
            }
            ImageView view = new ImageView(new Image((new File(String.format("src/images/%s.png", itemString))).toURI().toString()));
            int row = i / 4;
            int col = i % 4;
            view.setFitHeight(70);
            view.setFitWidth(70);
            imageGrid.add(view, col, row);
            GridPane.setHalignment(view, HPos.CENTER);
            GridPane.setValignment(view, VPos.CENTER);
            
            ImageView goldView = new ImageView(new Image((new File("src/images/gold_pile.png")).toURI().toString()));
            goldView.setFitHeight(28);
            goldView.setFitWidth(28);

            Button itemButton = makeItemButton(item, i);

            GridPane gridPane = new GridPane();
            gridPane.add(goldView, 0, 0);
            gridPane.add(itemButton, 1, 0);
            overPane.getChildren().add(gridPane);

            AnchorPane.setTopAnchor(gridPane, getTopAnchor(i));
            AnchorPane.setLeftAnchor(gridPane, getLeftAnchor(i));
            i++;
        }
    }

    public Button makeItemButton(Item item, int position) {
        int price = shop.getSellPrice(item);
        Button sellButton = new Button(Integer.toString(price));
        sellButton.setFont(Font.font ("Bauhaus 93", FontWeight.BOLD, 18));
        
        sellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    shop.sell(item);
                    sellButton.setTextFill(Color.DARKRED);
                    sellButton.setDisable(true);
                }
            });
        return sellButton;
    }

    public double getTopAnchor(int i) {
        return ((i / 4) * 150 + 135);
    }

    public double getLeftAnchor(int i) {
        return ((i % 4) * 115 + 10);
    }
}