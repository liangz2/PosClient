/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import javafx.event.Event;
import view.UI.MainPane;
import view.basic.PosTab;

/**
 *
 * @author Icewill
 */
public class MenuCloseHandler extends CloseTabHandler {
    private PosTab currentTab;
    public MenuCloseHandler(MainPane mainPane) {
        super(mainPane);
    }
    @Override
    public void handle(Event arg0) {
        changeTabsClosable();
    }
}
