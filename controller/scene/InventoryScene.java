/*
 * A basic scene for displaying the product info and does not have editable cells 
 */
package controller.scene;

import controller.RemoteDBUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.data.DataFactory;
import model.product.Product;
import view.UI.MainPane;

/**
 *
 * @author Zhengyi
 */
public class InventoryScene extends PosScene {
    @FXML private TableView productTable;
    @FXML private Label searchLabel;
    @FXML private Label productCountLabel;
    @FXML private TableColumn model;
    @FXML private TableColumn quantity;
    @FXML private TableColumn price;
    @FXML private TableColumn weight;
    @FXML private TableColumn dateAdded;
    @FXML private TableColumn status;
    @FXML private TableColumn name;
    private Stage inventoryWindow;
    private Product returnProduct;
    private SalesScene salesScene;
    private Product[] productArray;
    private boolean isReturnProductScene;
    /**
     * constructor
     * @param fxmlLocation
     * @param myThread
     * @param mainPane 
     */
    public InventoryScene(URL fxmlLocation, MainPane mainPane) {
        super(fxmlLocation, mainPane);
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadData();
        // set up columns
        model.setCellValueFactory(new PropertyValueFactory("model"));
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        price.setCellValueFactory(new PropertyValueFactory("price"));
        weight.setCellValueFactory(new PropertyValueFactory("weight"));
        dateAdded.setCellValueFactory(new PropertyValueFactory("dateAdded"));
        status.setCellValueFactory(new PropertyValueFactory("status"));
        // end of setting up columns
        productTable.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    if (isReturnProductScene) {
                        returnProduct();
                    }
                }
            }
        });
        
        productTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (isReturnProductScene && t.getClickCount() == 2) {
                    returnProduct();
                }
            }
        });
    }
    
    private void returnProduct() {
        returnProduct = 
                (Product) productTable.getSelectionModel().getSelectedItem();
        salesScene.addNewPurchaseItem(returnProduct);
        inventoryWindow.close();
        salesScene.getLoadingBackground().setVisible(false);
    }
    /**
     * 
     * @param salesScene 
     */
    public void setSalesScene(SalesScene salesScene) {
        this.salesScene = salesScene;
    }
    
    public void setIsReturnProductScene(boolean isReturnProductScene) {
        this.isReturnProductScene = isReturnProductScene;
    }
    /**
     * 
     * @param window 
     */
    public void setParentWindow(Stage inventoryWindow) {
        this.inventoryWindow = inventoryWindow;
    }
    
    @Override
    public void loadData() {
//        super.loadData(myThread);
        if ((productArray = mainPane.getProductArray()) == null) {
            RemoteDBUtil dbUtil = DataFactory.getDBUtil();
            try {
                productArray = dbUtil.getProductList();
                mainPane.setProducts(productArray);
            } catch (Exception ex) {
                mainPane.setMessage("Unable to communicate with the server at the moment");
    //            myThread.cancel();
            }
        }
        productTable.setItems(FXCollections.observableArrayList(productArray));
    }
}
