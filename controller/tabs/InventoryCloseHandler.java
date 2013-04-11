/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tabs;

import javafx.event.Event;
import view.UI.Inventory;
import view.UI.MainPane;

/**
 *
 * @author Zhengyi
 */
public class InventoryCloseHandler extends CloseTabHandler {
    private Inventory inventory;
    public InventoryCloseHandler(MainPane mainPane) {
        super(mainPane);
        inventory = mainPane.getInventoryPane();
    }
    
    @Override
    public void handle(Event arg0) {
        changeTabsClosable();
    }
}
