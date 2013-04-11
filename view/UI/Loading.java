/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.UI;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;

/**
 *
 * @author Zhengyi
 */
public class Loading {
    private static Region screen;
    private static ProgressIndicator loading;
    
    public static Region getLoadingBackground() {
        screen = new Region();
        screen.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        screen.setVisible(false);

        return screen;
    }
    
    public static Region getLoginBackground() {
        screen = new Region();
        screen.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");

        return screen;
    }
    
    /**
     * 
     * @return 
     */
    public static ProgressIndicator getLoadingProgress() {
        loading = new ProgressIndicator();
        loading.setMaxSize(120, 120);
        loading.setVisible(false);
        
        return loading;
    }
}
