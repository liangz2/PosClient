/*
 * A small scene that is displayed when the entered product model number does not
 * exist
 */
package controller.scene;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Wilson
 */
public class NotFoundMessage extends PosScene {
    @FXML private Button yesButton;
    @FXML private Button noButton;
    public static String YES_BUTTON_ID = "yes";
    public static String NO_BUTTON_ID = "no";
    
    public NotFoundMessage(URL fxmlLocation) {
        super(fxmlLocation);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set button id
        yesButton.setId(YES_BUTTON_ID);
        noButton.setId(NO_BUTTON_ID);
    }
    
    public void setYesButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        yesButton.setOnAction(buttonHandler);
    }
    
    public void setNoButtonHandler(EventHandler<ActionEvent> buttonHandler) {
        noButton.setOnAction(buttonHandler);
    }
    
    public void focusYesButton() {
        yesButton.requestFocus();
    }
}
