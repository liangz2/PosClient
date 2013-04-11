/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.task;

import controller.RemoteDBUtil;
import controller.scene.LoginScene;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import model.data.DataFactory;
import model.user.User;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public class UserLoader extends Service<User> {
    private static String username;
    private static String password;
    private User user = null;
    private LoginScene loginScene;
    private MainPane mainPane;
    private static UserLoader userLoader;
    
    private UserLoader(String username, 
            String password, 
            final LoginScene loginScene,
            final MainPane mainPane) {
        UserLoader.username = username;
        UserLoader.password = password;
        this.loginScene = loginScene;
        this.mainPane = mainPane;
    }
    
    @Override
    protected Task<User> createTask() {
        loginScene.startLoading();
        // start a new task
        return new LoginTask();
    }
    
    /**
     * customized task to get user from server
     */
    class LoginTask extends Task<User> {
        LoginTask() {
            // set when login success or fails
            setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent arg0) {
                    if (user != null) {
                        loginScene.setVisible(false);
                        mainPane.setVisible(true);
                        mainPane.displaySuccessMessage("Welcome " + user.getFirstName());
                        mainPane.setLoginUser(user);
                    }
                    else {
                        loginScene.finishLoading();
                        mainPane.displayErrorMessage("Username or password incorrrect");
                    }
                }
            });
            setOnCancelled(new EventHandler<WorkerStateEvent>() {

                @Override
                public void handle(WorkerStateEvent arg0) {
                    loginScene.finishLoading();
                    mainPane.displayErrorMessage("Unable to connect to the server");
                }
            });
        }
        @Override
        protected User call() throws Exception {
            try {
                RemoteDBUtil dbUtil = DataFactory.getDBUtil();
                user = dbUtil.getSalesPerson(username, password);
            } catch (Exception e) {
                this.cancel();
            }
            return user;
        }
        
    }
    
    /**
     * user login
     * @param username
     * @param password
     * @param loginScene
     * @param mainPane 
     */
    public static void userLogin(String username, 
            String password, 
            final LoginScene loginScene,
            final MainPane mainPane) {
        if (userLoader == null) {
            userLoader = new UserLoader(username, password, loginScene, mainPane);
            userLoader.start();
        } else {
            UserLoader.username = username;
            UserLoader.password = password;
            userLoader.restart();
        }
    }
}
