/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.scene;

import controller.scene.CriticalMessageScene;
import controller.scene.InventoryMasterScene;
import controller.scene.InventoryScene;
import controller.scene.LoginScene;
import controller.scene.MessageScene;
import controller.scene.NotFoundMessage;
import controller.scene.SalesScene;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import view.UI.MainPane;

/**
 *
 * @author Zhengyi
 */
public class SceneFactory {
    public final static String SALES_SCENE = "salesScene.fxml";
    private final static String MENU_SCENE = "menuScene.fxml";
    private final static String INVENTORY_SCENE = "inventoryScene.fxml";
    private final static String LOGIN_SCENE = "loginScene.fxml";
    private final static String MESSAGE = "messageScene.fxml";
    private final static String CRITICAL_MESSAGE = "criticalMessageScene.fxml";
    public final static String INVENTORY_MASTER_SCENE = "invMasterScene.fxml";
    private final static String NOTFOUND_MESSAGE = "notFoundMessage.fxml";
    private static SalesScene salesScene;
    private static InventoryScene inventoryScene;
    private static InventoryMasterScene inventoryMasterScene;
    private static LoginScene loginScene;
    private static MessageScene msgScene;
    private static CriticalMessageScene criticalMsgScene;
    private static NotFoundMessage notFoundMessage;
    
    /**
     * 
     * @param mainPane
     * @return 
     */
    public static NotFoundMessage buildNotFoundMessage() {
        if (notFoundMessage == null) {
                notFoundMessage =
                    new NotFoundMessage(SceneFactory.class.getResource(NOTFOUND_MESSAGE));
                notFoundMessage.setVisible(false);
        }
        
        return notFoundMessage;
    }
    /**
     * 
     * @param owner
     * @param myThread
     * @return 
     */
    public static Parent buildSalesSecene(MainPane mainPane, 
            final Task myThread) {
        if (salesScene == null) {
            salesScene = 
                    new SalesScene(SceneFactory.class.getResource(SALES_SCENE),
                    myThread, 
                    mainPane);
        } else {
            salesScene.loadData(myThread);
        }
        
        return salesScene;
    }
    
    /**
     * 
     * @param owner
     * @param myThread
     * @return 
     */
    public static InventoryScene buildInventoryScene(MainPane mainPane) {
        if (inventoryScene == null) {
            inventoryScene = 
                    new InventoryScene(SceneFactory.class.getResource(INVENTORY_SCENE),
                    mainPane);
        } else {
            inventoryScene.loadData();
        }
        
        return inventoryScene;
    }
    
    public static InventoryMasterScene buildInventoryMasterScene(MainPane mainPane, 
            final Task myThread) {
        if (inventoryMasterScene == null) {
            inventoryMasterScene = 
                    new InventoryMasterScene(SceneFactory.class.getResource(INVENTORY_MASTER_SCENE),
                    myThread,
                    mainPane);
        } else {
            inventoryMasterScene.loadData(myThread);
        }
        
        return inventoryMasterScene;
    }
    
    /**
     * 
     * @return 
     */
    public static LoginScene buildLoginScene() {
        if (loginScene == null) { 
            loginScene = 
                    new LoginScene(SceneFactory.class.getResource(LOGIN_SCENE));
        } else {
            loginScene.reLoad();
        }
        
        return loginScene;
    }
    
    /**
     * 
     * @return 
     */
    public static MessageScene buildMsgScene() {
        if (msgScene == null) {
            msgScene = 
                    new MessageScene(SceneFactory.class.getResource(MESSAGE));
        } else {
            msgScene.loadData();
        }
        
        return msgScene;
    }
    
    public static CriticalMessageScene buildCritcalMsgScene() {
        if (criticalMsgScene == null) {
            criticalMsgScene = 
                    new CriticalMessageScene(SceneFactory.class.getResource(CRITICAL_MESSAGE));
        } else {
            criticalMsgScene.loadData();
        }
        
        return criticalMsgScene;
    }
    
    
}
