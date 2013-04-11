/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.UI;

import controller.task.InventoryLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Icewill
 */
public class Inventory extends VBox {
    private StackPane inventoryPane;
    public Inventory() {
        super.setId("inventory");
        loadInventory();
    }
    
    private void loadInventory() {
        inventoryPane = new StackPane();
        setVgrow(inventoryPane, Priority.ALWAYS);
        // display darker screen indicating loading
        Region veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        /* create loading service
        dbService = DBService.getDBService();
        Task getProductList = dbService.createGetProductListTask();
        dbService.setTask(getProductList);// create progress inidcator */
        ProgressIndicator p = new ProgressIndicator();
        p.setMaxSize(150, 150);
        // set up columns
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn quantity = new TableColumn("Quantity");
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        quantity.setMinWidth(60);
        
        TableColumn model = new TableColumn("Model No.");
        model.setCellValueFactory(new PropertyValueFactory("model"));
        
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn weight = new TableColumn("Weight");
        weight.setCellValueFactory(new PropertyValueFactory("weight"));
        TableColumn dateAdded = new TableColumn("Date Added In");

        dateAdded.setCellValueFactory(new PropertyValueFactory("dateAdded"));
        dateAdded.setMinWidth(200);
        
        TableColumn isActive = new TableColumn("Active");
        isActive.setCellValueFactory(new PropertyValueFactory("status"));
        // end of setting up columns
        
        TableView productTable = new TableView();
        productTable.getColumns().addAll(id, model, quantity, 
                price, weight, dateAdded, isActive);
        
        Label itemCount = new Label();
        itemCount.setText("hello");
        itemCount.setMinHeight(20);
        setVgrow(itemCount, Priority.NEVER);
        
        InventoryLoader task = new InventoryLoader();
       
        p.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        p.visibleProperty().bind(task.runningProperty());
        productTable.itemsProperty().bind(task.valueProperty());
        
        inventoryPane.getChildren().addAll(productTable, veil, p);
        
        getChildren().addAll(inventoryPane, itemCount);
        
        new Thread(task).start();
    }
}
