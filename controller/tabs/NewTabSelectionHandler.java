/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import controller.scene.InitSceneEventHandler;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import view.UI.MainPane;
import view.UI.StartupTab;

/**
 *
 * @author Icewill
 */
public class NewTabSelectionHandler implements EventHandler {
    private MainPane mainPane;
    private ObservableList<Tab> tabs;

    protected NewTabSelectionHandler(MainPane mainPane) {
        this.mainPane = mainPane;
    }
    
    /**
     * 
     * @param arg0 
     */
    @Override
    public void handle(Event arg0) {
        StartupTab newMenu = new StartupTab("New Tab", mainPane);
        newMenu.setId(PosTabID.MENU_ID);
        // add button ActionEvent to the new menu tab
        newMenu.addButtonHandler(new InitSceneEventHandler(mainPane));
        // add close hander to set tabs closable parameter
        newMenu.setOnClosed(TabHandlerFactory.buildGenericCloseHandler(mainPane));
        // get tabs and inster the new menu before the NewTab tab
        tabs = mainPane.getTabs();
        tabs.add(tabs.size() - 1, newMenu);
        // select the newly created tab
        mainPane.selectTab(newMenu);
        // if tab is not closable, change it
        if (tabs.size() > 2) {
            for (Tab tab: tabs) {
                if (tab.isClosable() == false) {
                    tab.setClosable(true);
                }
            }
        }
    }
}
