/*
 * Normal MessageScene that display on the bottom of the screen
 */
package controller.scene;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Icewill
 */
public class MessageScene extends PosScene {
    @FXML private StackPane messagePane;
    @FXML private Label messageLabel;
    private String message;
    private FadeTransition t;
    private Thread timer;
    private final int fadeTime = 500;
    private final int stayTime = 3000;
    
    public MessageScene(URL fxmlLocation) {
        super(fxmlLocation);
        t = new FadeTransition(Duration.millis(fadeTime), this);
        t.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (t.getToValue() == 0.0) {
                    messagePane.setVisible(false);
                }
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setMessage(final String msg) {
        message = msg;
    }
    
    /**
     * 
     */
    private void displayMessage() {
        if (!messagePane.isVisible()) {
            messageLabel.setText(message);
            messagePane.setVisible(true);
            t.setFromValue(0.0);
            t.setToValue(1.0);
            t.play();
        }
        if (timer == null || !timer.isAlive()) {
            timer = new Thread(new CloseMessageTimer());
            timer.start();
        }
    }
    
    /**
     * 
     * @param message 
     */
    public void displayErrorMessage(String message) {
        this.message = message;
        messageLabel.setTextFill(Color.RED);
        displayMessage();
    }
    /**
     * 
     */
    public void displayErrorMessage() {
        messageLabel.setTextFill(Color.RED);
        displayMessage();
    }
    /**
     * 
     * @param message 
     */
    public void displaySuccessMessage(String message) {
        this.message = message;
        messageLabel.setTextFill(Color.GREEN);
        displayMessage();
    }
    /**
     * Custom thread for keeping track of the display time
     * to close the error message
     */
    private class CloseMessageTimer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(stayTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageScene.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (messagePane.isVisible()) {
                t.setFromValue(1.0);
                t.setToValue(0.0);
                t.play();
            }
        }
    }
}
