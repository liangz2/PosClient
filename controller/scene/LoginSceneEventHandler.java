/*
 * A handler class that controls the two buttons on the log in screen
 */
package controller.scene;

import controller.scene.LoginScene;
import controller.task.UserLoader;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public class LoginSceneEventHandler implements EventHandler {
    private MainPane mainPane;
    private LoginScene loginScene;
    private static LoginSceneEventHandler loginButtonHandler;
    private LoginSceneEventHandler(MainPane mainPane, LoginScene loginScene) {
        this.mainPane = mainPane;
        this.loginScene = loginScene;
    }
    /**
     * 
     * @param arg0 
     */
    @Override
    public void handle(Event arg0) {
        Button source = (Button) arg0.getSource();
        if (source.getId().equals(loginScene.LOGIN_BUTTON)) {
            String username = loginScene.getUsername();
            String password = loginScene.getPassword();
            if (!username.isEmpty() && !password.isEmpty()) {
                UserLoader.userLogin(username, password, loginScene, mainPane);
            }
            else {
                mainPane.displayErrorMessage("Please enter username and password");
            }
        }
        else {
            System.exit(0);
        }
    }
    
    public static LoginSceneEventHandler getInstance(MainPane mainPane,
            LoginScene loginScene) {
        if (loginButtonHandler == null) {
            loginButtonHandler = new LoginSceneEventHandler(mainPane, loginScene);
        }
        return loginButtonHandler;
    }
}
