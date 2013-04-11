/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import javafx.event.Event;
import view.UI.MainPane;

/**
 *
 * @author Icewill
 */
public class SalesCloseHandler extends CloseTabHandler {

    public SalesCloseHandler(MainPane mainPane) {
        super(mainPane);
    }
    @Override
    public void handle(Event arg0) {
        super.changeTabsClosable();
    }
}
