/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.UI;

import controller.tabs.PosTabID;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.basic.PosImageButton;
import view.basic.PosTab;

/**
 *
 * @author Icewill
 */
public class StartupTab extends PosTab {
    private MenuPane menuPane;
    public StartupTab(String name, MainPane mainPane) {
        super(name, "menu", mainPane);
        init();
    }
    /**
     * initialization function
     */
    private void init() {
        // sales button
        PosImageButton salesButton = 
                buttonBuilder(PosTabID.SALES_ID, 
                "images/sales_100x100.png", "Sales");
        // inventory button
        PosImageButton inventoryMasterButton = 
                buttonBuilder(PosTabID.INVENTORY_MASTER_ID, 
                "images/inventory_100x100.png", "Inventory");
        // buttons
        PosImageButton settingButton = buttonBuilder("settings", "images/settings_100x100.png", "Settings");
//        Button t2 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t3 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t4 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t5 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t6 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t7 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
//        Button t8 = buttonBuilder("test2", "images/sales_48x48.png", "Test");
        // a tile pane that holds all buttons
        HBox salesRow = categoryBuilder("Sale/Work Order", salesButton);
        HBox inventoryRow = categoryBuilder("Inventory Master", inventoryMasterButton);
        HBox settingRow = categoryBuilder("Settings", settingButton);
        // the main panel
        menuPane = new MenuPane(Loading.getLoadingBackground(), Loading.getLoadingProgress());
        menuPane.getCategoryHolder().getChildren().
                addAll(salesRow, inventoryRow, settingRow);
        menuPane.addLoadingScreen();
        // add the panel to the tab
        this.setContent(menuPane);
    }

    /**
     * builds image buttons
     * @param id
     * @param imagePath
     * @param name
     * @return 
     */
    private PosImageButton buttonBuilder(String id, String imagePath, String name) {
        // get the image
        Image image = new Image(imagePath);
        // create a IamgeView
        ImageView view = new ImageView(image);
        // create button with name and image
        PosImageButton button = new PosImageButton(name, view);
        // set the id of the element
        button.setId(id);
        // add buttons to the arraylist;
        initButtons.add(button);
        return button;
    }
    
    /**
     * builds sub menus with given title
     * @param title
     * @param buttons
     * @return 
     */
    private HBox categoryBuilder(String title, final PosImageButton... b) {
        // create horizontal button box
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(b);
        // create horizaontal category with TitledPane
        HBox hBox = new HBox();
        TitledPane category = new TitledPane(title, buttonBox);
        category.setTextFill(Color.color(.5, .2, .1));
        category.setStyle("-fx-font-size:14px");
        // add the TtitledPane to another honrizontal box and return
        hBox.getChildren().add(category);
        return hBox;
    }
    
    public void startLoading() {
        menuPane.startLoading();
    }
    public void finishLoading() {
        menuPane.finishLoading();
    }
}
/**
 * 
 * @author Zhengyi
 */
class MenuPane extends StackPane {
    private VBox categoryHolder;
    private Region veil;
    private ProgressIndicator loading;
    public MenuPane(Region veil, ProgressIndicator loading) {
        super();
        categoryHolder = new VBox(10);
        categoryHolder.setPadding(new Insets(20));
        super.getChildren().add(categoryHolder);
        
        this.veil = veil;
        this.loading = loading;
    }
    
    protected VBox getCategoryHolder() {
        return categoryHolder;
    }
    
    protected void addLoadingScreen() {
        super.getChildren().addAll(veil, loading);
    }
    
    protected void startLoading() {
        veil.setVisible(true);
        loading.setVisible(true);
    }
    
    protected void finishLoading() {
        veil.setVisible(false);
        loading.setVisible(false);
    }
}
