/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.scene;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public abstract class PosScene extends StackPane implements Initializable {
    protected FXMLLoader fxmlLoader;
    protected Task myTask;
    protected MainPane mainPane;
    protected String errorMessage = "";
    
    public PosScene(URL fxmlLocation, Task myTask, MainPane mainPane) {
        fxmlLoader = new FXMLLoader(fxmlLocation);
        this.myTask = myTask;
        this.mainPane = mainPane;
        startFXMLLoader();
    }
    
    /**
     * 
     * @param fxmlLocation
     * @param mainPane 
     */
    public PosScene(URL fxmlLocation, MainPane mainPane) {
        fxmlLoader = new FXMLLoader(fxmlLocation);
        this.mainPane = mainPane;
        startFXMLLoader();
    }
    
    /**
     * 
     * @param fxmlLocation 
     */
    public PosScene(URL fxmlLocation) {
        fxmlLoader = new FXMLLoader(fxmlLocation);
        startFXMLLoader();
    }
    
    /**
     * 
     */
    private void startFXMLLoader() {
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        // load the fxml with this controller
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(SalesScene.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    /**
     * 
     * @param path
     * @return 
     */
    protected final ImageView loadImage(String path) {
        Image image = new Image(path);
        ImageView view = new ImageView(image);
        
        return view;
    }
    
    /**
     * 
     * @param newThread 
     */
    public void loadData(Task newThread) {
        myTask = newThread;
        setVisible(true); 
    }
    
    /**
     * 
     */
    public void loadData() {
        setVisible(true);
    }
    
    /**
     * 
     * @return 
     */
    public final String getErrorMessage() {
        return errorMessage;
    }
}
