/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.UI;

import controller.scene.CriticalMessageScene;
import controller.scene.MessageScene;
import controller.tooltips.PosTooltipFactory;
import css.PosCssLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import model.product.Product;
import model.user.User;
import view.basic.PosTab;

/**
 *
 * @author Icewill
 */
public final class MainPane extends TabPane {
    private PosTab initTab;
    private NewTab newTab;
    private Inventory inventory;
    private Stage owner;
    private User user;
    private SelectionModel mySelectionModel;
    private MessageScene messageScene;
    private CriticalMessageScene criticalMessageScene;
    private boolean productsAreLoaded;
    private Product[] products;
    private PosTooltipFactory tooltipFactory;
    
    public MainPane() {
        super();
        setTabMinWidth(100);
        setStyle("-fx-border-color: #AAA;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 1px;");
    }
    /**
     * adding the elements to the panel
     */
    public void initLayout() {
        this.setSide(Side.TOP);
        this.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        
        initTab = new StartupTab("Welcome", this);
        
        newTab = new NewTab(this);
        this.getTabs().addAll(initTab, newTab);
        
        mySelectionModel = getSelectionModel();
        mySelectionModel.select(initTab);
        getStylesheets().add(PosCssLoader.getCssFile("posStyle.css"));
        setVisible(false);
    }
    
    public void setTooltipFactory(PosTooltipFactory tooltipFactory) {
        this.tooltipFactory = tooltipFactory;
    }
    /**
     * add the CloseEvent handler all the existing tabs
     * @param c 
     */
    public void addInitTabCloseHandler(EventHandler c) {
        initTab.setOnClosed(c);
    }
    
    public void addInitButtonHandler(EventHandler e) {
        initTab.addButtonHandler(e);
    }
    
    public void addInitTabSelectionHandler(EventHandler e) {
        initTab.setOnSelectionChanged(e);
    }
    
    public void addNewTabSelectionHandler(EventHandler e) {
        newTab.setOnSelectionChanged(e);
    }

    public NewTab getNewTab() {
        return newTab;
    }
    
    public Inventory getInventoryPane() {
        return inventory;
    }
    
    public void setInventoryPane(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public PosTab getSelectedTab() {
        return (PosTab) mySelectionModel.getSelectedItem();
    }
    
    public void selectTab(PosTab tab) {
        mySelectionModel.select(tab);
    }
    
    public void setOwner(final Stage owner) {
        this.owner = owner;
    }
    
    public Stage getOwner() {
        return owner;
    }
    
    public void setMsgScene(MessageScene messageScene) {
        this.messageScene = messageScene;
    }
    
    public void setCriticalMsgScene(CriticalMessageScene criticalMessageScene) {
        this.criticalMessageScene = criticalMessageScene;
    }
    
    public void setMessage(String message) {
        messageScene.setMessage(message);
    }
    
    public void setCriticalMessage(String message) {
        criticalMessageScene.setMessage(message);
    }
    
    public void setProducts(Product[] products) {
        this.products = products;
    }
    
    public void setProductsAreLoaded(boolean loaded) {
        productsAreLoaded = loaded;
    } 
    
    public void displayErrorMessage(String message) {
        if (message == null) {
            messageScene.displayErrorMessage();
        } else {
            messageScene.displayErrorMessage(message);
        }
    }
    
    public void displaySuccessMessage(String message) {
        messageScene.displaySuccessMessage(message);
    }
    
    public void displayCriticalErrorMessage(String message) {
        if (message == null) {
            criticalMessageScene.displayCriticalErrorMessage();
        } else {
            criticalMessageScene.displayCriticalErrorMessage(message);
        }
    }
    
    public void displayCriticalSuccessMessage(String message) {
        criticalMessageScene.displayCriticalSuccessMessage(message);
    }
    
    public void setLoginUser(User user) {
        this.user = user;
    }
    
    public User getLoginUser() {
        return user;
    }
    
    public Product[] getProductArray() {
        return products;
    }
    
    public boolean getProductsAreLoaded() {
        return productsAreLoaded;
    }
    
    public PosTooltipFactory getTooltipFactory() {
        return tooltipFactory;
    }
}
