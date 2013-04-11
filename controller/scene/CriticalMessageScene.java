/*
 * An animated message box that show up and fade out with the JavaFX provided
 * functionality
 */
package controller.scene;

import java.net.URL;
import java.util.ResourceBundle;
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
public class CriticalMessageScene extends PosScene {
    @FXML private StackPane messagePane;
    @FXML private Label criticalMessageLabel;
    private String message;
    private FadeTransition t;
    private Thread timer;
    private final int fadeTime = 500;
    private final int stayTime = 3000;
    
    public CriticalMessageScene(URL fxmlLocation) {
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
    private void displayCriticalMessage() {
        criticalMessageLabel.setText(message);
        messagePane.setVisible(true);
        t.setFromValue(0.0);
        t.setToValue(1.0);
        t.play();
        
        if (timer == null || !timer.isAlive()) {
            timer = new Thread(new CloseMessageTimer());
            timer.start();
        } else {
            timer.interrupt();
            timer = new Thread(new CloseMessageTimer());
            timer.start();
        }
    }
  
    /**
     * display critical error message
     * @param message 
     */
    public void displayCriticalErrorMessage(String message) {
        this.message = message;
        criticalMessageLabel.setTextFill(Color.RED);
        displayCriticalMessage();
    }
    public void displayCriticalErrorMessage() {
        criticalMessageLabel.setTextFill(Color.RED);
        displayCriticalMessage();
    }
    public void displayCriticalSuccessMessage(String message) {
        this.message = message;
        criticalMessageLabel.setTextFill(Color.WHITE);
        displayCriticalMessage();
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
                return;
            }
            if (messagePane.isVisible()) {
                t.setFromValue(1.0);
                t.setToValue(0.0);
                t.play();
            }
        }
    }
}
