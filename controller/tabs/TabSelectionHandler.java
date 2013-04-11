/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import javafx.event.Event;
import javafx.event.EventHandler;
import view.UI.MainPane;
import view.basic.PosTab;

/**
 *
 * @author Icewill
 */
public class TabSelectionHandler implements EventHandler<Event> {
    private PosTab myTab;
    private MainPane mainPane;
    
    protected TabSelectionHandler(MainPane mainPane) {
        this.mainPane = mainPane;
        myTab = (PosTab) mainPane.getSelectedTab();
    }
    
    @Override
    public void handle(Event arg0) {
        mainPane.selectTab(myTab);
    }
}
