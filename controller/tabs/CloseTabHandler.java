/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import view.UI.MainPane;
import view.basic.PosTab;

/**
 *
 * @author Zhengyi
 */
public class CloseTabHandler implements EventHandler {
    protected MainPane mainPane;
    private ObservableList<Tab> tabs;
    private PosTab lastTab;
    
    protected CloseTabHandler(MainPane mainPane) {
        super();
        this.mainPane = mainPane;
    }
    
    protected void changeTabsClosable() {
        tabs = mainPane.getTabs();
        if (tabs.size() <= 2) {
            lastTab = (PosTab) tabs.get(0);
            if (lastTab.getId().equals(PosTabID.MENU_ID)) {
                lastTab.setClosable(false);
            }
            
            mainPane.selectTab(lastTab);
        }
    }

    @Override
    public void handle(Event arg0) {
        tabs = mainPane.getTabs();
        if (tabs.size() <= 2) {
            lastTab = (PosTab) tabs.get(0);
            if (lastTab.getId().equals(PosTabID.MENU_ID)) {
                lastTab.setClosable(false);
            }
            
            mainPane.selectTab(lastTab);
        }
    }
}
