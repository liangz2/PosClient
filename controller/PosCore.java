/*
 * The class that runs the progrram
 */
package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.UI.MainPane;

/**
 *
 * @author Zhengyi
 */

public class PosCore extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        // views
        MainPane mainPane = new MainPane();
        // controller
        PosController controller = 
                new PosController(mainPane, primaryStage, root);
        controller.init();
        // Stage configuration
        primaryStage.getIcons().add(new Image("images/inventory_48x48.png"));
        primaryStage.setTitle("Point of Sale");
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setScene(scene);
        // show the Stage
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
