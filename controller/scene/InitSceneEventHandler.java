/*
 * Button ActionHandlers for the initial screen
 */
package controller.scene;

import controller.tabs.PosTabID;
import controller.tabs.TabHandlerFactory;
import controller.task.PosSceneLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import view.UI.MainPane;
import view.UI.StartupTab;
import view.basic.PosTab;
import view.scene.SceneFactory;

/**
 *
 * @author Wilson
 */
public class InitSceneEventHandler implements EventHandler<ActionEvent> {
    private MainPane mainPane;
    private ObservableList<Tab> existingTabs;
    public InitSceneEventHandler(MainPane mainPane) {
        this.mainPane = mainPane;
    }
    
    /**
     * 
     * @param arg0 
     */
    @Override
    public void handle(ActionEvent arg0) {
        existingTabs = mainPane.getTabs();
        String id = ((Button) arg0.getSource()).getId();
        StartupTab currentMenu = (StartupTab) mainPane.getSelectedTab();
        // check exisitence of the tab
        for (Tab tab: existingTabs) {
            if (tab.getId().equals(id)) {
                // select the tab and exit the loop if already exixts
                mainPane.selectTab((PosTab) tab);
                return;
            }
        }
        // check the request id. switch(String) is only available with JDK7
        switch (id) {
            case PosTabID.INVENTORY_MASTER_ID:
                initTab(currentMenu, PosTabID.INVENTORY_MASTER_ID, SceneFactory.INVENTORY_MASTER_SCENE);
                break;
            case "settings":
                break;
            case PosTabID.SALES_ID:
                initTab(currentMenu, PosTabID.SALES_ID, SceneFactory.SALES_SCENE);
                break;
        }
    }
    
    /**
     * A private method that loads the scene by the provided name
     * @param tab
     * @param name 
     */
    private void initTab(StartupTab tab, String tabName, String sceneName) {
        // load the scene
        PosSceneLoader.loadScene(tab, tabName, sceneName, mainPane);
        // add close handler
        switch(tabName) {
            default: 
                tab.setOnClosed(TabHandlerFactory.buildGenericCloseHandler(mainPane));
                break;
        }
    }
}
