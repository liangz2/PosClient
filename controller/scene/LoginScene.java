/*
 * Login scene, consists of username and password fields, and two buttons
 */
package controller.scene;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author Icewill
 */
public class LoginScene extends PosScene {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    @FXML private ProgressIndicator loader;
    @FXML private Region veil;
    @FXML private Text usernameText;
    @FXML private Text passwordText;
    @FXML private CheckBox isAdmin;
    public final String LOGIN_BUTTON = loginButton.getId();
    public final String CANCEL_BUTTON = cancelButton.getId();
    
    public LoginScene(URL fxmlLocation) {
        super(fxmlLocation);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        username.setPromptText("username given");
        password.setPromptText("password given");
    }
    
    /**
     * 
     * @param e 
     */
    public void addButtonHandler(EventHandler e) {
        loginButton.setOnAction(e);
        cancelButton.setOnAction(e);
    }
    
    /**
     * displays the loading screen
     */
    public void startLoading() {
        veil.setVisible(true);
        loader.setVisible(true);
    }
    
    public void finishLoading() {
        veil.setVisible(false);
        loader.setVisible(false);
    }
    
    public String getUsername() {
        return username.getText();
    }
    
    public String getPassword() {
        return password.getText();
    }
    
    public void reLoad() {
        username.setText("");
        password.setText("");
        isAdmin.setSelected(false);
    }
}
