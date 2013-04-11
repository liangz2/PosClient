/*
 * Event controller class for SalesScene, determind which button is pressed
 */
package controller.scene;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import view.UI.MainPane;
import view.scene.SceneFactory;

/**
 *
 * @author Zhengyi
 */
public class SalesSceneEventHandler {
    private MainPane mainPane;
    private EventHandler invLookUp;
    private EventHandler checkOut;
    private EventHandler editCustomer;
    private EventHandler viewMargin;
    private EventHandler inventoryViewerButton;
    private EventHandler noButton;
    private InventoryScene inventory;
    private Stage inventoryWindow;
    private EventHandler<KeyEvent> modelCellF9;
    private SalesScene salesScene;
    private NotFoundMessage notFound;
    private AnchorPane salesPane;
    
    public SalesSceneEventHandler(MainPane mainPane, SalesScene salesScene,
            NotFoundMessage notFound, AnchorPane salesPane) {
        this.mainPane = mainPane;
        this.salesPane = salesPane;
        this.salesScene = salesScene;
        this.notFound = notFound;
    }
    /**
     * 
     * @param mainPane
     * @return 
     */
    public EventHandler getInvLookupScene() {
        if (invLookUp == null) {
            invLookUp = new EventHandler() {
                @Override
                public void handle(Event arg0) {
                    if (inventory == null) {
                        inventory = SceneFactory.buildInventoryScene(mainPane);
                    }
                    showInventoryWindow(mainPane.getOwner(), inventory);
                }
            };
        }
        
        return invLookUp;
    }
    
    /**
     * 
     * @param mainPane
     * @param salesScene
     * @param salesPane
     * @param notFound
     * @return 
     */
    public EventHandler getYesButtonHandler() {
        if (inventoryViewerButton == null) {
            inventoryViewerButton = new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent t) {
                    if (inventory == null) {
                        inventory = SceneFactory.buildInventoryScene(mainPane);
                    }
                    inventory.setSalesScene(salesScene);
                    inventory.setIsReturnProductScene(true);
                    notFound.setVisible(false);
                    showInventoryWindow(mainPane.getOwner(), inventory);
                }
                
            };
        }
        
        return inventoryViewerButton;
    }
    
    /**
     * 
     * @param salesPane
     * @param notFound
     * @param loadingBackground
     * @param salesScene
     * @return 
     */
    public EventHandler getNoButtonHandler() {
        if (noButton == null) {
            noButton = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    salesScene.getCurrentTextField().requestFocus();
                    notFound.setVisible(false);
                    salesScene.getLoadingBackground().setVisible(false);
                }
            };
        }
        return noButton;
    }
    
    
    public EventHandler getModelCellF9Handler() {
        if (modelCellF9 == null) {
            modelCellF9 = new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.F9) {
                        salesScene.getCurrentModelCell().setCancelEditAllowed(false);
                        if (inventory == null) {
                            inventory = SceneFactory.buildInventoryScene(mainPane);
                        }
                        inventory.setSalesScene(salesScene);
                        inventory.setIsReturnProductScene(true);
                        showInventoryWindow(mainPane.getOwner(), inventory);
                    }
                }
            };
        }
        return modelCellF9;
    }
    
    /**
     * 
     * @param mainPane
     * @return 
     */
    public EventHandler getPaymentHandler(final MainPane mainPane) {
        if (checkOut == null) {
            checkOut = new EventHandler() {
                @Override
                public void handle(Event t) {
                    
                }
                
            };
        }
        
        return checkOut;
    }
    
    /**
     * 
     * @param owner
     * @param scene
     * @return 
     */
    private void showInventoryWindow(Stage owner, InventoryScene inventoryScene) {
        if (inventoryWindow == null) {
            inventoryWindow = new Stage();
            inventoryWindow.initOwner(owner);
            inventoryWindow.initModality(Modality.WINDOW_MODAL);
            inventoryWindow.initStyle(StageStyle.UTILITY);
            inventoryWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent t) {
                    salesScene.getLoadingBackground().setVisible(false);
                    salesScene.getCurrentTextField().requestFocus();
                }
            });
            // set the pane to the parent
            inventoryWindow.setScene(new Scene(inventoryScene, 1000, 700));
            inventoryScene.setParentWindow(inventoryWindow);
        }
        inventoryWindow.show();
    }
}
