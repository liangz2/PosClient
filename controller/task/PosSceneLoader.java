/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.task;

import controller.scene.PosScene;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import view.UI.MainPane;
import view.UI.StartupTab;
import view.scene.SceneFactory;

/**
 *
 * @author Zhengyi
 */
public class PosSceneLoader extends Service<Parent>{ 
    protected StartupTab myTab;
    protected String sceneName;
    protected String tabName;
    private MainPane mainPane;
    private Parent scene;
    private static PosSceneLoader posSceneLoader;
    
    private PosSceneLoader(StartupTab myTab, String tabName, String sceneName, MainPane mainPane) {
        initService(myTab, tabName, sceneName);
        this.mainPane = mainPane;
    }
    
    private void initService(final StartupTab myTab, final String tabName, final String sceneName) {
        this.myTab = myTab;
        this.sceneName = sceneName;
        this.tabName = tabName;
    }
    
    @Override
    protected Task<Parent> createTask() {
        // start the loading screen
        myTab.startLoading();
        // creates the task to load scene
        return new LoadSceneTask();
    }
    
    public static void loadScene(StartupTab myTab, String tabName, String sceneName, MainPane mainPane) {
        if (posSceneLoader == null) {
            posSceneLoader = new PosSceneLoader(myTab, tabName, sceneName, mainPane);
            posSceneLoader.start();
        }
        else {
            posSceneLoader.initService(myTab, tabName, sceneName);
            posSceneLoader.restart();
        }
    }
    
    /**
     * a task that determents which scene to load
     */
    private class LoadSceneTask extends Task<Parent> {
        private LoadSceneTask() {
            // init succeeded behaviour
            this.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent e) {
                    PosScene scene = (PosScene) e.getSource().getValue();
                    // set the tab closable
                    myTab.setClosable(true);
                    myTab.clearButtonList();
                    myTab.setId(tabName);
                    myTab.setText(tabName.toUpperCase());
                    myTab.finishLoading();
                    myTab.setContent(scene);
                }
            });
            // init cancelled behaviour
            this.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                    @Override
                public void handle(WorkerStateEvent e) {
                    myTab.finishLoading();
                    mainPane.displayCriticalErrorMessage(null);
                }
            });
        }
        
        @Override
        protected Parent call() throws Exception {
            switch(sceneName) {
                case SceneFactory.SALES_SCENE:
                    scene = SceneFactory.buildSalesSecene(mainPane, this);
                    break;
                case SceneFactory.INVENTORY_MASTER_SCENE:
                    scene = SceneFactory.buildInventoryMasterScene(mainPane, this);
                    break;
                default:
                    scene = null;
            }
            return scene;
        }
    }
}
