/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.scene.InitSceneEventHandler;
import controller.scene.LoginSceneEventHandler;
import controller.scene.CriticalMessageScene;
import controller.scene.LoginScene;
import controller.scene.MessageScene;
import controller.tabs.TabHandlerFactory;
import controller.tooltips.PosTooltipFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.UI.MainPane;
import view.scene.SceneFactory;

/**
 *
 * @author Zhengyi
 */
public class PosController {
    private MainPane mainPane;
    private Stage owner;
    private LoginScene loginScene;
    private StackPane root;
    private MessageScene msgScene;
    private CriticalMessageScene criticalMsg;
    
    public PosController(MainPane mainPane, Stage owner, StackPane root) {
        this.mainPane = mainPane;
        this.owner = owner;
        this.root = root;
        this.loginScene = SceneFactory.buildLoginScene();
        this.msgScene = SceneFactory.buildMsgScene();
        this.criticalMsg = SceneFactory.buildCritcalMsgScene();
    }
    /**
     * initialize all necessary components
     */
    public void init() {
        // adding in action listeners, more scene components etc.
        mainPane.initLayout();
        mainPane.addInitButtonHandler(new InitSceneEventHandler(mainPane));
        mainPane.addNewTabSelectionHandler(TabHandlerFactory.buildNewTabSelectHandler(mainPane));
        mainPane.addInitTabCloseHandler(TabHandlerFactory.buildGenericCloseHandler(mainPane));
        mainPane.setOwner(owner);
        mainPane.setMsgScene(msgScene);
        mainPane.setCriticalMsgScene(criticalMsg);
        mainPane.setTooltipFactory(new PosTooltipFactory());
        // set the pane visible to false before loging in
        mainPane.setVisible(true);
        
        // login scene action listeners for button
        loginScene.addButtonHandler(LoginSceneEventHandler.getInstance(mainPane, loginScene));
        // add all component to the root pane
        root.getChildren().addAll(mainPane, msgScene, criticalMsg);
        // set the message display on the bottom of the scene
        StackPane.setAlignment(msgScene, Pos.BOTTOM_CENTER);
    }
}
